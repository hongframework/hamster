package com.hframework.datacenter.hamster.worker.tasks

import java.util.Date
import java.util.concurrent.atomic.{AtomicBoolean, AtomicLong}
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.{ConcurrentHashMap, ExecutionException, ExecutorService}

import com.hframe.hamster.common.ReplyProcessQueue
import com.hframe.hamster.node.cannal.Message
import com.hframe.hamster.node.monitor.bean.{ProcessEventData, ProcessNodeEventData, SharableEventData}
import com.hframe.hamster.node.task.common.{TaskData, TaskOrderMode}
import com.hframe.hamster.node.{HamsterConfig, HamsterConst}
import com.hframework.common.client.redis.RedisService
import com.hframework.common.frame.ServiceFactory
import com.hframework.datacenter.hamster.workes.bean.BatchDataSet
import com.hframework.datacenter.hamster.CycleLife
import com.hframework.datacenter.hamster.exceptions.{TaskAckException, TaskInitializationException, TaskRunningException}
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobContainerMeta, JobNodeMeta, NodeMataWrapper}
import com.hframework.datacenter.hamster.monitor.zk.ZKChildrenMonitor
import com.hframework.datacenter.hamster.monitor.zk.listeners.ZKChildrenListener
import com.hframework.datacenter.hamster.worker.HamsterWorker
import com.hframework.datacenter.hamster.zookeeper.{ZKClient, ZKPathUtils}
import com.hframework.hamster.sch.domain.model.{LogDeploy, LogDeploy_Example}
import com.hframework.hamster.sch.service.interfaces.ILogDeploySV
import org.I0Itec.zkclient.exception.ZkNodeExistsException
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.exception.ExceptionUtils
import org.apache.zookeeper.CreateMode
import org.eclipse.jetty.util.ConcurrentHashSet
import org.slf4j.MDC

import scala.collection.mutable
//import com.hframework.datacenter.hamster.worker.tasks.TermStatus

import scala.collection.JavaConverters._

trait Task extends Thread with CycleLife {
  var origThreadName = ""
  /** 启动 */
  override def setup(): Unit = {
    if (!running) {
      this.synchronized{
        if (!running) {
          running = true
          logger.info(s"${name} task starting ...")
          startInternal()
          logger.info(s"${name} task stop !") //由于这里extends Thread，启动进程结束即为任务停止
        }
      }
    }
  }
  override def run(): Unit = {
    origThreadName = Thread.currentThread.getName
    Thread.currentThread.setName(threadName)
    setup
  }

  override def destroy(): Unit = {
    Thread.currentThread.setName(origThreadName)
    shutdown
  }

  override def interrupt(): Unit = {
    Thread.currentThread.setName(origThreadName)
    shutdown
    super.interrupt()
  }

  def threadName: String = "*Task"
}
trait AbstractTask extends Task{
  protected var taskOrderMode = TaskOrderMode.NONE
}

trait TaskLocalRunner[T] extends Task {

  /*本地一次执行调用*/
  def invoke(taskData: TaskData[T]):Option[TaskData[T]]
}

abstract class PrototypeTask(prototypeKey: String)  extends AbstractTask{
  override def threadName: String = s"${prototypeKey}-Task"
}

trait DeployLog{
  val deployLog = {
    val log = new LogDeploy
    log.setDeployId(-1L)
    log.setStatus(0.toByte)
    log
  }

  def hasInvoked(deployCode: String, selectKey: String): Boolean = {
    val list = ServiceFactory.getService(classOf[ILogDeploySV]).getLogDeployListByExample({
      val example = new LogDeploy_Example
      example.createCriteria().andDeployCodeEqualTo(deployCode).andSelectKeyEqualTo(selectKey).andStatusEqualTo(2.toByte)
      example
    })
    list.size() > 0
  }

  def tryLogDeployStartInfo(deployCode: String, selectKey: String, startTime: Date = null, startInfo: String): Unit = {
    if(deployLog.getStatus == 0.toByte) {
      deployLog.setCreatorId(-999L)
      deployLog.setCreateTime(new Date())
      deployLog.setDeployCode(deployCode)
      deployLog.setSelectKey(selectKey)
      deployLog.setStatus(1.toByte)
      deployLog.setStartTime(startTime)
      deployLog.setStartInfo(startInfo)
      ServiceFactory.getService(classOf[ILogDeploySV]).create(deployLog)
    }
  }

  def tryLogDeployEndInfo(endTime: Date = null, endInfo: String): Unit = {
    if(deployLog.getStatus == 1.toByte) {
      deployLog.setEndTime(endTime)
      deployLog.setEndInfo(endInfo)
      deployLog.setStatus(2.toByte)
      deployLog.setModifierId(-999L)
      deployLog.setModifyTime(new Date())
      ServiceFactory.getService(classOf[ILogDeploySV]).update(deployLog)
    }
  }

  def tryLogDeployErrorInfo(endTime: Date, endInfo: String, errMsg: String): Unit = {
    if(deployLog.getStatus == 1.toByte) {
      deployLog.setEndTime(endTime)
      deployLog.setEndInfo(endInfo)
      deployLog.setStatus(3.toByte)
      deployLog.setModifierId(-999L)
      deployLog.setModifyTime(new Date())
      deployLog.setErrorInfo(errMsg)
      ServiceFactory.getService(classOf[ILogDeploySV]).update(deployLog)
    }
  }

  def tryLogDeployEndInfo(lastEventDataOp: Option[ProcessEventData]): Unit = {
    if(deployLog.getStatus == 1.toByte && lastEventDataOp.isDefined) {
      tryLogDeployEndInfo(new Date(lastEventDataOp.get.getLastTime * 1000L), lastEventDataOp.get.getBinlogEnd)
    }
  }

