package com.hframework.datacenter.hamster.alarmer

import java.util
import java.util.Calendar
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

import com.hframe.hamster.node.HamsterConfig
import com.hframe.hamster.node.cannal.bean.HeartBreak
import com.hframework.common.client.http.HttpClientUtils
import com.hframework.common.frame.ServiceFactory
import com.hframework.common.util.math.FibonacciUtils
import com.hframework.common.util.message.JsonUtils
import com.hframework.utils.scala.{Logging, HamsterContextInitializer}
import com.hframework.datacenter.hamster.monitor.db.DeployJobNodeMeta
import com.hframework.datacenter.hamster.monitor.db.JobRegistry.{deploymentDetailSV, jobSV}
import com.hframework.datacenter.hamster.monitor.zk.listeners._
import com.hframework.datacenter.hamster.monitor.zk.{ZKDataMonitor, ZKNodeMonitor}
import com.hframework.datacenter.hamster.zookeeper.ZKPathUtils._
import com.hframework.datacenter.hamster.zookeeper.{ZKClient, ZKPathUtils}
import com.hframework.hamster.cfg.domain.model.{CfgDeployment, CfgDeploymentDetail, CfgDeploymentDetail_Example, CfgJob_Example}
import com.hframework.hamster.cfg.service.interfaces.ICfgDatasourceSV
import org.apache.commons.lang.exception.ExceptionUtils
import org.apache.http.message.BasicNameValuePair

import scala.collection.JavaConverters._
import scala.collection.mutable

object HamsterAlarmer extends Logging{

  def main(args: Array[String]): Unit = {
    try{
      logger.info("hamster spring context startup ..")
      ServiceFactory.initContext(HamsterContextInitializer.context)
      logger.info("hamster spring context startup ok ..")

      val delayAlarmerInstance = new DelayAlarmer
      logger.info("delay alarmer startup")
      delayAlarmerInstance.start

      val instance =  new HamsterAlarmer
      logger.info("hamster scheduler startup ..")
      instance.start
      Runtime.getRuntime.addShutdownHook(new Thread() {
        override def run(): Unit = {
          logger.info("hamster alarmer shutdown ..")
          instance.shutdown()
          logger.info("hamster alarmer shutdown ok ..")
        }
      })
      logger.info("hamster alarmer startup ok ..")
      while(true)
        Thread.sleep(1000 * 10L)
    }catch{
      case e: Exception => logger.error(s"hamster alarmer startup error, ${ExceptionUtils.getFullStackTrace(e)}")
    }

  }
}
trait AlarmState
case object DoAlarm extends AlarmState
case object DoLastAlarm extends AlarmState
case object DoRestart extends AlarmState
case object DoResolved extends AlarmState
case object DoNothing extends AlarmState

object AlarmEngine{
  val alarmCounter = mutable.Map.empty[String, Int]
}

/**
  *  通过二进制运算匹配报警策略，位定义如下：
  *  | ----------------------------------------------------------------------|
  *  |     1            1          1     1       1       1      1      1     |
  *  |     ↑           ↑         ↑    ↑      ↑      ↑     ↑     ↑     |
  *  | max-times  block-restart   pow   fib   delay   block   rest   work    |
  *  |-----------------------------------------------------------------------|
  */
