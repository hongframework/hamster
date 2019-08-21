package com.hframework.datacenter.hamster.worker.tasks.select

import java.sql.{SQLTimeoutException, Timestamp}
import java.util
import java.util.Date
import java.util.concurrent.atomic.AtomicBoolean

import com.hframe.hamster.node.cannal.bean.EventType
import com.hframe.hamster.node.monitor.bean.ProcessEventData
import com.hframework.common.frame.ServiceFactory
import com.hframework.common.monitor.{ConfigMapMonitor, ConfigMonitor, Monitor, MonitorListener}
import com.hframework.common.util.DateUtils
import com.hframework.datacenter.hamster.workes.bean.{BatchDataSet, DataField, DataRow, DataSet}
import com.hframework.smartsql.client.DBClient
import com.hframework.smartsql.client.exceptions.DBQueryException
import com.mysql.jdbc.exceptions.MySQLTimeoutException
import com.hframework.datacenter.hamster.exceptions.{TaskInitializationException, TaskRunningException}
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.{SPELUtil, Times}
import com.hframework.datacenter.hamster.worker.tasks.AbstractSelector
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.MysqlScan
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.MysqlScan.{batchSizeCfg, dataScanScope, scanInterval, scanTimeField, _}
import com.hframework.datacenter.hamster.workes.bean.{DataField, DataRow, DataSet}
import com.hframework.datacenter.hamster.zookeeper.ZKPathUtils
import com.hframework.datacenter.utils.DBUtils
import com.hframework.hamster.cfg.domain.model.CfgDataview_Example
import com.hframework.hamster.cfg.service.interfaces.{ICfgDatasourceSV, ICfgDataviewSV}
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.exception.ExceptionUtils

import scala.collection.JavaConverters._
import scala.collection.mutable

object MysqlScanSelector{

  val globalEmptyArray =  Array.empty[Object]
  def registerDatabase(dbId: Long): (String, String) ={
    val dbKey = dbId.toString
    val dataSource = ServiceFactory.getService(classOf[ICfgDatasourceSV]).getCfgDatasourceByPK(dbId)
    DBClient.registerDatabase(dbKey, s"jdbc:mysql://${dataSource.getUrl}/${dataSource.getDb}?useUnicode=true&tinyInt1isBit=false",
      dataSource.getUsername, dataSource.getPassword)
    DBClient.setCurrentDatabaseKey(dbKey)
    (dbKey, dataSource.getDb)
  }

  def parseDbObject(dbObject: String, dbId: Long): (List[String], List[String], Option[String]) ={
    val dbViewConfig = ServiceFactory.getService(classOf[ICfgDataviewSV]).getCfgDataviewListByExample({
      val example = new CfgDataview_Example
      example.createCriteria().andViewNameEqualTo(dbObject).andCfgDatasourceIdEqualTo(dbId)
      example
    })
    val viewSql = if(dbViewConfig.asScala.length > 0) Some(dbViewConfig.asScala.head.getViewSql) else None
    val (allCfgTables, mainCfgTables) = if(viewSql.isDefined) {
      val viewConfig = dbViewConfig.get(0)
      val mainTables =  if(StringUtils.isNotBlank(viewConfig.getMainTables)) {
        viewConfig.getMainTables.split(",").dropWhile(_.isEmpty).map(_.trim).distinct.toList
      }else Nil
      val tables = if(StringUtils.isNotBlank(viewConfig.getTables)) {
        viewConfig.getTables.split(",").dropWhile(_.isEmpty).map(_.trim).distinct.toList
      }else Nil
      val allTables = (mainTables ::: tables).distinct
      (allTables, mainTables)
    }else {
      (List(dbObject), List(dbObject))
    }
    (allCfgTables, mainCfgTables, viewSql)
  }

