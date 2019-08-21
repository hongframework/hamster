package com.hframework.datacenter.hamster.worker

import java.io.File
import java.util.concurrent.{ArrayBlockingQueue, ConcurrentHashMap}

import com.hframe.hamster.common.AddressUtils
import com.hframe.hamster.node.HamsterConst
import com.hframework.common.frame.ServiceFactory
import com.hframework.datacenter.hamster.workes.bean.BatchDataSet
import com.hframework.utils.scala.{Logging, HamsterContextInitializer}
import com.hframework.datacenter.hamster.exceptions.TaskInitializationException
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta, JobNodeMetas}
import com.hframework.datacenter.hamster.monitor.zk.ZKNodeMonitor
import com.hframework.datacenter.hamster.monitor.zk.listeners.{ZKNodeListener, ZKNodeSimpleListener, ZKNodeWrapperListener}
import com.hframework.datacenter.hamster.worker.HamsterWorker.FlowArrayBlockingQueue
import com.hframework.datacenter.hamster.worker.tasks.{AbstractSelector, Task}
import com.hframework.datacenter.hamster.zookeeper.ZKPathUtils._
import com.hframework.datacenter.hamster.zookeeper.{ZKClient, ZKPathUtils}
import com.hframework.hamster.cfg.domain.model.CfgDeployment
import org.I0Itec.zkclient.exception.ZkNoNodeException
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.exception.ExceptionUtils

import scala.collection.JavaConverters._


object HamsterWorker extends Logging{
  var workerId: String = null

  var taskRunners = new ConcurrentHashMap[String, Task].asScala

  var nodeKeyAndSelectKeyMapping = new ConcurrentHashMap[String, DeployJobNodeMeta].asScala
  val flowQueueMaxSize = 1000
  type FlowArrayBlockingQueue = (DeployJobNodeMeta,JobNodeMeta,BatchDataSet)
  val flowQueue = new ArrayBlockingQueue[FlowArrayBlockingQueue](flowQueueMaxSize)

  def main(args: Array[String]): Unit = {
    try{
      logger.info("hamster spring context startup ..")
      ServiceFactory.initContext(HamsterContextInitializer.context)
      logger.info("hamster spring context startup ok ..")
      logger.info("hamster worker startup ..")
      val instance =  new HamsterWorker
      instance.start
      Runtime.getRuntime.addShutdownHook(new Thread() {
        override def run(): Unit = {
          logger.info("hamster worker shutdown ..")
          instance.shutdown()
          logger.info("hamster worker shutdown ok ..")
        }
      })
      logger.info("hamster worker startup ok ..")
      while(true) {
        logger.info(s"task runner dump: (${taskRunners.size}) ${taskRunners.keySet.toList.sorted.mkString("[\"", "\", \"", "\"]")}")
        Thread.sleep(1000 * 10L)
      }
    }catch{
      case e: Exception => logger.error(s"hamster worker startup error, ${ExceptionUtils.getFullStackTrace(e)}")
    }

  }

  def initTaskRunner(jobNodeMeta: JobNodeMeta, deployMeta: DeployJobNodeMeta, dependencyJobNode: List[JobNodeMeta] = Nil, terminator:Boolean = false): Option[(String, Task)] = {
    val executeMethod = jobNodeMeta.nodeMeta.executeMethod
    val executeUri = jobNodeMeta.nodeMeta.executeUri
    if("IGNORE_CLASS".equals(executeUri)) {
      return None
    }
    val taskRunnerClass = Class.forName(executeUri.trim)
    if(executeMethod == "java") {
      if(classOf[Task].isAssignableFrom(taskRunnerClass)) {
        deployMeta.deploymentInfo = ZKClient.readData(ZKPathUtils.getDeploymentConfigPath(deployMeta.deployCode), classOf[CfgDeployment])
        val cacheKey = deployMeta.deployCode + "." + deployMeta.nodeKey + (if(terminator) "_terminator" else "")
        val constructor = taskRunnerClass.getDeclaredConstructors.head
        val taskRunner = if(constructor.getParameterTypes.toList == List(classOf[String], classOf[DeployJobNodeMeta],
          classOf[JobNodeMeta], classOf[scala.List[JobNodeMeta]])) {//binlog terminator etc.
          (cacheKey, constructor.newInstance(deployMeta.deployCode + "/" +
            jobNodeMeta.jobMeta.selectKey, deployMeta, jobNodeMeta, dependencyJobNode))
        }else if(constructor.getParameterTypes.toList == List(classOf[String], classOf[DeployJobNodeMeta], classOf[JobNodeMeta])) {//extract tranform loader etc.
          (cacheKey, constructor.newInstance(deployMeta.deployCode +  "/" +
            jobNodeMeta.jobMeta.jobInfo.jobKey, deployMeta, jobNodeMeta))
        }else {
          throw new TaskInitializationException(s" ${executeUri} 's parameters is unknown !")
        }
        Some(taskRunner.asInstanceOf[(String, Task)])
      }else {
        logger.error(s"${executeUri} must assignable from Task.class !")
        throw new TaskInitializationException(s"${executeUri} must assignable from Task.class !")
      }
    }else {
      logger.warn(s"${executeUri} will run by ${executeMethod} !")
      None
    }
  }
}