case class AlarmEngine(cfgString: String = null) extends Logging {
  def restart() = {

  }

  def getStartAlarmMinute(alarmType: String): Int = {
    val alarmTypeInt = parseItem(alarmType)._1
    val timePeriod = if(isRestTime) parseItem("rest")._1 else parseItem("work")._1
    val cfgMaps = cfgMap.filter(kv => {
      (kv._1 | alarmTypeInt | timePeriod) == kv._1
    })
    if(cfgMaps.isEmpty) Int.MaxValue else
    cfgMaps.head._2.getOrElse(parseItem("timeout\\(\\d+\\)")._1, "1").toInt
  }

  def check(processKey: String, alarmType: String, delayMinutes: Long): AlarmState = {
    val alarmTypeInt = parseItem(alarmType)._1
    val timePeriod = if(isRestTime) parseItem("rest")._1 else parseItem("work")._1
    val matchCfgs = cfgMap.filter(kv => {
      (kv._1 | alarmTypeInt | timePeriod) == kv._1
    })
    if(matchCfgs.isEmpty) return DoNothing

    val matchCfg = matchCfgs.head

    val startAlarmMinute = getStartAlarmMinute(alarmType)
    val maxAlarmTimes = getMaxAlarmTimes(matchCfg._1, matchCfg._2)
    val checkResult = if(delayMinutes >= startAlarmMinute) {
      if(isMatch(matchCfg._1, "fib")){
        FibonacciUtils.isMatch((delayMinutes - startAlarmMinute + 1 ) / 1)
      }else if(isMatch(matchCfg._1, "pow\\(\\d+\\)")){
        val powerRoot = matchCfg._2.getOrElse(parseItem("pow\\(\\d+\\)")._1, "2").toInt
        val exponent = (Math.log(delayMinutes - startAlarmMinute + 1)/Math.log(powerRoot)).toInt
        Math.pow(powerRoot, exponent) == delayMinutes - startAlarmMinute + 1
      }else {
        throw new RuntimeException("neither fib nor pow(*) are set !")
      }
    }else false

    val alarmCounter = AlarmEngine.alarmCounter
    val counterKey = s"${processKey}_${alarmType}"
    if(checkResult) {
      if(alarmCounter.getOrElse(counterKey, 0) == maxAlarmTimes){
        alarmCounter += counterKey -> (alarmCounter.getOrElse(counterKey, 0) + 1)
        if(isMatch(matchCfg._1, "restart")) DoRestart else DoLastAlarm
      }else if(alarmCounter.getOrElse(counterKey, 0) > maxAlarmTimes){
        alarmCounter += counterKey -> (alarmCounter.getOrElse(counterKey, 0) + 1)
        DoNothing
      }else {
        alarmCounter += counterKey -> (alarmCounter.getOrElse(counterKey, 0) + 1)
        DoAlarm
      }
    }else {
      if(delayMinutes < startAlarmMinute && alarmCounter.contains(counterKey)) {
        alarmCounter -= counterKey
        DoResolved
      }else DoNothing
    }
  }

  def isMatch(alarmInt: Int, keyword: String): Boolean = {
    (alarmInt | parseItem(keyword)._1) == alarmInt
  }



  def getMaxAlarmTimes(cfgInt: Int, ext: Map[Int, String]): Int = {
    ext.getOrElse(parseItem("max\\(\\d+\\)")._1, "99999999").toInt
  }

  def isRestTime(): Boolean ={
    val cal = Calendar.getInstance()
    val hour = cal.get(Calendar.HOUR_OF_DAY)
    if(hour < 9  || hour >= 18){
      return true
    }
    val week = cal.get(Calendar.DAY_OF_WEEK)
    if(week == Calendar.SATURDAY || week == Calendar.SUNDAY) {
      return true
    }
    return false
  }

  val bitDefines = Array("work", "rest", "block", "delay", "timeout\\(\\d+\\)", "fib", "pow\\(\\d+\\)", "max\\(\\d+\\)", "restart")
  val cfgMap = if(cfgString != null) {
    cfgString.split(",").filterNot(_.trim.isEmpty).map(info => {
      val configMap = info.split("\\.").map(parseItem(_)).toMap
      (configMap.map(_._1).reduce(_ | _),
        configMap.filterNot(_._2 == null))
    }).toMap
  }else Map.empty[Int, Map[Int, String]]



  def parseItem(_item: String):(Int, String) = {
    val item = _item.trim

    val (index, itemExtNum) = if(item.isEmpty){
      (0, null.asInstanceOf[String])
    }else if(bitDefines.contains(item)) {
      (bitDefines.indexOf(item), null.asInstanceOf[String])
    }else {
      val matchOp = bitDefines.find(exp => item.matches(exp))
      if(matchOp.isDefined) {
        val exp = matchOp.get
        (bitDefines.indexOf(exp), item.substring(item.indexOf("(") + 1, item.indexOf(")")))
      }else {
        throw new RuntimeException(s"$item is not supported !")
      }
    }
    val itemBitNum = Math.pow(2, index).toInt
    (itemBitNum, itemExtNum)
  }

  logger.info(s"alarm engine init => $cfgString, $cfgMap")
}