  def getAllSubTables(tables: List[String]): List[(String, List[String])] ={
    val dbTables = DBClient.executeQueryList("show tables", globalEmptyArray).asScala.map(_.asScala.head.asInstanceOf[String])

    val allCfgTableSubs = tables.map(table => {
      val isNormalTable = table.matches("[a-zA-Z0-9_]+")
      (table, if(!isNormalTable) getSubTables(dbTables, table) else List(table))
    })
    allCfgTableSubs
  }

  def getSubTables(dbTables: mutable.Buffer[String], table: String): List[String] = {
    dbTables.filter(t => {
      val exprOp = SPELUtil.fetchExpress(table)
      if(exprOp.isDefined) {
        val expr = exprOp.get._1
        t.matches(table.replace(expr, ".+"))
      }else t.matches(table)
    }).toList
  }

  def addSelectString(table: String, isSingleTable: Boolean = false): String ={
    s"${if(!isSingleTable) table + "." else ""}create_time_ as ${table}_create_time_, " +
      s"${if(!isSingleTable) table + "." else ""}update_time_ as ${table}_update_time_, " +
      s"${if(!isSingleTable) table + "." else ""}id_ as ${table}_id_"
  }

  def getSqlsByTemplateSql(templateSql: String, allCfgTableSubs: List[(String, List[String])]) = {
    val subSqls = allCfgTableSubs.foldLeft(List((templateSql, List.empty[String])))((sqlEntrys, tablSubCfg) => {
      val parentTable = tablSubCfg._1
      val subTables = tablSubCfg._2
      val regexParentTable = parentTable.replace("$", "\\$").replace("{", "\\{").replace("}", "\\}")
      subTables.flatMap(subTable => {
        sqlEntrys.map(entry => {
          val sql = entry._1.replaceAll(regexParentTable, subTable)
          val tempList = subTable :: entry._2
          (sql, tempList)
        })
      })
    })
    subSqls.map(entry => (entry._1, entry._2.reverse))
  }

  def parseTimerInterval(scanInterval: String, dataScanScopes: List[String]): (Option[Long], Option[String], Option[List[String]]) = {
    val dataScanScopesOp = if(dataScanScopes.contains("auto")) None else {
      val soredList = dataScanScopes.distinct.sortWith((s1,s2) => {
        val diff = Times.timeUnit.indexOf(s1.last) - Times.timeUnit.indexOf(s2.last)
        if(diff != 0) diff > 0 else {
          s1.init.toInt > s1.init.toInt
        }
      })
      Some(soredList)
    }
    if(scanInterval.matches("\\d+(s|m|h|d|w|M|y)")) {
      val interval = scanInterval.take(scanInterval.length - 1).toInt
      val unit = scanInterval.last
      val internalSecond = unit match {
        case 's' => interval
        case 'm' => interval * 60
        case 'h' => interval * 60 * 60
        case 'd' => interval * 60 * 60 * 24
        case 'w' => interval * 60 * 60 * 24 * 7
        case 'M' => interval * 60 * 60 * 24 * 30
        case 'y' => interval * 60 * 60 * 24 * 365
      }
      (Some(internalSecond), None, dataScanScopesOp)
    }else if(scanInterval.matches("([0-9,-*/?]+ ){6,7}")){
      (None, Some(scanInterval), dataScanScopesOp)
    }else {
      new TaskRunningException(s"bucketInterval[${scanInterval}] illegal!")
      (None, None, None)
    }
  }

}

/**
  * Mysql Scan 选择器
  * @param prototypeKey 任务标识
  * @param __jobNodeMeta select任务节点定义
  * @param __dependencyJobNode 关联的extract任务节点
  */
