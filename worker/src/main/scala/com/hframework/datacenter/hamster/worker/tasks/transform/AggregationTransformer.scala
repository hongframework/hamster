package com.hframework.datacenter.hamster.worker.tasks.transform

import java.sql.Types
import java.util.concurrent.atomic.AtomicBoolean
import java.util.{Calendar, Date}

import com.hframe.hamster.node.cannal.bean.EventType
import com.hframe.hamster.node.monitor.bean.SharableEventData
import com.hframework.common.util.DateUtils
import com.hframework.datacenter.hamster.workes.bean.{BatchDataSet, DataField, DataRow, DataSet}
import com.hframework.datacenter.hamster.exceptions.{TaskInitializationException, TaskRunningException}
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.SPELUtil
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.Aggregation
import com.hframework.datacenter.hamster.worker.tasks.{AbstractTaskRunner, AbstractTransformer, AttrDef}
import com.hframework.datacenter.hamster.workes.bean.{DataField, DataRow, DataSet}

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * 数据聚合
  * @param prototypeKey  任务标识
  * @param __jobNodeMeta 任务节点定义
  */
class AggregationTransformer (prototypeKey: String, deployMeta: DeployJobNodeMeta, var __jobNodeMeta: JobNodeMeta)
  extends AbstractTransformer(prototypeKey, deployMeta,  __jobNodeMeta, __jobNodeMeta.jobMeta){

  val expressParseOp = mutable.Map.empty[String,  Option[(String, List[String])]]

  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this.__jobNodeMeta = newJobNodeMeta
    val result = parse
    aggDataFields = result._1
    bucketTimeField = result._2
    bucketTimeInterval = result._3
    bucketFields = result._4
    aggPairs = result._5
    allFilterExpr = result._6
  }
  val fieldTypeFilled = new AtomicBoolean(false)

  @volatile var (aggDataFields, bucketTimeField, bucketTimeInterval, bucketFields, aggPairs, allFilterExpr) = parse

  def parse = {
    val conf = Array.range(0, jobNode.rowSize).map(i => {
      val targetColumn = jobNode.getString(Aggregation.targetColumn, i)
      val bucketTimeField = jobNode.getString(Aggregation.bucketTimeField, i, false)
      val bucketFields = jobNode.getStrings(Aggregation.bucketFields, i)
      val metricFields = jobNode.getStrings(Aggregation.metricFields, i)
      val bucketTimeInterval = jobNode.getString(Aggregation.bucketTimeInterval, i)
      val metricFunction = jobNode.getString(Aggregation.metricFunction, i)
      val filterExpression = jobNode.getString(Aggregation.filterExpression, i, false)

      val filterExpressionOp = if(filterExpression != null && filterExpression.trim.isDefinedAt(0)) {
//        throw new TaskInitializationException(s"agg filter express [$filterExpression] can't be parsed !")
        SPELUtil.parseExpress("${" + filterExpression +  "}")
      }else None

      (bucketTimeField, bucketTimeInterval, bucketFields, targetColumn, metricFields , metricFunction,
        filterExpressionOp)
    })

    val aggKeys = conf.groupBy(row => {
      val bucketTimeField = row._1
      val bucketTimeInterval = row._2
      val bucketFields = row._3
      (getAliasName(bucketTimeField), bucketTimeInterval, bucketFields.map(getAliasName(_)))
    }).map(_._1)
    if(aggKeys.size > 1) {
      logger.error(s"数据聚合处理，多个指标统计时，指标需要相同【${aggKeys.mkString(",")}】！")
      throw new TaskRunningException(s"数据聚合处理，多个指标统计时，指标需要相同【${aggKeys.mkString(",")}】！")
    }
    val aggKey = aggKeys.head


    val aggPairs = conf.groupBy(row => (row._1, row._2, row._3)).map(group => {
      val bucketTimeField = group._1._1
      val bucketTimeInterval = group._1._2
      val bucketFields = group._1._3
      val aggKV = group._2.map(_row => (_row._4, _row._5, _row._6, _row._7)).toList
      (getNames(bucketTimeField), bucketTimeInterval, bucketFields.map(getNames(_)), aggKV)
    })

    val allAggKVs = aggPairs.flatMap(_._4).groupBy(_._1).map(_._2.head).toList//去除重名columns

    val _timeField = if(aggKey._1.isEmpty) AttrDef.Aggregation._time_interval else aggKey._1.trim
    val aggDataFields = List(new DataField(AttrDef.Aggregation._timestamp, Types.INTEGER, true),
      new DataField(_timeField, Types.VARCHAR, true)) :::
      aggKey._3.map(col => new DataField(col, true)) :::
      allAggKVs.map(aggKV => {
        val columnType = aggKV._3 match {
          case "count"  => Types.BIGINT
          case _ => Types.DECIMAL}
        new DataField(aggKV._1, columnType, false, aggKV._3)
      })


    (0 until aggDataFields.size).foreach(i => aggDataFields(i).setIndex(i))
    fieldTypeFilled.set(false)

//    val aggPair = aggPairs.head

    val allFilterExpr = conf.map(_._7).filter(_.isDefined).map(_.get).distinct

    (aggDataFields, aggKey._1, aggKey._2,
      aggKey._3,
//      aggPair._4.map(kv => (kv._2, kv._3, kv._4))
      aggPairs
      , allFilterExpr)
  }

  def getAliasName(fullName: String): String = {
    if(" (as|AS) ".r.findFirstIn(fullName).isDefined)
      fullName.split(" (as|AS) ")(1).trim
    else fullName
  }

  def getRealName(fullName: String): String = {
    if(" (as|AS) ".r.findFirstIn(fullName).isDefined)
      fullName.split(" (as|AS) ")(0).trim
    else fullName
  }

  def getNames(fullName: String): (String, String) = {
    if(" (as|AS) ".r.findFirstIn(fullName).isDefined)
      (fullName.split(" (as|AS) ")(0).trim, fullName.split(" (as|AS) ")(1).trim)
    else (fullName, fullName)
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
    val datas = dataSets.getDatas.asScala
    if(datas.isEmpty) None else {
      val aggDatas = datas.map(dataSet => {//datas一般情况这里只有一个元素
        val newDataSet = dataSet.clone
        newDataSet.setFields(aggDataFields.asJava)
        newDataSet.cleanRows()
        if(!fieldTypeFilled.getAndSet(true)){
          for(aggPair <- aggPairs) {
            val bucketFieldPairs = aggPair._3
            newDataSet.getFields.asScala.foreach(field => {
              if(field.getColumnType == 0 && bucketFieldPairs.find(_._2 == field.getColumnName).isDefined) {
                val tmpPair = bucketFieldPairs.find(_._2 == field.getColumnName).get
                field.setColumnType(dataSet.getField(tmpPair._1).getColumnType)
              }
            })
          }
        }

        for(aggPair <- aggPairs) {
          val bucketTimeField = aggPair._1._1 //realName
          val bucketTimeInterval = aggPair._2
          val bucketFields = aggPair._3.map(_._1) //realName
          val aggKVs = aggPair._4
          val timeIntervalKey = if(bucketTimeField.isEmpty) {
            dataSet.getRows.asScala.map(row => formatByBucketInterval(row.getExecuteTime, bucketTimeInterval))
          }else {
            dataSet.getFieldValues(bucketTimeField).asScala.map(time =>
              if(bucketTimeInterval.matches("\\d+L")) {
                val length = bucketTimeInterval.trim.take(bucketTimeInterval.trim.length - 1).toInt
                if(time.length >= length){
                  time.substring(0, length)
                }else throw new TaskRunningException(s"bucket time interval [$bucketFields] is not right !")
              }else {
                formatByBucketInterval(time.toLong * 1000, bucketTimeInterval)
              })
          }
          val meta = dataSet.getRows.asScala.map(row => (row.getExecuteTime, row.getBinLogPosition, row.getEventType))
          val bucketValues = bucketFields.map(field => dataSet.getFieldValues(field).asScala).transpose


          val groupRows = timeIntervalKey.zip(bucketValues).zip(meta).zip(dataSet.getRows.asScala).map(row => {
            val timeInterval = row._1._1._1
            val bucketValues: mutable.Buffer[String] = row._1._1._2.toBuffer
            val meta= row._1._2
            val rows = row._2
            (timeInterval, bucketValues, rows, meta)
          }).groupBy(row => (row._1, row._2))

          groupRows.foreach(group => {
            val timeInterval = group._1._1
            val x = timeInterval.padTo(14, '0')
            val unitTimestamp = DateUtils.parse(x, "yyyyMMddHHmmss").getTime / 1000
            val bucketValues = group._1._2
            val stats = dealSameGroupBatch(group, aggKVs, dataSet)

            if(stats.filterNot(_._2 == null).isDefinedAt(0)) {
              val row =if(aggPairs.size > 1) {
                newDataSet.getOrAddRow(meta.last._1, meta.last._2, (unitTimestamp.toString :: (timeInterval :: bucketValues.toList)).asJava)
              }else {
                newDataSet.addRowAndWithNull(meta.last._1, meta.last._2, (unitTimestamp.toString :: (timeInterval :: bucketValues.toList)).asJava)
              }
              mergeRowValue(newDataSet, row, stats.filterNot(_._2 == null))
            }
          })

        }
        newDataSet
      })
      val newBatchDataSet = dataSets.clone()
      newBatchDataSet.setDatas(aggDatas.asJava)
      Some(newBatchDataSet)
    }
  }

  def mergeRowValue(dataSet: DataSet, row: DataRow, stats: List[(String, Any)]): Unit ={
    for (elem <- stats) {
      val fieldIndex = dataSet.getFieldIndex(elem._1)
      if(fieldIndex < 0) {
        throw new TaskRunningException(s"field[${elem._1}]  not find in data set[${dataSet.getFields.asScala.map(_.getColumnName).mkString(", ")}]")
      }
      val fieldValue = elem._2
      val origValue = row.getValues.get(fieldIndex)
      val mergeValue = if(origValue != null && !origValue.isEmpty) {
        if(fieldValue.isInstanceOf[Int]) {
          fieldValue.asInstanceOf[Int] + origValue.toInt
        }else if(fieldValue.isInstanceOf[Double]) {
          fieldValue.asInstanceOf[Double] + origValue.toDouble
        }else if(fieldValue.isInstanceOf[BigDecimal]) {
          fieldValue.asInstanceOf[Int] + origValue.toInt
        }else{
          throw new TaskRunningException(s"type = ${fieldValue.getClass}, value = ${fieldValue.toString}," +
            s" orig = ${origValue}, unsupport merge !")
        }

      }else fieldValue

      row.getValues.set(fieldIndex, mergeValue.toString)
    }

  }
  def filterDataSetValues(values: mutable.Buffer[(String, mutable.Buffer[String], DataRow, (Long, String, EventType))],
                          filterExpressionOp: Option[(String, List[String])],dataSet: DataSet) = {
    val filterExprOp = if(filterExpressionOp.isDefined){
      val expr = filterExpressionOp.get._1
      val fields = filterExpressionOp.get._2
      val fieldIndexs = fields.map(fld => fld -> {
        val idx = dataSet.getFieldIndex(fld)
        if(idx < 0) {
          throw new TaskRunningException(s"[$fld] can't be find in dataset !")
        }
        idx
      })
      (Some(expr, fields, fieldIndexs))
    }else None

    val filterValues = values.filter(row => {
      if(filterExprOp.isDefined) {
        val parameters = filterExprOp.get._3.map(entry => entry._1 -> {
          val value = row._3.getValues.get(entry._2)
          if(dataSet.getField(entry._2).getColumnType == Types.VARCHAR){
            s"'$value'"
          }else value
        }).toMap
        SPELUtil.invokeExpress(filterExprOp.get._1, filterExprOp.get._2, parameters).toBoolean
      }else true
    })
    filterValues
  }

  def dealSameGroupBatch(group: ((String, mutable.Buffer[String]), mutable.Buffer[(String, mutable.Buffer[String], DataRow, (Long, String, EventType))]),
                         aggKVs:List[(String, List[String], String, Option[(String, List[String])])],
                         dataSet: DataSet): List[(String, Any)] = {
    val values = group._2
    val stats = aggKVs.map(kv => {
      val (targetColumn, metricFields , metricFunction, filterExpressionOp) = kv

      val filterValues = filterDataSetValues(values, filterExpressionOp, dataSet)

      val metricVal = metricFields.head
      val fieldIndex = dataSet.getFieldIndex(metricVal)
      val metricExpressOp = if(fieldIndex < 0) {
        val exprOp = getInitExpr(metricVal)
        if(exprOp.isDefined) {
          val flds = exprOp.get._2
          Some(exprOp.get._1, flds, flds.map(fld => fld -> dataSet.getFieldIndex(fld)))
        }else {
          logger.error(s"$metricVal is not field or express !")
          throw new TaskRunningException(s"$metricVal is not exists field !")
        }
      } else None

      val aggValues = filterValues.map(row => {
        val value = if(metricExpressOp.isDefined) {
          val parameters = metricExpressOp.get._3.map(entry => entry._1 -> row._3.getValues.get(entry._2)).toMap
          SPELUtil.invokeExpress(metricExpressOp.get._1, metricExpressOp.get._2, parameters)
        }else {
          row._3.getValues.get(fieldIndex)
        }
        parseValue(value, metricFunction)
      })

      val stat = if(aggValues.isEmpty) null else metricFunction match {
        case "count" => aggValues.length
        case "sum" => aggValues.sum
        case "max" => aggValues.max
        case "min" => aggValues.min
        case "avg" => aggValues.sum / aggValues.length
        case "distinctcount" => 0L//TODO
        case "percentiles" => 0L//TODO
        case _ => throw new TaskRunningException(s"express [${metricFunction}] is not supported !")
      }
      (targetColumn, stat)
    })
    stats
  }

  def getInitExpr(metricVal: String): Option[(String, List[String])] = {
    if(!expressParseOp.contains(metricVal)){
      logger.info(s"$metricVal is not exists field !")
      expressParseOp.put(metricVal, SPELUtil.parseExpress(metricVal))
    }
    expressParseOp.get(metricVal).get
  }

  case class BaseMetric(count: Long, sum: Double, max: Double, min: Double)

  def parseValue(value: String, metricFunction: String): Double = {
    metricFunction match {
      case "count" => 1d
      case "sum" | "max" | "min" | "avg" | "distinctcount" | "percentiles"=> if(value == null) 0.00 else value.toDouble
      case _ => throw new TaskRunningException(s"express [${metricFunction}] is not supported !")
    }
  }

  def formatByBucketInterval(executeTime: Long, bucketInterval: String): String = {
    if(bucketInterval.matches("\\d+(s|m|h|d|w|M|y)")) {
      val interval = bucketInterval.take(bucketInterval.length - 1).toInt
      val unit = bucketInterval.last
      val internalString = unit match {
        case 's' => DateUtils.getDate(new Date(executeTime), "yyyyMMddHHmmss")
        case 'm' => DateUtils.getDate(new Date(executeTime), "yyyyMMddHHmm")
        case 'h' => DateUtils.getDate(new Date(executeTime), "yyyyMMddHH")
        case 'd' => DateUtils.getDate(new Date(executeTime), "yyyyMMdd")
        case 'w' => {
          val cal = Calendar.getInstance()
          cal.setFirstDayOfWeek(Calendar.MONDAY)
          cal.setTimeInMillis(executeTime)
          val weekOfMonth = cal.get(Calendar.WEEK_OF_MONTH)
          DateUtils.getDate(new Date(executeTime), "yyyyMM") + weekOfMonth
        }
        case 'M' => DateUtils.getDate(new Date(executeTime), "yyyyMM")
        case 'y' => DateUtils.getDate(new Date(executeTime), "yyyy")
      }
      internalString
    }else {
      new TaskRunningException(s"bucketInterval[${bucketInterval}] illegal!")
      ""
    }
  }
}
