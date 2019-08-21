package com.hframework.datacenter.hamster.worker.tasks.delete
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.{ArrayList, Calendar, Date}
import java.util.concurrent.atomic.AtomicBoolean

import util.control.Breaks._
import com.hframework.common.frame.ServiceFactory
import com.hframework.datacenter.hamster.workes.bean.BatchDataSet
import com.hframework.smartsql.client.DBClient
import com.hframework.smartsql.client.exceptions.DBUpdateException
import com.hframework.datacenter.hamster.exceptions.{TaskInitializationException, TaskRunningException}
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.tasks.select.QuartzServer
import com.hframework.datacenter.hamster.worker.tasks.AbstractSelector
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.MysqlDelete
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.MysqlDelete.{batchSizeCfg, scanInterval, scanTimeField, _}
import com.hframework.datacenter.hamster.workes.bean.{DataField, DataRow, DataSet}
import com.hframework.datacenter.hamster.zookeeper.ZKPathUtils

import collection.JavaConverters._
import com.hframework.hamster.cfg.service.interfaces.{ICfgDatasourceSV, ICfgDataviewSV}

object MysqlDeleter{

  val globalEmptyArray =  Array.empty[Object]
  def registerDatabase(dbId: Long): (String, String) ={
    val dbKey = dbId.toString
    val dataSource = ServiceFactory.getService(classOf[ICfgDatasourceSV]).getCfgDatasourceByPK(dbId)
    DBClient.registerDatabase(dbKey, s"jdbc:mysql://${dataSource.getUrl}/${dataSource.getDb}?useUnicode=true&tinyInt1isBit=false",
      dataSource.getUsername, dataSource.getPassword)
    DBClient.setCurrentDatabaseKey(dbKey)
    (dbKey, dataSource.getDb)
  }

}
class MysqlDeleter(prototypeKey: String,
                   deployMeta: DeployJobNodeMeta,
                   var __jobNodeMeta: JobNodeMeta,
                   var __dependencyJobNode: scala.List[JobNodeMeta] = Nil)
  extends AbstractSelector(prototypeKey,deployMeta, __jobNodeMeta, __jobNodeMeta.jobMeta,
    ZKPathUtils.getJobGroupProcessPath(deployMeta.deployCode, __jobNodeMeta.jobMeta.selectKey), __dependencyJobNode) {

  val (id_, update_time_, create_time_) = ("id_", "update_time_", "create_time_")

  val globalEmptyArray =  Array.empty[Object]
  @volatile var (dbKey, dbName, dbObject, sqlTemplates,intervalOp,cronExpressOp, scanTimeField, filterExpression,historyIntervalSecond,batchSizeConf,backupChoice) = parse
  val initQuartzServer = new AtomicBoolean(false)
  val quartzServer = new QuartzServer(deployCode, __jobNodeMeta.jobMeta.selectKey)
  quartzServer.selectorMonitorPath = finalWatchPath

  def parse:(String, String, String, String, Option[Long],Option[String], String,String,Long,Long,String) = {
    val backupChoice = jobNode.getString(MysqlDelete.backupChoice)
    val batchSizeConf = jobNode.getLong(MysqlDelete.batchSizeCfg)
    val filterExpression = jobNode.getString(MysqlDelete.filterExpression)
    val filterFinal = if (filterExpression == "") filterExpression else "  and " + filterExpression
    val dbId = jobNode.getLong(sourceDB)
    val (dbKey, dbName) = MysqlDeleter.registerDatabase(dbId)
    val historyInterval = jobNode.getString(MysqlDelete.historyInterval)
    val historyIntervalSecond = parseWhereTimeInterval(historyInterval).get
    val timerIntervalConf = jobNode.getString(MysqlDelete.scanInterval)
    val (intervalOp, cronExpressOp) = parseTimerInterval(timerIntervalConf)
    val dbObject = jobNode.getString(sourceTable)
    val scanTimeField = {
      val scanTimeField = jobNode.getString(MysqlDelete.scanTimeField, 0, false)
      if(scanTimeField == null || scanTimeField.isEmpty) update_time_ else scanTimeField.trim
    }
    val sqlTemplates = s"delete  FROM $dbObject    %s order by id_ limit ${batchSizeConf}"
    (dbKey, dbName, dbObject, sqlTemplates,intervalOp,cronExpressOp, scanTimeField, filterFinal,historyIntervalSecond,batchSizeConf,backupChoice)
  }

  def parseInterval(interval:String):Option[Long] = {
    val intervalTmp = interval.take(interval.length - 1).toInt
    val unit = interval.last
    val internalSecond = unit match {
      case 's' => intervalTmp
      case 'm' => intervalTmp * 60
      case 'h' => intervalTmp * 60 * 60
      case 'd' => intervalTmp * 60 * 60 * 24
      case 'w' => intervalTmp * 60 * 60 * 24 * 7
      case 'M' => intervalTmp * 60 * 60 * 24 * 30
      case 'y' => intervalTmp * 60 * 60 * 24 * 365
    }
    Some(internalSecond)
  }
  def parseWhereTimeInterval(interval:String):Option[Long] = {
    val intervalTmp = interval.take(interval.length - 1).toInt
    val unit = interval.last
    val currentCal:Calendar = Calendar.getInstance()
    val currentTimeStamp = currentCal.getTime.getTime
    val internalSecond = unit match {
      case 's' => intervalTmp
      case 'm' => intervalTmp * 60
      case 'h' => intervalTmp * 60 * 60
      case 'd' => intervalTmp * 60 * 60 * 24
      case 'w' => intervalTmp * 60 * 60 * 24 * 7
      case 'M' => {currentCal.add(Calendar.MONDAY, -intervalTmp)
        val lastMonth : Calendar = currentCal
        (currentTimeStamp - lastMonth.getTime.getTime)/1000
      }
      case 'y' => {currentCal.add(Calendar.YEAR, -intervalTmp)
        val lastMonth : Calendar = currentCal
        (currentTimeStamp - lastMonth.getTime.getTime)/1000
      }
    }
    Some(internalSecond)
  }

  def parseTimerInterval(scanInterval: String): (Option[Long], Option[String]) = {
    if(scanInterval.matches("\\d+(s|m|h|d|w|M|y)")) {
      (parseInterval(scanInterval), None)
    }else if(scanInterval.matches("([0-9,-*/?]+ ){6,7}")){
      (None, Some(scanInterval))
    }else {
      new TaskRunningException(s"bucketInterval[${scanInterval}] illegal!")
      (None, None)
    }
  }
  def checkTableExists(_table: String): Boolean = {
    val tables = DBClient.executeQueryList("show tables", globalEmptyArray).asScala
    tables.find(table => table.asScala.head == _table).isDefined
  }
  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this.__jobNodeMeta = newJobNodeMeta
    val result = parse
    dbKey = result._1
    dbName = result._2
    dbObject = result._3
    sqlTemplates = result._4
    intervalOp = result._5
    cronExpressOp = result._6
    scanTimeField = result._7
    filterExpression = result._8
    historyIntervalSecond = result._9
    batchSizeConf = result._10
    backupChoice = result._11
    if(initQuartzServer.get()) {
      quartzServer.reload(intervalOp, cronExpressOp, None)
    }
  }
  def scanTimeFieldType[T](timeField: T):String ={
    timeField match {
        case x:Timestamp => "date"
        case y:Integer =>"sec"
        case _=>throw new Exception("unsupported Type of scanTimeField")
      }
  }


  def timeCondition(timeType:String,timeUpperLimit:Long): String ={
    val whereCondition = s"where ${scanTimeField} < "
    timeType match{
      case "sec" => whereCondition + (timeUpperLimit ).toString
//      case "msec" => whereCondition + (timeUpperLimit * 1000 ).toString
      case "date" => {
        val fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val timeStr = fm.format(new Date(timeUpperLimit * 1000))
        whereCondition + s"'${timeStr}'"
      }
      case _ =>throw new Exception("unsupported Type of scanTimeField")
    }
  }
  def backupName(tableName:String): (String,String,String) ={
    DBClient.setCurrentDatabaseKey(dbKey)
    val timesql = s"select min(${scanTimeField}) from $tableName WHERE 1=1  ${filterExpression}"
    val mintime = DBClient.executeQueryList(timesql,globalEmptyArray).get(0).asScala(0)
    val timeType = scanTimeFieldType(mintime)
    if (backupChoice == "backup") {
      //只删除不备份逻辑
      (tableName + "_backup",filterExpression,timeType)
    } else if (backupChoice != "no"){
      //每个批次只处理最小年份（月份）数据，解决同一批次有跨年数据问题
      if (backupChoice == "YYYY"){
        val (timeYearUpperLimit,timeUpperLimit) = getNextYearFirstDay(timeType,mintime)
        (tableName + s"_${timeYearUpperLimit}", s" and ${scanTimeField} < ${timeUpperLimit}" + filterExpression,timeType)
      }else{
        val (timeYearUpperLimit,timeMonthUpperLimit,timeUpperLimit) = getNextMonthFirstDay(timeType,mintime)
        (tableName + s"_${timeYearUpperLimit}${timeMonthUpperLimit}", s" and ${scanTimeField} < ${timeUpperLimit}" + filterExpression,timeType)
      }
    }else {
      ("",filterExpression,timeType)
    }
  }
  def timeFieldToDate[T](timeType:String,timeFiled:T):Date ={
    val timeDateType = timeType match{
      case "date" => timeFiled
      case "sec" => new Date(timeFiled.toString.toLong * 1000)
      //      case "msec" =>new Date(timeFiled.toString.toLong)
    }
    timeDateType.asInstanceOf[Date]
  }
  def timeFieldToFinal(timeType:String,cal:Calendar):String ={
    timeType match{
      case "date" => {
        val df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val timeDate = cal.getTime
        s"'${df.format(timeDate)}'"
      }
      case "sec" => (cal.getTime.getTime / 1000).toString
      //      case "msec" =>new Date(timeFiled.toString.toLong)
    }
  }
  def getNextYearFirstDay[T](timeType:String,timeFiled:T): (Int,String) = {
    val timeDate = timeFieldToDate(timeType,timeFiled)
    val cal:Calendar = Calendar.getInstance()
    cal.setTime(timeDate)
    val timeYearUpperLimit = cal.get(Calendar.YEAR)
    cal.add(Calendar.YEAR,1)
    cal.set(Calendar.MONTH,0)//月份从0开始
    cal.set(Calendar.DAY_OF_MONTH,1)
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE,0)
    cal.set(Calendar.SECOND,0)
    val timeUpperLimit = timeFieldToFinal(timeType,cal)
    (timeYearUpperLimit,timeUpperLimit)
  }
  def getNextMonthFirstDay[T](timeType:String,timeFiled:T): (Int,String,String) = {
    val timeDate = timeFieldToDate(timeType,timeFiled)
    val cal:Calendar = Calendar.getInstance()
    cal.setTime(timeDate)
    val timeYearUpperLimit = cal.get(Calendar.YEAR)
    val timeMonthUpperLimit = cal.get(Calendar.MONTH) +1//月份从0开始
    cal.add(Calendar.MONTH,1)
    cal.set(Calendar.DAY_OF_MONTH,1)
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE,0)
    cal.set(Calendar.SECOND,0)
    val timeUpperLimit = timeFieldToFinal(timeType,cal)
    val timeMonthUpperLimitStr = if (timeMonthUpperLimit > 9) timeMonthUpperLimit.toString  else "0"+timeMonthUpperLimit.toString
    (timeYearUpperLimit,timeMonthUpperLimitStr,timeUpperLimit)
  }
  /**
    * 执行delete，这里只用了QuartzServer的定时调度，忽略lastDownBatch等批次信息
    */
  override def select(): BatchDataSet = {
    if(initQuartzServer.compareAndSet(false, true)) {
      quartzServer.start(intervalOp, cronExpressOp, None)
    }
    do{
      breakable {
        val scanSignal = quartzServer.waitForQuartzSignal
        DBClient.setCurrentDatabaseKey(dbKey)
        //      val historyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((scanSignal.toTs - historyIntervalSecond ) * 1000))
        val (tableBackupName, filterExpressionFinal, timeType) = try {
          backupName(dbObject)
        } catch {
          case e: Exception => {
            quartzServer.signalFinish(scanSignal)
            break()
          }
        }

        val createTableSql = s"create table ${tableBackupName}  like $dbObject  "
        val whereConditionFinal = timeCondition(timeType, scanSignal.toTs - historyIntervalSecond) + filterExpressionFinal
        val insertTableSql = s"insert ignore into ${tableBackupName}  select * from $dbObject  $whereConditionFinal order by id_ limit ${batchSizeConf}" //忽略重复项（若线程突然中断，从头更新）
        val deleteTableSql = sqlTemplates.format(whereConditionFinal)
        var deleteCount = batchSizeConf
        while (deleteCount >= batchSizeConf) {
          if (backupChoice != "no") {
            try {
              logger.info(s"[SQL]${insertTableSql} !")
              DBClient.executeUpdate(insertTableSql, globalEmptyArray)
            } catch {
              case e: DBUpdateException => {
                //表不存在则创建
                if (!checkTableExists(tableBackupName)) {
                  try {
                    logger.info(s"[SQL] ${createTableSql}")
                    DBClient.executeUpdate(createTableSql, globalEmptyArray)
                    DBClient.executeUpdate(insertTableSql, globalEmptyArray)
                  } catch {
                    case e: Exception => {
                      logger.error(s"${e.getMessage}")
                    }
                  }
                }
              }
              case e: Exception => {
                logger.error(s"${e.getMessage}")
              }
            }
            logger.info("transaction of insert is completed !!!")
          }

          try {
            logger.info(s"[SQL]$deleteTableSql")
            deleteCount = DBClient.executeUpdate(deleteTableSql, globalEmptyArray)
          } catch {
            case e: Exception => {
              logger.error(s"${e.getMessage}")
            }
          }
        }

      logger.debug("there is not any data which need to be deleted !")
      quartzServer.signalFinish(scanSignal)
    }
    }while(true)
    val batchDataSet = new BatchDataSet
    batchDataSet.setBatchId(quartzServer.scanSignal.signalId)
    batchDataSet
  }

  /** 销毁逻辑 */
  override protected def destroyInternal(): Unit = {
    quartzServer.stop

  }
  override def ackTermi(messageId: Long): Unit = {
    //quartzServer.ack(messageId)
  }

}