class HamsterAlarmer extends Logging{

  val deployMonitor = new ZKNodeMonitor("process_root", getDeploymentConfigRootPath)
  deployMonitor.addListener(new ZKNodeWrapperListener("deploy_root") {
//    override def getParentListener: ZKNodeListener =  new ZKNodeWrapperListener("deploy_detail") {
      override def getParentListener: ZKNodeListener = new ZKNodeSimpleListener {
        override def nodeCreate(paths: List[String]): Unit = {
          paths.foreach(nodeDataChange(_))
        }

        override def nodeDataChange(path: String): Unit = {
          val deploymentInfo = ZKClient.readData(path, classOf[CfgDeployment])
          logger.info(s"deployment info update => $path, ${JsonUtils.writeValueAsString(deploymentInfo)}")
          deploymentInfoCache.put(path, (deploymentInfo, AlarmEngine(deploymentInfo.getAlarmStrategy)))
        }
      }
//    }
  })
  val selectorsMonitor = new ZKNodeMonitor("process_root", getProcessRootPath)
  selectorsMonitor.addListener(new ZKNodeWrapperListener("deploy_root") {//root
    override def getParentListener: ZKNodeListener = new ZKNodeWrapperListener("jgroups_or_jobs") {//jobtemplate
      override def getParentListener: ZKNodeListener = new ZKNodeWrapperListener("selector", false) {//jobInfo
        override def getParentListener: ZKNodeListener = new ZKNodeSimpleListener {//jobNode
          override def nodeCreate(selectorPaths: List[String]): Unit = {
            selectorPaths.foreach(selectorPath => {
              logger.info(s"[${selectorPath}] add ! ")
              nodeDataChange(selectorPath)
            })
          }

          override def nodeDelete(selectorPath: String): Unit = {
            logger.info(s"[${selectorPath}] remove ! ")
            jobGroupHeartbeatCache.remove(selectorPath)
          }

          override def nodeDataChange(selectorPath: String): Unit = {
            try{
              val heartBreak = ZKClient.readData(selectorPath, classOf[HeartBreak])
              logger.info(s"$selectorPath data = $heartBreak")
              val fetchTimeStamp = heartBreak.getFetchTimeStamp
              jobGroupHeartbeatCache.put(selectorPath, fetchTimeStamp)
              if(heartBreak.getExecuteTimeStamp > 0) {
                if(jobGroupExecuteCache.containsKey(selectorPath)) {
                  val target = jobGroupExecuteCache.get(selectorPath)
                  target.setFetchTimeStamp(heartBreak.getFetchTimeStamp)
                  target.setExecuteTimeStamp(heartBreak.getExecuteTimeStamp)
                }else {
                  jobGroupExecuteCache.put(selectorPath, heartBreak)
                }
              }
            }catch {
              case _ => logger.warn(s"${ExceptionUtils.getFullStackTrace(_)}")
            }
          }
        }
      }
    }
  })

  val bootstrapInvoke = new AtomicBoolean(true)
  val zkConfigPathCheckLists = new util.ArrayList[String]

  val jobGroupMonitorCache = new ConcurrentHashMap[String, ZKDataMonitor]()
  val jobGroupHeartbeatCache = new  ConcurrentHashMap[String, Long]()
  val jobGroupExecuteCache = new  ConcurrentHashMap[String, HeartBreak]()
  val deploymentInfoCache = new  ConcurrentHashMap[String, (CfgDeployment, AlarmEngine)]()
  val restartCache = new ConcurrentHashMap[String, Long]()

  def start: Unit ={
    initZK
    deployMonitor.setup
    selectorsMonitor.setup
    checkCycle
  }