class HamsterWorker extends Logging{

  def start: Unit ={
    flowBuffer.start()
    initAliveWorkerZK
//    groupMonitor.setup()
    jobsMonitor.setup()
    deploymentMonitor.setup()
  }
  val flowBuffer = new FlowBuffer(HamsterWorker.flowQueue)
  def initAliveWorkerZK: Unit = ZKClient.createEphemeral(ZKPathUtils.getAliveWorkerPath(getWorkerId), AddressUtils.getHostIp, true)

  def getWorkerId: String ={
    val workerId = System.getProperty(HamsterConst.NID_NAME)
    if (StringUtils.isBlank(workerId)) throw new RuntimeException("worker-id is blank !")
    logger.info("workerId = {}", workerId)
    HamsterWorker.workerId = workerId
    workerId
  }


  def shutdown(): Unit ={

  }

  def startFullJobRunner(selectNode: JobNodeMeta, extractJobNodes: List[JobNodeMeta], deployMeta:  DeployJobNodeMeta): Unit = {
    for (extractJobNode <- extractJobNodes) {
      val jobConfigPath = ZKPathUtils.getJobConfigPath(extractJobNode.jobMeta.jobTemplate.templateCode, extractJobNode.jobMeta.jobInfo.jobCode)
      val otherJobNodes = ZKClient.getChildren(jobConfigPath).asScala.map(path => {
        ZKClient.readData(jobConfigPath + "/" + path, classOf[JobNodeMeta])
      }).filterNot(jobNode => jobNode.nodeMeta.nodeKey == extractJobNode.nodeMeta.nodeKey)
      var sortedJobNodes = extractJobNode :: Nil
      for(i <- 1 to otherJobNodes.size) {
        val nextJobNodeOp = otherJobNodes.find(_.prevNodeMeta.nodeMeta.nodeKey == sortedJobNodes.head.nodeMeta.nodeKey)
        if(nextJobNodeOp.isDefined) {
          sortedJobNodes = nextJobNodeOp.get :: sortedJobNodes
        }else {
          logger.error(s"${sortedJobNodes.last.nodeMeta.nodeKey}'s next node is not find !")
        }
      }
      val cacheKey = deployMeta.deployCode + "." + deployMeta.nodeKey
      val taskRunner = new MultiTaskRunner(extractJobNode.jobMeta.jobInfo.jobKey, deployMeta, selectNode, sortedJobNodes.reverse)
      HamsterWorker.taskRunners.put(cacheKey, taskRunner)
      logger.info(s"[$cacheKey] task create and start !")
      taskRunner.start()
    }
  }



