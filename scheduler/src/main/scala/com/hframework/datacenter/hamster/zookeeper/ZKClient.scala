package com.hframework.datacenter.hamster.zookeeper

import java.util

import com.alibaba.otter.canal.common.utils.JsonUtils
import com.hframe.hamster.common.zkclient.ZkClientx
import com.hframe.hamster.node.zk.ZooKeeperClient
import com.hframework.utils.scala.{JacksonScalaUtils, Logging}
import com.hframework.datacenter.hamster.zookeeper.ZKClient.createPersistent
import org.I0Itec.zkclient.IZkStateListener
import org.I0Itec.zkclient.exception.{ZkException, ZkInterruptedException, ZkNoNodeException, ZkNodeExistsException}
import org.apache.commons.lang.exception.ExceptionUtils
import org.apache.zookeeper.CreateMode
import org.apache.zookeeper.Watcher.Event

object ZKClient extends Logging{

  val client = ZooKeeperClient.getInstance

  def readData(path: String): Array[Byte] = client.readData(path)

  def readData[T](path: String, clazz: Class[T]): T = {
    val readBytes = readData(path)
    val readString = if(readBytes == null) {
      ""
    }else {
      new String(readBytes)
    }

    if(clazz == classOf[String]) {
      readString.asInstanceOf[T]
    }else {
      if(readString == "") {
        JacksonScalaUtils.fromJson("{}", clazz)
      }else {
        JacksonScalaUtils.fromJson(readString, clazz)
      }

    }
  }

  def exists(path: String): Boolean = client.exists(path)

  @throws[ZkInterruptedException]
  @throws[IllegalArgumentException]
  @throws[ZkException]
  @throws[RuntimeException]
  def create(path: String, data: AnyRef, mode: CreateMode): String = client.create(path, transformObjectToString(data), mode)

  def get(): ZkClientx = client

  def createEphemeral(path: String, data: AnyRef, createParents: Boolean = false): Unit = {
    logger.info(s"create ephemeral node:path=${path},data=${data}")
    try {
      create(path, data, CreateMode.EPHEMERAL)
    } catch {
      case e: ZkNodeExistsException =>
        //出现这种情况 主要为服务重启时间间隔小于30s，zk还没有释放临时节点，就要新增加节点
        logger.warn(s"[${path}]create ephemeral node repeat! maybe last session not expired , ${e.getMessage}")
      case e: ZkNoNodeException =>
        if (!createParents) {
          logger.error(s"[${path}]create ephemeral node error, ${ExceptionUtils.getFullStackTrace(e)}")
          throw e
        }else {//上级路径不存在，连带创建
          val parentDir = path.substring(0, path.lastIndexOf('/'))
          logger.info(s"[${path}]create ephemeral node error, maybe parent path is not exists , ${e.getMessage}")
          createPersistent(parentDir, null, createParents)
          createEphemeral(path, data, createParents)
        }
    }
    //zk注册session重连创建node节点（session断掉后会自动删除临时节点）
    client.subscribeStateChanges(new IZkStateListener() {
      @throws[Exception]
      override def handleStateChanged(state: Event.KeeperState): Unit = {}

      @throws[Exception]
      override def handleNewSession(): Unit = createEphemeral(path, data, createParents)
    })
  }

  /**
    * Create a persistent node.
    *
    * @param path
    * @throws ZkInterruptedException   if operation was interrupted, or a required reconnection got interrupted
    * @throws IllegalArgumentException if called from anything except the ZooKeeper event thread
    * @throws ZkException              if any ZooKeeper exception occurred
    * @throws RuntimeException         if any other exception occurs
    */
  @throws[ZkInterruptedException]
  @throws[IllegalArgumentException]
  @throws[ZkException]
  @throws[RuntimeException]
  def createPersistent(path: String, data : Object = null, createParents: Boolean = false): Unit =
    client.createPersistent(path, transformObjectToString(data), createParents)

  def transformObjectToString(data: Object): String = {
    if(data != null && !data.isInstanceOf[String]){
      JacksonScalaUtils.toJson(data)
    }else {
      data.asInstanceOf[String]
    }
  }

  def writeData(path: String, data: Object = null): Unit = {
    try{
      logger.info(s"[${path}] write data : ${data}!")
      client.writeData(path, transformObjectToString(data))
    }catch {
      case e: Exception => logger.warn(s"[${path}] write data failed, " +
        s"${ExceptionUtils.getFullStackTrace(e)}")
    }

  }

  /**
    * Create or update a persistent node.
    *
    * @param path
    * @throws ZkInterruptedException   if operation was interrupted, or a required reconnection got interrupted
    * @throws IllegalArgumentException if called from anything except the ZooKeeper event thread
    * @throws ZkException              if any ZooKeeper exception occurred
    * @throws RuntimeException         if any other exception occurs
    */
  @throws[ZkInterruptedException]
  @throws[IllegalArgumentException]
  @throws[ZkException]
  @throws[RuntimeException]
  def createOrUpdatePersistent(path: String, data : Object = null, createParents: Boolean = false): Unit = {
    try{
      if(data != null && client.exists(path)) {
        val origStr = new String(readData(path))
        val targetStr = JacksonScalaUtils.toJson(data)
        if(origStr == null || targetStr != origStr.replaceAll("\"workerId\":\"[-0-9]+\"", "\"workerId\":null")) {
          writeData(path, data)
        }
      }else {
        createPersistentIfNotExists(path,data, createParents)
      }
    }catch {
      case e: Exception => logger.warn(s"[${path}] create or update zk persistent node failed, " +
        s"${ExceptionUtils.getFullStackTrace(e)}")
    }
  }

  /**
    * Create a persistent node.
    *
    * @param path
    * @throws ZkInterruptedException   if operation was interrupted, or a required reconnection got interrupted
    * @throws IllegalArgumentException if called from anything except the ZooKeeper event thread
    * @throws ZkException              if any ZooKeeper exception occurred
    * @throws RuntimeException         if any other exception occurs
    */
  @throws[ZkInterruptedException]
  @throws[IllegalArgumentException]
  @throws[ZkException]
  @throws[RuntimeException]
  def createPersistentIfNotExists(path: String, data : Object = null, createParents: Boolean = false): Unit = {
    try{
      logger.info(s"[${path}] create zk persistent node ,data : ${data}!")
      createPersistent(path, data, createParents)
    }catch {
      case e: Exception => logger.warn(s"[${path}] create zk persistent node failed, " +
        s"${ExceptionUtils.getFullStackTrace(e)}")
    }
  }

  def getChildren(path: String): util.List[String] = client.getChildren(path)

  def delete(path: String, deleteChildren: Boolean = false): Boolean = if(deleteChildren) client.deleteRecursive(path) else client.delete(path)


  def tryDelete(path: String, deleteChildren: Boolean = false): Boolean = {
    try delete(path, deleteChildren) catch {
      case e: Exception => logger.warn(s"[${path}] try delete node failed, " +
        s"${ExceptionUtils.getFullStackTrace(e)}")
        false
    }
  }
}
