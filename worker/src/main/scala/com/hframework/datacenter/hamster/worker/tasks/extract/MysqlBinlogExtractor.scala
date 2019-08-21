package com.hframework.datacenter.hamster.worker.tasks.extract

import java.sql.Types
import java.{lang, util}

import com.alibaba.otter.canal.filter.aviater.AviaterRegexFilter
import com.hframe.hamster.node.cannal.bean.{EventData, EventType}
import com.hframe.hamster.node.monitor.bean.SharableEventData
import com.hframework.common.util.RegexUtils
import com.hframework.datacenter.hamster.workes.bean.{BatchDataSet, DataField, DataRow}
import com.hframework.datacenter.enums.CfgSubscribeDataOperTypeEnum._
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobContainerMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.tasks.AbstractExtractor
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.Binlog
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.Binlog.sourceDB
import com.hframework.datacenter.hamster.workes.bean.{DataField, DataRow}
import org.apache.commons.lang.exception.ExceptionUtils
import org.springframework.expression.common.TemplateParserContext
import org.springframework.expression.spel.standard.SpelExpressionParser

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * 通用数据提取器
  * @param prototypeKey 任务标识
  * @param __jobNodeMeta extract任务节点定义
  */
class MysqlBinlogExtractor(prototypeKey: String, deployMeta: DeployJobNodeMeta, var __jobNodeMeta: JobNodeMeta)
  extends AbstractExtractor(prototypeKey, deployMeta, __jobNodeMeta, __jobNodeMeta.jobMeta){

  type ParseResult = (String, Int, String, List[String], List[String])

  @volatile var (sourceTable, operType, filterExpression, sourceColumns, dataFilterVars): ParseResult = parse

  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this.__jobNodeMeta = newJobNodeMeta
    val result = parse
    sourceTable = result._1
    operType = result._2
    filterExpression = result._3
    sourceColumns = result._4
    dataFilterVars = result._5
  }

  def parse(): ParseResult = {
    val sourceTable = jobNode.getString(Binlog.sourceTable)
    val operType = jobNode.getInt(Binlog.operType)
    val filterExpression = jobNode.getString(Binlog.filterExpression)
    val sourceColumns = jobNode.getStrings(Binlog.sourceColumns)
    val dataFilterVars = if(filterExpression != null && !filterExpression.isEmpty){
      var vars = RegexUtils.find(filterExpression.replaceAll("'[^']*'", ""), "[a-zA-Z]+[a-zA-Z0-9._]*")
      vars.distinct.toList.sorted
    }else scala.List.empty[String]
    (sourceTable, operType, filterExpression, sourceColumns, dataFilterVars)
  }



  /**
    * 执行一次业务处理
    *
    * @param processId 处理批次号
    * @param eventData 元数据
    * @param dataSets   待处理数据
    * @return 处理结果数据
    */
  override def execute(processId: Long, eventData: SharableEventData, dataSets:  BatchDataSet): Option[BatchDataSet] = {
    //进行Data数据过滤
    val datas = dataSets.getDatas.asScala

    val filterDatas =
      datas.filter(dataSet => checkTableMatch(dataSet.getTableName, sourceTable))
        .map(dataSet => {
          val filterRows = dataSet.getRows.asScala
            .filter(row => checkEventTypePass(operType.toByte, row.getEventType))
            .filter(row => checkFilterPass(dataSet.getFields.asScala, row, filterExpression))
          if(filterRows.isEmpty) None else {
            val newDataSet = dataSet.clone()
            newDataSet.setFields(new util.ArrayList[DataField](dataSet.getFields))
            newDataSet.setRows(filterRows.map(row =>{
              val newRow = row.clone()
              newRow.setValues(new util.ArrayList[String](row.getValues))
              newRow.setUpdates(new util.ArrayList[lang.Boolean](row.getUpdates))
              newRow.setOldValues(new util.ArrayList[String](row.getOldValues))
              newRow
            }).asJava)
            Some(newDataSet)
          }
        }).filter(_.isDefined).map(_.get)



    if(filterDatas.isEmpty) None else {
      if(!sourceColumns.isEmpty) {
        for(dataSet <- filterDatas) {
          val removeIndexs = dataSet.getFields.asScala.zipWithIndex.
            filterNot(field => sourceColumns.contains(field._1.getColumnName)).map(field => Integer.valueOf(field._2))
          dataSet.removeField(removeIndexs.asJava)
        }
      }

      val newBatchDataSet = dataSets.clone()
      newBatchDataSet.setDatas(filterDatas.asJava)
      Some(newBatchDataSet)
    }
  }

  def checkTableMatch(curTable: String, cfgTable: String): Boolean = {
    curTable.eq(cfgTable) || new AviaterRegexFilter(cfgTable).filter(curTable)
  }

  private def checkEventTypePass(dbObjectOperateType: Byte, eventType: EventType): Boolean = dbObjectOperateType.toInt match {
    case oType if(DML.getValue == oType) => eventType.isDml
    case oType if(INSERT_UPDATE.getValue == oType) => eventType.isInsert || eventType.isUpdate
    case oType if(INSERT.getValue == oType) => eventType.isInsert
    case oType if(UPDATE.getValue == oType) => eventType.isUpdate
    case oType if(DELETE.getValue == oType) => eventType.isDelete
  }


  def checkFilterPass(fields: mutable.Buffer[DataField], row: DataRow, filterExp: String): Boolean = {
    var resultExp = filterExp

    try {
      if (filterExp == null || filterExp.isEmpty) return true

      if (dataFilterVars != null && dataFilterVars.length > 0) for (dataFilterVar <- dataFilterVars) {
        resultExp = resultExp.replaceAll(dataFilterVar, getValValueFromData(dataFilterVar, fields, row))
      }
      val parser = new SpelExpressionParser
      parser.parseExpression("#{" + resultExp + "}", new TemplateParserContext).getValue(classOf[Boolean])
    } catch {
      case e: Exception =>
        logger.error("SPEL execute failed, [filterExp = {}, resultExp = {}]", filterExp, resultExp)
        logger.error(ExceptionUtils.getFullStackTrace(e))
        false
    }
  }

  def getValValueFromData(dataFilterVar: String, fields: mutable.Buffer[DataField], row: DataRow): String = {
    val columnName = if(dataFilterVar.startsWith("orig.")) dataFilterVar.substring(5) else dataFilterVar
    val dataField = fields.find(_.getColumnName == columnName)
    if(dataField.isDefined) {
      val index = fields.indexOf(dataField.get)
      val value = if(dataFilterVar.startsWith("orig.")) {
        val oldValues = row.getOldValues.asScala
        if(oldValues.size > index) oldValues(index) else null
      }else {
        row.getValues.asScala(index)
      }
      dataField.get.getColumnType match {
        case Types.CHAR | Types.VARCHAR | Types.LONGVARCHAR  => "'" + value + "'"
        case _ => value
      }
    }else {
      dataFilterVar
    }
  }

  @Deprecated
  private def checkFilterPass(data: EventData, filterExp: String): Boolean = {
    var resultExp = filterExp

    try {
      if (filterExp == null || filterExp.isEmpty) return true


      if (dataFilterVars != null && dataFilterVars.length > 0) for (dataFilterVar <- dataFilterVars) {
        resultExp = resultExp.replaceAll(dataFilterVar, getValValueFromData(dataFilterVar, data))
      }
      val parser = new SpelExpressionParser
      parser.parseExpression("#{" + resultExp + "}", new TemplateParserContext).getValue(classOf[Boolean])
    } catch {
      case e: Exception =>
        logger.error("SPEL execute failed, [filterExp = {}, resultExp = {}]", filterExp, resultExp)
        logger.error(ExceptionUtils.getFullStackTrace(e))
        false
    }
  }

  @Deprecated
  private def getValValueFromData(dataFilterVar: String, data: EventData): String = {
    val columnName = if(dataFilterVar.startsWith("orig.")) dataFilterVar.substring(5) else dataFilterVar
    val columns = if(dataFilterVar.startsWith("orig.")) data.getOldColumns.asScala else data.getColumns.asScala

    var targetColumn = columns.find(_ == columnName)
    if(targetColumn.isDefined) {
      targetColumn.get.getColumnType match {
        case Types.CHAR | Types.VARCHAR | Types.LONGVARCHAR  => "'" + targetColumn.get.getColumnValue + "'"
        case _ => targetColumn.get.getColumnValue
      }
    }else {
      dataFilterVar
    }
  }


}