  def tryLogDeployErrorInfo(lastEventDataOp: Option[ProcessEventData] = None, errMsg: String): Unit = {
    if(deployLog.getStatus == 1.toByte) {
      if(lastEventDataOp.isDefined) {
        deployLog.setEndTime(new Date(lastEventDataOp.get.getLastTime * 1000L))
        deployLog.setEndInfo(lastEventDataOp.get.getBinlogEnd)
      }
      deployLog.setStatus(3.toByte)
      deployLog.setModifierId(-999L)
      deployLog.setModifyTime(new Date())
      deployLog.setErrorInfo(errMsg)
      ServiceFactory.getService(classOf[ILogDeploySV]).update(deployLog)
    }
  }
}

abstract class AbstractSelector(prototypeKey: String,
                                deployMeta: DeployJobNodeMeta,
                                var _jobNodeMeta: JobNodeMeta,
                                var _jobMeta: JobContainerMeta,
                                watchPath : String,
                                var dependencyJobNode: scala.List[JobNodeMeta] = Nil)
  extends AbstractTaskRunner(prototypeKey, deployMeta, _jobNodeMeta, _jobMeta, watchPath, true) with DeployLog{

  override def threadName: String = s"${prototypeKey}-Selector"

  val jgroupKey = jobMeta.selectKey

  /**Selector仅reload JobNodeMeta 没有意义，因为没有需要动态修改的项目*/
  @Deprecated
  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this._jobNodeMeta = newJobNodeMeta
    this._jobMeta = newJobNodeMeta.jobMeta
  }

  def reload(dependencyJobNode: scala.List[JobNodeMeta]): Unit = {
    this.dependencyJobNode = dependencyJobNode
  }
  var lastEventDataOp: Option[ProcessEventData] = None
  def ackTermi (messageId: Long, eventData: ProcessEventData = null): Unit = {
    lastEventDataOp = Some(eventData)
    ackTermi(messageId)
  }

  def ackTermi (messageId: Long)

  override lazy val processMonitor = {
    ZKClient.createPersistentIfNotExists(watchPath, createParents = true)
    val monitor = new ZKChildrenMonitor(prototypeKey, watchPath, true, false)
    monitor.addListener(new ZKChildrenListener(){//不要用lambda表达式书写，可能存在BUG
      override def childrenChange(add: List[String], del: List[String], all: List[String], versionNo: String = ""): Unit = {
        logger.info(s"selector receive process signal => [$versionNo]add:${add.mkString("[",", ","]")}, " +
          s"del:${del.mkString("[",", ","]")}, dump:${all.mkString("[",", ","]")}")
        add.foreach(process => addReply(process.toLong))
        del.foreach(process => processIdsQueue.remove(process.toLong))
        val capacity = parallelism - all.length
        if (capacity > 0) this synchronized {
          //二次检查
          if (parallelism - ZKClient.getChildren(watchPath).size > 0) {
            logger.debug(s"create new process node  : capacity = ${parallelism}, size = ${all.length}, add = ${capacity}")
            addNewOneProcessToZk
          }
        }
      }
    })
    Some(monitor)
  }

  /** 启动逻辑
    * 1). selector选择出数据后，同步Selector元数据至ZK
    * 2). 根据实时关联的extractor进行数据提取,分别同步元数据与数据至ZK与REDIS启动子流程，对于提取后Extract为空，则直接将子流程发送Termin信号
    * 3). terminator实时监听所有子流程，对于所有子流程都发出了Termin信息或者对应的子流程被取消，则完成一次selector处理
    */
  override protected def startInternal(): Unit = {
    MDC.put(HamsterConst.splitTaskLogFileKey, prototypeKey)
    //TODO
    while (isRunning) try{
      logger.info("data fetch begin ..")
      //step.1 获取待处理数据（阻塞获取）
      val dataSets = select
      tryLogDeployStartInfo(deployCode, jobNodeMeta.jobMeta.selectKey, new Date(dataSets.getBinlogStartTs * 1000L), dataSets.getBinlogStart)
      logger.info("data fetch finish .. ")

      //step.2 获取可用的process资源（阻塞获取）
      val eventData = waitForEventData(true)
      eventData.setBatchId(dataSets.getBatchId)
      eventData.setBinlogStart(dataSets.getBinlogStart)
      eventData.setBinlogEnd(dataSets.getBinlogEnd)
      eventData.setFirstTime(dataSets.getBinlogStartTs)
      eventData.setLastTime(dataSets.getBinlogEndTs)

      val taskData = new BaseTaskData(dataSets, eventData)
      taskData.setCreateTime(new Date)
      HamsterWorker.flowQueue.offer((deployMeta,_jobNodeMeta,dataSets))
       //队列满返回false,put会发生阻塞
      //step.3 将元数据(META-DATA)写入ZooKeeper集群中
      val processId = taskData.getEventData.getProcessId
      taskData.setJobs(dependencyJobNode.map(_.nodeMeta.nodeKey).asJava)
      updateTaskMetaDataToZK(getProcessPath(processId), taskData.getEventData)

      //step.4 异步启动调用所有Extractor进行子流程处理
      val syncZK = new AtomicBoolean(false)
      for(dependJobNode <- dependencyJobNode) {
        executorService.submit(new Runnable {
          override def run(): Unit = {
            MDC.put(HamsterConst.splitTaskLogFileKey, prototypeKey)
            val currentName = Thread.currentThread.getName
            Thread.currentThread.setName(jgroupKey + "－Extractor")
            try
              doOnceExtract(deployCode, dependJobNode, taskData, syncZK)
            catch {
              case e: Exception => {
                logger.error(s"task node process error: ${jgroupKey}, ${ExceptionUtils.getFullStackTrace(e)}")
                destroy
              }
            } finally {
              Thread.currentThread.setName(currentName)
              MDC.remove(HamsterConst.splitTaskLogFileKey)
            }
          }
        })
      }
//        dependencyJobNode.foreach(dJobNode => {
//
//        })


    }catch{
      case e: InterruptedException => {
        logger.warn(s"thread interrupted => ${ExceptionUtils.getFullStackTrace(e)}")
        tryLogDeployEndInfo(lastEventDataOp)
        destroy
        //TODO 将读出来的messge信息进行回滚处理
      }
      case e: ExecutionException => {
        tryLogDeployErrorInfo(lastEventDataOp, e.getMessage)
        destroy
        logger.error(s"thread  execution interrupted => ${ExceptionUtils.getFullStackTrace(e)}")
      }
      case e :Exception  => {
        tryLogDeployErrorInfo(lastEventDataOp, e.getMessage)
        destroy
        logger.error(s"unknown exception => ${ExceptionUtils.getFullStackTrace(e)}")
      }
    }
  }

  /**
    * 进行一次数据选择
    * @return
    */
  def select(): BatchDataSet


  def doOnceExtract(deployCode: String, extractNode: JobNodeMeta, taskData : BaseTaskData, syncZK: AtomicBoolean): Unit ={
    val processId = taskData.getEventData.getProcessId
    val cacheKey = deployCode + "." + extractNode.nodeMeta.nodeKey

    val extractRunner = HamsterWorker.taskRunners.get(cacheKey)
    if(extractRunner.isEmpty) {
      val deployMeta = ZKClient.readData(
        ZKPathUtils.getDeploymentJobConfigPath(deployCode, extractNode.nodeMeta.nodeKey), classOf[DeployJobNodeMeta])
      if(deployMeta.workerId == HamsterWorker.workerId) {
        logger.warn(s"[${cacheKey}] task runner is not exists, can't invoke local, sleep 3s try again !")
        Thread.sleep(3000L)//睡眠三秒钟,尝试继续调用
        doOnceExtract(deployCode, extractNode, taskData, syncZK)
      }else {//尽量避免出现这种场景，select与extractor尽量在一个进程中完成处理，如果出现分布式处理，那么效率会大大降低
        logger.warn(s"[${cacheKey}] task runner is not not local, runner by worker [${deployMeta.workerId }] !")
        if(!syncZK.getAndSet(true)) {//只同步一次
          //3.将数据(DATA)写入Redis缓存服务器中(24小时后失效)
          redisService.saveOrUpdate(ZKPathUtils.getJobGroupProcessRedisPath(deployCode, jgroupKey, processId),
            taskData, redisDataExpiredMinutes * 60)
          //4.将元数据(META-DATA)写入ZooKeeper集群中
          val zkPath = ZKPathUtils.getTaskProcessPath(deployCode,extractNode.jobMeta.jobTemplate.templateCode,
            extractNode.jobMeta.jobInfo.jobCode, "select", processId)
          saveTaskMetaDataToZK(zkPath, taskData.getEventData)
        }
      }

    }else if(!extractRunner.get.isInstanceOf[AbstractExtractor]) throw new TaskInitializationException(s"[${cacheKey}] task runner is not extract type ! ") else {
      val executor = extractRunner.get.asInstanceOf[AbstractExtractor]
      executor.localTaskDataCache.put(processId, taskData)
      executor.localEventDataCache.put(processId, taskData.getEventData.asInstanceOf[SharableEventData])
      executor.addReply(processId)//将待处理的数据写入Reply队列中，让Extractor自行异步处理
    }
  }


  override def waitForEventData(requireContinuous: Boolean = false): SharableEventData = {
    val processId = waitForProcess(requireContinuous)
    val eventData = new SharableEventData
    eventData.setCurrNid(HamsterWorker.workerId.toLong)
    eventData.setNextNid(HamsterWorker.workerId.toLong) //TODO 需要通过负载算法进行选取next id
    eventData.setProcessId(processId)
    eventData.setPrototypeValue(prototypeKey)
    markProcessUsed(processId)
    eventData
  }

  def addNewOneProcessToZk: Unit = {
    val nodeData = new ProcessNodeEventData
    nodeData.setStatus(ProcessNodeEventData.Status.UNUSED) // 标记为未使用
    nodeData.setNid(HamsterWorker.workerId.toLong)
    val childPath = ZKClient.create(watchPath + "/", nodeData, CreateMode.PERSISTENT_SEQUENTIAL)
    // 创建为顺序的节点
    val process = StringUtils.substringAfterLast(childPath, "/")
    logger.info(s"create zk process : root path = ${watchPath}, child name = ${process}, " +
      s"mode = ${CreateMode.PERSISTENT_SEQUENTIAL} success !")
  }

  def markProcessUsed(processId: Long) = {
    val nodeData = new ProcessNodeEventData
    nodeData.setStatus(ProcessNodeEventData.Status.USED) // 标记为已使用
    nodeData.setNid(HamsterWorker.workerId.toLong)
    val processPath = getProcessPath(processId)
    logger.info(s"mark used zk process path : ${processPath}")
    try
      ZKClient.writeData(processPath, nodeData)
    catch {
      case e: Exception => logger.error(s"mark used zk process path : ${processPath} error, ${ExceptionUtils.getFullStackTrace(e)}")
    }
  }

  /**
    * 执行一次业务处理
    *
    * @param processId 处理批次号
    * @param eventData 元数据
    * @param dataSets   待处理数据
    * @return 处理结果数据
    */
  override def execute(processId: Long, eventData: SharableEventData, dataSets:  BatchDataSet): Option[BatchDataSet] = None
}

