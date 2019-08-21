package com.hframework.utils.scala

import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by zhangquanhong on 2017/7/7.
  */
trait Logging {

  private var _logger: Logger = null

  protected def logName = {
    this.getClass.getName
  }

  protected def logger:Logger = {
    if(_logger == null) {
      _logger = LoggerFactory.getLogger(logName)
    }
    _logger
  }

}
