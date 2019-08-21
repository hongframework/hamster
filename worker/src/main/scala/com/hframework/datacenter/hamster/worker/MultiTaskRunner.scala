package com.hframework.datacenter.hamster.worker

import java.util
import java.util.Date
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.ReentrantLock

import com.hframe.hamster.node.HamsterConst
import com.hframe.hamster.node.cannal.bean.EventType
import com.hframework.common.util.DateUtils
import com.hframework.datacenter.hamster.workes.bean.{BatchDataSet, DataField, DataRow, DataSet}
import com.hframework.smartsql.client.DBClient
import com.hframework.datacenter.hamster.exceptions.TaskInitializationException
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.MysqlScan.{sourceDB, sourceTable}
import com.hframework.datacenter.hamster.worker.tasks.extract.{MysqlBinlogExtractor, MysqlScanExtractor}
import com.hframework.datacenter.hamster.worker.tasks.select.{MysqlScanSelector, SqlParser}
import com.hframework.datacenter.hamster.worker.tasks.{AbstractTaskRunner, DeployJson, DeployLog, PrototypeTask}
import com.hframework.datacenter.hamster.workes.bean.{DataField, DataRow, DataSet}
import com.hframework.datacenter.utils.DBUtils
import org.apache.commons.lang.exception.ExceptionUtils
import org.slf4j.MDC

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.util.control.Breaks.{break, breakable}

