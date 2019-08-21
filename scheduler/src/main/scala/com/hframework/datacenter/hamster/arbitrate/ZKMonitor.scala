package com.hframework.datacenter.hamster.arbitrate

import com.hframe.hamster.common.AddressUtils
import com.hframe.hamster.node.zk.{ZKPathUtils, ZooKeeperClient}
import com.hframework.utils.scala.Logging
import com.hframework.datacenter.hamster.zookeeper.ZKClient
import org.I0Itec.zkclient.IZkStateListener
import org.I0Itec.zkclient.exception.{ZkNoNodeException, ZkNodeExistsException}
import org.apache.commons.lang.exception.ExceptionUtils
import org.apache.zookeeper.CreateMode
import org.apache.zookeeper.Watcher.Event

trait  ZKMonitor extends Logging{
  protected val zkClientx = ZooKeeperClient.getInstance


  def destroy(nid: Long): Unit = {
    try {
      zkClientx.delete(ZKPathUtils.getNodePath(nid))
      logger.info("node delete from zk success !")
    } catch {
      case e: ZkNodeExistsException =>
        logger.error(ExceptionUtils.getFullStackTrace(e))
    }
  }

}
