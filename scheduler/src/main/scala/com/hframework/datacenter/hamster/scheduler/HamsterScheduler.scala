package com.hframework.datacenter.hamster.scheduler

import java.util
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

import com.hframework.common.frame.ServiceFactory
import com.hframework.common.monitor.Node.OperateType
import com.hframework.utils.scala.{Logging, HamsterContextInitializer}
import com.hframework.datacenter.hamster.monitor.db.JobRegistry.{DeploymentND, JobInfo, WorkerND}
import com.hframework.datacenter.hamster.monitor.db._
import com.hframework.datacenter.hamster.monitor.zk.ZKChildrenMonitor
import com.hframework.datacenter.hamster.monitor.zk.listeners.ZKChildrenListener
import com.hframework.datacenter.hamster.zookeeper.ZKPathUtils._
import com.hframework.datacenter.hamster.zookeeper.{ZKClient, ZKPathUtils}
import com.hframework.hamster.cfg.domain.model.{CfgDeployment, CfgNode}
import org.apache.commons.lang.exception.ExceptionUtils
import org.apache.commons.lang3.StringUtils

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.util.Random

object HamsterScheduler extends JobListener with Logging{

  def main(args: Array[String]): Unit = {
    try{
      logger.info("hamster spring context startup ..")
      ServiceFactory.initContext(HamsterContextInitializer.context)
      logger.info("hamster spring context startup ok ..")
      val instance =  new HamsterScheduler
      logger.info("hamster scheduler startup ..")
      instance.start
      Runtime.getRuntime.addShutdownHook(new Thread() {
        override def run(): Unit = {
          logger.info("hamster scheduler shutdown ..")
          instance.shutdown()
          logger.info("hamster scheduler shutdown ok ..")
        }
      })
      logger.info("hamster scheduler startup ok ..")
      while(true)
        Thread.sleep(1000 * 10L)
    }catch{
      case e: Exception => logger.error(s"hamster scheduler startup error, ${ExceptionUtils.getFullStackTrace(e)}")
    }

  }
}


class HamsterScheduler extends JobListener with ZKChildrenListener with Logging{
  val jobRegistry = JobRegistry
  jobRegistry.addListener(this)

  val zkMonitor = new ZKChildrenMonitor("scheduler", ZKPathUtils.getAliveWorkerRootPath)
  zkMonitor.addListener(this)

  val bootstrapInvoke = new AtomicBoolean(true)
  val zkConfigPathCheckLists = new util.ArrayList[String]

  def start: Unit ={
    initZK
    jobRegistry.start
    zkMonitor.setup
  }

  def initZK: Unit ={
    ZKClient.createPersistentIfNotExists(ZKPathUtils.getJobsConfigRootPath, createParents = true)
    ZKClient.createPersistentIfNotExists(ZKPathUtils.getJobGroupsConfigRootPath, createParents = true)
    ZKClient.createPersistentIfNotExists(ZKPathUtils.getDeploymentConfigRootPath, createParents = true)
    ZKClient.createPersistentIfNotExists(ZKPathUtils.getConfigWorkerRootPath, createParents = true)
    ZKClient.createPersistentIfNotExists(ZKPathUtils.getAliveWorkerRootPath, createParents = true)
    val jGroups = ZKClient.getChildren(ZKPathUtils.getJobGroupsConfigRootPath).asScala
    for(groupCode <- jGroups) {
      val groupPath = ZKPathUtils.getJobGroupConfigPath(groupCode)
      ZKClient.getChildren(groupPath).asScala.foreach(groupJob =>
        zkConfigPathCheckLists.add(groupPath + "/" + groupJob))
      zkConfigPathCheckLists.add(groupPath)
    }
    val jobTemplates = ZKClient.getChildren(ZKPathUtils.getJobsConfigRootPath)
    jobTemplates.asScala.foreach(jobTemplate => {
      val jobTemplatePath = ZKPathUtils.getJobsConfigRootPath + "/" + jobTemplate
      val jobs = ZKClient.getChildren(jobTemplatePath).asScala
      for(jobCode <- jobs) {
        val jobPath = jobTemplatePath + "/" + jobCode
        ZKClient.getChildren(jobPath).asScala.foreach(taskNode =>
          zkConfigPathCheckLists.add(jobPath + "/" + taskNode))
        zkConfigPathCheckLists.add(jobPath)
      }
      zkConfigPathCheckLists.add(jobTemplatePath)
    })

    val deployments = ZKClient.getChildren(ZKPathUtils.getDeploymentConfigRootPath)
    deployments.asScala.foreach(deployment => {
      val deploymentPath = ZKPathUtils.getDeploymentConfigRootPath + "/" + deployment
      ZKClient.getChildren(deploymentPath).asScala.foreach(detail => {
        zkConfigPathCheckLists.add(deploymentPath + "/" + detail)
      })
      zkConfigPathCheckLists.add(deploymentPath)
    })
    ZKClient.getChildren(ZKPathUtils.getConfigWorkerRootPath).asScala.foreach(worker => {
      zkConfigPathCheckLists.add(ZKPathUtils.getConfigWorkerRootPath + "/" + worker)
    })
  }