  def checkCycle: Unit ={
    val defaultDelayOrBlockTime = HamsterConfig.getInstance.getHamsterTaskProcessingThreshold.toLong
    val noticePushUrl = HamsterConfig.getInstance.getItilAlarmPushUrl
    val env = HamsterConfig.getInstance.getHamsterEnv
    val envName = if(env == "debug" || env == "test") "【测试环境】" else if(env == "dev") "【开发环境】" else "【线上环境】"
    while (true) {
      Thread.sleep(1000L * 60) //一分钟的扫描间隔

      for (selectorProcessPath <- jobGroupHeartbeatCache.asScala.keySet) {
        try{
          val isBlock = checkBlock(selectorProcessPath, noticePushUrl, defaultDelayOrBlockTime, envName)
          if(!isBlock) {
            checkDelay(selectorProcessPath, noticePushUrl, defaultDelayOrBlockTime, envName)
          }
        }catch {
          case e: Exception => {
            logger.error(s"check cycle error => ${ExceptionUtils.getFullStackTrace(e)}")
          }
        }
      }
      logger.warn(s"alarm counter dump: ${AlarmEngine.alarmCounter}")
    }
  }

  def sendMessage(tag: String, selectorProcessPath: String, delayMinutes: Long,
                  envName: String, noticePushUrl: String, extMessage: String = null) = {
    try {
      val (deployName, deployCode) = getDeploymentInfo(selectorProcessPath)
      val dbName = getDbName(selectorProcessPath)
      HttpClientUtils.post(noticePushUrl, new util.ArrayList[BasicNameValuePair]() {{
        add(new BasicNameValuePair("type", "hamster"))
        add(new BasicNameValuePair("title", s"${envName}hamster队列$tag（名称：${deployName}； " +
          s"编码：${deployCode}；数据库：${dbName}）"))
        add(new BasicNameValuePair("content", s"监控路径[$selectorProcessPath]，" +
          s"${tag}时长${delayMinutes}分钟${if(extMessage != null && extMessage.isDefinedAt(0)) s"，$extMessage" else ""}"))
      }})
    } catch {
      case throwable: Throwable =>
        logger.error(s"itil alarm push error => ${ExceptionUtils.getFullStackTrace(throwable)}")
    }
  }


  def restart(selectorProcessPath: String): Boolean = {

    val canRestart = if(restartCache.containsKey(selectorProcessPath)){//一个小时内就出问题，说明重启不好使，不用再重启了
      restartCache.get(selectorProcessPath) + 1 * 60 * 60 * 1000 < System.currentTimeMillis()
    }else true

    if(canRestart) {
      val deploymentCode = selectorProcessPath.substring("/hamster/process/".size, selectorProcessPath.indexOf("/jgroups/"))
      val deployPath = ZKPathUtils.getDeploymentConfigPath(deploymentCode)
      val deployment = deploymentInfoCache.get(deployPath)._1
      val selectKey = selectorProcessPath.substring(selectorProcessPath.lastIndexOf("/") + 1)
      val aboutJobKeys = ZKClient.getChildren(deployPath).asScala.map(nodeKey => {
        val data = ZKClient.readData(deployPath + "/" + nodeKey, classOf[DeployJobNodeMeta])
        if(data.selectKey == selectKey){
          data.jobKey
        }else null
      }).filterNot(_ == null).distinct

      val aboutJobIds = aboutJobKeys.flatMap(jobKey => {
        jobSV.getCfgJobListByExample({
          val example = new CfgJob_Example()
          example.createCriteria().andCodeEqualTo(jobKey.substring(jobKey.lastIndexOf(".") + 1))
          example}).asScala
          .map(_.getId)
      }).distinct

      logger.info(s"restart jobs : [${aboutJobKeys.mkString(", ")}], [${aboutJobIds.mkString(", ")}]")

      deploymentDetailSV.updateByExample({
        val deployment = new CfgDeploymentDetail()
        deployment.setStatus(3.toByte)
        deployment},{
        val example = new CfgDeploymentDetail_Example()
        example.createCriteria().andDeploymentIdEqualTo(deployment.getId).andJobIdIn(aboutJobIds.asJava)
        example
      })

      Thread.sleep(10 * 1000L)

      val result = deploymentDetailSV.updateByExample({
        val deployment = new CfgDeploymentDetail()
        deployment.setStatus(2.toByte)
        deployment},{
        val example = new CfgDeploymentDetail_Example()
        example.createCriteria().andDeploymentIdEqualTo(deployment.getId).andJobIdIn(aboutJobIds.asJava)
        example
      })
      restartCache.put(selectorProcessPath, System.currentTimeMillis())
      result > 0
    }else {
      logger.warn(s"restart jobs : $selectorProcessPath failed, restart less than 1 hour !")
      false
    }
  }