  def startJobNode(jobNodeMeta: JobNodeMeta, deployMeta: DeployJobNodeMeta, dependencyJobNode: List[JobNodeMeta] = Nil, terminator:Boolean = false): Unit = {
    if (deployMeta.workerId == HamsterWorker.workerId) {
      val taskRunnerOp = HamsterWorker.initTaskRunner(jobNodeMeta, deployMeta, dependencyJobNode, terminator)
      if(taskRunnerOp.isDefined) {
        val taskRunner = taskRunnerOp.get
        HamsterWorker.nodeKeyAndSelectKeyMapping.put(taskRunner._1, deployMeta)
        if(HamsterWorker.taskRunners.contains(taskRunner._1)) {
          logger.warn(s"[${taskRunner._1}] task exists ! can't create repeat !")
        }else {
          HamsterWorker.taskRunners.put(taskRunner._1, taskRunner._2)
          logger.info(s"[${taskRunner._1}] task create and start !")
          taskRunner._2.start()

          //每启动一个新的任务，重新加载一下对应的selector节点关联的extract节点信息
          val selectCacheKey = deployMeta.deployCode + "." + deployMeta.selectKey
          if(taskRunner._1 != selectCacheKey && !terminator) {//非selector与terminator节点
            if(HamsterWorker.taskRunners.contains(selectCacheKey)) {
              reloadDependencyJobNodes(deployMeta.selectKey, deployMeta.deployCode)
            }else {
              logger.info(s"[${taskRunner._1}] task start ,but it's selector not local")
            }
          }

        }
      }
//      val executeMethod = jobNodeMeta.nodeMeta.executeMethod
//      val executeUri = jobNodeMeta.nodeMeta.executeUri
//      val taskRunnerClass = Class.forName(executeUri.trim)
//      if(executeMethod == "java") {
//        if(classOf[Task].isAssignableFrom(taskRunnerClass)) {
//          deployMeta.deploymentInfo = ZKClient.readData(ZKPathUtils.getDeploymentConfigPath(deployMeta.deployCode), classOf[CfgDeployment])
//          val cacheKey = deployMeta.deployCode + "." + deployMeta.nodeKey + (if(terminator) "_terminator" else "")
//          HamsterWorker.nodeKeyAndSelectKeyMapping.put(cacheKey, deployMeta)
//          val constructor = taskRunnerClass.getDeclaredConstructors.head
//          val taskRunner = if(constructor.getParameterTypes.toList == List(classOf[String], classOf[DeployJobNodeMeta],
//            classOf[JobNodeMeta], classOf[scala.List[JobNodeMeta]])) {//binlog terminator etc.
//            (cacheKey -> constructor.newInstance(deployMeta.deployCode + "/" +
//              jobNodeMeta.jobMeta.selectKey, deployMeta, jobNodeMeta, dependencyJobNode))
//          }else if(constructor.getParameterTypes.toList == List(classOf[String], classOf[DeployJobNodeMeta], classOf[JobNodeMeta])) {//extract tranform loader etc.
//            (cacheKey -> constructor.newInstance(deployMeta.deployCode +  "/" +
//              jobNodeMeta.jobMeta.jobInfo.jobKey, deployMeta, jobNodeMeta))
//          }else {
//            throw new TaskInitializationException(s" ${executeUri} 's parameters is unknown !")
//          }
//          if(HamsterWorker.taskRunners.contains(taskRunner._1)) {
//            logger.warn(s"[${taskRunner._1}] task exists ! can't create repeat !")
//          }else {
//            HamsterWorker.taskRunners.put(taskRunner._1, taskRunner._2.asInstanceOf[Task])
//            logger.info(s"[${taskRunner._1}] task create and start !")
//            taskRunner._2.asInstanceOf[Task].start()
//
//           //每启动一个新的任务，重新加载一下对应的selector节点关联的extract节点信息
//           val selectCacheKey = deployMeta.deployCode + "." + deployMeta.selectKey
//            if(cacheKey != selectCacheKey && !terminator) {//非selector与terminator节点
//              if(HamsterWorker.taskRunners.contains(selectCacheKey)) {
//                reloadDependencyJobNodes(deployMeta.selectKey, deployMeta.deployCode)
//              }else {
//                logger.info(s"[${taskRunner._1}] task start ,but it's selector not local")
//              }
//            }
//
//          }
//
//        }else {
//          logger.error(s"${executeUri} must assignable from Task.class !")
//          throw new TaskInitializationException(s"${executeUri} must assignable from Task.class !")
//        }
//      }else {
//        logger.warn(s"${executeUri} will run by ${executeMethod} !")
//      }
    }
  }

  def stopJobNode(nodeKey: String, selector:Boolean = false, terminator:Boolean = false): Unit = {
    val taskRunner = HamsterWorker.taskRunners.get(nodeKey)
    if(taskRunner.isDefined) {
      logger.info(s"task [$nodeKey] find and stop !")
      taskRunner.get.interrupt()
      if(!terminator && !selector) {
        if(HamsterWorker.nodeKeyAndSelectKeyMapping.contains(nodeKey)) {
          val deploymentMeta = HamsterWorker.nodeKeyAndSelectKeyMapping.get(nodeKey).get
          reloadDependencyJobNodes(deploymentMeta.selectKey, deploymentMeta.deployCode)
        }else {
          throw new TaskInitializationException(s"$nodeKey not in " +
            s"${HamsterWorker.nodeKeyAndSelectKeyMapping.keySet.mkString("[", ", ", "]")}" )
        }
      }
    }else {
      logger.warn(s"task [$nodeKey] not find for stop !")
    }
    HamsterWorker.taskRunners.remove(nodeKey)

  }