class MysqlScanSelector(prototypeKey: String,
                        deployMeta: DeployJobNodeMeta,
                        var __jobNodeMeta: JobNodeMeta,
                        var __dependencyJobNode: scala.List[JobNodeMeta] = Nil)
  extends AbstractSelector(prototypeKey,deployMeta, __jobNodeMeta, __jobNodeMeta.jobMeta,
    ZKPathUtils.getJobGroupProcessPath(deployMeta.deployCode, __jobNodeMeta.jobMeta.selectKey), __dependencyJobNode){

  val (id_, update_time_, create_time_) = ("id_", "update_time_", "create_time_")



  val globalEmptyArray =  Array.empty[Object]

  @volatile var (dbKey, dbName, dbObject, sqlTemplates, allCfgTables, mainCfgTables, intervalOp, cronExpressOp, dataScanScopeOp, batchSize, scanStartDateOp, scanTimeField) = parse
  @volatile var sqlStruts = sqlTemplates.map(sqlTemplate => (sqlTemplate._1, initSqlStruts(sqlTemplate))).toMap[String, (List[DataField], Integer, Integer, Integer, Integer)]

  val addStartR = s"(\\w+\\.)?${scanTimeField} BETWEEN ".r

  val initQuartzServer = new AtomicBoolean(false)
  val quartzServer = new QuartzServer(deployCode, __jobNodeMeta.jobMeta.selectKey)
  quartzServer.selectorMonitorPath = finalWatchPath

  val tablesChangeMonitor = new ConfigMonitor[List[String]](3){
    override def fetch(): List[String] = {
      DBClient.setCurrentDatabaseKey(dbKey)
      val dbTables = DBClient.executeQueryList("show tables", globalEmptyArray).asScala.map(_.asScala.head.asInstanceOf[String])
      dbTables.toList
    }
  }
  tablesChangeMonitor.addListener(new MonitorListener[List[String]](){
    override def onEvent(monitor: Monitor[List[String]]): Unit = {
      reload(__jobNodeMeta)
    }
  })

  def parse:(String, String, String, List[(String, String)], List[String], List[String], Option[Long], Option[String], Option[List[String]], Long, Option[(String, Long)], String) = {
    val dbId = jobNode.getLong(sourceDB)
    val (dbKey, dbName) = MysqlScanSelector.registerDatabase(dbId)

    val timerIntervalConf = jobNode.getString(scanInterval)
    val dataScanScopeConf = jobNode.getStrings(dataScanScope)
    val (intervalOp, cronExpressOp, dataScanScopesOp) = MysqlScanSelector.parseTimerInterval(timerIntervalConf,dataScanScopeConf)

    val dbObject = jobNode.getString(sourceTable)
    val scanTimeField = {
      val scanTimeField = jobNode.getString(MysqlScan.scanTimeField, 0, false)
      if(scanTimeField == null || scanTimeField.isEmpty) update_time_ else scanTimeField.trim
    }

    val (allCfgTables, mainCfgTables, viewSql) = MysqlScanSelector.parseDbObject(dbObject, dbId)

    val allCfgTableSubs = MysqlScanSelector.getAllSubTables(allCfgTables)
    logger.info(s"all split tables = $allCfgTableSubs")
    val tableNotExistsOp = allCfgTableSubs.find(_._2.isEmpty)
    val sqlAndEntitys: List[(String, List[String])] = if(tableNotExistsOp.isDefined) {//字表暂不存在场景
      logger.warn(s"${tableNotExistsOp.get._1} sub tables not exists !")
      List.empty[(String, List[String])]
    }else if(viewSql.isDefined) {//定义为视图场景
      val viewSqlSegment = SqlParser.parse(viewSql.get.replaceAll("%","%%"))//where name like %变现通% 需要转换为 %%变现通%%，否则format时报错
      viewSqlSegment.addWhere(mainCfgTables.map(table => s"${table}.${scanTimeField} BETWEEN '%s' AND '%s'").mkString(" OR "))
      viewSqlSegment.addSelect(mainCfgTables.map(MysqlScanSelector.addSelectString(_)).mkString(", "))
      viewSqlSegment.addOrderBy(mainCfgTables.map(table => s"$table.${scanTimeField}, $table.id_").mkString(","))
      if(dataScanScopesOp.isEmpty) {//如果没有指定扫描数据段，表明为增量处理，增量需要添加limit,否则不需要添加，如统计一分钟投资客户数就不需要添加
        viewSqlSegment.addLimit("%s, %s")
      }
      val concatSql = viewSqlSegment.runtimeSql
      logger.info(s"concatSql = ${concatSql}")

      MysqlScanSelector.getSqlsByTemplateSql(concatSql, allCfgTableSubs)
    }else {//定义为单表场景
      allCfgTableSubs.flatMap(tablSubCfg => {
        tablSubCfg._2.map(t => (s"SELECT ${MysqlScanSelector.addSelectString(t, true)}, t.* FROM $t t WHERE ${scanTimeField} BETWEEN '%s' AND '%s' ORDER BY ${scanTimeField}, id_ LIMIT %s, %s", List(t)))
      })
    }
    logger.info(s"all sqls = ${sqlAndEntitys.map(_._1).mkString("[",", ","]")}")

    val scanStartDate= deployMeta.deploymentInfo.getLogBeginTimestamp

    val scanStartDataOp = if(scanStartDate != null) Some((DateUtils.getDate(scanStartDate, "yyyy-MM-dd HH:mm:ss"), scanStartDate.getTime/1000L)) else None

    val batchSizeConf = jobNode.getLong(batchSizeCfg)
    (dbKey, dbName, dbObject, sqlAndEntitys.map(entry => (entry._1, entry._2.asInstanceOf[List[String]].mkString(","))),
      allCfgTables, mainCfgTables, intervalOp, cronExpressOp, dataScanScopesOp, batchSizeConf,scanStartDataOp, scanTimeField)
  }



  def getSubTables(dbTables: mutable.Buffer[String], table: String): List[String] = {
    dbTables.filter(t => {
      if(SPELUtil.parseExpress(table).isDefined) {
        val expr = SPELUtil.parseExpress(table).get._1
        t.matches(table.replace(expr, ".+"))
      }else t.matches(table)
    }).toList
  }

  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this.__jobNodeMeta = newJobNodeMeta
    val result = parse
    dbKey = result._1
    dbName = result._2
    dbObject = result._3
    sqlTemplates = result._4
    allCfgTables = result._5
    mainCfgTables = result._6
    intervalOp = result._7
    cronExpressOp = result._8
    dataScanScopeOp = result._9
    batchSize = result._10
    scanStartDateOp = result._11
    scanTimeField = result._12
    if(initQuartzServer.get()) {
      quartzServer.reload(intervalOp, cronExpressOp, dataScanScopeOp)
    }
  }

  override def reload(dependencyJobNode: scala.List[JobNodeMeta]): Unit = {
    super.reload(dependencyJobNode)
    this.__dependencyJobNode = dependencyJobNode
  }


  def checkTableExists(_table: String): Boolean = {
    val tables = DBClient.executeQueryList("show tables", globalEmptyArray).asScala
    tables.find(table => table.asScala.head == _table).isDefined
  }

  def getSqlStruts(sqlEntry: (String, String)): (List[DataField], Integer, Integer, Integer, Integer) = {
    val structsOp = sqlStruts.get(sqlEntry._1)
    if(structsOp.isDefined) {
      structsOp.get
    }else {
      sqlStruts += (sqlEntry._1 -> initSqlStruts(sqlEntry))
      sqlStruts.get(sqlEntry._1).get
    }
  }

  def initSqlStruts(sqlEntry: (String, String)): (List[DataField], Integer, Integer, Integer, Integer) = {
    val parameters = Array.fill(2*mainCfgTables.length)("2000-01-01 00:00:00").toList ::: List("1","1")
    val sql = sqlEntry._1.format(parameters: _*)
    val struts = DBUtils.executeQueryStruts(dbKey,
      sql, null)
      .asScala.toList.zipWithIndex.map(colAndIndex => {
      val colName = colAndIndex._1._1
      val colType = colAndIndex._1._2
      val index = colAndIndex._2
      new DataField(index, colType, colName, true, false)
    })
    val mainTable = sqlEntry._2.split(",").head.trim
    val indexOfId_ = struts.indexWhere( _.getColumnName == s"${mainTable}_id_")
    val indexOfCreateTime_ = struts.indexWhere( _.getColumnName == s"${mainTable}_${create_time_}")
    val indexOfUpdateTime_ = struts.indexWhere( _.getColumnName == s"${mainTable}_${update_time_}")
    val indexOfScanTimeField = struts.indexWhere( _.getColumnName == s"${mainTable}_${scanTimeField}")

    if(indexOfId_ < 0) throw new TaskInitializationException(s"$sql con't find ${mainTable}_id_ field !")
    if(indexOfCreateTime_ < 0) throw new TaskInitializationException(s"$sql con't find ${mainTable}_${create_time_} field !")
    if(indexOfUpdateTime_ < 0) throw new TaskInitializationException(s"$sql con't find ${mainTable}_${update_time_} field !")
    if(indexOfScanTimeField < 0) throw new TaskInitializationException(s"$sql con't find ${mainTable}_${indexOfScanTimeField} field !")

    logger.info(s"initSqlStruts: $sql | ${struts.map(_.getColumnName)} | ${indexOfId_}| ${indexOfCreateTime_}| ${indexOfUpdateTime_}| $indexOfScanTimeField")

    (struts, indexOfId_, indexOfCreateTime_, indexOfUpdateTime_, indexOfScanTimeField)
  }

  def getRowMeta(values: util.List[String], struts: (List[DataField], Integer, Integer, Integer, Integer)): (String, String, Long, String) ={
//    logger.info(s"test= ${values.asScala.mkString("[",", ","]")} | ${struts._2} | ${values.get(struts._2)}")
    val id_ = values.get(struts._2).toLong
    val createTime_ = values.get(struts._3)
    val updateTime_ = values.get(struts._4)
    val scanTime_ = values.get(struts._5)
    (createTime_, updateTime_, id_, scanTime_)
  }

  def getStartTimeStamp(sqlTemplate: (String, String)): (String, Long) ={
    val tables = sqlTemplate._2.split(",").dropWhile(_.isEmpty).map(_.trim).distinct.toList
    val mainTables = mainCfgTables.filter(tables.contains(_))
    if(scanStartDateOp.isDefined) {
      scanStartDateOp.get
    }else if(mainTables.isDefinedAt(0)) {
      val times = mainTables.flatMap(t => {
        val sql = s"select min(${update_time_}),min(${create_time_}) from $t"
        logger.info(s"[SQL]:$sql")
        DBClient.setCurrentDatabaseKey(dbKey)
        DBClient.executeQueryList(sql,globalEmptyArray).asScala.head.asScala.toList
      }).distinct.map(time =>
        if(time == null) null.asInstanceOf[String]
        else if(time.isInstanceOf[String]) time.asInstanceOf[String]
        else DateUtils.getDate(time.asInstanceOf[Date], "yyyy-MM-dd HH:mm:ss"))

      val minUpdateTime = times.reduce((t1,t2) => {
        if(t1 == null) {
          t2
        }else if(t2 == null) {
          t1
        }else {
          val ts1 = DateUtils.parse(t1, "yyyy-MM-dd HH:mm:ss").getTime
          val ts2 = DateUtils.parse(t2, "yyyy-MM-dd HH:mm:ss").getTime
          if(ts1 > ts2) t2 else t1
        }
      })

      if(minUpdateTime == null) ("2000-01-01 00:00:00", 946656000L) else
      (minUpdateTime.asInstanceOf[String], DateUtils.parse(minUpdateTime.asInstanceOf[String], "yyyy-MM-dd HH:mm:ss").getTime/1000L)
    }else ("2000-01-01 00:00:00", 946656000L)
  }


  def getBatchScanFromToInfo(fromDate: String, toDate: String, fromTs: Long, toTs: Long, intervalTs: Long,
                             sqlTemplate: (String, String), lastDownBatchOp: Option[ScanBatch],
                             splitQueryByMinPeriod: Boolean):(String, Long, String, Long, Boolean) = {
    val isNewTemplate = if(quartzServer.scanSignal.sqlKeys != null ) !quartzServer.scanSignal.sqlKeys.contains(sqlTemplate._2) else true

    val (batchScanFromDate, batchScanFromTs, batchScanToDate, batchScanToTs) =
      if(lastDownBatchOp.isDefined && lastDownBatchOp.get.hasNext) {//存在下一页扫描
        val lastPageEndTime = lastDownBatchOp.get.dataEndTime
        val lastPageEndTimeTs = DateUtils.parseYYYYMMDDHHMMSS(lastPageEndTime).getTime/1000L
        (lastPageEndTime, lastPageEndTimeTs, lastDownBatchOp.get.to, lastDownBatchOp.get.toTs)
      }else if(isNewTemplate) {//如果新的扫描源第一次signal处理中
        val (batchFrom, batchFromTs) =
          if(lastDownBatchOp.isEmpty || lastDownBatchOp.get.sqlKey != sqlTemplate._2) {//新的扫描源的第一个批次
             val (from, fromTs) = getStartTimeStamp(sqlTemplate)
            (from, fromTs)
          }else {//新的扫描源的某批次时间段处理结束
            val nextScanFromTs = lastDownBatchOp.get.toTs + 1
            val nextScanFrom = DateUtils.getDateYYYYMMDDHHMMSS(new Date(nextScanFromTs * 1000L))
            (nextScanFrom, nextScanFromTs)
          }
        val batchEndTs = if(splitQueryByMinPeriod && batchFromTs + intervalTs -1 < toTs ) batchFromTs + intervalTs -1 else toTs
        val batchEnd = DateUtils.getDateYYYYMMDDHHMMSS(new Date(batchEndTs * 1000L))
        (batchFrom, batchFromTs, batchEnd, batchEndTs)
      } else (fromDate, fromTs, toDate, toTs)
    (batchScanFromDate, batchScanFromTs, batchScanToDate, batchScanToTs, isNewTemplate && !splitQueryByMinPeriod)
  }


  def scanOneSource(sqlTemplate: (String, String), scanSignal: ScanSignal, lastDownBatchOp: Option[ScanBatch]):
          Option[(ScanSignal, (String, String), Long, mutable.Buffer[util.List[AnyRef]], String, Long, String, Long, Boolean)]= {
    val fromDate = scanSignal.from //信号扫描开始时间
    val toDate = scanSignal.to//信号扫描结束时间
    val (fromTs, toTs) = (scanSignal.fromTs, scanSignal.toTs)
    val intervalTs = toTs - fromTs + 1 //信号扫描时间间隔（秒）

    var hasNext = false
    var splitQueryByMinPeriod = if(lastDownBatchOp.isDefined && lastDownBatchOp.get.sqlKey == sqlTemplate._2) lastDownBatchOp.get.splitQuery else false
    var tmpLastDownBatchOp = if(lastDownBatchOp.isDefined && lastDownBatchOp.get.sqlKey == sqlTemplate._2) lastDownBatchOp else None
    do{
      val (batchScanFromDate, batchScanFromTs, batchScanToDate, batchScanToTs, checkTimeOut) =
        getBatchScanFromToInfo(fromDate, toDate, fromTs, toTs, intervalTs, sqlTemplate, tmpLastDownBatchOp, splitQueryByMinPeriod)

      val parameters = List.fill(mainCfgTables.length)(List(batchScanFromDate, batchScanToDate)).flatMap(x => x)
      val allParams = parameters ::: List(0, batchSize + 1)
      val sql = if(lastDownBatchOp.isDefined && lastDownBatchOp.get.hasNext) {
        val tempSql = sqlTemplate._1.format(allParams: _*)

        val btOp = addStartR.findFirstIn(tempSql)
        if(btOp.isDefined) {
          val aliasPrefix = if(btOp.get.contains(".")) btOp.get.substring(0, btOp.get.indexOf(".") + 1) else ""
          tempSql.replace(btOp.get, s"(${aliasPrefix}id_ > ${lastDownBatchOp.get.endId} OR ${aliasPrefix}${scanTimeField} > '$batchScanFromDate') AND ${btOp.get}")
        }else tempSql
      }else sqlTemplate._1.format(allParams: _*)
//      logger.info(s"[SQL]: $sql")
      val timeOutSeconds = if(checkTimeOut) 3 else -1
      try{
        val datas = DBClient.executeQueryList(sql , globalEmptyArray, timeOutSeconds).asScala
  //      scanSignal.sqlKeys += sqlTemplate._2
        logger.info(s"[SQL]: $sql(${datas.size})")
        if(datas.isEmpty){
          hasNext = batchScanToTs < toTs
          if(hasNext){//构建一个临时的batch供判断供后续时间段的处理
            tmpLastDownBatchOp = Some(ScanBatch(scanSignal, sqlTemplate._2, false, 1, null, -1, null, -1,
              batchScanFromDate, batchScanToDate, batchScanFromTs, batchScanToTs,
              splitQuery = splitQueryByMinPeriod))//如果没有数据，可以理解为批量数据跑完，可以将splitQuery=true，待验证
          }
        }else {
          val curPageNo = if(lastDownBatchOp.isDefined && lastDownBatchOp.get.hasNext){
            lastDownBatchOp.get.pageNo + 1
          }else 1
          return Some((scanSignal, sqlTemplate, curPageNo, datas, batchScanFromDate, batchScanFromTs, batchScanToDate, batchScanToTs, splitQueryByMinPeriod))
        }
      }catch {
          case e:DBQueryException if(e.getParentException.isInstanceOf[MySQLTimeoutException]) => {
          logger.error(s"timeout => ${ExceptionUtils.getFullStackTrace(e)}")
          splitQueryByMinPeriod = true
          hasNext = true
        }
      }
    }while(hasNext)
    None
  }


  def selectData(): Option[(ScanSignal, (String, String), Long, mutable.Buffer[util.List[AnyRef]], String, Long, String, Long, Boolean)] ={
    do {
      val scanSignal = quartzServer.waitForQuartzSignal //获取处理信号

      val lastDownBatchOp = scanSignal.lastDownBatch //该处理信号-上次处理批次信息

      //获取需要继续处理的sqlTemplates
      val sqlTempes = if(lastDownBatchOp.isDefined) {
        val lastDealSqlTempIndex = sqlTemplates.indexWhere(entry => entry._2 == lastDownBatchOp.get.sqlKey)
        if(lastDownBatchOp.get.hasNext || lastDownBatchOp.get.to != scanSignal.to) {//如果存在翻页数据，或者批次处理结束时间部位信号结束时间，继续处理该扫描源
          sqlTemplates.slice(lastDealSqlTempIndex, sqlTemplates.length)
        }else if(lastDealSqlTempIndex + 1 < sqlTemplates.length){
          sqlTemplates.slice(lastDealSqlTempIndex + 1, sqlTemplates.length)
        }else Nil
      }else {
        if(sqlTemplates.isEmpty){
          logger.warn("sql templates is empty , no data deal !")
        }
        sqlTemplates
      }

      for (sqlTemplate <- sqlTempes) {
        val scanResultOp = scanOneSource(sqlTemplate, scanSignal, lastDownBatchOp)
        if(scanResultOp.isDefined) {
          return scanResultOp
        }
        quartzServer.scanSignal.sqlKeys += sqlTemplate._2//每处理完一个扫描信号源，刷新一下sqlKeys，可能有新的分表刚创建加入进来
      }
//      /*
//       *  一个信号处理结束后，刷新一下sqlKeys，可能有新的分表刚创建加入进来
//       */
//      quartzServer.scanSignal.sqlKeys ++= scanSignal.sqlKeys

      // 当前时刻无batch需要处理，填充v_cursor时钟信息
      if (lastDownBatchOp.isEmpty)
        quartzServer.ackVirtualSignal(scanSignal)

      quartzServer.signalFinish(scanSignal)
    }while (true)
    None
  }

  /**
    * 进行一次数据选择
    * @return
    */
  override def select(): BatchDataSet = {
    if(initQuartzServer.compareAndSet(false, true)) {
      quartzServer.start(intervalOp, cronExpressOp, dataScanScopeOp)
      tablesChangeMonitor.start()
    }

    val selectDataOp = selectData
    if(selectDataOp.isDefined){
      val (scanSignal, sqlEntry, pageNo, datas, batchScanFromDate, batchScanFromTs, batchScanToDate, batchScanToTs, splitQueryByMinPeriod) = selectDataOp.get
      val struts = getSqlStruts(sqlEntry)
      val dataSet = new DataSet
      dataSet.setSchemaName(dbObject)
      dataSet.setTableName(dbObject)
      dataSet.setFields(struts._1.asJava)
      val hasNext = datas.size > batchSize//表明存在分页
      val newDatas = if(hasNext) datas.dropRight(1) else datas

      dataSet.setRows(newDatas.map(data => {
        val row = new DataRow
        row.setValues(data.asScala.map(i => if(i == null) null.asInstanceOf[String] else i.toString).asJava)
        row.setOldValues(Nil.asJava)
        row.setUpdates(Nil.asJava)
        val (createTime_, _, id_, scanTime_) = getRowMeta(row.getValues, struts)
        row.setBinLogPosition(s"${scanTime_}|${id_.toString.reverse.padTo(10, '0').reverse}")
        row.setEventType(if(createTime_ == scanTime_) EventType.INSERT else EventType.UPDATE )
        row.setExecuteTime(DateUtils.parse(scanTime_, "yyyy-MM-dd HH:mm:ss.s").getTime/1000)
        row
      }).asJava)

      val (_, _, startId_, startScanTime_) = getRowMeta(dataSet.getRows.asScala.head.getValues, struts)
      val (_, _, endId_, endScanTime_) = getRowMeta(dataSet.getRows.asScala.last.getValues, struts)

      val scanBatch = ScanBatch(scanSignal, sqlEntry._2, hasNext, pageNo,
        startScanTime_, startId_, endScanTime_, endId_,
        batchScanFromDate, batchScanToDate, batchScanFromTs, batchScanToTs, splitQuery = splitQueryByMinPeriod)
      quartzServer.addBatch(scanBatch)

      val batchDataSet = new BatchDataSet
      batchDataSet.setBatchId(scanBatch.batchId)
      batchDataSet.setBinlogStart(s"${startScanTime_}|${startId_.toString.reverse.padTo(10, '0').reverse}")
      batchDataSet.setBinlogEnd(s"${endScanTime_}|${endId_.toString.reverse.padTo(10, '0').reverse}")
      batchDataSet.setBinlogStartTs(DateUtils.parse(startScanTime_, "yyyy-MM-dd HH:mm:ss.s").getTime/1000)
      batchDataSet.setBinlogEndTs(DateUtils.parse(endScanTime_, "yyyy-MM-dd HH:mm:ss.s").getTime/1000)
      batchDataSet.setCreateTime(DateUtils.getCurrentDate)
      batchDataSet.setDatas(List(dataSet).asJava)
      return batchDataSet
    }else null
  }



  /** 销毁逻辑 */
  override protected def destroyInternal(): Unit = {
    quartzServer.stop
    tablesChangeMonitor.destroy
  }
  override def ackTermi(messageId: Long): Unit = {
    quartzServer.ack(messageId)
  }

}
