package com.hframework.datacenter.hamster.monitor.zk

import java.util
import java.util.Collections

import com.hframework.datacenter.hamster.monitor.zk.listeners.{ZKChildrenListener, ZKListener, ZKNodeListener}

/**
  * 用于监控zk直接root path 下子节点的变化
  * ROOT ->Child-Node(FirstLevel) -> Child-Node(SecondLevel)
  * @param prototypeKey
  */
@Deprecated
class ZKChildrenTwoLevelMonitor(prototypeKey: String, rootPath: String)
  extends ZKChildrenMonitor(prototypeKey, rootPath)
  with ZKNodeListener{

  override def childrenChange(add: List[String], del: List[String], all: List[String], versionNo: String = ""): Unit = {
    add.foreach(child => {
      var newWatcher = new ZKNodeMonitor(prototypeKey, getChildPath(child))
      newWatcher.addListener(this)
      newWatcher.setup
    })
    notifyListener((listener: ZKChildrenListener) => listener.childrenChange(add, del, all))
  }

  override def nodeDelete(path: String): Unit = notifySpecialListener(firstLevelNodeListeners, (listener: ZKNodeListener) => listener.nodeDelete(path))

  override def nodeDataChange(path: String): Unit = notifySpecialListener(firstLevelNodeListeners, (listener: ZKNodeListener) => listener.nodeDataChange(path))

  override def nodeChildrenChange(path: String, children: scala.List[String] = Nil): Unit = notifySpecialListener(firstLevelNodeListeners, (listener: ZKNodeListener) => listener.nodeChildrenChange(path, children))

  override def nodeCreate(paths: scala.List[String]): Unit = notifySpecialListener(firstLevelNodeListeners, (listener: ZKNodeListener) => listener.nodeCreate(paths))

  //监听器
  protected val firstLevelNodeListeners: util.List[ZKNodeListener] = Collections.synchronizedList(new util.ArrayList[ZKNodeListener])

  def addFirstLevelNodeListener(listener: ZKNodeListener): Unit = {
    this.firstLevelNodeListeners.add(listener) //TODO
    //    if (running) {
    //      listener.childrenChange(childrenCache)
    //    }
  }

}