  def stopJobNodeByMeta(jobNodeMeta: JobNodeMeta, deployMeta: DeployJobNodeMeta, terminator:Boolean = false): Unit = {
    val cacheKey = deployMeta.deployCode + "." + deployMeta.nodeKey + (if(terminator) "_terminator" else "")
    val taskRunner = HamsterWorker.taskRunners.get(cacheKey)
    if(taskRunner.isDefined) {
      taskRunner.get.interrupt()
      logger.info(s"task [$cacheKey] find and stop !")
      reloadDependencyJobNodes(deployMeta.selectKey, deployMeta.deployCode)
    }else {
      logger.warn(s"task [$cacheKey] not find for stop !")
    }
    HamsterWorker.taskRunners.remove(cacheKey)
  }

  @Deprecated
  /** 当job 对应的配置属性项发生变化时，对taskRunner的Meta属性进行重新加载*/
  def reloadMetaIfConfigChanged(jobNodeMeta: JobNodeMeta) = {
    val taskRunnerOpt = HamsterWorker.taskRunners.get(jobNodeMeta.nodeMeta.nodeKey)
    if(taskRunnerOpt.isEmpty) {
      logger.error(s"[${jobNodeMeta.nodeMeta.nodeKey}]task runner not exists, can not reload it !")
      throw new TaskInitializationException(s"[${jobNodeMeta.nodeMeta.nodeKey}]task runner not exists, can not reload it !")
    }
    val taskRunner = taskRunnerOpt.get
    val reloadMethod = taskRunner.getClass.getDeclaredMethod("reload", classOf[JobNodeMeta])
    reloadMethod.invoke(taskRunner, jobNodeMeta)
  }

  /** 当workerId分配变化时，启动或者停止对应taskRunner*/
  def startOrStopIfWorkerIdChanged(jobNodeMeta: JobNodeMeta, deployMeta: DeployJobNodeMeta, dependencyJobNode: List[JobNodeMeta] = Nil, terminator:Boolean = false): Boolean = {
    val cacheKey = deployMeta.deployCode + "." + deployMeta.nodeKey + (if(terminator) "_terminator" else "")
    if (deployMeta.workerId == HamsterWorker.workerId
      && HamsterWorker.taskRunners.get(cacheKey).isEmpty) {
      startJobNode(jobNodeMeta, deployMeta, dependencyJobNode, terminator)
      true
    }else if (deployMeta.workerId != HamsterWorker.workerId
      && HamsterWorker.taskRunners.get(cacheKey).isDefined) {
      stopJobNodeByMeta(jobNodeMeta, deployMeta,terminator)
      false
    }else {
      false
    }
  }

  def reloadJobNodeContent(jobNodeMeta: JobNodeMeta): Unit = {
    val aboutWorkers = HamsterWorker.taskRunners.filter(taskRunner => {
      taskRunner._1.endsWith(jobNodeMeta.nodeMeta.nodeKey)
    })
    logger.info(s"[${jobNodeMeta.nodeMeta.nodeKey}] reload workers : ${aboutWorkers.keys.mkString("[",",","]")}!")
    aboutWorkers.values.foreach(taskRunner => {
      val reloadMethod = taskRunner.getClass.getDeclaredMethod("reload", classOf[JobNodeMeta])
      reloadMethod.invoke(taskRunner, jobNodeMeta)
    })
  }

  def reloadJobNode(jobNodeMeta: JobNodeMeta, deployMeta: DeployJobNodeMeta, dependencyJobNode: List[JobNodeMeta] = Nil, terminator:Boolean = false): Unit = {
//    val isCreateNow =
      startOrStopIfWorkerIdChanged(jobNodeMeta, deployMeta, dependencyJobNode, terminator)
//    if(!isCreateNow && jobNodeMeta.nodeMeta.workerId == HamsterWorker.workerId) {//TODO 这里应该不需要了
//      reloadMetaIfConfigChanged(jobNodeMeta)
//    }
  }

