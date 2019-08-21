package com.hframework.datacenter.hamster.worker.tasks.select

import java.util.Date
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.{AtomicBoolean, AtomicLong}

import com.alibaba.otter.canal.common.utils.BooleanMutex
import com.hframe.hamster.common.ReplyProcessQueue
import com.hframe.hamster.node.cannal.bean.HeartBreak
import com.hframe.hamster.node.zk.ZooKeeperClient
import com.hframework.common.util.DateUtils
import com.hframework.utils.scala.{JacksonScalaUtils, Logging}
import com.hframework.datacenter.hamster.exceptions.TaskInitializationException
import com.hframework.datacenter.hamster.worker.Times
import com.hframework.datacenter.hamster.zookeeper.{ZKClient, ZKPathUtils}
import org.apache.commons.lang.exception.ExceptionUtils
import org.quartz.impl.StdSchedulerFactory
import org.quartz.{JobDataMap, _}

import scala.collection.JavaConverters._
import scala.collection.mutable

object QuartzServer{
  val SIGNAL_QUEUE_KEY = "SIGNAL_QUEUE"
  val SIGNAL_MAP_KEY = "SIGNAL_MAP"
  val SCAN_SIGNAL_KEY = "SCAN_SIGNAL"
  val SCAN_INTERVAL_KEY = "SCAN_INTERVAL"
  val SCAN_CRON_KEY = "SCAN_CRON_KEY"
  val SCAN_SCOPE_KEY = "SCAN_SCOPE_KEY"
  val INIT_KEY = "INIT_KEY"

  lazy val scheduler = StdSchedulerFactory.getDefaultScheduler
}

/**
  * 处理批次信息
  * @param signal 处理信息信息
  * @param sqlKey 批次对应的扫描数据源
  * @param hasNext 该批次是否存在下一页
  * @param pageNo 该批次当前页码是多少
  * @param dataBeginTime 数据开始时间
  * @param startId 数据开始ID
  * @param dataEndTime 数据结束时间
  * @param endId 数据结束ID
  * @param from 扫描开始时间
  * @param to  扫描结束时间
  * @param batchId 批次号
  */
case class ScanBatch(var signal: ScanSignal, sqlKey: String, hasNext: Boolean, pageNo: Long,
                     dataBeginTime: String, startId: Long, dataEndTime: String, endId: Long,
                     from: String, to: String, var  fromTs: Long, var  toTs: Long, var batchId:Long = -1, splitQuery: Boolean)

case class ScanSignal(var signalId: Long, var from: String, var to: String, var  fromTs: Long, var  toTs: Long,
                      val sqlKeys: mutable.Set[String] = mutable.Set[String](), @volatile var lastDownBatch: Option[ScanBatch] = None)

class QuartzServer (deployCode: String, selectKey: String) extends Logging{

  protected val signalQueue: ReplyProcessQueue = new ReplyProcessQueue(50); // 响应式signal队列
  protected val signalMap = new ConcurrentHashMap[Long, ScanSignal](); // 响应式signal队列
  protected val initFlag = new AtomicBoolean(true)

  protected val ackQueue: ReplyProcessQueue = new ReplyProcessQueue(50); // 响应式signal队列
  protected val batchMap = new ConcurrentHashMap[Long, ScanBatch](); // 响应式signal队列
  private val canalState: BooleanMutex = new BooleanMutex(false)
  val batchIdCounter = new AtomicLong(1)
  val heartBreak = new HeartBreak
  var selectorMonitorPath:String = null

  val blankScanBatch = ScanBatch(null, "", false, -1, "", -1, "", -1, "", "", -1, -1, -1, false)

  val cursorPath = ZKPathUtils.getQuartzCursorPath(deployCode, selectKey)
  ZKClient.createPersistentIfNotExists(cursorPath, createParents = true)
  val vCursorPath = ZKPathUtils.getQuartzVCursorPath(deployCode, selectKey)
  ZKClient.createPersistentIfNotExists(vCursorPath, createParents = true)

  lazy val jobDetail = JobBuilder.newJob(classOf[SignalAddJob]).withIdentity(selectKey, deployCode).build()
  lazy val lastScanBatch = ZKClient.readData(cursorPath, classOf[ScanBatch])

