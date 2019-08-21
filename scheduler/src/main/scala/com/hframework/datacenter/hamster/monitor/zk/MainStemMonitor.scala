package com.hframework.datacenter.hamster.monitor.zk

import java.lang
import java.util.concurrent.TimeUnit

import com.alibaba.fastjson.JSON
import com.hframe.hamster.node.monitor.bean.{MainStemData, PipeLinePrototypeKey}
import com.hframe.hamster.node.monitor.listener.MainStemListener
import com.hframework.datacenter.hamster.zookeeper.ZKClient
import org.I0Itec.zkclient.IZkDataListener
import org.I0Itec.zkclient.exception.ZkNodeExistsException
import org.apache.commons.lang.exception.ExceptionUtils

import scala.collection.JavaConversions._

/**
  * 主备切换控制器，active的只有一位，所有的standy在进行竞争时
  * @param prototypeKey
  */
class MainStemMonitor(prototypeKey: String, rootPath: String, currentNodeId: Long)
  extends PrototypeMonitor[MainStemListener](prototypeKey, rootPath)
  with IZkDataListener{

  private var mainStemData: MainStemData = null

  override def handleDataChange(dataPath: String, data: scala.Any): Unit  = {
    val mainStemData = JSON.parseObject(data.asInstanceOf[Array[Byte]], classOf[MainStemData]).asInstanceOf[MainStemData]
    if (isMine(mainStemData.getNodeId)){
      if (!mainStemData.isActive) { //表明手工设置主节点失效，因此删除主节点，然后可以重新主机竞争环境
        releaseMainStem
      }else mutex.set(true)
    }
    setMainStemData(mainStemData)
  }
  override def handleDataDeleted(dataPath: String): Unit = {
    mutex.set(false)
    //delete前就是本机在运行
    if (mainStemData.isActive && isMine(mainStemData.getNodeId)) initMainStem
    else scheduler.schedule(new Runnable() {
      override def run(): Unit = {
        initMainStem
      }
    }, DEFAULT_DELAY_TIME, TimeUnit.SECONDS)
  }

  /** 启动逻辑 */
  override protected  def startInternal(): Unit = {
    ZKClient.get().subscribeDataChanges(rootPath, this)
    initMainStem
    logger.info(s"main stem monitor start finish, ${prototypeKey} ..")
  }

  /** 加载逻辑 */
  override def reload(): Unit = initMainStem

  /** 销毁逻辑 */
  override protected  def destroyInternal(): Unit = {
    logger.info(s"main stem monitor destroy, ${prototypeKey} ..")
    ZKClient.get().unsubscribeDataChanges(rootPath, this)
    releaseMainStem
    scheduler.shutdownNow
  }

  /**
    * 向zookeeper申请为“主”状态，如果已经存在直接更新即可
    */
  private def initMainStem(): Unit = {
    if (!running) return
    try {
      mutex.set(false)
      val mainStemData = new MainStemData
      mainStemData.setActive(true)
      mainStemData.setNodeId(currentNodeId)
      mainStemData.setPrototypeKey(PipeLinePrototypeKey.value(prototypeKey.toLong))
      mainStemData.setMainStemStatus(MainStemData.MainStemStatus.TAKEING)
      ZKClient.createEphemeral(rootPath, mainStemData, true)
      this.mainStemData = mainStemData
      fireProcessActiveEnter()
      mutex.set(true)
    } catch {
      case _: ZkNodeExistsException =>
        val mainStem = getZkData
        if (mainStem != null) {
          mainStemData = mainStem
          if (mainStemData.isActive && isMine(mainStemData.getNodeId)) mutex.set(true)
        }
        else initMainStem
      case e: Exception =>
        logger.error(s"init main stem error =>${prototypeKey}:${ExceptionUtils.getFullStackTrace(e)}")
    }
  }

  private def releaseMainStem(): Unit = {
    if (check) {
      mutex.set(false)
      ZKClient.delete(rootPath)
      fireProcessActiveExit()
    }
  }

  @throws[InterruptedException]
  def waitForPermit(): Unit = {
    logger.info("wait for main stem permit ..")
    mutex.get()
  }

  def isPermit: Boolean = mutex.state


  private def check: Boolean = {
    mainStemData = getZkData
    if (isMine(mainStemData.getNodeId)) return true
    false
  }

  def isMine(targetNodeId: lang.Long): Boolean = {
    currentNodeId equals targetNodeId
  }

  def getZkData: MainStemData = {
    val mainStem = ZKClient.readData(rootPath)
    JSON.parseObject(mainStem, classOf[MainStemData])
  }

  def setMainStemData(mainStemData:  MainStemData): Unit = this.mainStemData = mainStemData

  private def fireProcessActiveEnter(): Unit = {
    for (listener <- listeners) {
      listener.processActiveEnter()
    }
  }

  private def fireProcessActiveExit(): Unit = {
    for (listener <- listeners) {
      listener.processActiveExit()
    }
  }

}