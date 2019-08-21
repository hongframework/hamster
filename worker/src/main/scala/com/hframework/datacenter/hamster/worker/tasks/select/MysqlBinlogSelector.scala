package com.hframework.datacenter.hamster.worker.tasks.select

import java.net.InetSocketAddress
import java.util.Date

import com.alibaba.otter.canal.common.utils.BooleanMutex
import com.alibaba.otter.canal.instance.manager.model.CanalParameter
import com.hframe.ext.bean.CanalConfig
import com.hframe.hamster.node.HamsterConfig
import com.hframe.hamster.node.cannal.{CanalServer, Message}
import com.hframe.hamster.node.monitor.bean.ProcessEventData
import com.hframework.common.frame.ServiceFactory
import com.hframework.common.util.DateUtils
import com.hframework.datacenter.hamster.workes.bean.{BatchDataSet, DataField, DataRow}
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.tasks.AbstractSelector
import com.hframework.datacenter.hamster.worker.tasks.AttrDef.Binlog._
import com.hframework.datacenter.hamster.workes.bean.{DataField, DataRow, DataSet}
import com.hframework.datacenter.hamster.zookeeper.ZKPathUtils
import com.hframework.hamster.cfg.domain.model.CfgDatasource
import com.hframework.hamster.cfg.service.interfaces.ICfgDatasourceSV
import org.apache.commons.lang.StringUtils

import scala.collection.JavaConverters._

/**
  * Mysql Binlog 选择器
  * @param prototypeKey 任务标识
  * @param __jobNodeMeta select任务节点定义
  * @param __dependencyJobNode 关联的extract任务节点
  */