  def shutdown(): Unit ={
    jobRegistry.destroy
    zkMonitor.shutdown
  }

  override def processJobChanges(changeJobs: Map[String, JobRegistry.JobInfo], aboutWorkers: mutable.Buffer[WorkerMeta],
                                 aboutDeployments: mutable.Buffer[DeploymentND],
                                 aboutDeploymentDetails: mutable.Buffer[DeployJobNodeMeta] ): Unit = try{
    changeJobs.foreach(job => {
      val jobInfo = job._2
      val jobConfigPath = getJobConfigPath(jobInfo.templateCode, jobInfo.jobCode)
      jobInfo.operate match {
        case JobRegistry.add | JobRegistry.modify => {
          ZKClient.createPersistentIfNotExists(jobConfigPath, createParents = true)
          zkConfigPathCheckLists.remove(jobConfigPath)
          jobInfo.jobNodes.foreach(node => {
            val jobNodeConfigPath = ZKPathUtils.getJobNodeConfigPath(jobInfo.templateCode, jobInfo.jobCode, node.nodeCode)
            val jobNodeMeta = node.jobNodeContainerMeta
            val executeUrl = jobNodeMeta.nodeMeta.executeUri.split(";").head.trim
            //对于Mysql-sql循环执行Executor，由于只配置了Selector，并没有有效的Extractor，因此这里将Extractor的executeUrl赋值IGNORE_CLASS，Worker做忽略处理
            if(StringUtils.isBlank(executeUrl)) {
              jobNodeMeta.nodeMeta.executeUri = "IGNORE_CLASS"
            }else {
              jobNodeMeta.nodeMeta.executeUri = executeUrl
            }
            ZKClient.createOrUpdatePersistent(jobNodeConfigPath, jobNodeMeta, createParents = true)
            zkConfigPathCheckLists.remove(jobNodeConfigPath)
          })
        }
        case JobRegistry.delete => ZKClient.delete(jobConfigPath, true)
      }

      val jobGroupPath = ZKPathUtils.getJobGroupConfigPath(jobInfo.selectKey)
      jobInfo.operate match {
        case JobRegistry.add | JobRegistry.modify => {
          if(jobInfo.extractNode.isDefined) {
            val extractNodeMeta = jobInfo.extractNode.get.jobNodeContainerMeta.nodeMeta
            val extractJobMeta = jobInfo.extractNode.get.jobNodeContainerMeta.jobMeta
            val uris = extractNodeMeta.executeUri.split(";", 5)
            if(uris.length < 3) {
              throw new RuntimeException(s"node executeUri must has 3 class(${extractNodeMeta.executeUri}) => ${jobInfo.extractNode}")
            }
            val selectorRows = extractNodeMeta.rows.map(row => row.filterKeys(attrCode => {
              extractNodeMeta.dataSourceType match {
                case "binlog" => attrCode == "sourceDB"
                case "mysql" => attrCode == "sourceDB" || attrCode == "sourceTable" ||
                  attrCode == "scanInterval" || attrCode == "batchSize" || attrCode == "dataScanScope" || attrCode == "scanTimeField"
                case "kafka" => attrCode == "sourceKafka" || attrCode == "sourceTopic" || attrCode == "timeColumn" || attrCode == "messageType"
                case "hbase" => attrCode == "sourceHBase" || attrCode == "sourceTable"
                case "redis" => attrCode == "sourceRedis" || attrCode == "sourceKey"
                case "other" => true
//                case _ => new RuntimeException("暂不支持该数据源!")
              }
            }))
            val selectorClass = if(uris.size > 1) uris(1) else uris.head
            val terminatorClass = if(uris.size > 2) uris(2) else uris.last
            val selector = if(StringUtils.isNotBlank(selectorClass))
              NodeMeta(null, null, extractJobMeta.selectKey, "selector", extractNodeMeta.dataSourceType,
              extractNodeMeta.executeMethod, selectorClass, selectorRows) else null
            val terminator = if(StringUtils.isNotBlank(terminatorClass))
              NodeMeta(null, null, extractJobMeta.selectKey + "_terminator", "terminator", extractNodeMeta.dataSourceType,
              extractNodeMeta.executeMethod, terminatorClass, selectorRows) else null
            val metaData = JobNodeMetas(List(JobNodeMeta(JobContainerMeta(null,null,null,null, extractJobMeta.selectKey), selector),
              JobNodeMeta(JobContainerMeta(null,null,null,null, extractJobMeta.selectKey), terminator)))
            ZKClient.createOrUpdatePersistent(jobGroupPath, metaData, createParents = true)
            zkConfigPathCheckLists.remove(jobGroupPath)
            val jobGroupJobPath = ZKPathUtils.getJobGroupJobConfigPath(jobInfo.selectKey, jobInfo.extractNode.get.nodeKey)
            ZKClient.createPersistentIfNotExists(jobGroupJobPath, createParents = true)
            zkConfigPathCheckLists.remove(jobGroupJobPath)

          }else {
            logger.error(s"${jobInfo.jobKey} has not extract node !")
          }
        }
        case JobRegistry.delete => {
          if(jobInfo.extractNode.isDefined) {
            val jobGroupJobPath = ZKPathUtils.getJobGroupJobConfigPath(jobInfo.selectKey, jobInfo.extractNode.get.nodeKey)
            ZKClient.delete(jobGroupJobPath)
          }
          ZKClient.tryDelete(jobGroupPath)
        }
      }
    })

    aboutWorkers.foreach(meta => {
      val workerPath = ZKPathUtils.getConfigWorkerPath(meta.workerId)
      meta.operateType match {
        case OperateType.add | OperateType.modify => {
          configWorkers += (meta.workerId -> meta)
          ZKClient.createOrUpdatePersistent(workerPath, meta, createParents = true)
          zkConfigPathCheckLists.remove(workerPath)
        }
        case OperateType.delete => {
          ZKClient.delete(workerPath)
        }
      }
    })

    aboutDeployments.foreach(deploymentND => {
      val deployment = deploymentND.getObject
      val deployKey = deployment.getId.toString.concat("_").concat(deployment.getCode)
      val deploymentPath = ZKPathUtils.getDeploymentConfigPath(deployKey)
      deploymentND.getOperateType match {
        case OperateType.add | OperateType.modify => {
          deployments += (deployKey -> deployment)
          ZKClient.createOrUpdatePersistent(deploymentPath, deployment, createParents = true)
          zkConfigPathCheckLists.remove(deploymentPath)
        }
        case OperateType.delete => {
          deployments -= deployKey
          ZKClient.delete(deploymentPath, true)
        }
      }
    })

    //1.优先删除将会无Extract节点的Selector节点（这里主要是防止hamster-worker空跑selector节点,更改游标位置，导致下次启动时丢失数据）
    val deleteDeploymentDetails = aboutDeploymentDetails.filter(_.operate == OperateType.delete)
    val planDeleteNodeKeys = deleteDeploymentDetails.map(detail => detail.deployCode + "." + detail.nodeKey)
    val deleteSelectorPaths = deleteDeploymentDetails.map(meta => {
      val deploySelectorPath = ZKPathUtils.getDeploymentJobConfigPath(meta.deployCode, "_select_term." + meta.selectKey)
      (deploySelectorPath, meta)
    }).distinct
    deleteSelectorPaths.foreach(delete => {
      val deploySelectorPath = delete._1
      val meta = delete._2
      val nodes = ZKClient.getChildren(ZKPathUtils.getDeploymentConfigPath(meta.deployCode))
      val stillExistsNodeKeys =  nodes.asScala
        .filterNot(_.startsWith("_select_term."))
        .filterNot(nodeKey => planDeleteNodeKeys.contains(meta.deployCode + "." + nodeKey))
      val stillExistsSameSelectKeyNodeKeysOp = stillExistsNodeKeys.find(node => {
        val jobNodePath = ZKPathUtils.getDeploymentJobConfigPath(meta.deployCode, node)
        val nodeMeta = ZKClient.readData(jobNodePath, classOf[DeployJobNodeMeta])
        nodeMeta.selectKey == meta.selectKey
      })
      if(stillExistsSameSelectKeyNodeKeysOp.isEmpty){
        ZKClient.delete(deploySelectorPath, true)
      }
    })
    //2. 再次删除除Selector节点外的业务节点
    aboutDeploymentDetails.foreach(meta => {
      val deployJobPath = ZKPathUtils.getDeploymentJobConfigPath(meta.deployCode, meta.nodeKey)
      val deploySelectorPath = ZKPathUtils.getDeploymentJobConfigPath(meta.deployCode, "_select_term." + meta.selectKey)

      meta.operate match {
        case OperateType.add | OperateType.modify => {
          ZKClient.createOrUpdatePersistent(deploySelectorPath, DeployJobNodeMeta(meta.deployCode, null, meta.selectKey,
            meta.selectKey, meta.deployType, meta.masterLabels, meta.standbyLabels, null), createParents = true)
          ZKClient.createOrUpdatePersistent(deployJobPath, meta, createParents = true)
          zkConfigPathCheckLists.remove(deployJobPath)
          zkConfigPathCheckLists.remove(deploySelectorPath)
        }
        case OperateType.delete => {
          ZKClient.delete(deployJobPath, true)
        }
      }
    })


    if(bootstrapInvoke.getAndSet(false)){
      zkConfigPathCheckLists.asScala.foreach(path => {
        try{
          logger.info(s"[$path] bootstrap remove !")
          ZKClient.delete(path)
        }catch {
          case e => logger.info(s"[$path] bootstrap remove failed ! ${e.getMessage}")
        }
      })
    }
    re_balance
    logger.info("process change deal finish !")
  }catch {
    case e: Exception => logger.error(s"job change process failed => ${ExceptionUtils.getFullStackTrace(e)}")
  }

