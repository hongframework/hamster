package com.hframework.datacenter.hamster

import com.hframework.utils.scala.Logging
import org.apache.commons.lang.exception.ExceptionUtils

trait CycleLife extends Logging{
  protected var running = false
  /** 启动 */
  def setup(): Unit = {
    if (!running) {
      this.synchronized{
        if (!running) {
          running = true
          logger.info(s"${name} task starting ...")
          startInternal()
          logger.info(s"${name} task started !")
        }
      }
    }
  }

  /**名称描述 */
  def name: String

  /** 销毁 */
  def shutdown(): Unit = {
    logger.info(s"${name} task destroying ...")
    try
      destroyInternal
    catch {
      case e: Exception =>
        logger.error(s"${name} task destroy error : ${ExceptionUtils.getFullStackTrace(e)}")
    }

    logger.info(s"${name} task destroyed ...")
    running = false
  }

  /** 启动逻辑 */
  protected def startInternal(): Unit

  /** 加载逻辑 */
  def reload(): Unit

  /** 销毁逻辑 */
  protected def destroyInternal(): Unit

  def isRunning = running
}
