package com.hframework.datacenter.hamster.worker.tasks.transform

import java.util
import java.util.Date
import java.util.concurrent.atomic.AtomicBoolean

import com.hframe.hamster.node.cannal.bean.EventType
import com.hframe.hamster.node.monitor.bean.ProcessEventData
import com.hframework.common.frame.ServiceFactory
import com.hframework.common.monitor.{ConfigMapMonitor, ConfigMonitor, Monitor, MonitorListener}
import com.hframework.common.util.DateUtils
import com.hframework.smartsql.client.DBClient
import com.hframework.datacenter.hamster.exceptions.TaskRunningException
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.Times
import com.hframework.datacenter.hamster.worker.tasks.AbstractSelector
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.MysqlScan.{batchSizeCfg, dataScanScope, scanInterval, _}
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.Expand
import com.hframework.datacenter.hamster.worker.tasks.{AbstractTaskRunner, AbstractTransformer, AttrDef}
import com.hframework.datacenter.hamster.workes.bean.DataField
import com.hframework.datacenter.hamster.workes.bean.{DataField, DataRow, DataSet}
import com.hframework.datacenter.hamster.zookeeper.ZKPathUtils
import com.hframework.datacenter.utils.DBUtils
import com.hframework.hamster.cfg.domain.model.CfgDataview_Example
import com.hframework.hamster.cfg.service.interfaces.{ICfgDatasourceSV, ICfgDataviewSV}
import org.apache.commons.lang.StringUtils

import scala.collection.JavaConverters._
import scala.collection.mutable
import java.sql.Types
import java.util.{Calendar, Date}

import com.hframe.hamster.node.monitor.bean.SharableEventData
import com.hframework.datacenter.hamster.workes.bean.{BatchDataSet, DataField, DataSet}
import com.hframework.datacenter.hamster.worker.SPELUtil

/**
  * @author songge
  * @version 2018-11-14
  */
class ExpandTransformer (prototypeKey: String, deployMeta:DeployJobNodeMeta, var __jobNodeMeta: JobNodeMeta) extends AbstractTransformer (prototypeKey, deployMeta, __jobNodeMeta, __jobNodeMeta.jobMeta){
  val (id_, update_time_) = ("id_", "update_time_")

  val globalEmptyArray =  Array.empty[Object]
  var relationMap = new mutable.HashMap[String, String]


  var (relationDbName, relationTableName, originColumn, relatedColumn, sourceColumn, ifRetainOrigin) = parse

  val initQuartzServer = new AtomicBoolean(false)
  val sql = s"select $originColumn, $relatedColumn from $relationTableName where 1=1"
  val relationKeyIsString: Boolean = {
    val colType = DBUtils.executeQueryStruts(relationDbName,sql, null).asScala.head._2
    logger.info(s"relation key col type = $colType, ${colType == Types.VARCHAR || colType == Types.CHAR}")
    colType == Types.VARCHAR || colType == Types.CHAR
  }

  val relationChangeMonitor = new ConfigMonitor[mutable.HashMap[String, String]](3){
    override def fetch(): mutable.HashMap[String, String] = {
      DBClient.setCurrentDatabaseKey(relationDbName)
      val relationList = DBClient.executeQueryList(sql, globalEmptyArray).asScala.toList.map(_.asScala.toList.map(_.toString))
      relationMap = mutable.HashMap(relationList.map(s => (s(0), s(1))):_*)
      relationMap
    }
  }
  relationChangeMonitor.addListener(new MonitorListener[mutable.HashMap[String, String]](){
    override def onEvent(monitor: Monitor[mutable.HashMap[String, String]]): Unit = {
      relationMap = monitor.getObject
    }
  })

  def parse : (String, String, String, String, String, String) = {
    val relationDbId = jobNode.getLong(Expand.relationDB)
    val relationDbName = relationDbId.toString
    val relationTableName = jobNode.getString(Expand.relationTable)
    val originColumn = jobNode.getString(Expand.originColumn)
    val relatedColumn = jobNode.getString(Expand.relatedColumn)
    val sourceColumn = jobNode.getString(Expand.sourceColumn)
    val ifRetainOrigin = jobNode.getString(Expand.ifRetainOrigin)

    if (ifRetainOrigin != "yes" && ifRetainOrigin != "no")
      throw new TaskRunningException(s"value of ifRetainOrigin [${ifRetainOrigin}] is not legal !")

    val dataSource = ServiceFactory.getService(classOf[ICfgDatasourceSV]).getCfgDatasourceByPK(relationDbId)
    DBClient.registerDatabase(relationDbName, s"jdbc:mysql://${dataSource.getUrl}/${dataSource.getDb}?useUnicode=true&tinyInt1isBit=false",
      dataSource.getUsername, dataSource.getPassword)
    DBClient.setCurrentDatabaseKey(relationDbName)

    (relationDbName, relationTableName, originColumn, relatedColumn, sourceColumn, ifRetainOrigin)
  }