class MultiTaskRunner(prototypeKey: String, deployMeta: DeployJobNodeMeta, selectNode: JobNodeMeta,
                      allJobNodes: List[JobNodeMeta]) extends PrototypeTask(prototypeKey) with DeployLog{
  override def threadName: String = s"${prototypeKey}-MultiTaskRunner"
  /** 名称描述 */
  override def name: String  = s"[${prototypeKey}]"

  val fifoDatas = new ArrayBlockingQueue[(String, mutable.Buffer[java.util.List[AnyRef]])](100)
  val idIncr = new AtomicLong(0)
  val undoCnt = new AtomicLong(0)

  val globalEmptyArray =  Array.empty[Object]

  val taskRunners = allJobNodes.map(jobNode => {
    val taskRunnerOp = HamsterWorker.initTaskRunner(jobNode, deployMeta)
    if(taskRunnerOp.isEmpty) {
      logger.error(s"${jobNode.nodeMeta.nodeKey} task runner init error !")
      throw new TaskInitializationException(s"${jobNode.nodeMeta.nodeKey} task runner init error !")
    }
    taskRunnerOp.get
  })
  val extractor = taskRunners.head._2

  val jobNode = if(extractor.isInstanceOf[MysqlBinlogExtractor]) {
    val extractorRunner = extractor.asInstanceOf[MysqlBinlogExtractor]
    extractorRunner.jobNode
  }else if(extractor.isInstanceOf[MysqlScanExtractor]){
    val extractorRunner = extractor.asInstanceOf[MysqlScanExtractor]
    extractorRunner.jobNode
  }else{
    throw new TaskInitializationException(s"${prototypeKey} extractor is not instance of " +
      s"MysqlBinlogExtractor.class, or MysqlScanExtractor.class !")
  }

  val (dbKey, dbName, sqlEntries) = {
    val dbId = jobNode.getLong(sourceDB)
    val (dbKey, dbName) = MysqlScanSelector.registerDatabase(dbId)
    val dbObject = jobNode.getString(sourceTable)

    val (allCfgTables, mainCfgTables, viewSql) = MysqlScanSelector.parseDbObject(dbObject, dbId)
    val allCfgTableSubs = MysqlScanSelector.getAllSubTables(allCfgTables)

    val mainTableKeyFields = mainCfgTables.distinct.map(t => {
      val keyFields = DBClient.executeQueryMaps(dbKey, s"show columns from $t", globalEmptyArray)
        .asScala.filter(col => {val key = col.get("Key")
        key == "PRI"}).map(_.get("Field")).toList
      (t, keyFields)
    }).toMap

    val sqlAndEntitys: List[(String, List[String])] = if(viewSql.isDefined){//视图
      val viewSqlSegment = SqlParser.parse(viewSql.get.replaceAll("%","%%"))//where name like %变现通% 需要转换为 %%变现通%%，否则format时报错
      if(mainTableKeyFields.size == 1 && mainTableKeyFields.head._2.size == 1) {
        viewSqlSegment.addWhere(mainCfgTables.map(table => s"${table}.${mainTableKeyFields.head._2.head} > %s").mkString(" OR "))
        viewSqlSegment.addOrderBy(mainCfgTables.map(table => s"${table}.${mainTableKeyFields.head._2.head}").mkString(", "))
        viewSqlSegment.addSelect(mainCfgTables.map(table => s"${table}.${mainTableKeyFields.head._2.head} as ${table}_${mainTableKeyFields.head._2.head}__").mkString(", "))
        viewSqlSegment.addLimit("%s")
      }else{
        viewSqlSegment.addOrderBy(mainTableKeyFields.flatMap(entity => entity._2.map(key => s"${entity._1}.${key}")).mkString(", "))
        viewSqlSegment.addLimit("%s, %s")
      }

      for (t <- allCfgTables) {
        val addWhereOp = DeployJson.getAddWhereSql(deployMeta, t)
        if(addWhereOp.isDefined){
          viewSqlSegment.addWhere(addWhereOp.get)
        }
      }

      val concatSql = viewSqlSegment.runtimeSql
      MysqlScanSelector.getSqlsByTemplateSql(concatSql, allCfgTableSubs)
    }else {//表
      allCfgTableSubs.flatMap(tablSubCfg => {
        tablSubCfg._2.map(t => {
          val addWhereOp = DeployJson.getAddWhereSql(deployMeta, t)
          val sql = if(mainTableKeyFields.size == 1 && mainTableKeyFields.head._2.size == 1) {
            s"select * from $t where ${mainTableKeyFields.head._2.head} > %s " +
              s"${if(addWhereOp.isDefined) s"and ${addWhereOp.get} " else "" }order by ${mainTableKeyFields.head._2.head} limit %s"
          }else {
            s"select * from $t ${if(addWhereOp.isDefined) s"where ${addWhereOp.get} " else ""}limit %s, %s"
          }
          (sql, List(t))
        })
      })
    }
    logger.info(s"all sqls = ${sqlAndEntitys.map(_._1).mkString("[",", ","]")}")

    val mayAddKeyName = s"${mainTableKeyFields.head._1}_${mainTableKeyFields.head._2.head}__"

    val sqlInfos = sqlAndEntitys.map(sqlAndEntity => {
      val sql = sqlAndEntity._1
      val struts = DBUtils.executeQueryStruts(dbKey,
        sql.format(0,1), null)
        .asScala.toList.zipWithIndex.map(colAndIndex => {
        val colName = colAndIndex._1._1
        val colType = colAndIndex._1._2
        val index = colAndIndex._2
        new DataField(index, colType, colName, true, (mayAddKeyName :: mainTableKeyFields.flatMap(_._2).toList).toSet.contains(colName))
      })
      val keyIndexs = struts.filter(_.isKey).map(_.getIndex)
      if(keyIndexs.isEmpty){
        throw new TaskInitializationException(s"${prototypeKey} key index not find ! ")
      }

      val addKeyOp = keyIndexs.map(struts(_)).find(_.getColumnName == mayAddKeyName)
      val struts_ = if(addKeyOp.isDefined) struts.filterNot(_.getIndex == addKeyOp.get.getIndex) else struts
      (sqlAndEntity._2.mkString(","), sql, struts_ , keyIndexs, addKeyOp.isDefined)
    })
    (dbKey, dbName, sqlInfos)
  }

//
//  val (dbKey, dbName, sqlEntries) = if(extractor.isInstanceOf[MysqlBinlogExtractor]){
//    val extractorRunner = extractor.asInstanceOf[MysqlBinlogExtractor]
//    val jobNode = extractorRunner.jobNode
//    val dbId = jobNode.getLong(sourceDB)
//    val (dbKey, dbName) = MysqlScanSelector.registerDatabase(dbId)
//
//    val dbObject = jobNode.getString(sourceTable)
//    val dbTables = DBClient.executeQueryList("show tables", globalEmptyArray).asScala.map(_.asScala.head.asInstanceOf[String])
//    val tables = dbTables.filter(t => dbObject == t || new AviaterRegexFilter(dbObject).filter(t))
//    val sqlEntries = tables.map(t => {
//      val keyFields = DBClient.executeQueryMaps(dbKey, s"show columns from $t", globalEmptyArray)
//        .asScala.filter(col => {val key = col.get("Key")
//        key == "PRI"}).map(_.get("Field")).toList
//
//      val addWhereOp = DeployJson.getAddWhereSql(deployMeta, t)
//      val sql = if(keyFields.size == 1) {
//        s"select * from $t where ${keyFields.head} > %s ${if(addWhereOp.isDefined) s"and ${addWhereOp.get} " else "" }order by ${keyFields.head} limit %s"
//      }else {
//        s"select * from $t ${if(addWhereOp.isDefined) s"where ${addWhereOp.get} " else ""}limit %s, %s"
//      }
//
//      val struts = DBUtils.executeQueryStruts(dbKey,
//        s"select * from $t limit 1", null)
//        .asScala.toList.zipWithIndex.map(colAndIndex => {
//        val colName = colAndIndex._1._1
//        val colType = colAndIndex._1._2
//        val index = colAndIndex._2
//        new DataField(index, colType, colName, true, keyFields.contains(colName))
//      })
//
//      val keyIndexs = struts.filter(_.isKey).map(_.getIndex)
//      (t, sql, struts, keyIndexs)
//    })
//    (dbKey,dbName, sqlEntries)
//  }else {
//    throw new TaskInitializationException(s"${prototypeKey} extractor is not instance of MysqlBinlogExtractor.class !")
//  }



  val dealLock = new ReentrantLock()

  def getBatchDataSet(table: String, datas: mutable.Buffer[java.util.List[AnyRef]]): BatchDataSet = {
    val sqlEntry = sqlEntries.find(_._1 == table)
    val batchDataSet = new BatchDataSet
    batchDataSet.setBatchId(idIncr.getAndAdd(1))
    batchDataSet.setBinlogStart(s"0000000000000000|${sqlEntry.get._4.map(datas.head.get(_)).mkString("_").reverse.padTo(10, '0').reverse}")
    batchDataSet.setBinlogEnd(s"0000000000000000|${sqlEntry.get._4.map(datas.last.get(_)).mkString("_").reverse.padTo(10, '0').reverse}")
    batchDataSet.setCreateTime(DateUtils.getCurrentDate)

    val dataSet = new DataSet
    batchDataSet.setDatas(List(dataSet).asJava)
    dataSet.setSchemaName(dbName)
    dataSet.setTableName(table)
    dataSet.setFields(new util.ArrayList(sqlEntry.get._3.map(fld => {
      new DataField(fld.getIndex, fld.getColumnType, fld.getColumnName, fld.isNull, fld.isKey, fld.getFunction, fld.getDftVal)
    }).asJava))

    dataSet.setRows(datas.map(data => {
      val row = new DataRow
      row.setValues(data.asScala.map(item => if(item != null) item.toString else null.asInstanceOf[String]).asJava)
      row.setOldValues(Nil.asJava)
      row.setUpdates(Nil.asJava)
      row.setBinLogPosition(s"0000000000000000|${sqlEntry.get._4.map(data.get(_)).mkString("_").reverse.padTo(10, '0').reverse}")
      row.setEventType(EventType.INSERT)
      row.setExecuteTime(new Date().getTime/1000)
      row
    }).asJava)

    if(sqlEntry.get._5){//如果为新增的主键
      for(row <- dataSet.getRows.asScala){
        row.getValues.remove(sqlEntry.get._4.head)
      }
    }
    batchDataSet
  }



  @volatile var lastInvokeOp: Option[BatchDataSet] = None
  val consumerThread = new Thread( new Runnable {
    override def run(): Unit = {

      DBClient.setCurrentDatabaseKey(dbKey)
      while (true) {
        val (table, datas) = fifoDatas.take()
        var batchDataSet = getBatchDataSet(table, datas)
        tryLogDeployStartInfo(deployMeta.deployCode, allJobNodes.head.jobMeta.selectKey, batchDataSet.getCreateTime, startInfo = batchDataSet.getBinlogStart)
        for (taskRunner <- taskRunners) {
          if(taskRunner._2.isInstanceOf[AbstractTaskRunner]) {
            val runner = taskRunner._2.asInstanceOf[AbstractTaskRunner]
            val result = runner.execute(batchDataSet.getBatchId, null, batchDataSet)
            if(result.isDefined) {
              batchDataSet = result.get
            }
          }
        }
        lastInvokeOp = Some(batchDataSet)
        undoCnt.decrementAndGet()
      }
    }
  })


  /** 启动逻辑 */
  override protected def startInternal(): Unit = {
    MDC.put(HamsterConst.splitTaskLogFileKey, prototypeKey)
    try {
      if(hasInvoked(deployMeta.deployCode, allJobNodes.head.jobMeta.selectKey)){
        logger.info(s"[deployCode = ${deployMeta.deployCode}, selectKey = ${allJobNodes.head.jobMeta.selectKey}] : been invoked !")
      }else {
        consumerThread.start()
        DBClient.setCurrentDatabaseKey(dbKey)
        for (sqlEntry <- sqlEntries) {
          var offset  = 0L
          val size = 1000L
          breakable{
            while (true) {
              val finalSql = sqlEntry._2.format(offset, size)
              logger.info(s"$finalSql")
              val datas = DBClient.executeQueryList(finalSql , globalEmptyArray).asScala
              logger.info(s"deal data size = ${datas.size}")
              if(datas.length > 0) {
                undoCnt.incrementAndGet()
                fifoDatas.put((sqlEntry._1, datas))
                offset = if(sqlEntry._4.size == 1) {
                  val keyVal = datas.last.get(sqlEntry._4.head)
                  if(keyVal.isInstanceOf[Long]) keyVal.asInstanceOf[Long] else keyVal.toString.toLong
                } else offset + size
              }else {
                break
              }
            }
          }
        }

        breakable{
          while (true) {
            if(fifoDatas.size() == 0 && undoCnt.get() == 0) {
              break
            }else {
              Thread.sleep(100L)
            }
          }
        }
        tryLogDeployEndInfo(lastInvokeOp.get.getCreateTime, endInfo = lastInvokeOp.get.getBinlogEnd)
        logger.info("batch deal finish !")
      }
    }catch {
      case e: Exception => {
        logger.error(s"unknown exception => ${ExceptionUtils.getFullStackTrace(e)}")
        if(lastInvokeOp.isDefined)
          tryLogDeployErrorInfo(new Date(lastInvokeOp.get.getBinlogEndTs), lastInvokeOp.get.getBinlogEnd, e.getMessage)
        destroy
      }
    }finally MDC.remove(HamsterConst.splitTaskLogFileKey)
  }

  /** 加载逻辑 */
  override def reload(): Unit = ???

  /** 销毁逻辑 */
  override protected def destroyInternal(): Unit = consumerThread.interrupt()
}