  def checkBlock(selectorProcessPath: String, noticePushUrl: String, defaultBlockTime: Long, envName: String): Boolean = {
    val lastReportTimeStamp = jobGroupHeartbeatCache.get(selectorProcessPath)
    if(lastReportTimeStamp > 0) {
      val delayMinutes = (System.currentTimeMillis - lastReportTimeStamp) / 1000 / 60

      if(delayMinutes >=1) {
        getAlarmEngine(selectorProcessPath).check(selectorProcessPath, "block", delayMinutes) match {
          case DoAlarm => {
            logger.warn(s"[${selectorProcessPath}]: block about ${delayMinutes} minutes !")
            sendMessage("阻塞", selectorProcessPath, delayMinutes, envName, noticePushUrl)
            true
          }
          case DoLastAlarm => {
            logger.warn(s"[${selectorProcessPath}]: block about ${delayMinutes} minutes !")
            sendMessage("阻塞", selectorProcessPath, delayMinutes, envName, noticePushUrl, "报警已达阈值！")
            true
          }
          case DoRestart => {
            logger.warn(s"[${selectorProcessPath}]: block about ${delayMinutes} minutes and restart .. !")
            val result = restart(selectorProcessPath)
            if(result){
              sendMessage("阻塞", selectorProcessPath, delayMinutes, envName, noticePushUrl, "开始进行自动重启尝试..")
            }else {
              sendMessage("阻塞", selectorProcessPath, delayMinutes, envName, noticePushUrl, "尝试自动重启失败！")
            }
            true
          }
          case DoResolved => {
            sendMessage("阻塞恢复", selectorProcessPath, delayMinutes, envName, noticePushUrl)
            true
          }

          case _ => false
        }
      }else{
        val counterKey = s"${selectorProcessPath}_block"
        if(AlarmEngine.alarmCounter.contains(counterKey)) {
          AlarmEngine.alarmCounter -= counterKey
          sendMessage("阻塞恢复", selectorProcessPath, delayMinutes, envName, noticePushUrl)
        }
        false
      }
    }else false
  }



//  def isIgnoreTimePeriod(): Boolean ={
//    val cal = Calendar.getInstance()
//    val hour = cal.get(Calendar.HOUR_OF_DAY)
//    if(hour < 8  || hour >= 20){
//      return true
//    }
//    val week = cal.get(Calendar.DAY_OF_WEEK)
//    if(week == Calendar.SATURDAY || week == Calendar.SUNDAY) {
//      return true
//    }
//    return false
//  }

  def resolvedDelay(selectorProcessPath: String, noticePushUrl: String, heartBreak: HeartBreak,  envName: String): Unit ={
    val counterKey = s"${selectorProcessPath}_delay"
    if(AlarmEngine.alarmCounter.contains(counterKey)) {
      AlarmEngine.alarmCounter -= counterKey
    }
    if(heartBreak.isDelay) {
      val delayOccurMinutes = (System.currentTimeMillis - heartBreak.getDelayOccurTimeStamp) / 1000 / 60
      logger.warn(s"[${selectorProcessPath}]: delay is over !")
      sendMessage("延迟解除", selectorProcessPath, delayOccurMinutes, envName, noticePushUrl)
    }
    heartBreak.removeDelay
  }