  def reloadDependencyJobNodes(selectKey: String, deployCode: String): Unit ={
    val jobGroupConfigPath = ZKPathUtils.getJobGroupConfigPath(selectKey)
    try{
      val dependencyExtractJobNode = getDependencyJobNodes(jobGroupConfigPath, deployCode)
      val (selectNode, termNode) = getSelectorAndTerminator(jobGroupConfigPath)
      reloadDependencyJobNodes(deployCode + "." + selectNode.nodeMeta.nodeKey, dependencyExtractJobNode)
      reloadDependencyJobNodes(deployCode + "." + termNode.nodeMeta.nodeKey, dependencyExtractJobNode)
    }catch {
      case _: ZkNoNodeException => logger.info(s"reload dependency error => ${ExceptionUtils.getFullStackTrace(_)}")
    }
  }

//  @Deprecated
//  def reloadDependencyJobNodes(path: String): Unit ={
//    try{
//      val dependencyExtractJobNode = getDependencyJobNodes(path, null)
//      val (selectNode, termNode) = getSelectorAndTerminator(path)
//      reloadDependencyJobNodes(selectNode, dependencyExtractJobNode)
//      reloadDependencyJobNodes(termNode, dependencyExtractJobNode)
//    }catch {
//      case e: ZkNoNodeException => {
//        /*该场景发生为停止JobGroup时，先删除JobGroup下关联的所有的Job明细，再删除JobGroup；在删除关联Job明细时，
//          触发ChildrenChange事件，由于是异步执行，所以查询DependencyJobNodes时JobGroup已经被删除，将会报
//          ZkNoNodeException错误，这种情况无需处理，因为nodeDelete事件会直接处理JobGroup的删除*/
//        logger.info(s"$path children change ignore, because ${e.getMessage}")
//      }
//    }
//  }

  def reloadDependencyJobNodes(cacheKey: String, dependencyJobNode: List[JobNodeMeta] = Nil): Unit = {
    if(HamsterWorker.taskRunners.contains(cacheKey)) {
      logger.info(s"[${cacheKey}] reload dependency nodes, ${dependencyJobNode.map(_.nodeMeta.nodeKey).mkString("[", ",", "]")} !")
      val taskRunner = HamsterWorker.taskRunners.get(cacheKey).get
      val reloadMethod = taskRunner.getClass.getDeclaredMethod("reload", classOf[scala.List[JobNodeMeta]])
      reloadMethod.invoke(taskRunner, dependencyJobNode)
    }else {
      logger.warn(s"[${cacheKey}] update dependency node , but it 's not exists !")
    }
  }

//  @Deprecated
//  /** 系统刚启动加载时候，也走该分支 */
//  def reloadDependencyJobNodes(jobNodeMeta: JobNodeMeta, dependencyJobNode: List[JobNodeMeta] = Nil): Unit = {
//    val aboutWorkers = HamsterWorker.taskRunners.filter(taskRunner => {
//      taskRunner._1.endsWith(jobNodeMeta.nodeMeta.nodeKey)
//    }).values
//    aboutWorkers.foreach(taskRunner => {
//      val reloadMethod = taskRunner.getClass.getDeclaredMethod("reload", classOf[scala.List[JobNodeMeta]])
//      reloadMethod.invoke(taskRunner, dependencyJobNode)
//    })
//  }


//  val groupMonitor = new ZKNodeMonitor("jGroup_configs",getJobGroupsConfigRootPath)
//  groupMonitor.addListener(new ZKNodeWrapperListener("jGroup_configs"){//jgroup root
//    override def getParentListener: ZKNodeListener = new ZKNodeSimpleListener {//jgroup instance
//      override def nodeCreate(paths: List[String]): Unit = {
////        paths.foreach(path => {
////          val dependencyExtractJobNode = getDependencyJobNodes(path)
////          val (selectNode, termNode) = getSelectorAndTerminator(path)
////          startJobNode(selectNode, dependencyExtractJobNode)
////          startJobNode(termNode, dependencyExtractJobNode, true)
////        })
//      }
//      override def nodeDelete(path: String): Unit = {
////        val nodeKey = path.substring(ZKPathUtils.getJobGroupsConfigRootPath.length + 1).replaceAll("/",".")
////        logger.info(s"shutdown task [$nodeKey], because path [$path] deleted !")
//////        val (selectNode, termNode) = getSelectorAndTerminator(path)
////        stopJobNode(nodeKey)//selectNode
////        stopJobNode(nodeKey + "_terminator")//termNode
//      }
//
////      /** 这种场景原则上很少发生，发生的唯一可能就是workerId发生了变化，进行任务重分配 */
//      override def nodeDataChange(path: String): Unit = {
////        logger.warn(s"[${path}] data change is not expired !")
////        val dependencyExtractJobNode = getDependencyJobNodes(path)
////        val (selectNode, termNode) = getSelectorAndTerminator(path)
////        reloadJobNode(selectNode, dependencyExtractJobNode)
////        reloadJobNode(termNode, dependencyExtractJobNode)
//      }
//
//      /** 同一个canal 新增加新的表订阅，触发该场景，重要！！！ */
//      override def nodeChildrenChange(path: String, children: scala.List[String] = Nil): Unit = {
////        reloadDependencyJobNodes(path)
//      }
//    }
//  })

