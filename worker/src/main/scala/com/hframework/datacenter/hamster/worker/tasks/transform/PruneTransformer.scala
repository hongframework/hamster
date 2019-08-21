
package com.hframework.datacenter.hamster.worker.tasks.transform

import java.sql.Types

import com.hframe.hamster.node.monitor.bean.SharableEventData
import com.hframework.datacenter.hamster.workes.bean.{BatchDataSet, DataField, DataSet}
import com.hframework.datacenter.hamster.exceptions.TaskRunningException
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.{IPUtils, SPELUtil, UAUtils}
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.Prune
import com.hframework.datacenter.hamster.worker.tasks.{AbstractTaskRunner, AbstractTransformer}
import com.hframework.datacenter.hamster.workes.bean.{DataField, DataSet}

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * 数据减枝
  * 字段名的映射，值的转换，以及删除，新增字段
  * @param prototypeKey 任务标识
  * @param __jobNodeMeta 任务节点定义
  */
class PruneTransformer(prototypeKey: String, deployMeta: DeployJobNodeMeta, var __jobNodeMeta: JobNodeMeta)
  extends AbstractTransformer(prototypeKey, deployMeta, __jobNodeMeta, __jobNodeMeta.jobMeta){

  val functionPrefix = Map("IP" -> List("country", "province", "city", "county"),
    "UA" -> List("device_type", "device_brand_name", "device_name", "is_mobile", "browser", "browser_version", "os", "os_version", "ua_md5"))

  @volatile var (mappingValues, fixValues, exprValues, exprVars, functionVales) = parse

  def parse:(Map[String, List[String]], Map[DataField, String], Map[DataField, (String, List[String])], List[String], Map[(String, String), Array[DataField]]) = {
    if(jobNode.rowSize > 0) {
      val confMap = (0 to jobNode.rowSize - 1).map(index => {
        jobNode.getString(Prune.sourceColumn, index) -> jobNode.getString(Prune.targetColumn, index)
      })
      val fixValues = confMap.filter(entry => entry._1.startsWith("[") && entry._1.endsWith("]"))
        .map(entry => {
          val fixVal = entry._1.substring(1, entry._1.length -1).trim
          (new DataField(entry._2, Types.VARCHAR, false) -> fixVal)
        }).toMap
      val exprValues = confMap.filter(entry => SPELUtil.parseExpress(entry._1).isDefined && functionPrefix.find(fp => entry._1.contains(fp._1 + ":")).isEmpty)
        .map(entry => new DataField(entry._2, Types.BIGINT, false) -> SPELUtil.parseExpress(entry._1).get).toMap
      val functionVales = confMap.filter(entry => SPELUtil.parseExpress(entry._1).isDefined && functionPrefix.find(fp => entry._1.contains(fp._1 + ":")).isDefined)
        .map(entry => {
          {
            val vars = SPELUtil.parseExpress(entry._1).get._2
            (vars.find(functionPrefix.contains(_)).get, vars.find(!functionPrefix.contains(_)).get)
          } -> entry._2.split(",").filter(_.trim.isDefinedAt(0)).map(col => new DataField(col.trim, Types.VARCHAR, false))
        }).toMap
      val exprVars = (exprValues.values.flatMap(_._2).toList.distinct ++ functionVales.keySet.map(_._2)).distinct

      val mappingValues =  confMap.filterNot(entry => (entry._1.startsWith("[") && entry._1.endsWith("]"))
        || SPELUtil.parseExpress(entry._1).isDefined)

      val mapValues = mappingValues.groupBy(_._1).map(g => g._1 -> g._2.map(_._2).toList)
      (mapValues, fixValues, exprValues, exprVars, functionVales)
    }else (Map.empty[String, List[String]], Map.empty[DataField, String], Map.empty[DataField, (String, List[String])],
      List.empty[String], Map.empty[(String, String), Array[DataField]])
  }

  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this.jobNodeMeta = newJobNodeMeta
    val result = parse
    mappingValues = result._1
    fixValues = result._2
    exprValues = result._3
    exprVars = result._4
    functionVales = result._5
  }

  def addExtendValue(dataSet: DataSet, exprRet: mutable.Buffer[Array[String]], fields: Array[DataField], functionCode: String) = {
    val supportColumns = functionPrefix.get(functionCode).get
    for (field <- fields) {
      val index = supportColumns.indexOf(field.getColumnName)
      if(dataSet.getField(field.getColumnName) == null) {
        dataSet.getFields.add(field)

        dataSet.getRows.asScala.zip(exprRet).foreach(zip => {
          zip._1.getValues.add(zip._2(index))
        })
      }else {
        dataSet.setFieldValues(field.getColumnName, exprRet.map(_(index)).asJava)
      }
    }
  }

  /**
    * 执行一次业务处理
    *
    * @param processId  处理批次号
    * @param eventData  元数据
    * @param dataSets 待处理数据
    * @return 处理结果数据
    */
  override def execute(processId: Long, eventData: SharableEventData, dataSets:  BatchDataSet): Option[BatchDataSet] = {
    //进行Data数据过滤
    val filterDatas = dataSets.getDatas.asScala
    if(filterDatas.isEmpty) None else {
      for(dataSet <- filterDatas) {
        val exprVarValOp = if(exprVars.isDefinedAt(0)) Some(dataSet.getFieldValues(exprVars.asJava).asScala) else None

        if(!mappingValues.isEmpty) {
          val removeIndexs = dataSet.getFields.asScala.zipWithIndex.
            filter(field => !mappingValues.contains(field._1.getColumnName) && !field._1.isKey).map(field => Integer.valueOf(field._2))
          dataSet.removeField(removeIndexs.asJava)//删除多余字段

          try{
            mappingValues.foreach(mapping => if(mapping._2.length == 1) {
              val field = dataSet.getField(mapping._1)
              if (field != null) field.setColumnName(mapping._2.head)
            }else {
              val baseField = dataSet.getField(mapping._1)
              val baseFieldIndex = dataSet.getFieldIndex(mapping._1)
              mapping._2.drop(1).map(colName => {
                val newField = new DataField(colName, baseField.getColumnType, baseField.isKey, baseField.getFunction)
                newField.setIndex(dataSet.getFields.size())
                dataSet.getFields.add(newField)
              })
              for (row <- dataSet.getRows.asScala) {
                row.getValues.add(row.getValues.get(baseFieldIndex))
                if(row.getOldValues.size() > baseFieldIndex)
                  (1 until mapping._2.size).foreach(x => row.getOldValues.add(row.getOldValues.get(baseFieldIndex)))

                if(row.getUpdates.size() > baseFieldIndex)
                  (1 until mapping._2.size).foreach(x => row.getUpdates.add(row.getUpdates.get(baseFieldIndex)))
              }
              baseField.setColumnName(mapping._2.head)
            })//重命名
          }catch {
            case e: Exception => {
              if(mappingValues.filter(kv => dataSet.getField(kv._1) == null).size > 0){
                logger.error(s"table:[${dataSet.getTableName}] don't have field : ${mappingValues.filter(kv => dataSet.getField(kv._1) == null).keySet.mkString("[",",","]")}")
              }
              throw e
            }
          }
        }
        fixValues.foreach(mapping => {
          val field = mapping._1
          val fixVal = mapping._2
          dataSet.getFields.add(field)
          dataSet.getRows.forEach(row => {
            row.getValues.add(fixVal)
          })
        })
        exprValues.foreach(mapping => {
          val field = mapping._1
          val expr = mapping._2
          val exprRet = exprVarValOp.get.map(data => SPELUtil.invokeExpress(expr._1, expr._2, data.asScala.toMap))

          if(dataSet.getField(field.getColumnName) == null) {
            dataSet.getFields.add(field)

            dataSet.getRows.asScala.zip(exprRet).foreach(zip => {
              zip._1.getValues.add(zip._2)
            })
          }else {
            dataSet.setFieldValues(field.getColumnName, exprRet.asJava)
          }
        })

        functionVales.foreach(function => {
          val functionCode = function._1._1
          val functionColumn = function._1._2
          val fields = function._2
          functionCode match {
            case "IP" => {
              val exprRet = exprVarValOp.get.map(row => row.get(functionColumn)).map(IPUtils.parseCity(_))
              addExtendValue(dataSet, exprRet, fields, functionCode)
            }
            case "UA" => {
              val exprRet = exprVarValOp.get.map(row => row.get(functionColumn)).map(UAUtils.parseArray(_))
              addExtendValue(dataSet, exprRet, fields, functionCode)

            }
            case _ => {
              throw new TaskRunningException(s"function : $functionCode is not support !")
            }
          }
        })
      }

      val newBatchDataSet = dataSets.clone()
      newBatchDataSet.setDatas(filterDatas.asJava)
      Some(newBatchDataSet)
    }
  }

}
