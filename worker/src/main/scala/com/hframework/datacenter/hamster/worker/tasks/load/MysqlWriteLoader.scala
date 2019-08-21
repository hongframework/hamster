package com.hframework.datacenter.hamster.worker.tasks.load

import java.sql.Types
import java.util.Date
import java.util.concurrent.{ArrayBlockingQueue, ConcurrentHashMap}
import java.util.concurrent.locks.ReentrantLock

import com.hframe.hamster.node.cannal.bean.EventType
import com.hframe.hamster.node.monitor.bean.SharableEventData
import com.hframework.common.frame.ServiceFactory
import com.hframework.common.util.DateUtils
import com.hframework.datacenter.hamster.workes.bean.{BatchDataSet, DataField}
import com.hframework.smartsql.client.DBClient
import com.hframework.smartsql.client.exceptions.DBUpdateException
import com.mysql.jdbc.exceptions.jdbc4.{MySQLIntegrityConstraintViolationException, MySQLSyntaxErrorException, MySQLTransactionRollbackException}
import com.hframework.datacenter.hamster.exceptions.{TaskInitializationException, TaskRunningException}
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.SPELUtil
import com.hframework.datacenter.hamster.worker.tasks.AbstractLoader
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.Mysql
import com.hframework.datacenter.hamster.workes.bean.DataField
import com.hframework.hamster.cfg.domain.model.CfgDatatable_Example
import com.hframework.hamster.cfg.service.interfaces.{ICfgDatasourceSV, ICfgDatatableSV}
import org.mybatis.generator.internal.types.JdbcTypeNameTranslator

import scala.collection.JavaConverters._
import scala.collection.mutable

object MysqlWriteLoaderLock{
  val dbOpLocks = new ConcurrentHashMap[String, ReentrantLock]()
}

/**
  * 写入Mysql
  * @param prototypeKey 任务标识
  * @param __jobNodeMeta 任务定义
  */