  def getSelectorAndTerminator(path: String): (JobNodeMeta, JobNodeMeta) = {
    val selectAndTermNode = ZKClient.readData(path, classOf[JobNodeMetas])
    val selectNode = selectAndTermNode.jobNodeMetas.head
    val termNode = selectAndTermNode.jobNodeMetas.last
    (selectNode, termNode)
  }

  def getDependencyJobNodes(path: String, deployCode: String): List[JobNodeMeta] = {
    val dependencyExtractIds = ZKClient.getChildren(path).asScala.toList
    val dependencyExtractJobNode = dependencyExtractIds.map(extractId => {
      val extractInfo = extractId.split('.')
      val extractPath = ZKPathUtils.getJobNodeConfigPath(extractInfo(0),extractInfo(1),extractInfo(2))
      ZKClient.readData(extractPath, classOf[JobNodeMeta])
    })

    val curDeploymentDependencyExtractJobNode = dependencyExtractJobNode.filter(extractNode => {
      val tempPath = ZKPathUtils.getDeploymentJobConfigPath(deployCode, extractNode.nodeMeta.nodeKey)
      if(ZKClient.exists(tempPath)) {
        val tempDeployMeta = ZKClient.readData(tempPath, classOf[DeployJobNodeMeta])
        tempDeployMeta.workerId == HamsterWorker.workerId
      }else {
        false
      }
    })

    curDeploymentDependencyExtractJobNode
  }

  val jobsMonitor = new ZKNodeMonitor("config_logs", getJobsConfigRootPath)//job_configs
  jobsMonitor.addListener(new ZKNodeWrapperListener("config_logs") {//jobtemplate
    override def getParentListener: ZKNodeListener = new ZKNodeWrapperListener("config_logs") {//jobInfo
      override def getParentListener: ZKNodeListener = new ZKNodeWrapperListener("config_logs") {//jobNode
        override def getParentListener: ZKNodeListener = new ZKNodeSimpleListener {//
          override def nodeCreate(paths: List[String]): Unit = {
//            logger.info(s"create job node task ${paths.mkString("[ ", ", ", " ]")}")
//            paths.foreach(path => {
//              val jobNode = ZKClient.readData(path, classOf[JobNodeMeta])
//              startJobNode(jobNode)
//            })
          }

          override def nodeDelete(path: String): Unit = {
//            val nodeKey = path.substring(ZKPathUtils.getJobsConfigRootPath.length + 1).replaceAll("/",".")
//            logger.info(s"shutdown task [$nodeKey], because path [$path] deleted !")
//            stopJobNode(nodeKey)
          }

          override def nodeDataChange(path: String): Unit = {
            logger.info(s"task [$path] reload !")
            val jobNode = ZKClient.readData(path, classOf[JobNodeMeta])
            logger.info(s"[${jobNode.nodeMeta.nodeKey}] reload !")
            reloadJobNodeContent(jobNode)
          }
        }
      }
    }
  })

