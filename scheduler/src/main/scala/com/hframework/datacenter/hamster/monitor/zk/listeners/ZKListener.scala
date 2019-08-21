package com.hframework.datacenter.hamster.monitor.zk.listeners

import com.hframework.utils.scala.Logging
import com.hframework.datacenter.hamster.monitor.zk.ZKNodeMonitor
import com.hframework.datacenter.hamster.zookeeper.ZKClient
import org.I0Itec.zkclient.exception.ZkNoNodeException

import scala.collection.JavaConverters._

trait ZKListener{
  def childrenChange(add: List[String], del: List[String] = List.empty, all: List[String] = List.empty, versionNo: String = "")
  def nodeCreate(paths: List[String]): Unit
  def nodeDelete(path: String): Unit
  def nodeDataChange(path: String): Unit
  def nodeChildrenChange(path: String, children: scala.List[String] = Nil): Unit
}

trait ZKChildrenListener extends ZKListener{
  override def nodeCreate(paths: List[String]): Unit = ???
  override def nodeDelete(path: String): Unit = ???
  override def nodeDataChange(path: String): Unit = ???
  override def nodeChildrenChange(path: String, children: scala.List[String] = Nil): Unit = ???
}

trait ZKDataListener extends ZKListener{
  override def childrenChange(add: List[String], del: List[String] = List.empty, all: List[String] = List.empty, versionNo: String = "") = {}
  override def nodeCreate(paths: List[String]): Unit = ???
  override def nodeDelete(path: String): Unit = ???
  override def nodeDataChange(path: String): Unit = ???
  override def nodeChildrenChange(path: String, children: scala.List[String] = Nil): Unit = ???

  @throws[Exception]
  def handleDataChange(dataPath: String, data: Any): Unit

  @throws[Exception]
  def handleDataDeleted(dataPath: String): Unit
}

trait ZKNodeListener extends ZKListener{
  override def childrenChange(add: List[String], del: List[String] = List.empty, all: List[String] = List.empty, versionNo: String = "") = {}
}

trait ZKNodeSimpleListener extends ZKNodeListener{
  override def nodeCreate(paths: List[String]): Unit = {}
  override def nodeDelete(path: String): Unit = {}
  override def nodeDataChange(path: String): Unit = {}
  override def nodeChildrenChange(path: String, children: scala.List[String] = Nil): Unit = {}
}

abstract class ZKNodeWrapperListener(prototypeKey: String, leafWatchChildrenToo: Boolean = true) extends ZKNodeSimpleListener with Logging{
  var subNodeCache = Map.empty[String, Set[String]]

  lazy val parentListener: ZKNodeListener = getParentListener

  def getParentListener: ZKNodeListener

  override def nodeChildrenChange(path: String, children: scala.List[String] = Nil): Unit = {
    val childrenList =  if(children.isEmpty){
      try ZKClient.getChildren(path).asScala catch {
        case e: ZkNoNodeException => logger.info(s"$path children change ignore, because ${e.getMessage}")
          List.empty[String].toBuffer
      }
    }else children.toBuffer


    val addChildren = if(subNodeCache.contains(path)){
      childrenList.--(subNodeCache.get(path).get)
    }else {
      childrenList
    }
    addChildren.foreach(child => {
      logger.info(s"[${getChildPath(path,child)}]new child && add new watcher(${parentListener.getClass.getSimpleName})!")
      val newWatcher = new ZKNodeMonitor(prototypeKey, getChildPath(path,child), leafWatchChildrenToo)
      newWatcher.addListener(parentListener)
      newWatcher.setup
    })


    subNodeCache += (path -> childrenList.toSet)
    logger.info(s"[$path] children change, new children size = ${addChildren.length} !")
    //循环删除时，出问题了。
    if(!addChildren.isEmpty && parentListener != null) {
      logger.info(s"[$path] children change, new children size = ${addChildren.length}, try to invoke listener [$parentListener] for new node create !")
      parentListener.nodeCreate(addChildren.map(getChildPath(path, _)).toList)
    }

  }

  def getChildPath(path: String, child: String):String = {
    path + "/" + child
  }
}

abstract class ZKNodeWrapperSpecificListener(prototypeKey: String, leafWatchChildrenToo: Boolean = false, specificNode: String) extends ZKNodeSimpleListener with Logging{
  var subNodeCache = Map.empty[String, Set[String]]

  lazy val parentListener: ZKNodeListener = getParentListener

  def getParentListener: ZKNodeListener

  override def nodeChildrenChange(path: String, children: scala.List[String] = Nil): Unit = {
    val childrenList =  if(children.isEmpty){
      try ZKClient.getChildren(path).asScala catch {
        case e: ZkNoNodeException => logger.info(s"$path children change ignore, because ${e.getMessage}")
          List.empty[String].toBuffer
      }
    }else children.toBuffer


    val addChildren = if(subNodeCache.contains(path)){
      childrenList.--(subNodeCache.get(path).get)
    }else {
      if (specificNode == null || specificNode == "")
        childrenList
      else
        childrenList.filter(_.indexOf(specificNode) != -1)
    }
    addChildren.foreach(child => {
      logger.info(s"[${getChildPath(path,child)}]new child && add new watcher(${parentListener.getClass.getSimpleName})!")
      val newWatcher = new ZKNodeMonitor(prototypeKey, getChildPath(path,child), leafWatchChildrenToo)
      newWatcher.addListener(parentListener)
      newWatcher.setup
    })


    subNodeCache += (path -> childrenList.toSet)
    logger.info(s"[$path] children change, new children size = ${addChildren.length} !")
    //循环删除时，出问题了。
    if(!addChildren.isEmpty && parentListener != null) {
      logger.info(s"[$path] children change, new children size = ${addChildren.length}, try to invoke listener [$parentListener] for new node create !")
      parentListener.nodeCreate(addChildren.map(getChildPath(path, _)).toList)
    }

  }

  def getChildPath(path: String, child: String):String = {
    path + "/" + child
  }
}