class MysqlBinlogSelector(prototypeKey: String,
                          deployMeta: DeployJobNodeMeta,
                          var __jobNodeMeta: JobNodeMeta,
                          var __dependencyJobNode: scala.List[JobNodeMeta] = Nil)
  extends AbstractSelector(prototypeKey,deployMeta, __jobNodeMeta, __jobNodeMeta.jobMeta,
    ZKPathUtils.getJobGroupProcessPath(deployMeta.deployCode, __jobNodeMeta.jobMeta.selectKey), __dependencyJobNode){

  lazy val cancelConfig = {
    val dbId = jobNode.getLong(sourceDB)
    val dataSourceCfg: CfgDatasource = ServiceFactory.getService(classOf[ICfgDatasourceSV]).getCfgDatasourceByPK(dbId)
    val canalFilterString: String = dependencyJobNode.map(node =>
      dataSourceCfg.getDb.concat(".").concat(node.nodeMeta.wrapper.getString(sourceTable))).distinct.mkString(",")

    val logfileStartInfo:(Option[String], Option[Long], Option[Date]) = {
//      (jobNode.getString(logfileName, must = false), jobNode.getLong(logfileOffset, must = false), jobNode.getLong(logfileTimestamp, must = false))
      val logBeginPosition = deployMeta.deploymentInfo.getLogBeginPosition
      if(StringUtils.isNotBlank(logBeginPosition)){
        val logBeginFileName = logBeginPosition.substring(0, logBeginPosition.indexOf("|")).trim
        val logBeginOffset = logBeginPosition.substring(logBeginPosition.indexOf("|")+1).trim.toLong
        (Some(logBeginFileName), Some(logBeginOffset), None)
      } else {
        val logBeginTimestamp = deployMeta.deploymentInfo.getLogBeginTimestamp
        (None, None, (if(logBeginTimestamp != null) Some(logBeginTimestamp) else None))
      }
    }

    val config = new CanalConfig
    config.setPrototypeKey(prototypeKey)
    val cannalId = deployMeta.deploymentInfo.getId.toInt * 1000 + dbId
    //由于一个任务只会有一个mysqlBinlogWatch，因此将数据源ID作为cancel的ID
    config.setCanalId(cannalId)
    config.setClientId(dbId.toShort)//一个cancel只有一个消费者，所以id设置一致
    config.setDestination(jobNodeMeta.jobMeta.selectKey + "."+ deployCode)
    config.setFilter(canalFilterString)

    val canalParameter = new CanalParameter
    config.setCanalParameter(canalParameter)
    //设置canal基本信息
    canalParameter.setCanalId(cannalId)
    canalParameter.setSlaveId(cannalId)//嵌入式不做高可用
    canalParameter.setIndexMode(CanalParameter.IndexMode.MEMORY_META_FAILBACK)
    canalParameter.setMetaMode(CanalParameter.MetaMode.ZOOKEEPER) //如果选择MIXED模式，下次重启是，是否存在一秒钟的延迟？

    canalParameter.setZkClusters(List(HamsterConfig.getInstance.getZkServers).asJava)

    val url = dataSourceCfg.getUrl
    val address = url.substring(0, url.lastIndexOf(":"))
    val port = url.substring(url.lastIndexOf(":") + 1).toInt

    //设置数据库连接信息
    canalParameter.setMasterAddress(new InetSocketAddress(address, port))
    canalParameter.setDefaultDatabaseName(dataSourceCfg.getDb)
    canalParameter.setDbUsername(dataSourceCfg.getUsername)
    canalParameter.setDbPassword(dataSourceCfg.getPassword)

    //设置数据过滤信息
    canalParameter.setDdlIsolation(false)
    if(logfileStartInfo._1.isDefined && logfileStartInfo._2.isDefined) {
      canalParameter.setMasterLogfileName(logfileStartInfo._1.get)
      canalParameter.setMasterLogfileOffest(logfileStartInfo._2.get)
    }

    if(logfileStartInfo._3.isDefined) {
      canalParameter.setMasterTimestamp(logfileStartInfo._3.get.getTime)
    }

    config
  }

  val canalState = new BooleanMutex(false)
  val canalServer = new CanalServer(cancelConfig)
  canalServer.setSelectorMonitorPath(watchPath)

  /**Selector仅reload JobNodeMeta 没有意义，因为没有需要动态修改的项目*/
  @Deprecated
  override def reload(newJobNodeMeta: JobNodeMeta): Unit = {
    super.reload(newJobNodeMeta)
    this.__jobNodeMeta = newJobNodeMeta
  }

  override def reload(dependencyJobNode: scala.List[JobNodeMeta]): Unit = {
    super.reload(dependencyJobNode)
    this.__dependencyJobNode = dependencyJobNode
    val dbId = jobNode.getLong(sourceDB)
    val dataSourceCfg: CfgDatasource = ServiceFactory.getService(classOf[ICfgDatasourceSV]).getCfgDatasourceByPK(dbId)
    val canalFilterString: String = dependencyJobNode.map(node =>
      dataSourceCfg.getDb.concat(".").concat(node.nodeMeta.wrapper.getString(sourceTable))).distinct.mkString(",")
    canalServer.resetFilter(canalFilterString)

    //TODO
  }

  /**
    * 进行一次数据选择
    * @return
    */
  override def select(): BatchDataSet = {
    if (!canalState.state) {
      canalServer.start()
      canalState.set(true)
    }
    canalServer.waitForStarted() //阻塞等待
    val newMessage: Message = canalServer.fetch
    val data = newMessage.getDatas.asScala

    val batchDataSet = new BatchDataSet
    batchDataSet.setBatchId(newMessage.getMessageId)
    batchDataSet.setCreateTime(DateUtils.getCurrentDate)
    batchDataSet.setBinlogStart(data.head.getBinLogPostion)
    batchDataSet.setBinlogEnd(data.last.getBinLogPostion)
    batchDataSet.setBinlogStartTs(data.head.getExecuteTime/1000L)
    batchDataSet.setBinlogEndTs(data.last.getExecuteTime/1000L)

    data.foreach(eventData => {
      val dataSet = batchDataSet.getOrCreateDataSet(eventData.getTableId, eventData.getTableName, eventData.getSchemaName)
      val allColumns = eventData.getKeys.asScala.toList ::: eventData.getColumns.asScala.toList
      val oldColumns = eventData.getOldKeys.asScala.toList ::: eventData.getOldColumns.asScala.toList

      if(dataSet.getFields.isEmpty) {
        dataSet.setFields(allColumns.map(col =>
          new DataField(col.getIndex, col.getColumnType, col.getColumnName, col.isNull, col.isKey)).asJava)
      }

      val row = new DataRow
      eventData.getBinLogPostion
      row.setEventType(eventData.getEventType)
      row.setExecuteTime(eventData.getExecuteTime/1000L)
      row.setBinLogPosition(eventData.getBinLogPostion)
      row.setValues(allColumns.map(_.getColumnValue).asJava)
      row.setOldValues(oldColumns.map(_.getColumnValue).asJava)
      row.setUpdates(allColumns.map(col => java.lang.Boolean.valueOf(col.isUpdate)).asJava)
      dataSet.addRow(row)
    })


    batchDataSet
  }

  /** 销毁逻辑 */
  override protected def destroyInternal(): Unit = {
    canalServer.stop
  }

  override def ackTermi(messageId: Long): Unit = {
    canalServer.ack(messageId)
  }
}