abstract class AbstractExtractor(prototypeKey: String,
                                 deployMeta: DeployJobNodeMeta,
                                 var _jobNodeMeta: JobNodeMeta,
                                 var _jobMeta: JobContainerMeta,
                                 watchPath : String = null,
                                 watchZK: Boolean = false)
  extends AbstractTaskRunner(prototypeKey, deployMeta, _jobNodeMeta, _jobMeta, watchPath, watchZK) {
  override def threadName: String = s"${prototypeKey}-Extractor"


  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this._jobNodeMeta = newJobNodeMeta
    this._jobMeta = newJobNodeMeta.jobMeta

  }

  val localTaskDataCache = new ConcurrentHashMap[java.lang.Long, BaseTaskData]
  val localEventDataCache = new ConcurrentHashMap[java.lang.Long, SharableEventData]

  override def waitForEventData(requireContinuous: Boolean = false): SharableEventData = {
    val processId = waitForProcess(requireContinuous)
    val eventData = if(localEventDataCache.containsKey(processId)) {
      localEventDataCache.get(processId)
    }else ZKClient.readData(getProcessPath(processId), classOf[SharableEventData])
    eventData.setCurrNid(HamsterWorker.workerId.toLong)
    eventData.setNextNid(HamsterWorker.workerId.toLong) //TODO 需要通过负载算法进行选取next id
    eventData
  }

  override def getPrevTaskData(processId: Long):BaseTaskData = {
    if(localTaskDataCache.containsKey(processId)) {
      localTaskDataCache.get(processId)
    }else {
      val prevTaskDataRedisPath = ZKPathUtils.getJobProcessRedisPath(deployCode, jobKey, prevTaskCode.get, processId)
      logger.warn(s"${prevTaskDataRedisPath} ' local data is empty, maybe some wrong happened !")
      redisService.get(prevTaskDataRedisPath, classOf[BaseTaskData], classOf[BatchDataSet])
    }
  }

  override def removePrevTaskData(processId: Long): Unit = {
    if(localEventDataCache.containsKey(processId)) {
      localEventDataCache.remove(processId)
    }
    if(localTaskDataCache.containsKey(processId)) {
      logger.info(s"[${jobKey}] 'remove local cache data, process id eq ${processId} " +
        s"(size: ${localEventDataCache.size()}:${localTaskDataCache.size()})!")
      localTaskDataCache.remove(processId)
    }

  }
}

