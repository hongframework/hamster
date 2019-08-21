package com.hframework.datacenter.hamster.monitor.zk

import com.hframe.hamster.common.zkclient.{AsyncWatcher, ZooKeeperx}
import com.hframe.hamster.node.{HamsterConfig, HamsterConst}
import com.hframework.datacenter.hamster.monitor.zk.listeners.ZKNodeListener
import org.apache.commons.lang.exception.ExceptionUtils
import org.apache.zookeeper.Watcher.Event
import org.apache.zookeeper.data.Stat
import org.apache.zookeeper.{KeeperException, WatchedEvent}
import org.slf4j.MDC

import scala.collection.JavaConverters._

/**
  * 用于监控zk直接root path 下子节点的变化
  *
  * @param prototypeKey
  */
class ZKNodeMonitor(prototypeKey: String, rootPath: String, leafWatchChildrenToo: Boolean = true)
  extends PrototypeMonitor[ZKNodeListener](prototypeKey, rootPath)
    with ZKNodeListener{

  val childrenChangeDealDelayMs =  HamsterConfig.getInstance().getHamsterZkChildrenChangeDealDelayMs.toLong

  /** 子列表 **/
  var childrenCache = scala.collection.Set.empty[String]

  /** 启动逻辑 */
  override protected def startInternal(): Unit = addWatch

  def addWatch(): Unit = {
    addDataNodeWatch()
    addChildrenWatch(true)
  }

  lazy val dataNodeWatch = new AsyncWatcher {
    override def asyncProcess(event: WatchedEvent): Unit = {
      try {
        MDC.put(HamsterConst.splitTaskLogFileKey, prototypeKey)
        import Event.EventType._
        event.getType match {
          case NodeDeleted => {
            logger.info(s"miss watch for [${rootPath}],  because current node has deleted ! ")
            nodeDelete(rootPath)

          }
          case NodeCreated => {
            logger.warn(s" add watch for [${rootPath}],  because [${event.getPath}] ${event.getType} ! , not should existed !")
            nodeCreate(scala.List(rootPath))
            addDataNodeWatch()
          }
          case NodeDataChanged => {
            logger.info(s"add watch for [${rootPath}],  because [${event.getPath}] ${event.getType} ! ")
            addDataNodeWatch()
            nodeDataChange(rootPath)
          }
        }
      }catch {
        case e:Exception => logger.error(s"watch deal failed : ${ExceptionUtils.getFullStackTrace(e)}")
      } finally MDC.remove(HamsterConst.splitTaskLogFileKey)
    }
  }

  lazy val childrenWatch = new AsyncWatcher {
    override def asyncProcess(event: WatchedEvent): Unit = {
      try {
        MDC.put(HamsterConst.splitTaskLogFileKey, prototypeKey)
        import Event.EventType._
        event.getType match {
          case NodeDeleted => {//node-deleted不需要重复执行，通过addChildrenWatch来处理
//            logger.info(s"miss watch for [${rootPath}],  because current node has deleted ! ")
//            nodeDelete(rootPath)
          }
          case NodeChildrenChanged => {
            logger.info(s"add watch for [${rootPath}],  because [${event.getPath}] ${event.getType} ! ")
            /*同时删除上下两级节点，下级节点变化走该分支，由于是异步执行，所以addWatch会上级节点可能也删除了，
            会报KeeperException.NoNodeException错误，这时在捕获异常除调用nodeDelete(rootPath)且不加Watch*/
            addChildrenWatch(false)
            /*由于childrenChange触发是异步的，同时添加多个children时，无法保证顺序性，可能同一个children出现多次，
            也或者children没有读取出来，做个延迟读取处理，保证批量处理完成，是否需要做幂等处理*/
            Thread.sleep(childrenChangeDealDelayMs)
            nodeChildrenChange(event.getPath)
          }
        }
      }catch {
        case e:Exception => logger.error(s"watch deal failed : ${ExceptionUtils.getFullStackTrace(e)}")
      } finally MDC.remove(HamsterConst.splitTaskLogFileKey)
    }
  }

  def addDataNodeWatch(reAdd: Boolean = true): Unit = {
    try{
      val connection = zkClientx.getConnection
      val zookeeper = connection.asInstanceOf[ZooKeeperx].getZookeeper
      //需要AsyncWatcher并行触发watch事件，否则zkClientx retryUntilConnected会报错
      //需要判断路径是否存在，因为在删除process时是迭代删除，可能在一边添加时间时，另外一边已经刚好删除了父节点
      zookeeper.getData(rootPath, dataNodeWatch, new Stat())
    }catch {
      case e: KeeperException.NoNodeException => {
        logger.warn("在循环删除process的task node节点时,watch后处理是异步的，[{}]可忽略！", e.getMessage)
        nodeDelete(rootPath)
      }
      case e: KeeperException => {
        logger.warn("unkown error！=> {}", ExceptionUtils.getFullStackTrace(e))
        Thread.sleep(1000 * 5L) //zookeeper 连接被重置
        if(reAdd) addDataNodeWatch(false)
      }
      case e: Exception => logger.error("unkown error！=> {}", ExceptionUtils.getFullStackTrace(e))
    }
  }

  def addChildrenWatch(bootstrap: Boolean = false, reAdd: Boolean = true): Unit = {
    try{
      val connection = zkClientx.getConnection
      val zookeeper = connection.asInstanceOf[ZooKeeperx].getZookeeper
      //需要AsyncWatcher并行触发watch事件，否则zkClientx retryUntilConnected会报错
      //需要判断路径是否存在，因为在删除process时是迭代删除，可能在一边添加时间时，另外一边已经刚好删除了父节点
      val children = if(leafWatchChildrenToo) {
        zookeeper.getChildren(rootPath, childrenWatch)
      }else {
        zookeeper.getChildren(rootPath, false)
      }
      if(bootstrap){
        nodeChildrenChange(rootPath, children.asScala.toList)
      }
    }catch {
      case e: KeeperException.NoNodeException => {
        logger.warn("在循环删除process的task node节点时,watch后处理是异步的，[{}]可忽略！", e.getMessage)
        nodeDelete(rootPath)
      }
      case e: KeeperException.ConnectionLossException => {// 失去连接异常
        logger.warn("zk loss exception error！=> {}", ExceptionUtils.getFullStackTrace(e))
        Thread.sleep(1000 * 5L) //zookeeper 连接被重置
        if(reAdd) addChildrenWatch(bootstrap, true)
      }
      case e: KeeperException => {
        logger.warn("unkown error！=> {}", ExceptionUtils.getFullStackTrace(e))
        Thread.sleep(1000 * 5L) //zookeeper 连接被重置
        if(reAdd) addChildrenWatch(bootstrap, false)
      }
      case e: Exception => logger.error("unkown error！=> {}", ExceptionUtils.getFullStackTrace(e))
    }
  }

  /** 加载逻辑 */
  override def reload(): Unit = addWatch

  /** 销毁逻辑 */
  override protected  def destroyInternal(): Unit = {}

  override def nodeDelete(path: String): Unit = notifyListener((listener: ZKNodeListener) => listener.nodeDelete(path))

  override def nodeDataChange(path: String): Unit = notifyListener((listener: ZKNodeListener) => listener.nodeDataChange(path))

  override def nodeChildrenChange(path: String, children: scala.List[String] = Nil): Unit = notifyListener((listener: ZKNodeListener) => listener.nodeChildrenChange(path))

  override def nodeCreate(paths: scala.List[String]): Unit =  notifyListener((listener: ZKNodeListener) => listener.nodeCreate(paths))

  /** 名称描述 */
  override def name: String = s"[${prototypeKey}][${rootPath}]"
}