class MysqlWriteLoader(prototypeKey: String, deployMeta: DeployJobNodeMeta, var __jobNodeMeta: JobNodeMeta)
  extends AbstractLoader(prototypeKey, deployMeta,  __jobNodeMeta, __jobNodeMeta.jobMeta){

  val globalEmptyArray =  Array.empty[Object]
  val globalEmptyMap = Map.empty[String, (Long, String)]

  val keyLRUQueueCapacity = 1000
  val keyLRUQueue = new ArrayBlockingQueue[String](keyLRUQueueCapacity)

  val defaultFields = List(
    "id_" -> ("string", List("alter table %s add column id_ BIGINT(11) PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键';")),
    "source_key_" -> ("string", List("alter table %s add column source_key_ VARCHAR(50) NOT NULL UNIQUE COMMENT '数据Key';")),
    "source_data_set_" -> ("string", List("alter table %s add column source_data_set_ VARCHAR(256) NOT NULL COMMENT '来源数据集';")),
    "source_data_id_" -> ("string", List("alter table %s add column source_data_id_ VARCHAR(128) NOT NULL COMMENT '来源数据ID';")),
    "source_execute_time_" -> ("string", List("alter table %s add column source_execute_time_ BIGINT NOT NULL COMMENT '来源数据时间戳';")),
    "create_time_" -> ("string", List("alter table %s add column create_time_ timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间';")),
    "update_time_" -> ("string", List("alter table %s add column update_time_ timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新时间';",
      "alter table %s add index update_time_ (update_time_);"))

  )

  val defaultTable = """create table %s(
                      id_ BIGINT(11) PRIMARY KEY AUTO_INCREMENT COMMENT '自增主键',
                      source_key_ VARCHAR(50) NOT NULL COMMENT '数据Key',
                      source_data_set_ VARCHAR(256) NOT NULL COMMENT '来源数据集',
                      source_data_id_ VARCHAR(128) NOT NULL COMMENT '来源数据ID',
                      source_execute_time_ BIGINT NOT NULL COMMENT '来源数据时间戳',
                      create_time_ timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
                      update_time_ timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新时间',
                      UNIQUE KEY source_key_ (source_key_),
                      KEY update_time_ (update_time_));"""


  val cacheFields = scala.collection.mutable.Map.empty[String, String]

  //Map[tableName, Map[sourceDataSet, (sourceExecuteTime, sourceDataId)]]
  val repeatExecuteCheckMap = mutable.Map.empty[String, Map[String, (Long, String)]]

  /**
    * 数据重复性校验
    * 将启动时数据库最新的源数据时间与ID与当前更新值进行比对
    * @param tableName
    * @param sourceDataSet
    * @param sourceDataId
    * @param executeTime
    * @return
    */
  def isNotRepeatExecute(tableName: String, sourceDataSet: String, sourceDataId: String, executeTime: Long, sourceKey: String): Boolean = {
    if(updateCheck) {//只有检查更新才进行重复处理检查
      val checkMapOp = repeatExecuteCheckMap.get(tableName)
      val checkMap = if(checkMapOp.isEmpty) {
        if(checkTableExists(tableName)) {
          val queryMap = getLastCycleExecuteEndInfo(tableName)
          repeatExecuteCheckMap.put(tableName, queryMap)
          queryMap
        }else {
          repeatExecuteCheckMap.put(tableName, globalEmptyMap)
          globalEmptyMap
        }


      }else checkMapOp.get
      val dataSetOp = checkMap.get(sourceDataSet)
      if(dataSetOp.isDefined) {
        val lastExecuteTime = dataSetOp.get._1
        val lastDataId = dataSetOp.get._2
        if(executeTime != lastExecuteTime) {
          executeTime > lastExecuteTime
        }else {//同一个时间点同一条记录存在更新则认为重复处理
          checkLastCycleNotExecute(tableName, executeTime, sourceDataSet, sourceDataId, sourceKey)
        }
      }else {
        true
      }
    }else true
  }

  def getLastCycleExecuteEndInfo(tableName: String) = {
    DBClient.setCurrentDatabaseKey(dbKey)
    val result = DBClient.executeQueryList(
      s"""
         |SELECT
         |  MAX(source_execute_time_) AS "execute_time",
         |  source_data_set_ AS "data_set",
         |  MAX(source_data_id_) AS "data_id"
         |FROM $tableName
         |GROUP BY source_data_set_
         |HAVING MAX(source_execute_time_) > UNIX_TIMESTAMP(DATE_SUB(CURDATE(),INTERVAL 30 DAY));
       """.stripMargin.replaceAll("\r\n"," "), globalEmptyArray)
    result.asScala.map(row => {
      val list = row.asScala
      list(1).toString -> (list(0).asInstanceOf[Long], list(2).toString)
    }).toMap
  }

  def checkLastCycleNotExecute(tableName: String, lastExecuteTimestamp: Long, sourceDataSet: String, sourceDataId: String, sourceKey: String): Boolean = {
    DBClient.setCurrentDatabaseKey(dbKey)
    val sql = s"""
                 |SELECT
                 |  source_key_
                 |FROM $tableName
                 |WHERE source_execute_time_ = $lastExecuteTimestamp
                 |    AND source_data_set_ = '$sourceDataSet'
                 |    AND source_key_ = '$sourceKey'
       """.stripMargin.replaceAll("\r\n"," ")
    val result = DBClient.executeQueryList(
      sql, globalEmptyArray)
// |   AND source_data_id_ >= '$sourceDataId'
    logger.warn(s"check repeat update => size: [${result.size()}]; sql:[$sql]")
    result.isEmpty
  }

  @volatile var (dbKey, table, subTableOpt, insertOnly, updateCheck, keyFields, exprKeyStr, fixKeyStr) = parse

  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this.__jobNodeMeta = newJobNodeMeta
    val result = parse
    dbKey = result._1
    table = result._2
    subTableOpt = result._3
    insertOnly = result._4
    updateCheck = result._5
    keyFields = result._6
    exprKeyStr = result._7
    fixKeyStr = result._8
  }

  def tryUpdateTableStruct(sql: String): Unit = {
    try DBClient.executeUpdate(sql, globalEmptyArray) catch {
      case e: DBUpdateException => {
        if(!e.asInstanceOf[DBUpdateException].getParentException.getMessage.contains("Duplicate column name")){
          throw e
        }
      }
    }
  }

  /**
    * 如果添加存在重复列，说明已被别的具有相同目标操作表的Loader所处理（进程或线程）
    * @param table
    * @param columnName
    * @param columnType
    * @param sqls
    * @param key
    * @param function
    * @return
    */
  def tryToAlterTableAddColumn(table: String, columnName: String, columnType: String, sqls: List[String] = Nil,
                               key: Boolean = false, function: String = null) = {
    logger.warn(s"[table=${table}, column=${columnName}] not exists, create it!")
    if(sqls.isEmpty) {
      val tmpSql = s"alter table ${table} add column ${columnName} ${columnType match {
        case "VARCHAR" => columnType + "(128)"
        case "DECIMAL" => columnType + "(20,2)" + (if(function != null && function.trim.isDefinedAt(0)) " DEFAULT '0.00'" else "")
        case "BIGINT" => columnType + (if(function != null && function.trim.isDefinedAt(0)) " DEFAULT '0'" else "")
        case "CLOB" => "text"
        case _ => columnType
      }};"
      logger.info(s"[SQL]$tmpSql")
      tryUpdateTableStruct(tmpSql)
      if(key) {
        val indexSql = s"alter table $table add index $columnName ($columnName);"
        logger.info(s"[SQL]$indexSql")
        tryUpdateTableStruct(indexSql)
      }
    }else {
      sqls.foreach(sql => {
        logger.info(s"[SQL]$sql")
        tryUpdateTableStruct(sql.format(table,table))
      })
    }
    cacheFields.put(columnName, columnType)
  }

  def refreshCacheFields(dbKey: String, targetTable: String) = {
    val columns = DBClient.executeQueryMaps(dbKey, "show columns from " + targetTable, globalEmptyArray).asScala

    cacheFields.++=(columns.map(col => col.get("Field").toString -> col.get("Type").toString).toMap)
  }

  def getTableDefine(cfgDB: Long, cfgTable: String):(String, Option[(String, String)]) = {
    val cfgTableOp = """\$\{[yMdhms_]{3,}\}""".r.findFirstIn(cfgTable)
    if(cfgTableOp.isDefined) {
      val templateTable = cfgTable.replaceFirst("""[_]*\$\{[yMdhms_]{3,}\}""", "")
      (templateTable, Some(cfgTable, cfgTableOp.get))
    }else {
      val subTables = ServiceFactory.getService(classOf[ICfgDatatableSV]).getCfgDatatableListByExample({
        val example = new CfgDatatable_Example
        example.createCriteria().andCfgDatasourceIdEqualTo(cfgDB).andTemplateTableNameEqualTo(cfgTable)
        example
      })
      if(subTables.asScala.isDefinedAt(0)) {
        val subTable = subTables.get(0)
        val subTablePattern = """\$\{[yMdhms_]{3,}\}""".r.findFirstIn(subTable.getSubTableName)
        if(subTablePattern.isDefined) {
          (cfgTable, Some(subTable.getSubTableName, subTablePattern.get))
        }else {
          (cfgTable, None)
        }
      }else {
        (cfgTable, None)
      }
    }
  }


  def parse: (String, String, Option[(String, String)], Boolean, Boolean, List[String], List[(String, List[String])], String) ={
    val targetDB = jobNode.getLong(Mysql.targetDB)
    val cfgTable = jobNode.getString(Mysql.targetTable)
    val updateMethod = jobNode.getString(Mysql.updateMethod)
    val keyField = jobNode.getStrings(Mysql.keyField)
    if(updateMethod != "insert" && updateMethod != "i_or_u" && updateMethod != "i_or_cu") {
      throw new TaskInitializationException(s"updateMethod = '${updateMethod}' is not in [insert, i_or_u, i_or_cu]!")
    }

    val (templateTable, subTableOpt) = getTableDefine(targetDB, cfgTable)
    logger.info(s"table = $templateTable, sub table = $subTableOpt")
    val dbKey = targetDB.toString
    val dataSource = ServiceFactory.getService(classOf[ICfgDatasourceSV]).getCfgDatasourceByPK(targetDB)
    DBClient.registerDatabase(dbKey, s"jdbc:mysql://${dataSource.getUrl}/${dataSource.getDb}?useUnicode=true&tinyInt1isBit=false",
      dataSource.getUsername, dataSource.getPassword)

    DBClient.setCurrentDatabaseKey(dbKey)
    cacheFields.clear
    if(checkTableExists(templateTable)){
      refreshCacheFields(dbKey, templateTable)
      val missColumns = defaultFields.filterNot(df => {cacheFields.contains(df._1)})
      for (column <- missColumns){
        tryToAlterTableAddColumn(templateTable, column._1, column._2._1, column._2._2)
      }
    }else {
      logger.warn(s"[db=${dataSource.getDb}, table=${templateTable}] not exists, create it, [${defaultTable.format(templateTable)}]!")
      try{
        DBClient.executeUpdate(defaultTable.format(templateTable), globalEmptyArray)
        defaultFields.foreach(field => cacheFields.put(field._1, field._2._1))
      }catch {
        case e: Exception => {
          logger.error(s"${e.getMessage}")
        }
      }

    }

    val exprKeyStr = keyField.filter(SPELUtil.parseExpress(_).isDefined).map(SPELUtil.parseExpress(_).get)
    val fixKeyStr = keyField.filter(key => key.startsWith("[") && key.endsWith("]"))
      .map(key => key.substring(1, key.length - 1).trim).mkString("_")

    (dbKey, templateTable, subTableOpt, updateMethod == "insert", updateMethod == "i_or_cu",
      keyField.filterNot(key => key.startsWith("[") && key.endsWith("]"))
        .filterNot(SPELUtil.parseExpress(_).isDefined), exprKeyStr,  fixKeyStr)
  }

  def checkTableExists(_table: String): Boolean = {
    val tables = DBClient.executeQueryList("show tables", globalEmptyArray).asScala
    tables.find(table => table.asScala.head == _table).isDefined
  }

  def getOverrideFieldIndex(fields: mutable.Buffer[DataField]):(Int, Int, Int, Int) = {
    val soruceKeyIdx = fields.indexWhere(_.getColumnName == "source_key_")
    val soruceExecuteTimeIdx = fields.indexWhere(_.getColumnName == "source_execute_time_")
    val soruceDataSetIdx = fields.indexWhere(_.getColumnName == "source_data_set_")
    val soruceDataIdIdx = fields.indexWhere(_.getColumnName == "source_data_id_")

    (soruceKeyIdx, soruceExecuteTimeIdx, soruceDataSetIdx, soruceDataIdIdx )
  }

  def getOperateFields(fields: mutable.Buffer[DataField]):List[(String, String)] = {
    fields.filterNot(field => //source_key_, source_execute_time_, source_data_set_, source_data_id_ 复用的核心字段这里需要过滤
      field.getColumnName == "source_key_"|| field.getColumnName == "source_execute_time_" || field.getColumnName == "source_data_set_" || field.getColumnName == "source_data_id_" )
      .map(field => (field.getColumnName, field.getFunction)).toList :::
      defaultFields.filterNot(field =>
        field._1 == "id_" || field._1 == "update_time_" || field._1 == "create_time_")
        .map(field => (field._1, null))
  }

  def HeadSql(fields: mutable.Buffer[DataField]): String = {
    val operateFields = getOperateFields(fields)
    s"${operateFields.map(_._1).mkString("`","`, `","`")}"
  }

  def tryToUpdate(sql: String): Integer = {
    DBClient.executeUpdate(sql, globalEmptyArray)
  }

  def syncTableStruct(baseTable: String, targetTable: String) = {
    if(checkTableExists(targetTable)) {
      val ownFields = DBClient.executeQueryMaps(dbKey, s"show columns from $targetTable", globalEmptyArray)
        .asScala.map(col => col.get("Field")).toSet
      val baseFields = DBClient.executeQueryMaps(dbKey, s"show columns from $baseTable", globalEmptyArray).asScala
      baseFields.filterNot(baseField => ownFields.contains(baseField.get("Field"))).foreach(field => {
        DBClient.executeUpdate(s"alter table $targetTable add column ${field.get("Field")} ${field.get("Type")};", globalEmptyArray)
      })
    }else {
      val tableDef = DBClient.executeQueryMap(s"SHOW CREATE TABLE $baseTable", globalEmptyArray)
      if(tableDef.asScala.get("Create Table").isDefined) {
        val createSql = tableDef.asScala.get("Create Table").get
        DBClient.executeUpdate(createSql.toString.replaceFirst(baseTable, targetTable), globalEmptyArray)
      }else {
        throw new TaskRunningException(s"$baseTable not define !")
      }
    }
  }

  def executeDBUpdate(sql: String, realTableName: String, retrySqls: mutable.Buffer[String] = mutable.Buffer.empty,
                      updateIfExist: Boolean = true): Integer = {
    logger.info("[SQL]:" + sql)
    try tryToUpdate(sql) catch{
      case e: DBUpdateException => e.getParentException match {
        /*违反唯一性约束
       *发生场景为：
       * ①. 服务重启，上次为处理完批次数据再次激活处理(直接跳过即可)
       * ②. 由于配置的关系错误，做成消费产生了重复数据，比如只有Insert场景，而业务系统却对存在的记录做了Update处理(这里也将insert变成update)
       */
        case p: MySQLIntegrityConstraintViolationException => {
          logger.warn(s"${p.getClass.getName}: ${p.getMessage}")
          if(retrySqls.size > 1) {//批量添加多个元素时，才需要retry每一个
            retrySqls.foldLeft(0)((result, retrySql) => {
              result + executeDBUpdate(retrySql, realTableName)
            })
          }else {
            //insert调整为update
            0
          }
        }
        /*表不存在场景，普遍为分表需要新建*/
        case p: MySQLSyntaxErrorException if(p.getMessage.matches(".*Table '.*' doesn't exist.*")) => {
          logger.warn(s"${p.getClass.getName}: ${p.getMessage}")
          syncTableStruct(table, realTableName)
          executeDBUpdate(sql, realTableName, retrySqls, updateIfExist)
        }
        /*发生死锁情况，直接重试，直到成功为止*/
        case p: MySQLTransactionRollbackException if(p.getMessage.matches(".*Deadlock found when trying to get lock.*")) => {
          logger.warn(s"${p.getClass.getName}: ${p.getMessage}, try again:[$sql]")
          executeDBUpdate(sql, realTableName, retrySqls, updateIfExist)
        }
        case p: Exception => {
          logger.error(s"${p.getClass.getName}: ${p.getMessage}")
          throw e
        }
      }
      case e: Exception => {
        logger.error(s"${e.getClass.getName}: ${e.getMessage}")
        throw e
      }
    }
  }

  /**
    * 执行一次业务处理
    *
    * @param processId 处理批次号
    * @param eventData 元数据
    * @param dataSets  待处理数据
    * @return 处理结果数据
    */
  override def execute(processId: Long, eventData: SharableEventData, dataSets: BatchDataSet): Option[BatchDataSet] = {
    DBClient.setCurrentDatabaseKey(dbKey)
    val data = dataSets.getDatas.asScala
    data.foreach(dataset => {
      val fields = dataset.getFields.asScala
      //      val keyFieldIds = keyFields.map(key => fields.indexWhere(_.getColumnName == key))
      //配置主键与数据集默认的主键组成联合主键
      val keyFieldIds = fields.filter(field => keyFields.contains(field.getColumnName) || field.isKey).map(fields.indexOf(_))
      val missColumns = fields.filterNot(field => cacheFields.contains(field.getColumnName))
      for (column <- missColumns){
        tryToAlterTableAddColumn(table, column.getColumnName, JdbcTypeNameTranslator.getJdbcTypeName(column.getColumnType), key = column.isKey,  function = column.getFunction)
      }
      val  (soruceKeyIdx, soruceExecuteTimeIdx, soruceDataSetIdx, soruceDataIdIdx ) = getOverrideFieldIndex(fields)
      val operateFields = getOperateFields(fields)
      val operateFieldSql = s"${operateFields.map(_._1).mkString("`","`, `","`")}"
      val groupRows = dataset.getRows.asScala.groupBy(row => {
        val timestamp = if(subTableOpt.isDefined) {
          DateUtils.getDate(new Date(row.getExecuteTime * 1000L), subTableOpt.get._2.substring(2, subTableOpt.get._2.length - 1))
        }else {
          ""
        }
        (timestamp, row.getEventType)
      })

      //优先执行insert操作(否则同一时间的insert与update，update被insert覆盖不能生效)，同时delete操作不进行处理
      //对待处理数据进行
      val sortedAndFilterGroupRows = groupRows.toList.sortWith((i1, i2) =>{
        val key1 = i1._1
        val key2 = i2._1
        if(key1._1.compareTo(key2._1) != 0) {
          key1._1.compareTo(key2._1) < 0
        }else if(key1._2 == EventType.INSERT){
          true
        }else if(key2._2 == EventType.INSERT){
          false
        }else {
          true
        }
      }).filterNot(_._1._2 == EventType.DELETE)

      sortedAndFilterGroupRows.foreach(gr => {
        val timestamp = gr._1._1
        val eventType = gr._1._2
        val rows = gr._2
        val realTableName = if(subTableOpt.isDefined) subTableOpt.get._1.replace(subTableOpt.get._2, timestamp) else table


        val valueParts = rows.map(row => {
          val values = row.getValues.asScala

          val source_data_set_ = if(soruceDataSetIdx < 0) dataset.getTableName else values(soruceDataSetIdx)
          val source_data_id_ = if(soruceDataIdIdx < 0) row.getBinLogPosition else values(soruceDataIdIdx)
          val source_execute_time_ = if(soruceExecuteTimeIdx < 0) row.getExecuteTime else values(soruceExecuteTimeIdx).toLong

          val exprResult = exprKeyStr.map(expKeyPair => SPELUtil.invokeExpress(expKeyPair._1, expKeyPair._2,
            Map("table_" -> s"'${dataset.getTableName}'",
              "db_" -> s"'${dataset.getSchemaName}'",
              "execute_time_" -> s"'${source_execute_time_}'",
              "data_set_" -> s"'${source_data_set_}'",
              "data_id_" -> s"'${source_data_id_}'"
            ))).mkString("_")
          val source_key_ = if(soruceKeyIdx < 0) {keyFieldIds.map(values(_)).mkString("_") +
            (if(exprResult.isDefinedAt(0)) s"_$exprResult" else "") +
            (if(fixKeyStr.isDefinedAt(0)) s"_$fixKeyStr" else "")
          } else row.getValues.get(soruceKeyIdx)

          val removeKeywordValues = List(soruceKeyIdx, soruceExecuteTimeIdx, soruceDataSetIdx, soruceDataIdIdx).sortWith((i1, i2) => i1 > i2)
            .foldLeft(values.zipWithIndex)((vals, idx) => {
            if(idx >= 0) {
              vals.remove(idx)
              vals
            }else vals
          })

          //重启后，数据重复更新校验
          if(isNotRepeatExecute(realTableName, source_data_set_, source_data_id_, source_execute_time_, source_key_)){
            removeKeywordValues.map(indexAndVal => {
              val columnType = dataset.getField(indexAndVal._2).getColumnType
              columnType match {
                case Types.INTEGER | Types.BIGINT | Types.TINYINT | Types.SMALLINT => indexAndVal._1
                case Types.FLOAT |Types.DOUBLE |Types.DECIMAL => indexAndVal._1
//                case Types.TIMESTAMP => indexAndVal._1 //timestamp 需要添加''引号
                case _ => if(indexAndVal._1 != null) s"'${indexAndVal._1.replaceAll("'", "\\\\'")}'" else "null"
              }
            }).++(List(s"'${source_key_.replaceAll("'", "\\\\'")}'", s"'$source_data_set_'" , s"'$source_data_id_'", source_execute_time_))
          }else {
            logger.warn(s"check repeat update =>  checkMap = $repeatExecuteCheckMap, currentDataSet = $source_data_set_, " +
              s"currentDataId = $source_data_id_, currentExecuteTime = $source_execute_time_, currentDataKey = $source_key_")
            null
          }
        }).filter( _ != null)

        if(!valueParts.isEmpty) {

          val groupByValues = valueParts.groupBy(valPart => {
            val sourceKey =  valPart(valPart.length - 4)
            val isInsert = if(keyLRUQueue.contains(sourceKey)) false else eventType == EventType.INSERT || insertOnly
            isInsert
          })
          val insertValuePartsOp = groupByValues.get(true)
          val updateValuePartsOp = groupByValues.get(false)


          if(insertValuePartsOp.isDefined) {
            val valuePartSql = insertValuePartsOp.get.map(_.map(va => if(va == null) 0 else va)).map(_.mkString("(",", ",")")).mkString(", ")
            val updatePartSql = operateFields.map(field => s"`${field._1}` = ${getUpdateValue(s"VALUES(`${field._1}`)", field._1, field._2)}").mkString(", ")
            val batchInsertSql = s"insert into $realTableName($operateFieldSql) values $valuePartSql ON DUPLICATE KEY UPDATE $updatePartSql"
            executeDBUpdate(batchInsertSql, realTableName)
            //如下分拆逻辑，直接由ON DUPLICATE KEY UPDATE语法一条语句执行
//            val result = executeDBUpdate(batchInsertSql, realTableName)
//            if(result <= 0) {//Key冲突导致新增失败
//              if(insertValuePartsOp.get.length > 1) {//批量Insert场景
//                for (valPart <- insertValuePartsOp.get) {
//                  val singleResult = getInsertSqlAndExecute(realTableName, operateFields, valPart)
//                  if(singleResult <= 0){//将insert调增为update进行处理
//                    getUpdateSqlAndExecute(realTableName, operateFields, valPart, true)
//                  }
//                }
//              }else {//将insert调增为update进行处理
//                getUpdateSqlAndExecute(realTableName, operateFields, insertValuePartsOp.get.head, true)
//              }
//            }
          }else if(updateValuePartsOp.isDefined){
            for (values <- updateValuePartsOp.get) {
              val updateResult = getUpdateSqlAndExecute(realTableName, operateFields, values)
              if(updateResult == 0) {//如果更新失败，可能update记录不存在，尝试新增该记录
                val insertResult = getInsertSqlAndExecute(realTableName, operateFields, values)
                if(insertResult == 0){//如果记录新增失败，可能刚好由别的进程新增成功，再次进行更新操作
                  getUpdateSqlAndExecute(realTableName, operateFields, values)
                }
              }
            }
          }

          for (valPart <- valueParts) {
            val sourceKey =  valPart(valPart.length - 4).toString
            if(keyLRUQueue.size() == keyLRUQueueCapacity){
              keyLRUQueue.take()
              logger.info(s"take key LRU queue(size = ${keyLRUQueue.size()})")
            }
            if(keyLRUQueue.contains(sourceKey)) {
              keyLRUQueue.offer(sourceKey)
              keyLRUQueue.remove(sourceKey)
            }else {
              keyLRUQueue.offer(sourceKey)
            }
          }
        }
      })
    })

    None
  }
  def getInsertSqlAndExecute(realTableName: String, operateFields: List[(String, String)], values: mutable.Buffer[Any]): Int = {
    val columnKVs = operateFields.zip(values).filter(_._2 != null)
    val columnPartString = columnKVs.map(_._1).map(_._1).mkString("`","`, `","`")
    val valuePartString = columnKVs.map(_._2).mkString(", ")
    val insertSql = s"insert into $realTableName($columnPartString) values ($valuePartString)"
    executeDBUpdate(insertSql, realTableName)
  }

  def getUpdateSqlAndExecute(realTableName: String, operateFields: List[(String, String)], values: mutable.Buffer[Any], isRetryUpdate : Boolean = false): Int = {
    val updateSql =
      s"""update $realTableName
         |set ${operateFields.zip(values).filter(_._2 != null).map(zip => {s"`${zip._1._1}` = ${getUpdateValue(zip._2, zip._1._1, zip._1._2)}"}).mkString(", ")}
         |where source_key_ = ${values(values.length - 4)}
         |""".stripMargin.replaceAll("\r\n"," ")
    //这里不用sql进行控制，而是加载到内存由isNotRepeatExecute()方法进行控制
//           | ${if (updateCheck) s" and source_execute_time_ <= ${values(values.length - 1)} " +
//      s"and (source_data_set_ != ${values(values.length - 3)} or source_data_id_ != ${values(values.length - 2)})" else ""}
    executeDBUpdate(updateSql, realTableName)
  }

  def getUpdateValue(defaultValue: Any, field: String, function: String): String = {
    if(function == null || function.isEmpty){
      if(defaultValue != null) defaultValue.toString
      else null.asInstanceOf[String]

    }else function match {
      case "max" => s"GREATEST(`$field`, $defaultValue)"
      case "min" => s"LEAST(`$field`, $defaultValue)"
      case "avg" => s"`$field` / $defaultValue"
      case _ => s"`$field` + $defaultValue"
    }
  }

}
