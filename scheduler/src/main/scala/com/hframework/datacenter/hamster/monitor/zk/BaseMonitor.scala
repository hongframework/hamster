package com.hframework.datacenter.hamster.monitor.zk

import java.util
import java.util.Collections
import java.util.concurrent.{ExecutorService, Executors}

import com.alibaba.otter.canal.common.utils.BooleanMutex
import com.hframe.hamster.node.HamsterConst
import com.hframework.common.frame.ServiceFactory
import com.hframework.utils.scala.Logging
import com.hframework.datacenter.hamster.CycleLife
import com.hframework.datacenter.hamster.exceptions.TaskRunningException
import com.hframework.datacenter.hamster.monitor.zk.listeners.{ZKListener, ZKNodeListener}
import com.hframework.datacenter.hamster.zookeeper.ZKClient
import org.apache.commons.lang.exception.ExceptionUtils
import org.slf4j.MDC

import scala.collection.JavaConverters._

trait Monitor extends CycleLife with Logging{
  /** 启动 */
  override def setup(): Unit = {
    if (!running) {
      this.synchronized{
        if (!running) {
          running = true
          logger.info(s"${name} monitor starting ...")
          startInternal()
          logger.info(s"${name} monitor started !")
        }
      }
    }
  }
}

trait PrototypeListener[T <: ZKListener] extends Logging {

}

abstract class PrototypeMonitor[T](prototypeKey: String, rootPath: String)
  extends Monitor
    with Logging{
  //监听器
  protected val listeners: util.List[T] = Collections.synchronizedList(new util.ArrayList[T])

  protected val zkClientx = ZKClient.get()

  protected var mutex = new BooleanMutex
  //当zk主机节点删除后，通过该调度线程争当主机
  protected val scheduler = Executors.newScheduledThreadPool(1)
  //竞争主机的时间间隔（单位s)
  protected val DEFAULT_DELAY_TIME = 5

  /** 执行listener监听程序 */
  protected val executorService: ExecutorService = ServiceFactory.getService("executorService").asInstanceOf[ExecutorService]

  def addListener(listener: T): Unit = {
    if(listener != null) {
      this.listeners.add(listener) //TODO
    }
    //    if (running) {
    //      listener.childrenChange(childrenCache)
    //    }
  }

  def addListeners(endNodeListeners: util.List[T]): Unit = {
    this.listeners.addAll(endNodeListeners) //TODO
    //    if (running) {
    //      listener.childrenChange(childrenCache)
    //    }
  }


  def removeListener(listener: T): Unit = {
    this.listeners.add(listener)
  }

  def notifyListener(executor: ListenerExecutor[T]): Unit = {
    for (listener <- listeners.asScala) {
      executorService.submit(new Runnable() {
        override def run(): Unit = {
          try {
            MDC.put(HamsterConst.splitTaskLogFileKey, prototypeKey)
            executor.execute(listener)
          }catch {
            case e: Exception => logger.error(s"monitor notify failed ! ${ExceptionUtils.getFullStackTrace(e)}")
            throw new TaskRunningException(s"monitor notify failed ! ${e.getMessage}")
          }finally MDC.remove(HamsterConst.splitTaskLogFileKey)
        }
      })
    }
  }
  def notifySpecialListener[M](listeners: util.List[M], executor: ListenerExecutor[M]): Unit = {
    for (listener <- listeners.asScala) {
      executorService.submit(new Runnable() {
        override def run(): Unit = {
          try {
            MDC.put(HamsterConst.splitTaskLogFileKey, prototypeKey)
            executor.execute(listener)
          }catch {
            case e: Exception => e.printStackTrace()
          }finally MDC.remove(HamsterConst.splitTaskLogFileKey)
        }
      })
    }
  }


  def getNodeChildLevel(path: String): Int = {
    path.substring(rootPath.length).count(c => c == "/")
  }
//  def notifyListenerSimple(executor: SimpleListenerExecutor): Unit = {
//    for (listener <- listeners.asScala) {
//      executorService.submit(new Runnable() {
//        override def run(): Unit = {
//          try {
//            MDC.put(HamsterConst.splitTaskLogFileKey, prototypeKey)
//            executor.execute(listener)
//          }catch {
//            case e: Exception => e.printStackTrace()
//          }finally MDC.remove(HamsterConst.splitTaskLogFileKey)
//        }
//      })
//    }
//  }

  def getChildPath(child: String):String = {
    rootPath + "/" + child
  }

  /** 名称描述 */
  override def name: String = s"[${prototypeKey}][${rootPath}] "
}

trait ListenerExecutor[T]{
  def execute(t:T):Unit
}

trait SimpleListenerExecutor{
  def execute1(t:ZKListener):Unit
}