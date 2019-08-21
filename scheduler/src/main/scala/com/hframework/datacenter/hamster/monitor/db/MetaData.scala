package com.hframework.datacenter.hamster.monitor.db

import com.fasterxml.jackson.annotation.JsonIgnore
import com.hframework.common.monitor.Node
import com.hframework.datacenter.hamster.exceptions.TaskInitializationException
import com.hframework.datacenter.hamster.monitor.db.JobRegistry.{JobOperate, modify}
import com.hframework.hamster.cfg.domain.model.{CfgDeployment, CfgJobAttr}
import org.apache.commons.lang3.StringUtils

/**
  * Job容器元数据
  *
  * @param jobTemplate
  * @param jobInfo
  * @param jobNodes
  * @param extractNode
  * @param selectKey
  */
case class JobContainerMeta(jobTemplate: JobTemplateMeta, jobInfo: JobMeta,
                            jobNodes: Array[NodeMeta], extractNode: NodeMeta, selectKey: String)

case class JobSelectorMeta(nodeMeta: NodeMeta)

case class JobNodeMetas(jobNodeMetas: List[JobNodeMeta])

case class JobNodeMeta(jobMeta: JobContainerMeta, nodeMeta: NodeMeta, prevNodeMeta: JobBaseNodeMeta = null)

case class JobBaseNodeMeta(jobTemplate: JobTemplateMeta, jobInfo: JobMeta, nodeMeta: NodeMeta)
/**
  * Job模板元数据
  * @param templateCode
  * @param templateName
  */
case class JobTemplateMeta(templateCode: String, templateName: String)

/**
  * Job元数据
  * @param jobId
  * @param jobCode
  * @param jobKey
  */
case class JobMeta(jobId: Long, jobCode: String, jobKey: String)

case class DeployJobNodeMeta(deployCode: String, jobKey: String, selectKey: String, nodeKey: String,
                             deployType: String, masterLabels: Array[String], standbyLabels: Array[String],
                             operate: Node.OperateType = Node.OperateType.modify,
                             var workerId: String = null, var deploymentInfo : CfgDeployment = null)

case class WorkerMeta(workerId: String, ip: String, labels: Array[String], operateType: Node.OperateType)

case class NodeMataWrapper(nodeMeta: NodeMeta) {
  val rows = nodeMeta.rows
  val rowSize = rows.size
  @transient def get(code : String, rowNo : Int = 0, must: Boolean = true): String = {
    if(rowNo >= rowSize) throw new RuntimeException(new IndexOutOfBoundsException())
    if(must && !rows(rowNo).contains(code)) throw new TaskInitializationException(s"attr ${code} in [${nodeMeta.nodeCode}] not find !")
    rows(rowNo).get(code).getOrElse("")
  }

  @transient def getInt(code : String, rowNo : Int = 0, must: Boolean = true): Int = {
    val retString = get(code, rowNo, must)
    if(StringUtils.isNoneBlank(retString)){
      retString.trim.toInt
    }else {
      0
    }
  }

  @transient def getString(code : String, rowNo : Int = 0, must: Boolean = true) = get(code, rowNo, must)

  @transient def getLong(code : String, rowNo : Int = 0, must: Boolean = true): Long = {
    val retString = get(code, rowNo, must)
    if(StringUtils.isNoneBlank(retString)){
      retString.trim.toLong
    }else {
      0
    }
  }

  @transient def getShort(code : String, rowNo : Int = 0, must: Boolean = true): Short = {
    val retString = get(code, rowNo, must)
    if(StringUtils.isNoneBlank(retString)){
      retString.trim.toShort
    }else {
      0.toShort
    }
  }

  @transient def getStrings(code : String, rowNo : Int = 0, split : String = ","): scala.List[String] = {
    val retString = get(code, rowNo)
    if(StringUtils.isNoneBlank(retString)){
      retString.trim.split(split).toList
    }else {
      List.empty[String]
    }
  }

  @transient def getInts(code : String, rowNo : Int = 0, split : String = ","): scala.List[Int] = {
    val retString = get(code, rowNo)
    if(StringUtils.isNoneBlank(retString)){
      retString.trim.split(split).toList.map(_.trim.toInt)
    }else {
      List.empty[Int]
    }
  }

  @transient @JsonIgnore def getLongs(code : String,@transient  rowNo : Int = 0, @transient  split : String = ","): scala.List[Long] = {
    val retString = get(code, rowNo)
    if(StringUtils.isNoneBlank(retString)){
      retString.trim.split(split).toList.map(_.trim.toLong)
    }else {
      List.empty[Long]
    }
  }
}

/**
  * Job配置节点元数据
  * @param nodeCode
  * @param nodeName
  * @param nodeStage
  * @param dataSourceType
  * @param executeMethod
  * @param executeUri
  * @param rows
  */
case class NodeMeta(nodeCode: String, nodeName: String, nodeKey: String, nodeStage: String, dataSourceType: String,
                    executeMethod: String, var executeUri: String, rows: List[scala.collection.Map[String, String]], var workerId: String = null) {

  def wrapper(): NodeMataWrapper ={
    NodeMataWrapper(this)
  }

  @transient def removeRowIndexAndReturn(cfgJobAttr : CfgJobAttr) : Int ={
    val value = cfgJobAttr.getAttrCode
    if(value.contains("(") && value.contains(")")){
      cfgJobAttr.setAttrCode(value.substring(0, value.indexOf("(")))
      value.substring(value.indexOf("(") + 1, value.indexOf(")")).toInt
    }else {
      0
    }
  }

}