  override def childrenChange(add: List[String], del: List[String], all: List[String], versionNo: String = ""): Unit = {
    add.foreach(workerId => {
      //TODO 需要判断configWorkers对应的worker配置是否与注册的worker信息一致，另外aboutWorkers变化时也需要更改该配置信息
      aliveWorkers.put(workerId, true)
    })
    del.foreach(workerId => {
      aliveWorkers.remove(workerId)
    })
    re_balance
  }


  def checkWorkerId(workerId: String, hostIp: String): Boolean = {
    val workerConfigPath = getConfigWorkerPath(workerId)
    if(ZKClient.exists(workerConfigPath)) {
      val data = ZKClient.readData(getConfigWorkerRootPath.concat(workerId), classOf[CfgNode])
      if(!hostIp.eq(data.getIp)) {
        logger.error(s"worker ip is not right, config ip is ${data.getIp}, host ip is ${hostIp}")
        throw new RuntimeException(s"worker ip is not right, config ip is ${data.getIp}, host ip is ${hostIp}")
      }else {
        return true
      }
    }
    false
  }

  @Deprecated
  def trySetSelectAndTermNodeWorkerId(selectKey: String, workerId: String, jobInfo: JobInfo): Unit = {
    val path = getJobGroupConfigPath(selectKey)
    val selectAndTermNode = ZKClient.readData(path, classOf[JobNodeMetas])
    val selectNode = selectAndTermNode.jobNodeMetas.head
    val termNode = selectAndTermNode.jobNodeMetas.last
    if(selectNode.nodeMeta.workerId != workerId) {
      selectNode.nodeMeta.workerId = workerId
      termNode.nodeMeta.workerId = workerId
      ZKClient.writeData(path, selectAndTermNode)

      val extractNode = jobInfo.extractNode.get
      //          extractNode.workerId = Option.apply(nextId)
      val jobNodeConfigPath = ZKPathUtils.getJobNodeConfigPath(jobInfo.templateCode, jobInfo.jobCode, extractNode.nodeCode)
      val data = ZKClient.readData(jobNodeConfigPath, classOf[JobNodeMeta])
      data.nodeMeta.workerId = workerId
      ZKClient.writeData(jobNodeConfigPath, data)
    }
  }