  def getRelatedId (originId: String): String = {
    var relatedId:String = "-1"
    if (relationMap.contains(originId)) {
      if (relationMap(originId) != "") {
        relatedId = relationMap(originId)
      }
    }
    relatedId
  }

  def getAggrFunction(data: DataSet): List[String] = {
    val aggrFunction = data.getFields.asScala.map(field => {
      field.getFunction
    })
    aggrFunction.toList
  }

  def getKeyIndex(data:DataSet): List[Int] = {
    val keyIndex = data.getFields.asScala.filter(field => field.isKey).map(field => field.getIndex)
    keyIndex.toList
  }

  override def execute(processId: Long, eventData: SharableEventData, dataSets: BatchDataSet) : Option[BatchDataSet] = {
    if(initQuartzServer.compareAndSet(false, true)) {
      relationChangeMonitor.start()
    }
    val datas = dataSets.getDatas.asScala
    if (datas.isEmpty) None
    else {
      val expDatas = datas.map(dataSet => {
        val sourceColumnIndex = dataSet.getFieldIndex(sourceColumn)
        if(relationKeyIsString) dataSet.getField(sourceColumn).setColumnType(Types.VARCHAR)
        val newDataSet = dataSet.clone
        newDataSet.cleanRows
        // 为结果数据的fields添加parent字段
        val sourceColumnField = newDataSet.getField(sourceColumnIndex)
        val parentColumnField = new DataField(newDataSet.getFields.size, sourceColumnField.getColumnType, "parent_"+sourceColumn, sourceColumnField.isNull, sourceColumnField.isKey)
        parentColumnField.setFunction(sourceColumnField.getFunction)
        parentColumnField.setDftVal(sourceColumnField.getDftVal)
        newDataSet.getFields.add(parentColumnField)
        // 为结果数据的rows添加parent字段 TODO

        // 获取各字段的聚合函数
        val aggrFunction = getAggrFunction(dataSet)
        val keyIndex = getKeyIndex(dataSet)
        val newDataSetDictionary = new mutable.HashMap[List[String], mutable.ArrayBuffer[String]]
        val newDataSetMeta = new mutable.HashMap[List[String], List[String]]
        val originDataSetRows = dataSet.getRows.asScala
        for (originRow <- originDataSetRows) {
          val originRowValues = originRow.getValues.asScala
          val originRowId = originRowValues(sourceColumnIndex)
          var lastId = originRowId
          var visitId = originRowId
          if (ifRetainOrigin == "no") {
            visitId = getRelatedId(lastId)
          }
          while (visitId != "-1") {
            val nextId = getRelatedId(visitId)
            val newRowValues = originRowValues.to[mutable.ArrayBuffer]
            newRowValues(sourceColumnIndex) = visitId
            newRowValues += nextId
            val key:List[String] = keyIndex.map(index => newRowValues(index))
            if (!newDataSetDictionary.contains(key)) {
              newDataSetDictionary.put(key, newRowValues)
              newDataSetMeta.put(key, List(originRow.getExecuteTime.toString, originRow.getBinLogPosition))
            }
            else {
              val rowValue = newDataSetDictionary.get(key).get
              for (j <- 0 to aggrFunction.length - 1) {
                if(rowValue(j) == null || rowValue(j).isEmpty) rowValue(j) = "0"
                if(newRowValues(j) == null || newRowValues(j).isEmpty) newRowValues(j) = "0"

                aggrFunction(j) match {
                  case null =>
                  case "sum" | "count" => {
                    rowValue(j) = (rowValue(j).toDouble + newRowValues(j).toDouble).toString
                  }
                  case "max" => {
                    if (rowValue(j).toDouble < newRowValues(j).toDouble)
                      rowValue(j) = newRowValues(j)
                  }
                  case "min" => {
                    if (rowValue(j).toDouble > newRowValues(j).toDouble)
                      rowValue(j) = newRowValues(j)
                  }
                  case _ => throw new TaskRunningException(s"express [${aggrFunction(j)}] is not supported !")
                }
              }
            }
            lastId = visitId
            visitId = getRelatedId(lastId)
          }
        }
        for ((k, v) <- newDataSetDictionary) {
          val newDataMeta = newDataSetMeta.get(k).get
          newDataSet.addRow(newDataMeta(0).toLong, newDataMeta(1), v.toList.asJava)
        }
        newDataSet
      })
      val newBatchDataSet = dataSets.clone()
      newBatchDataSet.setDatas(expDatas.asJava)
      Some(newBatchDataSet)
    }
  }
}
