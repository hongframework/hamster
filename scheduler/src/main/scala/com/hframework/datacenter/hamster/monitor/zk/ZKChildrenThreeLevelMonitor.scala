package com.hframework.datacenter.hamster.monitor.zk

import java.util
import java.util.Collections

import com.hframe.hamster.node.HamsterConst
import com.hframework.datacenter.hamster.monitor.zk.listeners.{ZKChildrenListener, ZKListener, ZKNodeListener}
import com.hframework.datacenter.hamster.zookeeper.ZKClient
import org.slf4j.MDC

import scala.collection.JavaConverters._

/**
  * 用于监控zk直接root path 下子节点的变化
  * ROOT ->Child-Node(FirstLevel) -> Child-Node(SecondLevel) -> Child-Node(ThirdLevel)
  * @param prototypeKey
  */
@Deprecated
class ZKChildrenThreeLevelMonitor(prototypeKey: String, rootPath: String)
  extends ZKChildrenTwoLevelMonitor(prototypeKey, rootPath)
    with ZKNodeListener{

  var subNodeCache = Map.empty[String, Set[String]]

  override def nodeChildrenChange(path: String, children: scala.List[String] = Nil): Unit ={
    //到这里的只有第一层子节点下发生了变化
    val children = ZKClient.getChildren(path).asScala
    val add = if(subNodeCache.contains(path)){
      children.--(subNodeCache.get(path).get)
    }else {
      children
    }
    add.foreach(child => {
      var newWatcher = new ZKNodeMonitor(prototypeKey, getChildPath(child))
      newWatcher.addListeners(secondLevelNodeListeners)
      newWatcher.setup
    })
    subNodeCache += (path -> children.toSet)
    notifySpecialListener(firstLevelNodeListeners, (t: ZKNodeListener) => (listener: ZKNodeListener) => listener.nodeChildrenChange(path))

  }

  //叶子节点变化监听
  protected val secondLevelNodeListeners: util.List[ZKNodeListener] = Collections.synchronizedList(new util.ArrayList[ZKNodeListener])

  def addSecondLevelNodeListener(listener: ZKNodeListener): Unit = {
    this.secondLevelNodeListeners.add(listener) //TODO
    //    if (running) {
    //      listener.childrenChange(childrenCache)
    //    }
  }

}