  lazy val scanSignal = {
    logger.info(s"zookeeper last scan batch  = $lastScanBatch")
    val _signal = if(lastScanBatch == null || lastScanBatch.batchId <= 0) {
      ScanSignal(0, "-1", "-1", -1, -1)
    }else {
      val signal = lastScanBatch.signal
//      if(signal.sqlKeys == null)
//        signal.sqlKeys =  mutable.Set[String]()
      //    lastScanBatch.signal = null
      signal
      //    lastScanBatch.signal.lastDownBatch = Some(lastScanBatch)
      //    lastScanBatch.signal
    }
    logger.info(s"zookeeper last scan batch  = ${_signal}")
    _signal
  }

  def getJobData(intervalOp: Option[Long], cronExpressOp: Option[String], dataScanScopeOp: Option[List[String]]) = {
    val parameters = Map(
      QuartzServer.SIGNAL_QUEUE_KEY -> signalQueue,
      QuartzServer.SIGNAL_MAP_KEY -> signalMap,
      QuartzServer.SCAN_SIGNAL_KEY -> scanSignal,
      QuartzServer.SCAN_INTERVAL_KEY -> intervalOp,
      QuartzServer.SCAN_CRON_KEY -> cronExpressOp,
      QuartzServer.SCAN_SCOPE_KEY -> dataScanScopeOp,
      QuartzServer.INIT_KEY -> initFlag
    ).asJava
    new JobDataMap(parameters)
  }

  def start(intervalOp: Option[Long], cronExpressOp: Option[String], dataScanScopeOp: Option[List[String]] = None): Unit = {
      //    if(lastScanBatch != null && lastScanBatch.batchId > 0) {
      if(lastScanBatch != null && lastScanBatch.batchId > 0 && (lastScanBatch.hasNext || lastScanBatch.toTs != scanSignal.toTs)) {//如果上一个信号是未处理结束信号，则继续添加进入队列中
        signalMap.put(scanSignal.signalId, scanSignal.copy(lastDownBatch = Some(lastScanBatch)))
        signalQueue.offer(scanSignal.signalId)
        logger.info(s"zookeeper last scan batch  = ${signalMap.get(scanSignal.signalId)}")
      }
      startScheduler(intervalOp, cronExpressOp, dataScanScopeOp)
      canalState.set(true)
  }

  def startScheduler(intervalOp: Option[Long], cronExpressOp: Option[String], dataScanScopeOp: Option[List[String]] = None): Unit = {
    val schedBuilder = if(intervalOp.isDefined) {
      SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(intervalOp.get.toInt).repeatForever()
    }else if(cronExpressOp.isDefined) {
      CronScheduleBuilder.cronSchedule(cronExpressOp.get)
    }else {
      throw new TaskInitializationException(s"quartzServer[deployCode=$deployCode;selectKey=$selectKey] " +
        s"neither interval nor cron config !")
    }
    val trigger = TriggerBuilder.newTrigger().withIdentity(selectKey, deployCode).withSchedule(schedBuilder)
      .usingJobData(getJobData(intervalOp, cronExpressOp, dataScanScopeOp)).build()
    QuartzServer.scheduler.scheduleJob(jobDetail, trigger)
    QuartzServer.scheduler.start()
  }

  def reload(intervalOp: Option[Long], cronExpressOp: Option[String], dataScanScopeOp: Option[List[String]] = None): Unit = {
    if(QuartzServer.scheduler.checkExists(jobDetail.getKey)) {
      QuartzServer.scheduler.deleteJob(jobDetail.getKey)
    }
    startScheduler(intervalOp, cronExpressOp, dataScanScopeOp)
  }

  def stop: Unit = {
    if(QuartzServer.scheduler.checkExists(jobDetail.getKey)) {
      QuartzServer.scheduler.deleteJob(jobDetail.getKey)
    }
//    scheduler.shutdown()
    canalState.set(false)
    heartBreak.reset()
  }