  val deploymentMonitor =  new ZKNodeMonitor("config_logs", getDeploymentConfigRootPath)//deploy_configs
  deploymentMonitor.addListener(new ZKNodeWrapperListener("config_logs") {//deploy_config
  override def getParentListener: ZKNodeListener = new ZKNodeWrapperListener("config_logs") {//jobNode
  override def getParentListener: ZKNodeListener = new ZKNodeSimpleListener {
  override def nodeCreate(paths: List[String]): Unit = {
    logger.info(s"create job tasks runner : ${paths.mkString("[ ", ", ", " ]")}")
    //优先启动任务处理节点，再启动selector节点
    val sortedPaths = paths.filterNot(_.contains("_select_term.")).++(paths.filter(_.contains("_select_term.")))
    sortedPaths.foreach(deploymentPath => {
      val deployMeta = ZKClient.readData(deploymentPath, classOf[DeployJobNodeMeta])
      if(deployMeta.deployType == "normal") {

      }
      deployMeta.deploymentInfo = ZKClient.readData(deploymentPath.substring(0, deploymentPath.lastIndexOf("/")), classOf[CfgDeployment])
      if (deployMeta.workerId == HamsterWorker.workerId) {
        if(deployMeta.deployType == "normal") {
          if(deployMeta.jobKey == null) {//jobgroup -> selector
            val jobGroupConfigPath = ZKPathUtils.getJobGroupsConfigRootPath + "/" + deployMeta.selectKey
            val dependencyExtractJobNode = getDependencyJobNodes(jobGroupConfigPath, deployMeta.deployCode)
            val (selectNode, termNode) = getSelectorAndTerminator(jobGroupConfigPath)
            if(selectNode != null && selectNode.nodeMeta != null) {
              startJobNode(selectNode, deployMeta, dependencyExtractJobNode )
            }
            if(termNode != null && termNode.nodeMeta != null){
              startJobNode(termNode, deployMeta, dependencyExtractJobNode, true)
            }

          }else {//jobNode
            val jobNodeConfigPath = ZKPathUtils.getJobsConfigRootPath + "/" + deployMeta.nodeKey.replaceAll("\\.", "/")
            val jobNode = ZKClient.readData(jobNodeConfigPath, classOf[JobNodeMeta])
            startJobNode(jobNode, deployMeta)
          }
        }else {
          if(deployMeta.jobKey == null) {
            val jobGroupConfigPath = ZKPathUtils.getJobGroupsConfigRootPath + "/" + deployMeta.selectKey
            val dependencyExtractJobNode = getDependencyJobNodes(jobGroupConfigPath, deployMeta.deployCode)
            val selectNode = getSelectorAndTerminator(jobGroupConfigPath)._1
            startFullJobRunner(selectNode, dependencyExtractJobNode, deployMeta)
          }
        }
      }else {
        logger.warn(s"task[$deploymentPath] 's worker is ${deployMeta.workerId}, not localhost !")
      }
    })
  }

    override def nodeDelete(deploymentPath: String): Unit = {
      val nodeKey = deploymentPath.substring(ZKPathUtils.getDeploymentConfigRootPath.length + 1).replaceAll("/",".").replace("._select_term.",".")
      if(deploymentPath.split("/").last.startsWith("_select_term.")){
        logger.info(s"shutdown task [$nodeKey], because path [$deploymentPath] deleted !")
        stopJobNode(nodeKey, selector = true)//selectNode
        stopJobNode(nodeKey + "_terminator", terminator = true)//termNode
      }else {
        logger.info(s"shutdown task [$nodeKey], because path [$deploymentPath] deleted !")
        stopJobNode(nodeKey)
      }
    }

    override def nodeDataChange(deploymentPath: String): Unit = {
      val deployMeta = ZKClient.readData(deploymentPath, classOf[DeployJobNodeMeta])
      logger.info(s"task [$deploymentPath] reload !")
      if(deployMeta.deployType == "normal") {
        if(deployMeta.jobKey == null) {//jobgroup -> selector
        val jobGroupConfigPath = ZKPathUtils.getJobGroupsConfigRootPath + "/" + deployMeta.selectKey
          val dependencyExtractJobNode = getDependencyJobNodes(jobGroupConfigPath, deployMeta.deployCode)
          val (selectNode, termNode) = getSelectorAndTerminator(jobGroupConfigPath)
          reloadJobNode(selectNode, deployMeta, dependencyExtractJobNode)
          reloadJobNode(termNode, deployMeta, dependencyExtractJobNode, true)
        }else {//jobNode
        val jobNodeConfigPath = ZKPathUtils.getJobsConfigRootPath + "/" + deployMeta.nodeKey.replaceAll("\\.", "/")
          val jobNode = ZKClient.readData(jobNodeConfigPath, classOf[JobNodeMeta])
          reloadJobNode(jobNode, deployMeta)
        }
      }
    }
  }
  }
  })
}