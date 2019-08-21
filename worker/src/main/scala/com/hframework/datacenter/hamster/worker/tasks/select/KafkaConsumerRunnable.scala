package com.hframework.datacenter.hamster.worker.tasks.select

import java.util.Properties
import java.util.concurrent.ArrayBlockingQueue
import com.hframe.hamster.node.cannal.bean.HeartBreak
import com.hframe.hamster.node.zk.ZooKeeperClient
import com.hframework.utils.scala.JacksonScalaUtils
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer, OffsetAndMetadata}
import org.apache.kafka.common.TopicPartition
import scala.collection.JavaConverters._
import scala.collection.mutable
import com.hframework.datacenter.hamster.worker.tasks.Task
/**
  * @author songge
  * @version 2019-01-21
  */
class KafkaConsumerRunnable(props: Properties,
                            topics: List[String],
                            offsetsQueue: ArrayBlockingQueue[mutable.HashMap[TopicPartition, OffsetAndMetadata]],
                            recordsQueue: ArrayBlockingQueue[ConsumerRecords[String, String]],
                            queueMaxSize: Int,finalWatchPath: String) extends Task{

  var consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(topics.asJava)
  val heartBreak = new HeartBreak
  val windowSize = queueMaxSize / 10
  @Override
  override protected def startInternal(): Unit = {
    while (isRunning) {
      this.synchronized {
        val remainQueueSize = windowSize - recordsQueue.size()
        if (remainQueueSize > 0) {
          val records = consumer.poll(3000)
          if (!records.isEmpty) {
            recordsQueue.put(records)
          }
          else {
            updateHeartBreak(None)
          }
        }
        // 提交offsets
        while (!offsetsQueue.isEmpty) {
          val offsets = offsetsQueue.take()
          consumer.commitSync(offsets.asJava)
        }
      }
    }
  }
  def reload(props: Properties, topics: List[String]): Unit = {
    this.synchronized {
      if (consumer.isInstanceOf[KafkaConsumer[String, String]]) {
        while (!offsetsQueue.isEmpty) {
          val offsets = offsetsQueue.take()
          consumer.commitSync(offsets.asJava)
        }
        consumer.close()
      }
      consumer = new KafkaConsumer[String, String](props)
      consumer.subscribe(topics.asJava)
    }
  }
  def updateHeartBreak(executeTimeStamp:Option[Long]): Unit ={
    if (executeTimeStamp isDefined) heartBreak.setExecuteTimeStamp(executeTimeStamp.get )
    val currentTime = System.currentTimeMillis
    val delaySeconds = (currentTime  - heartBreak.getFetchTimeStamp)/1000
    if (delaySeconds > 10 ){
      heartBreak.setFetchTimeStamp(currentTime)
      logger.info("update heart break :{},{}",finalWatchPath, heartBreak)
      ZooKeeperClient.getInstance.writeData(finalWatchPath,JacksonScalaUtils.toJson(heartBreak))
    }
  }
  override def threadName: String = "KafkaConsumerRunnable_Task"

  override def name: String = "KafkaConsumerRunnable"

  override def reload(): Unit = {}
  /** 销毁 */
  override protected def destroyInternal(): Unit = {
  }
  override def shutdown(): Unit ={
    super.shutdown()
  }
}