abstract class AbstractTerminator(prototypeKey: String,
                                  deployMeta: DeployJobNodeMeta,
                                  var _jobNodeMeta: JobNodeMeta,
                                  var _jobMeta: JobContainerMeta,
                                  watchPath : String = null,
                                  var dependencyJobNode: List[JobNodeMeta] = Nil)
  extends AbstractTaskRunner(prototypeKey,deployMeta, _jobNodeMeta, _jobMeta, watchPath, true) {
  override def threadName: String = s"${prototypeKey}-Terminator"

  @volatile var childJobInfo: Map[String, JobNodeMeta] = dependencyJobNode.map(jobNode => jobNode.jobMeta.jobInfo.jobKey -> jobNode).toMap
  val reloadTimestamp = new AtomicLong(Long.MaxValue)
  val reloadNewJobKeys = new ConcurrentHashSet[String]()

  val repayProcessMap = new ConcurrentHashMap[Long, ConcurrentHashSet[String]]
  val repayProcessLock = new ReentrantLock()

  val terminMonitor = mutable.Map.empty[String, ZKChildrenMonitor]
  dependencyJobNode.map(childJobNode => getDependencyMonitor(childJobNode))
    .foreach(entry => terminMonitor.put(entry._1, entry._2))

  /** Selector仅reload JobNodeMeta 没有意义，因为没有需要动态修改的项目 */
  @Deprecated
  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this._jobNodeMeta = newJobNodeMeta
    this._jobMeta = newJobNodeMeta.jobMeta
  }

  def reload(dependencyJobNode: scala.List[JobNodeMeta]): Unit = {
    this.dependencyJobNode = dependencyJobNode


    val dependencyMap = dependencyJobNode.map(jobNode => jobNode.jobMeta.jobInfo.jobKey -> jobNode).toMap[String, JobNodeMeta]

    //删除多余的Monitor
    val removeMonitors = terminMonitor.filterNot(entity => dependencyMap.contains(entity._1))
    removeMonitors.foreach(monitor =>{
      logger.info(s"monitor destroy !")
      monitor._2.shutdown()
    })
    removeMonitors.foreach(monitor => terminMonitor.remove(monitor._1))

    //添加新增的Monitor
    val addDependencyJobNode = dependencyMap.filterNot(entity => terminMonitor.contains(entity._1))
    val addMonitors = addDependencyJobNode.map(entity => getDependencyMonitor(entity._2))
    addMonitors.foreach(entity => terminMonitor.put(entity._1, entity._2))

    addMonitors.foreach(_._2.setup)

    if(System.currentTimeMillis() > reloadTimestamp.get() + 3 * 1000) reloadNewJobKeys.clear()
    reloadNewJobKeys.addAll(addDependencyJobNode.map(_._1).toList.asJava)
    reloadTimestamp.set(System.currentTimeMillis())
    childJobInfo = dependencyJobNode.map(jobNode => jobNode.jobMeta.jobInfo.jobKey -> jobNode).toMap
  }


  override lazy val processMonitor = if(watchPath != null && !watchPath.isEmpty){
    ZKClient.createPersistentIfNotExists(watchPath, createParents = true)

    val monitor = new ZKChildrenMonitor(prototypeKey, watchPath, true){
      /** 启动逻辑 */
      override protected def startInternal(): Unit = {
        //原则上这里应该删除上一轮已经被标记为使用（Mark Used）的processes,但是由于MarkUsed并无实际意义，且可以直接覆盖，所以这里不做处理
        super.startInternal()
      }
    }
    monitor.addListener(new ZKChildrenListener {//不要用lambda表达式书写，可能存在BUG
    override def childrenChange(add: List[String], del: List[String], all: List[String], versionNo: String = ""): Unit = {
      logger.info(s"termin receive process signal => [$versionNo]add:${add.mkString("[",", ","]")}, " +
        s"del:${del.mkString("[",", ","]")}, dump:${all.mkString("[",", ","]")}")
      add.foreach(process => addReply(process.toLong))
      del.foreach(process => processIdsQueue.remove(process.toLong))
    }
    })
    Some(monitor)
  }else {
    None
  }

  def getDependencyMonitor(childJobNode: JobNodeMeta): (String, ZKChildrenMonitor) = {
    val jobKey = childJobNode.jobMeta.jobInfo.jobKey
    jobKey -> {
      val path = ZKPathUtils.getTaskProcessRootPath(deployCode,
        childJobNode.jobMeta.jobTemplate.templateCode,
        childJobNode.jobMeta.jobInfo.jobCode, termCode)
      //子任务刚创建时，对应的termin节点可能还没有创建
      ZKClient.createPersistentIfNotExists(path, createParents = true)
      val monitor = new ZKChildrenMonitor(prototypeKey, path, true){
        /** 启动逻辑 */
        override protected def startInternal(): Unit = {
          removeAllChildren//任务启动时，直接需要删除上一轮未处理结束的批次，这些批次的数据会重新执行
          super.startInternal()
        }
      }
      monitor.addListener(new ZKChildrenListener {//不要用lambda表达式书写，可能存在BUG
        override def childrenChange(add: List[String], del: List[String], all: List[String], versionNo: String = ""): Unit = {
          logger.info(s"receive termin signal => [$versionNo]jobKey: [$jobKey], add:${add.mkString("[",", ","]")}, " +
            s"del: ${del.mkString("[",", ","]")}, dump:${all.mkString("[",", ","]")}")
          //BUG修复：如果有多个add时，需要排序优先处理小的processId，否则形成阻塞（优先处理的大的processId等待小的processId，而小的processId在同一线程中没有机会处理）
          add.map(_.toLong).sorted.foreach(processId => {
            val processOp = try{
              repayProcessLock.lock()
              repayProcessMap.putIfAbsent(processId, new ConcurrentHashSet[String])
              repayProcessMap.get(processId).add(jobKey)

              val replyJobKeys = repayProcessMap.get(processId)
              logger.info(s"try termin: [$versionNo]processId = $processId; jobKeys = (${replyJobKeys.size()})${replyJobKeys.asScala.mkString(", ")}")
              //所以子流程处理完毕

              //当在发布里面添加job后，3秒的缓冲时间供之前的batch完成结束处理，说明：这里并不最佳实现，
              // 原则上应该SharableEventData有Init传递jobKeys是最佳方案
              val requiredSet = if(System.currentTimeMillis() > reloadTimestamp.get() + 3 * 1000)
                childJobInfo.keySet
              else childJobInfo.keySet.dropWhile(reloadNewJobKeys.contains(_))
              /*判断需要在锁中处理，否多个子任务同时tryTermin时内部判断都都有效，那么将tryAck多次，导致重复处理报错
                注意：另外当删除任务时，原先6个job，删除一个job时，但该job已经ack，那么同样会发生重复处理，这里需要tryTermin内部处理*/
              if(requiredSet.forall(replyJobKeys.contains(_))) {
                logger.info(s"try termin: processId = $processId; jobKeys = (${replyJobKeys.size()}) all sub flow is ack ,try ack begin !")
                Some(processId, replyJobKeys.asScala)
              }else {
                None
              }
            }finally {
              repayProcessLock.unlock()
            }

            if(processOp.isDefined) {
              tryTermin(processOp.get._1, processOp.get._2)
            }
          })
        }
      })
      monitor
    }
  }

  def tryAck(processId: Long, eventData: ProcessEventData, tryTimes: Integer = 0): Unit = {
    ack(processId, eventData, tryTimes) match {
      case Success =>
      case Failed => throw new TaskAckException(s"${watchPath}]: ${processId} => ack failed !")
      case Blocking => {
        logger.warn(s"[${watchPath}]: ${processId} => ack blocking, maybe selector not find, sleep 3s try again !")
        Thread.sleep(3 * 1000L)
        tryAck(processId, eventData, tryTimes + 1)
      }
      case Outstrip => {
        Thread.sleep(if(tryTimes > 1000) 60* 1000L else 300L)
        tryAck(processId, eventData, tryTimes + 1)
      }
    }
  }

  def tryInit(processId: Long, eventData: ProcessEventData): Unit = {
    val status = init(processId, eventData) match {
      case Success =>
      case Failed => throw new TaskAckException(s"${watchPath}]: ${processId} => ack failed !")
      case Blocking => {
        logger.warn(s"[${watchPath}]: ${processId} => init blocking, sleep 3s try again !")
        Thread.sleep(3 * 1000L)
        tryInit(processId, eventData)
      }
      case Outstrip => {
//        logger.warn(s"[${watchPath}]: ${processId} => init outstrip, do nothing !")
//        Thread.sleep(if(tryTimes > 1000) 60* 1000L else 300L)
//        tryAck(processId, eventData, tryTimes + 1)
      }
    }
  }

  /**
    * 任务处理结束确认
    * Success: 确认成功; Failed: 确认失败,抛出异常; Blocking:确认阻塞,等待x秒重试
    * @param processId 处理批次号
    * @param eventData 元数据
    * @return 选项：Success, Failed, Blocking
    */
  def ack(processId: Long, eventData: ProcessEventData, tryTimes: Integer = 0): TermStatus

  /**
    * 任务处理开始确认
    * Success: 开始成功; Failed: 开始失败,抛出异常; Blocking:开始阻塞,等待x秒重试
    * @param processId 处理批次号
    * @param eventData 元数据
    * @return 选项：Success, Failed, Blocking
    */
  def init(processId: Long, eventData: ProcessEventData): TermStatus

  /**
    * 执行一次业务处理
    * 结束逻辑
    * @param processId 处理批次号
    * @param eventData 元数据
    * @param dataSets   待处理数据
    * @return 处理结果数据
    */
  override def execute(processId: Long, eventData: SharableEventData, dataSets:  BatchDataSet): Option[BatchDataSet] = None

  def tryTermin(processId: Long, replyJobKeys: mutable.Set[String]): Unit = {
    val headJobInfo = childJobInfo.find(entry => replyJobKeys.contains(entry._1))
    if(headJobInfo.isDefined) {
      val path = ZKPathUtils.getTaskProcessPath(deployCode,
        headJobInfo.get._2.jobMeta.jobTemplate.templateCode,
        headJobInfo.get._2.jobMeta.jobInfo.jobCode, termCode, processId)
      val eventData = ZKClient.readData(path, classOf[ProcessEventData])
      tryAck(processId, eventData)
    }else {
      logger.error(s"[${watchPath}]: ${processId} => config = ${childJobInfo.keySet.mkString(",")}, really = ${replyJobKeys.mkString(",")} ! ")
      throw new TaskRunningException("can't find valid ack meta data !")
    }


    ZKClient.delete(getProcessPath(processId), true) //删除selector下 processId
    replyJobKeys.foreach(jobKey => {
      val jobInfo = childJobInfo.get(jobKey)
      if(jobInfo.isDefined) {
        val jobRootPath = ZKPathUtils.getJobProcessRootPath(deployCode, jobInfo.get.jobMeta.jobTemplate.templateCode, jobInfo.get.jobMeta.jobInfo.jobCode)
        ZKClient.getChildren(jobRootPath).asScala.foreach(taskCode => {//删除所有子任务下对应的process节点
        val taskProcessPath = (jobRootPath :: taskCode :: processId.toString.reverse.padTo(10, '0').reverse :: Nil).mkString("/")
          ZKClient.delete(taskProcessPath)
        })
      }else {
        logger.warn(s"[${watchPath}]${jobKey} 's termin deal miss ! ")
        //这里会产生垃圾数据，只有下次重启才会主动清除，这里暂不处理！TODO
      }
    })
    repayProcessMap.remove(processId) //删除本地缓存
  }

  override def waitForEventData(requireContinuous: Boolean = false): SharableEventData = {
    val processId = waitForProcess(requireContinuous)
    val eventData = new SharableEventData
    eventData.setCurrNid(HamsterWorker.workerId.toLong)
    eventData.setNextNid(HamsterWorker.workerId.toLong) //TODO 需要通过负载算法进行选取next id
    eventData.setProcessId(processId)
    eventData.setPrototypeValue(prototypeKey)
    eventData
  }

  /** 启动逻辑 */
  override protected def startInternal(): Unit = {
    MDC.put(HamsterConst.splitTaskLogFileKey, prototypeKey)
    terminMonitor.values.foreach(monitor => {
      logger.info(s"termin monitor ${monitor} start !")
      monitor.setup
    })
    while (isRunning) {
      try{
        val eventData = waitForEventData()
        val processId = eventData.getProcessId
        repayProcessMap.putIfAbsent(processId, new ConcurrentHashSet[String])
//        repayProcessMap.synchronized({
//          if(!repayProcessMap.contains(processId)) {
//            repayProcessMap.put(processId, scala.collection.mutable.Set.empty[String])
//          }
//        })

        tryInit(processId, eventData)
      }catch{
        case e: InterruptedException => {
          logger.warn(s"thread interrupted => ${ExceptionUtils.getFullStackTrace(e)}")
          destroy
          //TODO 将读出来的messge信息进行回滚处理
        }
        case e: ExecutionException => logger.error(s"thread  execution interrupted => ${ExceptionUtils.getFullStackTrace(e)}")
        case e :Exception  =>logger.error(s"unknown exception => ${ExceptionUtils.getFullStackTrace(e)}")
      }
    }
  }

  /** 销毁逻辑 */
  override protected def destroyInternal(): Unit = {
    terminMonitor.values.foreach(monitor => {
      logger.info(s"${monitor} destroy !")
      monitor.shutdown()
    })
  }
}