  /**
    * 资源分配
    */
  def re_balance(): Unit ={
    val globalActAliveWorkers = aliveWorkers.asScala.filter(_._2).keySet.toList
    if(globalActAliveWorkers.isEmpty) {
      logger.warn("alive workers is empty !")
      return
    }else {
      logger.info(s"alive workers = ${globalActAliveWorkers.mkString("[ ", ",", " ]")}")
    }
    val aliveWorkerLabels = aliveWorkers.keys().asScala.map(workerId => {
      val meta = ZKClient.readData(ZKPathUtils.getConfigWorkerPath(workerId), classOf[WorkerMeta])
      workerId -> meta.labels
    }).toMap
    logger.info(s"alive workers labels= ${aliveWorkerLabels.mkString("[ ", ",", " ]")}")

    val deployments = ZKClient.getChildren(ZKPathUtils.getDeploymentConfigRootPath).asScala
    import scala.util.control.Breaks._
    for(deploymentCode <- deployments) {
      breakable{
        val deploymentDetails = ZKClient.getChildren(ZKPathUtils.getDeploymentConfigPath(deploymentCode)).asScala
        val selectorKeys = deploymentDetails.filter(_.startsWith("_select_term.")).map(_.substring("_select_term.".length))
        val jobNodes = deploymentDetails.filterNot(_.startsWith("_select_term."))

        //[重要]这里需要先写业务处理节点，再写Selector节点，这样尽量保证worker先启动业务处理节点，在启动Selector节点
        var selectWrites = Map.empty[String, DeployJobNodeMeta]
        for (selectorKey <- selectorKeys) {
          val selectPath = ZKPathUtils.getDeploymentJobConfigPath(deploymentCode, "_select_term." + selectorKey)
          val selectMeta = ZKClient.readData(selectPath, classOf[DeployJobNodeMeta])

          val curActAliveWorkers = aliveWorkerLabels.filter(_._2.exists(selectMeta.masterLabels.contains(_))).keySet
          if(curActAliveWorkers.isEmpty) {
            logger.warn(s"[deploy = $deploymentCode, selector = ${selectorKey}, " +
              s"master-label = ${selectMeta.masterLabels.mkString("[",", ", "]")}] alive workers not find !")
            break()
          }

          if(selectMeta.workerId == null || !curActAliveWorkers.contains(selectMeta.workerId)) {
            val selectKeyStr = deploymentCode + "." + selectorKey
            val selectWorkerId: String = if(!selectKeyWorkerIds.contains(selectKeyStr) || !curActAliveWorkers.contains(selectKeyWorkerIds.get(selectKeyStr).get)) {
              val nextId = getNextWorkerId(curActAliveWorkers.toList.sorted)
              selectKeyWorkerIds += (selectKeyStr -> nextId)
              nextId
            }else {
              selectKeyWorkerIds.get(selectKeyStr).get
            }
            logger.info(s"[$selectPath] worker re-assign: origin = ${selectMeta.workerId}, new = $selectWorkerId")
            selectMeta.workerId = selectWorkerId
            selectWrites += (selectPath -> selectMeta)
          }
        }

        for (jobNodeKey <- jobNodes) {
          val nodePath = ZKPathUtils.getDeploymentJobConfigPath(deploymentCode, jobNodeKey)
          val nodeMeta = ZKClient.readData(nodePath, classOf[DeployJobNodeMeta])
          val curActAliveWorkers = aliveWorkerLabels.filter(_._2.exists(nodeMeta.masterLabels.contains(_))).keySet
          if(nodeMeta.workerId == null || !curActAliveWorkers.contains(nodeMeta.workerId)) {
            val selectKeyStr = deploymentCode + "." + nodeMeta.selectKey
            val selectWorkerId: String = if(!selectKeyWorkerIds.contains(selectKeyStr) || !curActAliveWorkers.contains(selectKeyWorkerIds.get(selectKeyStr).get)) {
              val nextId = getNextWorkerId(curActAliveWorkers.toList.sorted)
              selectKeyWorkerIds += (selectKeyStr -> nextId)
              nextId
            }else {
              selectKeyWorkerIds.get(selectKeyStr).get
            }
            logger.info(s"[$nodePath] worker re-assign: origin = ${nodeMeta.workerId}, new = $selectWorkerId")
            nodeMeta.workerId = selectWorkerId
            ZKClient.writeData(nodePath, nodeMeta)

          }
        }

        Thread.sleep(500L)
        for (select <- selectWrites) {
          ZKClient.writeData(select._1, select._2)
        }
      }
    }

//    jobRegistry.runtimeJobs.asJava.asScala.values.foreach(jobInfo => {
////      val jobInfo = job._2
//      val selectWorkerId: String = if(jobInfo.selector.isDefined) {
//        if(!selectKeyWorkerIds.contains(jobInfo.selectKey)) {
//          val nextId = getNextWorkerId(actAliveWorkers)
//          selectKeyWorkerIds += (jobInfo.selectKey -> nextId)
//          nextId
//        }else {
//          selectKeyWorkerIds.get(jobInfo.selectKey).get
//        }
//      }else {
//        getNextWorkerId(actAliveWorkers)
//      }
//      trySetSelectAndTermNodeWorkerId(jobInfo.selectKey, selectWorkerId, jobInfo)
//      jobInfo.jobNodes.reverse.foreach(node => {
//        if(node.workerId.isEmpty) {
//          node.workerId = Option.apply(selectWorkerId)
//          val jobNodeConfigPath = ZKPathUtils.getJobNodeConfigPath(jobInfo.templateCode, jobInfo.jobCode, node.nodeCode)
//          val data = ZKClient.readData(jobNodeConfigPath, classOf[JobNodeMeta])
//          data.nodeMeta.workerId = selectWorkerId
//          ZKClient.writeData(jobNodeConfigPath, data)
//        }
//      })
//    })
  }

  /**
    * 分配待执行worker
    * TODO 这里根据实际情况进行算法优化
    * @param all worker列表
    * @return
    */
  def getNextWorkerId(all: List[String]): String = {
    if(all.isEmpty) {
      "-1"
    }else {
      all(Random.nextInt(all.size))
    }
  }

  var deployments =  Map.empty[String, CfgDeployment]
  var configWorkers =  Map.empty[String, WorkerMeta]
  var aliveWorkers = new ConcurrentHashMap[String, Boolean]()
  var selectKeyWorkerIds = Map.empty[String, String]
}