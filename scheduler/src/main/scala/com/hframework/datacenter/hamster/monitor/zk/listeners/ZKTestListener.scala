package com.hframework.datacenter.hamster.monitor.zk.listeners

import com.hframework.utils.scala.Logging

abstract class ZKNodeWrapperTestListener(prototypeKey: String)
  extends ZKNodeWrapperListener(prototypeKey)
    with Logging {
  override def nodeCreate(paths: List[String]): Unit = {
    paths.foreach(path => {
      logger.info(s"${prototypeKey}[${path}] created !")
    })
  }

  override def nodeDelete(path: String): Unit = {
    logger.info(s"${prototypeKey}[${path}] deleted !")
  }

  override def nodeDataChange(path: String): Unit = {
    logger.info(s"${prototypeKey}[${path}] data change !")
  }
}

class ZKNodeSimpleTestListener(prototypeKey: String)
extends ZKNodeSimpleListener
with Logging {
  override def nodeCreate(paths: List[String]): Unit = {
  paths.foreach(path => {
  logger.info(s"${prototypeKey}[${path}] created !")
})
}

  override def nodeDelete(path: String): Unit = {
  logger.info(s"${prototypeKey}[${path}] deleted !")
}

  override def nodeDataChange(path: String): Unit = {
  logger.info(s"${prototypeKey}[${path}] data change !")
}
}