  def checkDelay(selectorProcessPath: String, noticePushUrl: String, defaultDelayTime: Long,  envName: String): Unit = {
    if(jobGroupExecuteCache.containsKey(selectorProcessPath)) {
      val heartBreak = jobGroupExecuteCache.get(selectorProcessPath)
      val executeTimestamp = heartBreak.getExecuteTimeStamp
      val isDelay = heartBreak.isDelay
      if(executeTimestamp > 0) {
        try {
          val delayMinutes = (heartBreak.getFetchTimeStamp - executeTimestamp) / 1000 / 60
          if(delayMinutes >= 1) {
            val startAlarmMinute = getAlarmEngine(selectorProcessPath).getStartAlarmMinute("delay")
            if (delayMinutes >= startAlarmMinute) {//判断延迟报警阈值
              heartBreak.setDelay
              val delayOccurMinutes = (System.currentTimeMillis - heartBreak.getDelayOccurTimeStamp) / 1000 / 60

              getAlarmEngine(selectorProcessPath).check(selectorProcessPath, "delay", delayOccurMinutes + startAlarmMinute) match {
                case DoAlarm => {
                  logger.warn(s"[${selectorProcessPath}]: occur about $delayOccurMinutes minutes, " +
                    s"delay about ${delayMinutes} minutes !")
                  sendMessage("延迟", selectorProcessPath, delayMinutes, envName, noticePushUrl, s"延迟已发生${delayOccurMinutes}分钟")
                }
                case DoRestart | DoLastAlarm => {//延迟不进行restart处理
                  logger.warn(s"[${selectorProcessPath}]: occur about $delayOccurMinutes minutes, " +
                    s"delay about ${delayMinutes} minutes !")
                  sendMessage("延迟", selectorProcessPath, delayMinutes, envName, noticePushUrl, s"延迟已发生${delayOccurMinutes}分钟,报警已达阈值！")
                }
              }
            }else resolvedDelay(selectorProcessPath, noticePushUrl, heartBreak, envName)
          }else resolvedDelay(selectorProcessPath, noticePushUrl, heartBreak, envName)

        } catch {
          case throwable: Throwable =>
            logger.error(s"itil alarm push error => ${ExceptionUtils.getFullStackTrace(throwable)}")
        }
      }
    }

  }

  def getDeploymentInfo(selectorProcessPath: String): (String, String) = {
    val deploymentCode = selectorProcessPath.substring("/hamster/process/".size, selectorProcessPath.indexOf("/jgroups/"))
    val deployPath = ZKPathUtils.getDeploymentConfigPath(deploymentCode)
    val deployment = deploymentInfoCache.get(deployPath)._1
    (deployment.getName, deployment.getCode)
  }

  def getAlarmEngine(selectorProcessPath: String): AlarmEngine= {
    val deploymentCode = selectorProcessPath.substring("/hamster/process/".size, selectorProcessPath.indexOf("/jgroups/"))
    val deployPath = ZKPathUtils.getDeploymentConfigPath(deploymentCode)
    if(deploymentInfoCache.get(deployPath) == null) return null else
    deploymentInfoCache.get(deployPath)._2
  }
  def getDbName(selectorProcessPath: String): String= {
    val dbIdOp = selectorProcessPath.split("\\.").toList.find(_.matches("\\d+"))
    if(dbIdOp.isDefined) {
      val dbId = dbIdOp.get.toLong
      val dataSource = ServiceFactory.getService(classOf[ICfgDatasourceSV]).getCfgDatasourceByPK(dbId)
      if(dataSource != null) dataSource.getDb else null
    }else null
  }



//  def getTaskKeyInfo(selectorProcessPath: String) = {
//    val deploymentCode = selectorProcessPath.substring("/hamster/process/".size, selectorProcessPath.indexOf("/jgroups/"))
//    val deployPath = ZKPathUtils.getDeploymentConfigPath(deploymentCode)
//
//    val dbIdOp = selectorProcessPath.split("\\.").toList.find(_.matches("\\d+"))
//    val dbName = if(dbIdOp.isDefined) {
//      val dbId = dbIdOp.get.toLong
//      val dataSource = ServiceFactory.getService(classOf[ICfgDatasourceSV]).getCfgDatasourceByPK(dbId)
//      if(dataSource != null) dataSource.getDb else null
//    }else null
//
////    ZKClient.readData(deployPath, classOf[CfgDeployment])
//    val deployment = deploymentInfoCache.get(deployPath)._1
//    (deployment.getName, deployment.getCode, dbName)
//  }



  def initZK: Unit = ZKClient.createPersistentIfNotExists(getProcessRootPath, createParents = true)

  def shutdown(): Unit ={
    selectorsMonitor.shutdown
    deployMonitor.shutdown()
    jobGroupMonitorCache.asScala.values.foreach(_.shutdown())
  }
}