  private def updateHeartBreak(signalObject: ScanSignal): Unit = {

    val currentTime = System.currentTimeMillis
    val delaySeconds = (currentTime - heartBreak.getFetchTimeStamp) / 1000
    //        logger.info("fetchTime = " + heartBreak.getFetchTimeStamp() + "; executeTime = " + heartBreak.getExecuteTimeStamp());
    if (delaySeconds >= 10) {
      heartBreak.setExecuteTimeStamp(if(signalObject.lastDownBatch.isDefined) {
        DateUtils.parseYYYYMMDDHHMMSS(signalObject.lastDownBatch.get.dataEndTime).getTime
      }else signalObject.fromTs * 1000L)
      heartBreak.setFetchTimeStamp(currentTime)
      logger.info("update heart break : {}, {}", selectorMonitorPath, heartBreak)
      ZooKeeperClient.getInstance.writeData(selectorMonitorPath, JacksonScalaUtils.toJson(heartBreak))
//      heartBreak.setExecuteTimeStamp(-1L)
    }
  }

  @throws[InterruptedException]
  def waitForQuartzSignal: ScanSignal = {
    logger.info("wait for available signal..")
    val signalId = signalQueue.peek
    val signalObject = signalMap.get(signalId)
    logger.info(s"[${this.getClass.getSimpleName}] signal-queue take signal [${signalId}, $signalObject] success !")
    updateHeartBreak(signalObject)
    signalObject
  }

  def addBatch(batch: ScanBatch): Long ={
    if(batch.batchId <= 0) {
      val batchId = batchIdCounter.getAndIncrement()
      batch.batchId = batchId
    }

    if(!batch.hasNext && batch.toTs == batch.signal.toTs){
      batch.signal.sqlKeys += batch.sqlKey//每处理完一个扫描信号源，刷新一下sqlKeys，可能有新的分表刚创建加入进来
    }

    batch.signal = batch.signal.copy(lastDownBatch = None, sqlKeys = batch.signal.sqlKeys.toList.to)
    batchMap.put(batch.batchId, batch)
    ackQueue.offer(batch.batchId)

    val signalId = batch.signal.signalId
    val signal = signalMap.get(signalId)
    signal.lastDownBatch = Some(batch.copy(signal = null))
    logger.info(s"add batch $signal")
    signalMap.put(signalId, signal)
    batch.batchId
  }

  def signalFinish(signal: ScanSignal): Unit ={
    signalQueue.remove(signal.signalId)
    signalMap.remove(signal.signalId)
  }

  @throws[InterruptedException]
  def waitForStarted(): Unit = {
    canalState.get()
  }

  def ack(batchId: Long): Unit = {
    try {
      logger.info("ack batchId = " + batchId)
      waitForStarted()
      if (!ackQueue.contains(batchId))
        logger.warn(s"batch id [$batchId] not exists, maybe restart happened !")
      else {
        var firstlyMessageId: Long = -1L
        do {
          if (firstlyMessageId != -1L) {
            if ((System.currentTimeMillis / 1000) % 5 == 0)
              logger.warn("ack not in order, current is [{}], waiting for [{}]", batchId, firstlyMessageId)
            Thread.sleep(1000L)
          }
          firstlyMessageId = ackQueue.peek
        } while (batchId > firstlyMessageId)
        //这里选择的是大于，如果小于可以直接进行回复，在异常回复后，messageIdsQueue只会有新的messageId
        ackInternal(batchId)
      }
    } catch {
      case e: InterruptedException =>
        logger.error("ack error : => {}", ExceptionUtils.getFullStackTrace(e))
      case e: Exception =>
        logger.error("ack error : => {}", ExceptionUtils.getFullStackTrace(e))
        throw e
    }
  }

  def ackVirtualSignal(originSignal: ScanSignal): Unit = {
    val virtualSignal = blankScanBatch.copy(
      signal = originSignal,
      toTs = originSignal.toTs
    )
    ZKClient.writeData(vCursorPath, virtualSignal)
    logger.debug(s"ackVirtualSignal: {}, {}", vCursorPath, virtualSignal)
  }

  private def ackInternal(batchId: Long): Unit = {
    val scanBatch = batchMap.remove(batchId)
    ZKClient.writeData(cursorPath, scanBatch.copy(
      signal = scanBatch.signal.copy(
        lastDownBatch = None)))
    ackQueue.remove(batchId)
  }
}

