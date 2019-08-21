package com.hframework.datacenter.hamster.worker.tasks.load

import java.util.concurrent.atomic.AtomicInteger

import com.hframe.ext.bean.SubscribeData
import com.hframe.hamster.node.HamsterConst
import com.hframe.hamster.node.logHandlers.LoadDumpHandler
import com.hframe.hamster.node.monitor.bean.SharableEventData
import com.hframework.common.client.kafka.KafkaService
import com.hframework.common.frame.ServiceFactory
import com.hframework.common.util.message.JsonUtils
import com.hframework.datacenter.hamster.workes.bean.BatchDataSet
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.tasks.AbstractLoader
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.Kafka
import com.hframework.hamster.cfg.service.interfaces.{ICfgBrokerSV, ICfgTopicSV}
import org.apache.commons.lang.math.NumberUtils
import org.slf4j.MDC

import scala.collection.JavaConverters._
import scala.util.Random

/**
  * Kafka生产者
  * @param prototypeKey 任务标识
  * @param __jobNodeMeta 任务定义
  */
class KafkaProducerLoader(prototypeKey: String, deployMeta: DeployJobNodeMeta, var __jobNodeMeta: JobNodeMeta)
  extends AbstractLoader(prototypeKey, deployMeta,  __jobNodeMeta, __jobNodeMeta.jobMeta){

  val robinNextKey = new AtomicInteger(0)

  @volatile var (service, topic, partition, partitionField, partitionStrategy) = parse

  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this.__jobNodeMeta = newJobNodeMeta
    val result = parse
    service = result._1
    topic = result._2
    partition = result._3
    partitionField = result._4
    partitionStrategy = result._5
  }

  def parse: (String,  String, Int, String, Long) = {
    val kafkaId = jobNode.getLong(Kafka.targetKafka) //中介者
    val topicId = jobNode.getLong(Kafka.targetTopic) //Topic
    val partitionField = jobNode.getString(Kafka.partitionField) //分区字段
    val partitionStrategy = jobNode.getLong(Kafka.partitionStrategy) //分区策略
    val broker = ServiceFactory.getService(classOf[ICfgBrokerSV]).getCfgBrokerByPK(kafkaId)
    val topic = ServiceFactory.getService(classOf[ICfgTopicSV]).getCfgTopicByPK(topicId)
    (broker.getAddrList, topic.getCfgTopicCode, topic.getPartitions.toInt, partitionField, partitionStrategy)
  }

  /**
    * 执行一次业务处理
    * @param processId 处理批次号
    * @param eventData 元数据
    * @param dataSets   待处理数据
    * @return 处理结果数据
    */
  override def execute(processId: Long, eventData: SharableEventData , dataSets:  BatchDataSet): Option[BatchDataSet] = {
    val data = dataSets.getDatas.asScala
    val subscribeDatas = data.flatMap(dataSet => {
      val fields = dataSet.getFields.asScala
      val rows = dataSet.getRows.asScala
      rows.map(row => {
        val values = row.getValues.asScala
        val oldValues = row.getOldValues.asScala
        val updates = row.getUpdates.asScala

        val subscribeData = new SubscribeData
        subscribeData.setSchemaName(dataSet.getSchemaName)
        subscribeData.setTableName(dataSet.getTableName)
        subscribeData.setEventType(row.getEventType)
        subscribeData.setExecuteTime(String.valueOf(row.getExecuteTime))
        subscribeData.setTablePosition(row.getBinLogPosition)
        subscribeData.setSubscribeId(String.valueOf("-1"))
        subscribeData.setLoadTime(System.currentTimeMillis + "")

        subscribeData.setColumns(fields.zipWithIndex.map(fieldAndIndex => {
          val field = fieldAndIndex._1
          val index = fieldAndIndex._2
          val dataItem = new SubscribeData.DataItem
          dataItem.setColumnName(field.getColumnName)
          dataItem.setColumnType(field.getColumnType)
          dataItem.setColumnValue(values(index))
          dataItem.setUpdate(if(updates.size > index) updates(index) else false)
          dataItem.setOldColumnValue(if(oldValues.size > index) oldValues(index) else null)
          field.getColumnName -> dataItem
        }).toMap.asJava)
        subscribeData
      })
    })
    subscribeDatas.foreach(subscribeData => {
      val keyVal = subscribeData.getColumns.get(partitionField).getColumnValue
      val msgString = JsonUtils.writeValueAsString(subscribeData)
      val key = partitionStrategy match {
        case RandomStrategy.index => Random.nextInt(partition)
        case RoundRobinStrategy.index => {
          robinNextKey.compareAndSet(partition, 0)
          robinNextKey.getAndIncrement()
        }
        case ConsistentHashStrategy.index => {
          if(keyVal == null) Random.nextInt(partition)
          else if (NumberUtils.isDigits(keyVal.trim)) keyVal.toInt % partition.toInt
          else keyVal.hashCode % partition.toInt
        }
        case LeastActiveStrategy.index => Random.nextInt(partition)
      }
      val future = ServiceFactory.getService(classOf[KafkaService]).sendMessage(service, topic, key, msgString)
      future.get() //这里需要,否则异步执行，抛出异常线程无法中断

      LoadDumpHandler.info(prototypeKey,
        s"severs = ${service} | topic = ${topic} | partition = ${key} | msg id = ${} | msg = ${msgString}")
    })
    None //load处理完成后，下一步待处理数据为None
  }
}

class Strategy(val index: Byte,val name: String)
object ConsistentHashStrategy extends Strategy(1, "一致性Hash")
object RoundRobinStrategy extends Strategy(2, "轮询")
object RandomStrategy extends Strategy(3, "随机")
object LeastActiveStrategy extends Strategy(4, "最小活跃数")
