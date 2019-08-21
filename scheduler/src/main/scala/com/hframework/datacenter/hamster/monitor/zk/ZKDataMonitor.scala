package com.hframework.datacenter.hamster.monitor.zk

import com.hframework.datacenter.hamster.monitor.zk.listeners.{ZKDataListener, ZKNodeListener}
import com.hframework.datacenter.hamster.zookeeper.ZKClient
import org.I0Itec.zkclient.IZkDataListener

/**
  * 用于监控zk直接path下节点数据的变化
  *
  * @param prototypeKey
  */
class ZKDataMonitor(prototypeKey: String, rootPath: String)
  extends PrototypeMonitor[ZKDataListener](prototypeKey, rootPath)
  with IZkDataListener{
  /** 启动逻辑 */
  protected override def startInternal(): Unit = {
    zkClientx.subscribeDataChanges(rootPath, this)
    val data = ZKClient.readData(rootPath, classOf[String])
    handleDataChange(rootPath, data)
  }

  /** 加载逻辑 */
  override def reload(): Unit = {
    destroyInternal
    startInternal
  }

  /** 销毁逻辑 */
  protected override def destroyInternal(): Unit = {
    zkClientx.unsubscribeDataChanges(rootPath, this)
  }

  override def handleDataDeleted(dataPath: String): Unit =
    notifyListener((listener: ZKDataListener) => listener.handleDataDeleted(dataPath))

  override def handleDataChange(dataPath: String, data: scala.Any): Unit =
    notifyListener((listener: ZKDataListener) => listener.handleDataChange(dataPath, data))
}