class SignalAddJob extends Job with Logging{
  override def execute(context: JobExecutionContext): Unit = {
    val query = context.getMergedJobDataMap.get(QuartzServer.SIGNAL_QUEUE_KEY).asInstanceOf[ReplyProcessQueue]
    val map = context.getMergedJobDataMap.get(QuartzServer.SIGNAL_MAP_KEY).asInstanceOf[ConcurrentHashMap[Long, ScanSignal]]
    val scanSignal = context.getMergedJobDataMap.get(QuartzServer.SCAN_SIGNAL_KEY).asInstanceOf[ScanSignal]
//    val intervalOp = context.getMergedJobDataMap.get(QuartzServer.SCAN_INTERVAL_KEY).asInstanceOf[Option[Long]]
//    val cronExpressOp = context.getMergedJobDataMap.get(QuartzServer.SCAN_CRON_KEY).asInstanceOf[Option[String]]
    val scopesOp = context.getMergedJobDataMap.get(QuartzServer.SCAN_SCOPE_KEY).asInstanceOf[Option[List[String]]]
    val initFlag = context.getMergedJobDataMap.get(QuartzServer.INIT_KEY).asInstanceOf[AtomicBoolean]
    val intervalMs = if(context.getPreviousFireTime != null ) {
      context.getScheduledFireTime.getTime - context.getPreviousFireTime.getTime
    }else {
      context.getNextFireTime.getTime - context.getScheduledFireTime.getTime
    }

    var timeExpr = if(scopesOp.isDefined) scopesOp.get.last
    else if(intervalMs >= 365 * 24 * 60 * 60 * 1000L) "-1y"//处理一年以上数据
    else if(intervalMs >= 28 * 24 * 60 * 60 * 1000L) "-1M"//处理一月以上数据
    else if(intervalMs >= 7 * 60 * 60 * 1000L) "-1w"//处理一星期以上数据
    else if(intervalMs >= 24 * 60 * 60 * 1000L) "-1d"//处理一天以上数据
    else if(intervalMs >= 60 * 60 * 1000L) "-1h"//处理一小时以上数据
    else if(intervalMs >= 60 * 1000L) "-1m"//处理一分钟以上数据
    else if(intervalMs >= 1 * 1000L) "-1s"//处理一秒以上数据
    else "-1s"//默认处理1秒以上数据

    //服务重启首次添加信号时，需要将信号处理的时间间隔与停止时保持一致，不然后续再loader进行重复更新校验时数据，因为数据范围不一致导致校验失败
    val endTs = if(initFlag.getAndSet(false) && scopesOp.isEmpty && scanSignal.toTs > 0) {
      val eTs = (scanSignal.toTs + 1) + (intervalMs/1000L -1) //下一秒加时间间隔
      logger.warn(s"first time add signal => fromTs : ${scanSignal.toTs + 1}, endTs : $eTs")
      eTs
    }else Times.getEndTime(timeExpr).getTime.getTime/1000L
    val beginTs =  if(scopesOp.isDefined) {//如果数据扫描方式为指定时间段，直接获取开始时间作为起始点
      Times.getBeginTime(scopesOp.get.head, scanSignal.toTs * 1000L).getTime.getTime / 1000L
    }else {
      if(scanSignal.toTs > 0) {//否则接续上一次处理
        scanSignal.toTs + 1
      }else {
        endTs - intervalMs/1000L + 1
      }
    }



    scanSignal.signalId = scanSignal.signalId + 1
    scanSignal.fromTs = beginTs
    scanSignal.toTs = endTs

    scanSignal.from = DateUtils.getDateYYYYMMDDHHMMSS(new Date(scanSignal.fromTs * 1000))
    scanSignal.to = DateUtils.getDateYYYYMMDDHHMMSS(new Date(scanSignal.toTs * 1000))

    val newSignal = scanSignal.copy()
    map.put(newSignal.signalId, newSignal)
    query.offer(newSignal.signalId)
    logger.info(s"add new signal : (${query.size()})$newSignal")
  }
}