package com.hframework.datacenter.hamster.worker.tasks.select

import java.sql.Types
import java.util.Properties
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.atomic.AtomicBoolean

import com.alibaba.fastjson.JSON
import com.hframe.hamster.node.cannal.bean.EventType
import com.hframework.common.frame.ServiceFactory
import com.hframework.common.util.{DateUtils, RegexUtils}
import com.hframework.datacenter.hamster.workes.bean.{BatchDataSet, DataField, DataRow}
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.tasks.AbstractSelector
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.KafkaConsumerProps
import com.hframework.datacenter.hamster.workes.bean.{DataField, DataRow}
import com.hframework.datacenter.hamster.zookeeper.ZKPathUtils
import com.hframework.hamster.cfg.service.interfaces.ICfgBrokerSV
import org.apache.kafka.clients.consumer.{ConsumerRecords, OffsetAndMetadata}
import org.apache.kafka.common.TopicPartition

import scala.collection.JavaConverters._
import scala.collection.mutable
/**
  * @author songge
  * @version 2019-01-02
  */
class KafkaSelector(prototypeKey: String,
                    deployMeta: DeployJobNodeMeta,
                    var __jobNodeMeta: JobNodeMeta,
                    var __dependencyJobNode: scala.List[JobNodeMeta] = Nil)
  extends AbstractSelector(prototypeKey,deployMeta, __jobNodeMeta, __jobNodeMeta.jobMeta,
    ZKPathUtils.getJobGroupProcessPath(deployMeta.deployCode, __jobNodeMeta.jobMeta.selectKey), __dependencyJobNode) {

  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this.__jobNodeMeta = newJobNodeMeta
    val result = parse
    props = result._1
    sourceTopic = result._2
    messageType = result._3
    timeColumn = result._4
    sourceColumns = result._5
    filterExpression = result._6
    dataFilterVars = result._7
    consumer.reload(props, sourceTopic)
  }

  def parse: (Properties, List[String], String, String, String, String, List[String]) = {
    val server = ServiceFactory.getService(classOf[ICfgBrokerSV]).getCfgBrokerByPK(jobNode.getLong(KafkaConsumerProps.sourceKafka))
    val props = new Properties
    props.put("bootstrap.servers", server.getAddrList)
    props.put("group.id", deployMeta.selectKey)
    //props.put("client.id", "")
    props.put("client.id", "consumer")
    props.put("auto.offset.reset", "latest")
    props.put("enable.auto.commit", "false")
    props.put("session.timeout.ms", "30000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val sourceTopic = jobNode.getString(KafkaConsumerProps.sourceTopic)
    val timeColumn = jobNode.getString(KafkaConsumerProps.timeColumn)
    val messageType = jobNode.getString(KafkaConsumerProps.messageType)
    val dataFilterVars = if(filterExpression != null && !filterExpression.isEmpty){
      val vars = RegexUtils.find(filterExpression.replaceAll("'[^']*'", ""), "[a-zA-Z]+[a-zA-Z0-9._]*")
      vars.distinct.toList.sorted
    }else scala.List.empty[String]

    (props, sourceTopic.split(",").toList, messageType, timeColumn, sourceColumns, filterExpression, dataFilterVars)
  }

  @volatile var (props, sourceTopic, messageType, timeColumn, sourceColumns, filterExpression, dataFilterVars) = parse

  val queueMaxSize: Int = 100
  val columnMaxNumber: Int = 20
  // consumer poll下来的records的存放队列
  val recordsQueue = new ArrayBlockingQueue[ConsumerRecords[String, String]](queueMaxSize)
  // 需要commit的offsets队列
  val offsetsQueue = new ArrayBlockingQueue[mutable.HashMap[TopicPartition, OffsetAndMetadata]](queueMaxSize)
  // 存放一个batch中的offsets，方便ack时使用
  val batchOffsetsMap = new mutable.HashMap[Long, mutable.HashMap[TopicPartition, OffsetAndMetadata]]()

  var batchId: Long = 0
  val logType: String = "log"
  val isUpdates = List.fill[java.lang.Boolean](columnMaxNumber)(true)
  logger.info(s"initiate consumer thread: ${props.get("bootstrap.servers")} | ${sourceTopic} | ${deployMeta.selectKey}")
  val consumer = new KafkaConsumerRunnable(props, sourceTopic, offsetsQueue, recordsQueue, queueMaxSize,finalWatchPath)
  //var consumerThread = new Thread(consumer)
  val initQuartzServer = new AtomicBoolean(false)

  override def select(): BatchDataSet = {
    // 如果是第一次select 开启consumerThread
    if(initQuartzServer.compareAndSet(false, true)) {
      consumer.start()
      logger.info(s"consumer of ${deployMeta.jobKey} start!")
    }

    // 填充batchDataSet信息
    batchId += 1
    val currentTime = DateUtils.getCurrentDate
    val batchDataSet = new BatchDataSet
    batchDataSet.setBatchId(batchId)
    batchDataSet.setCreateTime(currentTime)
    batchDataSet.setBinlogStart(s"start")
    batchDataSet.setBinlogEnd(s"end")
    var binlogStartTs: Long = 0
    var binlogEndTs: Long = Long.MinValue

    val dataSetFieldsMap = new mutable.HashMap[String, mutable.HashMap[String, Int]]()

    val records = recordsQueue.take()
    logger.info(s"batch $batchId has ${records.count} records to fill")
    // records => dataSets
    records.asScala.foreach(record => {
      logger.debug(s"client: ${props.get("client.id")}, topic: ${record.topic}, partition: ${record.partition}, offset: ${record.offset}, key: ${record.key}, value: ${record.value}")
      if (record.value() == "") {}
      else {
        try {
          val tableId: Long = -1
          val tableName: String = record.topic + "|" + record.partition.toString
          val schemaName: String = record.topic

          val dataSet = batchDataSet.getOrCreateDataSet(tableId, tableName, schemaName)
          val dataSetIndex = tableName
          val fieldsMap = if(dataSetFieldsMap.contains(dataSetIndex)) {
            dataSetFieldsMap(dataSetIndex)
          } else { new mutable.HashMap[String, Int]() }

          val originData =  record.value
          var dataTime: Long = 0
          val dataFields = dataSet.getFields.asScala
          val dataValues = messageType match {
            case "json" => {
              val dataMap = try{
                logger.info(s"json => $originData")
                JSON.parseObject(originData).asScala
              }catch {
                case e: Exception => {
                  throw new IgnoreException(e)
                }
              }
              // 如果此dataMap存在dataField中不包含的列，那么新增一列
              dataMap.keys.foreach(columnName => {
                if (!fieldsMap.contains(columnName)) {
                  val fieldsSize = dataFields.size
                  fieldsMap.put(columnName, fieldsSize)
                  dataFields.+=(new DataField(fieldsSize, Types.VARCHAR, columnName, false, false))
                  dataSet.addNullRowsField()
                }
              })
              val values = new mutable.ListBuffer[String]()
              logger.info(s"field => ${dataFields.map(_.getColumnName)}")
              dataFields.foreach(dataField => {
                val columnName = dataField.getColumnName
                if (dataMap.contains(columnName) && dataMap.get(columnName) != null) values.+=(dataMap.get(columnName).get.toString)
                else values.+=(null)
              })
              // 从时间列中取出数据时间
              val dataTimeTemp = if (dataMap.contains(timeColumn)) dataMap.get(timeColumn).get else currentTime.getTime
              dataTime = if (dataTimeTemp.getClass.getSimpleName == "String") {
                dataTimeTemp.asInstanceOf[String].toLong / 1000
              } else if (dataTimeTemp.isInstanceOf[java.math.BigDecimal]){
                dataTimeTemp.asInstanceOf[java.math.BigDecimal].longValue()
              }else if(dataTimeTemp.isInstanceOf[java.lang.Integer]){
                dataTimeTemp.asInstanceOf[java.lang.Integer].toLong
              }
              else dataTimeTemp.asInstanceOf[Long] / 1000
              // 返回按序的一条数据列表
              values
            }
            case "string" => {
              // 用系统时间作为数据时间
              dataTime = currentTime.getTime / 1000
              // 如果dataFields中没有logType列，则新增一列
              if (!fieldsMap.contains(logType)) {
                val fieldsSize = dataFields.size
                fieldsMap.put(logType, fieldsSize)
                dataFields.+=(new DataField(0, Types.VARCHAR, logType, false, false))
                dataSet.addNullRowsField()
              }
              List[String](record.value)
            }
          }
          if (!dataSetFieldsMap.contains(dataSetIndex)) dataSetFieldsMap.put(dataSetIndex, fieldsMap)
          // 比较时间信息
          if (dataTime < binlogStartTs) binlogStartTs = dataTime
          if (dataTime > binlogEndTs) binlogEndTs = dataTime
          logger.debug(s"new row in batch ${batchId}: value ${dataValues} | dataTime ${dataTime}")
          // 给dataSet填充一行数据
          val row = new DataRow
          row.setEventType(EventType.INSERT)
          row.setExecuteTime(dataTime)
          row.setBinLogPosition(s"${record.topic}|${record.partition}|${record.offset}")
          row.setValues(dataValues.asJava)
          row.setOldValues(Nil.asJava)
          row.setUpdates(Nil.asJava)
          dataSet.addRow(row)
        }
        catch {
          case e: Exception => {
            logger.error(s"kafka consumer parsing record error: ", e)
            if(!e.isInstanceOf[IgnoreException]) {
              throw e
            }

          }
        }
      }
    })

    // 遍历数据之后，设置batch的start和end时间
    batchDataSet.setBinlogStartTs(binlogStartTs)
    batchDataSet.setBinlogEndTs(binlogEndTs)
    //心跳
    consumer.updateHeartBreak(Some(binlogEndTs*1000))

    // 填充batch的offsets信息，待ack时使用
    val offsets = new mutable.HashMap[TopicPartition, OffsetAndMetadata]()
    records.partitions.asScala.foreach(partition => {
      val partitionRecords = records.records(partition)
      val lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset()
      offsets.put(partition, new OffsetAndMetadata(lastOffset + 1))
    })

    batchOffsetsMap.+=((batchId, offsets))
    batchDataSet
  }


  // 从batchInfo中取出offsets数据，并放入offsets队列待consumer完成commit
  override def ackTermi(batchId: Long): Unit = {
    if (batchOffsetsMap.contains(batchId)) {
      val offsets = batchOffsetsMap(batchId)
      logger.info(s"batch $batchId is going to be committed")
      if (offsets.nonEmpty) offsetsQueue.add(offsets)
      batchOffsetsMap.remove(batchId)
    }
  }
  /** 销毁逻辑 */
  override protected def destroyInternal(): Unit = {
    consumer.shutdown()
  }


  class IgnoreException(cause: Throwable) extends RuntimeException(cause)
}
