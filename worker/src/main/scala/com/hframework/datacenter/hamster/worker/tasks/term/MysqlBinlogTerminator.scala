package com.hframework.datacenter.hamster.worker.tasks.term

import java.util.concurrent.atomic.AtomicLong

import com.hframe.hamster.node.monitor.bean.ProcessEventData
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.HamsterWorker
import com.hframework.datacenter.hamster.worker.tasks.select.MysqlBinlogSelector
import com.hframework.datacenter.hamster.worker.tasks._
import com.hframework.datacenter.hamster.zookeeper.ZKPathUtils

import scala.collection.mutable

/**
  * Mysql Binlog 应答
  * @param prototypeKey
  * @param __jobNodeMeta
  * @param __dependencyJobNode
  */
class MysqlBinlogTerminator(prototypeKey: String,
                            deployMeta: DeployJobNodeMeta,
                            var __jobNodeMeta: JobNodeMeta,
                            var __dependencyJobNode: scala.List[JobNodeMeta] = Nil)
  extends AbstractTerminator(prototypeKey, deployMeta, __jobNodeMeta, __jobNodeMeta.jobMeta,
    ZKPathUtils.getJobGroupProcessPath(deployMeta.deployCode, __jobNodeMeta.jobMeta.selectKey), __dependencyJobNode){
//  val inits = new java.util.TreeMap[String, ProcessAck]()
  val inits = mutable.Buffer.empty[ProcessAck]
  val acks = mutable.Buffer.empty[ProcessAck]

  val nextAckId = new AtomicLong(-1)

  /**Selector仅reload JobNodeMeta 没有意义，因为没有需要动态修改的项目*/
  @Deprecated
  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this.__jobNodeMeta = newJobNodeMeta
  }

  override def reload(dependencyJobNode: scala.List[JobNodeMeta]): Unit = {
    super.reload(dependencyJobNode)
    this.__dependencyJobNode = dependencyJobNode
  }

  /**
    * 任务处理结束确认
    * Success: 确认成功; Failed: 确认失败,抛出异常; Blocking:确认阻塞,等待x秒重试
    *
    * @param processId 处理批次号
    * @param eventData 元数据
    * @return 选项：Success, Failed, Blocking
    */
  override def ack(processId: Long, eventData: ProcessEventData, tryTimes: Integer = 0): TermStatus = {
    if(nextAckId.get() > processId) {
      //比如：另外当删除任务时，原先6个job，删除一个job时，但该job已经ack，那么同样会发生重复处理，这里需要tryTermin内部处理
      //另外tryAck重复处理时，是否同样进入该分支中？
      logger.warn(s"repeat add processId, tryProcessId = $processId; nextAckId = ${nextAckId.get()}; ignore !")
      return Success
    }
    if(tryTimes == 0) insertProcessAckToBufferAndSorted(acks, ProcessAck(processId, eventData, false))
    ackInternal(processId, tryTimes)
  }

  /**
    * 有序插入
    * @param buffer
    * @param element
    * @return
    */
  def insertProcessAckToBufferAndSorted(buffer: mutable.Buffer[ProcessAck], element: ProcessAck): Boolean = {
    buffer.synchronized{
      val bufferLength = buffer.length
      for(i <- 1 to bufferLength) {
        val tempElement = buffer(bufferLength - i)
        if(tempElement.processId < element.processId) {
          buffer.insert(bufferLength - i + 1, element)
          return true
        }else if(tempElement.processId == element.processId){
          return false
        }
      }
      buffer.insert(0, element)
      return true
    }
  }

  /**
    * 任务处理开始确认
    * Success: 开始成功; Failed: 开始失败,抛出异常; Blocking:开始阻塞,等待x秒重试
    *
    * @param processId 处理批次号
    * @param eventData 元数据
    * @return 选项：Success, Failed, Blocking
    */
  override def init(processId: Long, eventData: ProcessEventData): TermStatus = {
    insertProcessAckToBufferAndSorted(inits, ProcessAck(processId, eventData, false))
    return ackInternal(processId, isInitTry = true)
  }

  def ackInternal(processId: Long, tryTimes: Integer = 0, isInitTry: Boolean = false): TermStatus ={
    acks.synchronized{
      inits.synchronized{
        if(inits.size > 0 && acks.size > 0 &&
          inits.head.processId == acks.head.processId
          && (nextAckId.get() < 0
          || nextAckId.get() == acks.head.processId)) {
          val selectCacheKey = deployMeta.deployCode + "." + deployMeta.selectKey
          val selectorTask = HamsterWorker.taskRunners.get(selectCacheKey)
          if(selectorTask.isDefined) {
            val selector = selectorTask.get.asInstanceOf[AbstractSelector]
            selector.ackTermi(acks.head.eventData.getBatchId, acks.head.eventData)
            nextAckId.set(acks.head.processId + 1)
            inits.remove(0)
            acks.remove(0)
            Success
          }else {
            Blocking
          }
        }else {
          if(tryTimes > 1000 || tryTimes % 100 == 0){//5分钟前每隔30s提示一次，5分钟后一分钟分钟提示
            if(!isInitTry) {
              logger.warn(s"disorder repay: tryProcessId = $processId; nextAckId = ${nextAckId.get()}; " +
                s"inits = ${inits.map(_.processId).mkString("[", ", ", "]")}; " +
                s"acks = ${acks.map(_.processId).mkString("[", ", ", "]")}, sleep " +
                s"${if(tryTimes > 1000) 60 + "s" else 300 + "ms"} and try again !")
            }
          }

          Outstrip
        }
      }
    }

  }
  case class ProcessAck(processId: Long, eventData: ProcessEventData = null, ack: Boolean = false)
}