abstract class AbstractTransformer(prototypeKey: String,
                                   deployMeta: DeployJobNodeMeta,
                                   var _jobNodeMeta: JobNodeMeta,
                                   var _jobMeta: JobContainerMeta,
                                   watchPath : String = null,
                                   watchZK: Boolean = true) extends AbstractTaskRunner(
  prototypeKey, deployMeta, _jobNodeMeta, _jobMeta, watchPath, watchZK) {
  override def threadName: String = s"${prototypeKey}-Transformer"

  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this._jobNodeMeta = newJobNodeMeta
    this._jobMeta = newJobNodeMeta.jobMeta
  }
}

abstract class AbstractLoader(prototypeKey: String,
                              deployMeta: DeployJobNodeMeta,
                              var _jobNodeMeta: JobNodeMeta,
                              var _jobMeta: JobContainerMeta,
                              watchPath : String = null,
                              watchZK: Boolean = true) extends AbstractTaskRunner(
  prototypeKey, deployMeta, _jobNodeMeta, _jobMeta, watchPath, watchZK) {
  override def threadName: String = s"${prototypeKey}-Loader"

  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this._jobNodeMeta = newJobNodeMeta
    this._jobMeta = newJobNodeMeta.jobMeta
  }
}

abstract class AbstractTaskRunner(prototypeKey: String,
                                  deployMeta: DeployJobNodeMeta,
                                  var jobNodeMeta: JobNodeMeta,
                                  var jobMeta: JobContainerMeta,
                                  var watchPath : String = null,
                                  watchZK: Boolean = true) extends PrototypeTask(prototypeKey){
  override def threadName: String = s"${prototypeKey}-TaskRunner"

  protected var processOrdered = false

  protected val DEFAULT_PARALLELISM_SIZE: Integer = 10
  protected var parallelism: Int = DEFAULT_PARALLELISM_SIZE //process并行数
  protected var queueSize: Int = DEFAULT_PARALLELISM_SIZE * 5 //queue 大小
  protected val redisDataExpiredMinutes: Long = HamsterConfig.getInstance.getHamsterRedisProcessingDataExpiredMinutes.toLong
  protected var processIdsQueue: ReplyProcessQueue = new ReplyProcessQueue(queueSize); // 响应式processId队列
  protected val lastProcessAtomic = new AtomicLong(-1L)

  protected val redisService: RedisService = ServiceFactory.getService("redisService").asInstanceOf[RedisService]
  protected var executorService: ExecutorService = ServiceFactory.getService("executorService").asInstanceOf[ExecutorService]

  type BaseTaskData = TaskData[BatchDataSet]
  type ParserResult = (NodeMataWrapper, String, String, String, String, Option[String], String)
  val termCode = "term"
  val deployCode = deployMeta.deployCode

  var (jobNode, jobTemplate, jobCode, taskCode, jobKey, prevTaskCode, finalWatchPath) = init


  if(StringUtils.isNotBlank(jobTemplate) && StringUtils.isNotBlank(jobCode)) {
    ZKClient.createPersistentIfNotExists(ZKPathUtils.getTaskProcessRootPath(deployCode, jobTemplate, jobCode, taskCode), null, true)
    ZKClient.createPersistentIfNotExists(ZKPathUtils.getTaskProcessRootPath(deployCode, jobTemplate, jobCode, termCode), null, true)
  }

  /** 加载逻辑 */
  override def reload(): Unit = {
    val result = init
    jobNode = result._1
    jobTemplate = result._2
    jobCode = result._3
    taskCode = result._4
    jobKey = result._5
    prevTaskCode = result._6
    finalWatchPath = result._7
  }

  def reload(newJobNodeMeta: JobNodeMeta): Unit ={
    this.jobNodeMeta = newJobNodeMeta
    this.jobMeta = newJobNodeMeta.jobMeta
    reload
  }

  def init(): ParserResult = {
    val jobNode = jobNodeMeta.nodeMeta.wrapper
    val jobTemplate = if(jobMeta.jobTemplate != null) jobMeta.jobTemplate.templateCode else ""
    val jobCode = if(jobMeta.jobInfo != null ) jobMeta.jobInfo.jobCode else ""
    val taskCode = jobNodeMeta.nodeMeta.nodeCode
    val jobKey =if(jobMeta.jobInfo != null )  jobMeta.jobInfo.jobKey else ""
    val prevTaskCode: Option[String] = if(jobNodeMeta.prevNodeMeta != null) {
      Some(jobNodeMeta.prevNodeMeta.nodeMeta.nodeCode)
    }else None
    val lastWatchPath = if(watchPath == null && prevTaskCode.isDefined) {
      ZKPathUtils.getTaskProcessRootPath(deployCode, jobNodeMeta.prevNodeMeta.
        jobTemplate.templateCode, jobNodeMeta.prevNodeMeta.jobInfo.jobCode, prevTaskCode.get)
    }else {
      watchPath
    }
    (jobNode, jobTemplate, jobCode, taskCode, jobKey, prevTaskCode, lastWatchPath)
  }

  def isEmpty(taskData: BaseTaskData): Boolean = {
    val batchDataSet = taskData.getMessage
    if(batchDataSet.getDatas.isEmpty){
      true
    }else {
      batchDataSet.getDatas.asScala.find(data => !data.getRows.isEmpty).isEmpty
    }
  }

  lazy val processMonitor = if(finalWatchPath != null && !finalWatchPath.isEmpty){
    ZKClient.createPersistentIfNotExists(finalWatchPath, createParents = true)

    val monitor = new ZKChildrenMonitor(prototypeKey, finalWatchPath, true){
      /** 启动逻辑 */
      override protected def startInternal(): Unit = {
        removeAllChildren//任务启动时，直接需要删除上一轮未处理结束的批次，这些批次的数据会重新执行
        super.startInternal()
      }
    }
    monitor.addListener(new ZKChildrenListener {//不要用lambda表达式书写，可能存在BUG
      override def childrenChange(add: List[String], del: List[String], all: List[String], versionNo: String = ""): Unit = {
        logger.info(s"middle receive process signal => [$versionNo]add:${add.mkString("[",", ","]")}, " +
          s"del:${del.mkString("[",", ","]")}, dump:${all.mkString("[",", ","]")}")
        add.foreach(process => addReply(process.toLong))
        del.foreach(process => processIdsQueue.remove(process.toLong))
      }
    })
    Some(monitor)
  }else {
    None
  }

  /** 启动 */
  override def setup(): Unit = {
    if(processMonitor.isDefined){
      val monitor = processMonitor.get
      monitor.setup()

    }
    super.setup()

  }

  /** 销毁 */
  override def shutdown(): Unit = {
    if(processMonitor.isDefined){
      processMonitor.get.shutdown()
    }
    super.shutdown()
  }

  def getPrevTaskData(processId: Long):BaseTaskData = {
    redisService.get(
      ZKPathUtils.getJobProcessRedisPath(deployCode, jobKey, prevTaskCode.get, processId),
      classOf[BaseTaskData], classOf[BatchDataSet])
  }

  def removePrevTaskData(processId: Long):Unit = {
    //存入redis中的数据设置了到期自动失效时长，为减少交互，这里暂不主动进行数据删除
    redisService.delete(ZKPathUtils.getJobProcessRedisPath(deployCode, jobKey, prevTaskCode.get, processId))
  }


  /** 名称描述 */
  override def name: String = s"[${prototypeKey}][${finalWatchPath}:${watchZK}]${jobNodeMeta.nodeMeta.nodeKey} "

  /** 启动逻辑 */
  override protected def startInternal(): Unit = {
    //TODO
    MDC.put(HamsterConst.splitTaskLogFileKey, prototypeKey)
    while (isRunning) {
      try{
        val eventData = waitForEventData(processOrdered)
        val processId = eventData.getProcessId
        val taskData: BaseTaskData = getPrevTaskData(processId)
        val resultData: Option[BatchDataSet] = if(isEmpty(taskData)) None else
          execute(processId, eventData, taskData.getMessage)
//        HamsterWorker.flowQueue.offer((deployMeta,jobNodeMeta,resultData.get))
        if(resultData.isDefined) {
          val newTaskData = new BaseTaskData(resultData.get, eventData)

          //将数据(DATA)写入Redis缓存服务器中(2小时后失效)
          val redisPath = ZKPathUtils.getJobProcessRedisPath(deployCode, jobKey, taskCode, processId)
//          logger.info(s"redis key = [$redisPath], expired = ${redisDataExpiredMinutes * 60}")
          redisService.saveOrUpdate(redisPath, newTaskData, redisDataExpiredMinutes * 60)

          //将元数据(META-DATA)写入ZooKeeper集群中
          val zkPath = ZKPathUtils.getTaskProcessPath(deployCode, jobTemplate, jobCode, taskCode, processId)
          HamsterWorker.flowQueue.offer((deployMeta,jobNodeMeta,resultData.get))
          saveTaskMetaDataToZK(zkPath, eventData)
        }else {
          //无数据情况下，直接写成结束型号即可
          HamsterWorker.flowQueue.offer((deployMeta,jobNodeMeta,taskData.getMessage))
          logger.info(s"[${ZKPathUtils.getTaskProcessPath(deployCode, jobTemplate, jobCode, taskCode, processId)}] invoke result is empty, add term!")
          val zkPath = ZKPathUtils.getTaskProcessPath(deployCode, jobTemplate, jobCode, termCode, processId)
          saveTaskMetaDataToZK(zkPath, eventData)
        }
        removePrevTaskData(processId)
      }catch{
        case e: InterruptedException => {
          logger.warn(s"thread interrupted => ${ExceptionUtils.getFullStackTrace(e)}")
          destroy
          //TODO 将读出来的messge信息进行回滚处理
        }
        case e: ExecutionException => {//java.util.concurrent.ExecutionException: org.springframework.kafka.core.KafkaProducerException
          logger.error(s"thread  execution interrupted => ${ExceptionUtils.getFullStackTrace(e)}")
          destroy
        }
        case e :Exception  => {
          logger.error(s"unknown exception => ${ExceptionUtils.getFullStackTrace(e)}")
          destroy
        }
      }
    }


  }


  /** 销毁逻辑 */
  override protected def destroyInternal(): Unit = {
  }


  /**
    * 执行一次业务处理
    *
    * @param processId 处理批次号
    * @param eventData 元数据
    * @param dataSets 待处理数据
    * @return 处理结果数据
    */
  def execute(processId: Long, eventData: SharableEventData, dataSets:  BatchDataSet): Option[BatchDataSet]

  def addReply(processId: Long): Unit = {
    val result = processIdsQueue.offer(processId)
    if (result)
      logger.debug(s"[${this.getClass.getSimpleName}] process-queue add process [${processId}] success !")
    else logger.debug(s"[${this.getClass.getSimpleName}] process-queue add process [[${processId}] failed !")
  }

  def ack(processId: Long): Unit = {
    val result = processIdsQueue.offer(processId)
    if (result)
      logger.debug(s"[${this.getClass.getSimpleName}] process-queue add process [${processId}] success !")
    else logger.debug(s"[${this.getClass.getSimpleName}] process-queue add process [[${processId}] failed !")
  }


  /**
    * 【说明】在select时获取的processId需要保证有序，否则messageBatchId与processId非同步增大
    * 那么按照processId从小到大ack处理时，message-batchId不是有序，导致ack失败
    * @param requireContinuous
    * @return
    */
  @throws[InterruptedException]
  def waitForProcess(requireContinuous: Boolean = false): Long = {
    logger.info("wait for available process..")
    val processId = if(requireContinuous) getContinuousProcess else  processIdsQueue.take
    logger.debug(s"[${this.getClass.getSimpleName}] process-queue take process [${processId}] success !")
    processId.asInstanceOf[Long]
  }

  def getContinuousProcess(): Long ={
    val expectId = lastProcessAtomic.get() + 1L
    val nextId = if(expectId == 0L || expectId == processIdsQueue.peek()){//是连续数字
      processIdsQueue.take.toLong
    }else if(processIdsQueue.contains(expectId)) {
      processIdsQueue.remove(expectId)
      expectId
    }else {
      Thread.sleep(100L)
      logger.warn(s"wait for a continuous process failed : expect = $expectId , queue = ${processIdsQueue.toArray.mkString(",")}")
      getContinuousProcess
    }
    lastProcessAtomic.set(nextId)
    nextId
  }

  def waitForEventData(requireContinuous: Boolean = false): SharableEventData = {
    val processId = waitForProcess(requireContinuous)
    val eventData = ZKClient.readData(getProcessPath(processId), classOf[SharableEventData])
    eventData.setCurrNid(HamsterWorker.workerId.toLong)
    eventData.setNextNid(HamsterWorker.workerId.toLong) //TODO 需要通过负载算法进行选取next id
    eventData
  }


  def getProcessPath(process: Long) = finalWatchPath.concat("/").concat(process.toString.reverse.padTo(10, '0').reverse)

  def saveTaskMetaDataToZK(path: String, data: ProcessEventData): Unit = {
    logger.info(s"[${path}] insert task meta data ,${data}")
    try {
      ZKClient.create(path, data, CreateMode.PERSISTENT)
      logger.debug(s"[${path}]create node successful !")
    } catch {
      case e: ZkNodeExistsException =>
        logger.error(s"[${path}]create node error, {}", ExceptionUtils.getFullStackTrace(e))
    }
  }

  def updateTaskMetaDataToZK(path: String, data: ProcessEventData) = {
    logger.info(s"[${path}] update task meta data ,${data}")
    try {
      ZKClient.writeData(path, data)
      logger.debug(s"[${path}] update node successful !")
    } catch {
      case e: ZkNodeExistsException =>
        logger.error(s"[${path}] update node error, {}", ExceptionUtils.getFullStackTrace(e))
    }
  }

}