package com.hframework.datacenter.hamster.worker.tasks.execute

import java.util.concurrent.atomic.AtomicBoolean

import com.hframework.datacenter.hamster.workes.bean.BatchDataSet
import com.hframework.smartsql.client.DBClient
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.SPELUtil
import com.hframework.datacenter.hamster.worker.tasks.AbstractSelector
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.Executor
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.Executor.{batchInterval, scanInterval, sourceDB, _}
import com.hframework.datacenter.hamster.worker.tasks.select.{MysqlScanSelector, QuartzServer}
import com.hframework.datacenter.hamster.zookeeper.ZKPathUtils

import scala.collection.mutable


/**
  * Mysql SQL执行器
  * @param prototypeKey 任务标识
  * @param __jobNodeMeta select任务节点定义
  * @param __dependencyJobNode 关联的extract任务节点
  */
class MysqlSqlExecutor(prototypeKey: String,
                        deployMeta: DeployJobNodeMeta,
                        var __jobNodeMeta: JobNodeMeta,
                        var __dependencyJobNode: scala.List[JobNodeMeta] = Nil)
  extends AbstractSelector(prototypeKey,deployMeta, __jobNodeMeta, __jobNodeMeta.jobMeta,
    ZKPathUtils.getJobGroupProcessPath(deployMeta.deployCode, __jobNodeMeta.jobMeta.selectKey), __dependencyJobNode){


  val globalEmptyArray =  Array.empty[Object]

  @volatile var (dbKey, dbName, sql, scanIntervalOp, scanCronExpressOp, batchIntervalOp) = parse

  val initQuartzServer = new AtomicBoolean(false)
  val quartzServer = new QuartzServer(deployCode, __jobNodeMeta.jobMeta.selectKey)
  quartzServer.selectorMonitorPath = finalWatchPath

  def parse:(String, String, String, Option[Long], Option[String], Option[Long]) = {
    val dbId = jobNode.getLong(sourceDB)
    val (dbKey, dbName) = MysqlScanSelector.registerDatabase(dbId)
    val sqlTemplate = jobNode.getString(Executor.sql)
    val timerIntervalConf = jobNode.getString(scanInterval)
    val batchIntervalConf = jobNode.getString(batchInterval)
    val (scanIntervalOp, scanCronExpressOp, _) = MysqlScanSelector.parseTimerInterval(timerIntervalConf, List("auto"))
    val (batchIntervalOp, _, _) = MysqlScanSelector.parseTimerInterval(batchIntervalConf, List("auto"))

    (dbKey, dbName, sqlTemplate, scanIntervalOp, scanCronExpressOp, batchIntervalOp)
  }


  def getSubTables(dbTables: mutable.Buffer[String], table: String): List[String] = {
    dbTables.filter(t => {
      if(SPELUtil.parseExpress(table).isDefined) {
        val expr = SPELUtil.parseExpress(table).get._1
        t.matches(table.replace(expr, ".+"))
      }else t.matches(table)
    }).toList
  }

  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this.__jobNodeMeta = newJobNodeMeta
    val result = parse
    dbKey = result._1
    dbName = result._2
    sql = result._3
    scanIntervalOp = result._4
    scanCronExpressOp = result._5
    batchIntervalOp = result._6

    if(initQuartzServer.get()) {
      quartzServer.reload(scanIntervalOp, scanCronExpressOp, None)
    }
  }

  override def reload(dependencyJobNode: scala.List[JobNodeMeta]): Unit = {
    super.reload(dependencyJobNode)
    this.__dependencyJobNode = dependencyJobNode
  }

  /**
    * 进行一次数据选择
    * @return
    */
  override def select(): BatchDataSet = {
    if(initQuartzServer.compareAndSet(false, true)) {
      quartzServer.start(scanIntervalOp, scanCronExpressOp, None)
    }
    while(true) {
      val scanSignal = quartzServer.waitForQuartzSignal //获取处理信号
      selectData
      quartzServer.signalFinish(scanSignal)
    }
    null
  }




  def selectData():Int ={
    var total:Int = 0
    do {
        val count = DBClient.executeUpdate(dbKey, sql, globalEmptyArray)
        total += count
        logger.info(s"[SQL]: $sql(${count})(${total})")

        if(count == 0) {
          return total
        }else {
          if(batchIntervalOp.isDefined) {
            Thread.sleep(batchIntervalOp.get * 1000)
          }
        }
    }while (true)
     0
  }

  /** 销毁逻辑 */
  override protected def destroyInternal(): Unit = {
    quartzServer.stop
  }
  override def ackTermi(messageId: Long): Unit = {
    quartzServer.ack(messageId)
  }

}
