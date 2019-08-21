package com.hframework.datacenter.hamster.monitor.db

import java.{lang, util}
import java.util.Map

import com.fasterxml.jackson.annotation.JsonIgnore
import com.hframework.common.frame.ServiceFactory
import com.hframework.common.monitor.Node.OperateType
import com.hframework.common.monitor.{ConfigMapMonitor, ConfigMultiMonitor, Node}
import com.hframework.common.util.collect.bean.Fetcher
import com.hframework.common.util.message.JsonUtils
import com.hframework.utils.scala.{JacksonScalaUtils, JsonScalaUtils, Logging}
import com.hframework.hamster.cfg.domain.model._
import com.hframework.hamster.cfg.service.interfaces._
import org.apache.commons.lang.exception.ExceptionUtils
import org.apache.commons.lang3.StringUtils
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

import scala.collection.JavaConverters._
import scala.collection.mutable

object DeployTypeUtil {

  def parse(index : lang.Byte): DeployType = {
    if(index == null) normal else
    index.toInt match {
      case 1 => normal
      case 2 => full
      case 3 => fix
      case _ => normal
    }
  }
}

trait DeployType
case object normal extends DeployType // 默认,增量形式
case object full extends DeployType // 完整的
case object fix extends DeployType // 固定区间的

object JobRegistry extends Logging{
  trait JobOperate
  case object add extends JobOperate
  case object modify extends JobOperate
  case object delete extends JobOperate


  lazy val jobTemplateSV = ServiceFactory.getService(classOf[ICfgJobTemplateDefSV])
  lazy val nodeTemplateSV = ServiceFactory.getService(classOf[ICfgNodeDefSV])
  lazy val nodeTemplateAttrSV = ServiceFactory.getService(classOf[ICfgNodeAttrDefSV])
  lazy val jobTemplateNodeSV = ServiceFactory.getService(classOf[ICfgJobTemplateNodeDefSV])
  lazy val jobSV = ServiceFactory.getService(classOf[ICfgJobSV])
  lazy val jobAttrSV = ServiceFactory.getService(classOf[ICfgJobAttrSV])

  lazy val nodeSV = ServiceFactory.getService(classOf[ICfgNodeSV])

  lazy val deploymentSV = ServiceFactory.getService(classOf[ICfgDeploymentSV])
  lazy val deploymentDetailSV = ServiceFactory.getService(classOf[ICfgDeploymentDetailSV])

  lazy val labelSV = ServiceFactory.getService(classOf[ICfgLabelSV])

  type JobTemplateND = Node[CfgJobTemplateDef]
  type NodeTemplateND = Node[CfgNodeDef]
  type NodeTemplateAttrND = Node[CfgNodeAttrDef]
  type JobTemplateNodeRelND = Node[CfgJobTemplateNodeDef]
  type JobND = Node[CfgJob]
  type JobAttrND = Node[CfgJobAttr]
  type WorkerND = Node[CfgNode]

  type DeploymentND = Node[CfgDeployment]
  type DeploymentDetailND = Node[CfgDeploymentDetail]
  type LabelND = Node[CfgLabel]

  var listeners = mutable.Buffer.empty[JobListener]

  def addListener(listener: JobListener): Unit = {
    listeners.append(listener)
  }

  def start = dbMonitor.start

  def destroy  = dbMonitor.destroy

  def network = dbMonitor.getNodeNetwork

  var runtimeJobs = scala.collection.mutable.Map.empty[String, JobInfo]
//  var runtimeJobGroups = scala.collection.Map.empty[String, JobGroup]


  def getJobNodes(detailND: DeploymentDetailND, jobND: Node[CfgJob]): mutable.Buffer[DeployJobNodeMeta]  = {
    val deploymentND = detailND.getInput(classOf[CfgDeployment])
    if(deploymentND == null) {
      //如果deployment不存在，这里不做处理
      logger.warn(s"deploymentId = ${detailND.getObject.getDeploymentId} is not activity !")
      return mutable.Buffer.empty[DeployJobNodeMeta]
    }
    val deployment = deploymentND.getObject
    if(deployment == null) {
      //如果deployment不存在，这里不做处理
      logger.warn(s"deploymentId = ${detailND.getObject.getDeploymentId} is not activity !")
      return mutable.Buffer.empty[DeployJobNodeMeta]
    }
    val deploymentKey = deployment.getId.toString.concat("_").concat(deployment.getCode)
    val deployType = DeployTypeUtil.parse(deployment.getType).toString

    if(jobND == null) {
      logger.warn(s"deploymentId = ${deployment.getId}, jobId = ${detailND.getObject.getJobId}, job is not activity !")
      List.empty[DeployJobNodeMeta].toBuffer
    }else {
      val newJobInfo = new JobInfo(jobND)

      val masterLabels = if(StringUtils.isNoneBlank(deployment.getLabels)) {
        deployment.getLabels.split(",").toList.map(network.get(classOf[CfgLabel]).get(_).getObject.asInstanceOf[CfgLabel].getCode).toArray
      }else Array.empty[String]
      val standbyLabels = if(StringUtils.isNoneBlank(deployment.getSecondLabels)) {
        deployment.getSecondLabels.split(",").toList.map(network.get(classOf[CfgLabel]).get(_).getObject.asInstanceOf[CfgLabel].getCode).toArray
      }else Array.empty[String]
      newJobInfo.jobNodes.map(node => {
        if(!cacheJob.contains(jobND.getObject.getId.toString) || detailND.getOperateType == OperateType.delete) {
          DeployJobNodeMeta(deploymentKey, newJobInfo.jobKey, newJobInfo.selectKey, node.nodeKey, deployType, masterLabels, standbyLabels, OperateType.delete)
        }else {
          DeployJobNodeMeta(deploymentKey, newJobInfo.jobKey, newJobInfo.selectKey, node.nodeKey, deployType, masterLabels, standbyLabels, OperateType.modify)
        }

      })
    }
  }

  def reloadJobs(jobNodes : mutable.Buffer[JobND], aboutWorkers: mutable.Buffer[WorkerND],
                 aboutDeployments: mutable.Buffer[DeploymentND],
                 aboutDeploymentDetails: mutable.Buffer[DeploymentDetailND] ): Unit ={
    val jobs = jobNodes.map(_.getObject)
    logger.debug(s"reload jobs => ${JsonUtils.writeValueAsString(jobs.asJava)}")
    val changeJobs = jobNodes.map(jobND => {
      val newJobInfo = new JobInfo(jobND)
      if(!runtimeJobs.contains(newJobInfo.jobKey)) {
        newJobInfo.operate = add
      }else if(!cacheJob.contains(jobND.getObject.getId.toString)) {
        newJobInfo.operate = delete
      }
      newJobInfo.jobKey -> newJobInfo
    }).toMap[String, JobInfo]

    logger.info(s"changed jobs => ${JacksonScalaUtils.toJson(changeJobs)}")
    //由于runtimeJobs在HamsterScheduler rebalance中有用到，所以这里需要先添加，再通知
    changeJobs.foreach(job => {
      if(job._2.operate == add || job._2.operate == modify) {
        runtimeJobs.put(job._1, job._2)
      }else if(job._2.operate == delete){
        runtimeJobs.remove(job._1)
      }
    })

    val deployJobNodeMetas = aboutDeploymentDetails.flatMap(detailND => {
      val jobND = detailND.getInput(classOf[CfgJob])
      getJobNodes(detailND, jobND)

    })

    val workerMetas = aboutWorkers.map(workerND => {
      val worker = workerND.getObject
      val labels = if(StringUtils.isNoneBlank(worker.getLabels)) {
        worker.getLabels.split(",").toList.map(network.get(classOf[CfgLabel]).get(_).getObject.asInstanceOf[CfgLabel].getCode).toArray
      }else Array.empty[String]
      WorkerMeta(worker.getCfgNodeCode, worker.getIp, labels, workerND.getOperateType)
    })

    val deployJobNodeMetasRelat = jobNodes.flatMap(job => {
      job.getOutputs(classOf[CfgDeploymentDetail]).asScala.flatMap(detail => {
        getJobNodes(detail, job)
      })
    })



    listeners.foreach(_.processJobChanges(changeJobs, workerMetas, aboutDeployments, deployJobNodeMetas.++(deployJobNodeMetasRelat)))


    logger.debug(s"snapshot jobs => ${JacksonScalaUtils.toJson(runtimeJobs.values)}")
//    logger.info(s"snapshot jobs => ${compact(parse(runtimeJobs.values.mkString("[", ",", "]")))}")

//    var changedJobGroups = scala.collection.Map.empty[String, JobGroup]
//    changeJobs.foreach(job => {
//      val jobKey = job._1
//      val jobInfo = job._2
//      val selectKey = jobInfo.selectKey
//      if(!runtimeJobGroups.contains(selectKey)){
//        runtimeJobGroups += (selectKey -> new JobGroup(selectKey, jobInfo.extractNode.get.dataSourceType))
//      }
//      runtimeJobGroups.get(selectKey).get.addJobInfo(jobInfo)
//      changedJobGroups += (selectKey -> runtimeJobGroups.get(selectKey).get)
//    })
//
//    listeners.foreach(_.processJobGroupChanges(changedJobGroups))
//
//    println(s"snapshot job groups => ${compact(parse(runtimeJobGroups.values.mkString("[", ",", "]")))}")
  }

  val dbMonitor = new ConfigMultiMonitor(5) {
    override def onEvent(nodeLists: util.List[Node[_]]): Unit = {
      try{

        val aboutJobs = nodeLists.asScala.flatMap({
          case x : JobTemplateND if x.getObject.isInstanceOf[CfgJobTemplateDef] => {
            List(x)
              .flatMap(_.getOutputs(classOf[CfgJob]).asScala)
          }
          case x : NodeTemplateND if x.getObject.isInstanceOf[CfgNodeDef]  => {
            List(x)
              .flatMap(_.getOutputs(classOf[CfgJobTemplateNodeDef]).asScala)
              .flatMap(_.getInputs(classOf[CfgJobTemplateDef]).asScala)
              .flatMap(_.getOutputs(classOf[CfgJob]).asScala)
          }
          case x : NodeTemplateAttrND if x.getObject.isInstanceOf[CfgNodeAttrDef]  => {
            List(x)
              .flatMap(_.getInputs(classOf[CfgNodeDef]).asScala)
              .flatMap(_.getOutputs(classOf[CfgJobTemplateNodeDef]).asScala)
              .flatMap(_.getInputs(classOf[CfgJobTemplateDef]).asScala)
              .flatMap(_.getOutputs(classOf[CfgJob]).asScala)
          }
          case x : JobTemplateNodeRelND if x.getObject.isInstanceOf[CfgJobTemplateNodeDef]  => {
            List(x)
              .flatMap(_.getInputs(classOf[CfgJobTemplateDef]).asScala)
              .flatMap(_.getOutputs(classOf[CfgJob]).asScala)
          }
          case x : JobND if x.getObject.isInstanceOf[CfgJob]  => {
            List(x)
          }
          case x : JobAttrND if x.getObject.isInstanceOf[CfgJobAttr]  => {
            List(x)
              .flatMap(_.getInputs(classOf[CfgJob]).asScala)
          }
          case x : WorkerND if x.getObject.isInstanceOf[CfgNode]  => {
            List()
          }
          case x : DeploymentND if x.getObject.isInstanceOf[CfgDeployment]  => {
            List()
          }
          case x : DeploymentDetailND if x.getObject.isInstanceOf[CfgDeploymentDetail]  => {
            List()
          }

          case x : LabelND if x.getObject.isInstanceOf[CfgLabel]  => {
            List()
          }
  //        case _ => List.empty
          case other : Node[_] => {
            throw new RuntimeException(s"unsupprot $other")
          }
        }).distinct

        val aboutWorkers = nodeLists.asScala.filter(node => node.getObject.isInstanceOf[CfgNode]).map(_.asInstanceOf[WorkerND])
        val aboutDeployments = nodeLists.asScala.filter(node => node.getObject.isInstanceOf[CfgDeployment]).map(_.asInstanceOf[DeploymentND])
        val aboutDeploymentDetails = nodeLists.asScala.flatMap({
          case x: DeploymentDetailND if x.getObject.isInstanceOf[CfgDeploymentDetail] => {
            List(x)
          }
          case x: DeploymentND if x.getObject.isInstanceOf[CfgDeployment] => {
            List(x)
              .flatMap(_.getOutputs(classOf[CfgDeploymentDetail]).asScala)
          }
          case _ => List.empty[DeploymentDetailND]
        }).distinct

        if(aboutJobs.length > 0 || aboutWorkers.length > 0 || aboutDeployments.length > 0 || aboutDeploymentDetails.length > 0) {
          reloadJobs(aboutJobs, aboutWorkers, aboutDeployments, aboutDeploymentDetails)
        }

      }catch {
        case e:Exception => {
          logger.error(s"error => ${ExceptionUtils.getFullStackTrace(e)}")
        }
      }
    }
  }



  // 添加任务模板子监听
  dbMonitor.addSubMonitor(new ConfigMapMonitor[CfgJobTemplateDef](5) {
    override def keyProperty(t: CfgJobTemplateDef): String = t.getId.toString
    override def fetch(): util.List[CfgJobTemplateDef] = {
      jobTemplateSV.getCfgJobTemplateDefListByExample({
        val example = new CfgJobTemplateDef_Example()
        example.createCriteria().andStatusEqualTo(1.toByte)
        example
      })
    }
  })

  // 添加节点模板子监听
  dbMonitor.addSubMonitor(new ConfigMapMonitor[CfgNodeDef](5) {
    override def keyProperty(t: CfgNodeDef): String = t.getId.toString
    override def fetch(): util.List[CfgNodeDef] = {
      nodeTemplateSV.getCfgNodeDefListByExample({
        val example = new CfgNodeDef_Example()
        example.createCriteria().andStatusEqualTo(1.toByte)
        example
      })
    }
  })

  // 添加节点模板属性子监听
  val nodeTemplateAttrMonitor = new ConfigMapMonitor[CfgNodeAttrDef](5) {
    override def keyProperty(t: CfgNodeAttrDef): String = t.getId.toString
    override def fetch(): util.List[CfgNodeAttrDef] =
      nodeTemplateAttrSV.getCfgNodeAttrDefListByExample({
        val example = new CfgNodeAttrDef_Example()
        example.createCriteria().andStatusEqualTo(1.toByte)
        example})
    }

  nodeTemplateAttrMonitor.addFetcher(classOf[CfgNodeDef], new Fetcher[CfgNodeAttrDef, String] {
    override def fetch(t: CfgNodeAttrDef): String = t.getNodeId.toString}
  )
  dbMonitor.addSubMonitor(nodeTemplateAttrMonitor)


  // 添加任务模板关联节点子监听
  val jobTemplateNodeMonitor = new ConfigMapMonitor[CfgJobTemplateNodeDef](5) {
    override def keyProperty(t: CfgJobTemplateNodeDef): String = t.getId.toString
    override def fetch(): util.List[CfgJobTemplateNodeDef] = {
      jobTemplateNodeSV.getCfgJobTemplateNodeDefListByExample({
        val example = new CfgJobTemplateNodeDef_Example()
        example.createCriteria().andStatusEqualTo(1.toByte)
        example.setOrderByClause("execute_order")
        example})
    }
  }
  jobTemplateNodeMonitor.addFetcher(classOf[CfgJobTemplateDef], new Fetcher[CfgJobTemplateNodeDef, String] {
    override def fetch(t: CfgJobTemplateNodeDef): String = t.getJobTemplateId.toString }
  )
  jobTemplateNodeMonitor.addFetcher(classOf[CfgNodeDef], new Fetcher[CfgJobTemplateNodeDef, String] {
    override def fetch(t: CfgJobTemplateNodeDef): String = t.getNodeId.toString}
  )
  dbMonitor.addSubMonitor(jobTemplateNodeMonitor)

  // 添加任务定义子监听
  val jobMonitor = new ConfigMapMonitor[CfgJob](5) {
    override def keyProperty(t: CfgJob): String = t.getId.toString
    override def fetch(): util.List[CfgJob] = jobSV.getCfgJobListByExample({
        val example = new CfgJob_Example()
        example.createCriteria().andStatusEqualTo(1.toByte)
        example})
  }
  jobMonitor.addFetcher(classOf[CfgJobTemplateDef], new Fetcher[CfgJob, String] {
    override def fetch(t: CfgJob): String = t.getJobTemplateId.toString}
  )
  dbMonitor.addSubMonitor(jobMonitor)


  // 添加任务属性定义子监听
  val jobAttrMonitor = new ConfigMapMonitor[CfgJobAttr](5) {
    override def keyProperty(t: CfgJobAttr): String = t.getId.toString
    override def fetch(): util.List[CfgJobAttr] = jobAttrSV.getCfgJobAttrListByExample({
      val example = new CfgJobAttr_Example()
      example
    })
  }
  jobAttrMonitor.addFetcher(classOf[CfgJob], new Fetcher[CfgJobAttr, String] {
    override def fetch(t: CfgJobAttr): String = t.getJobId.toString}
  )
  jobAttrMonitor.addFetcher(classOf[CfgNodeAttrDef], new Fetcher[CfgJobAttr, String] {
    override def fetch(t: CfgJobAttr): String = {
      t.getAttrId.toString
    }}
  )
  dbMonitor.addSubMonitor(jobAttrMonitor)

  def cacheJob = dbMonitor.getNodeNetwork.get(classOf[CfgJob]).asInstanceOf[Map[String, Node[CfgJob]]].asScala
//  def cacheJobAttr = dbMonitor.getNodeNetwork.get(classOf[CfgJobAttr]).asInstanceOf[Map[String, Node[CfgJobAttr]]].asScala
//
//  def cacheJobTemplate = dbMonitor.getNodeNetwork.get(classOf[CfgJobTemplateDef]).asInstanceOf[Map[String, Node[CfgJobTemplateDef]]].asScala
//  def cacheJobTemplateNodeRel = dbMonitor.getNodeNetwork.get(classOf[CfgJobTemplateNodeDef]).asInstanceOf[Map[String, Node[CfgJobTemplateNodeDef]]].asScala
//  def cacheNodeTemplate = dbMonitor.getNodeNetwork.get(classOf[CfgNodeDef]).asInstanceOf[Map[String, Node[CfgNodeDef]]].asScala
//  def cacheNodeTemplateAttr = dbMonitor.getNodeNetwork.get(classOf[CfgNodeAttrDef]).asInstanceOf[Map[String, Node[CfgNodeAttrDef]]].asScala

  // 添加任务模板子监听
  dbMonitor.addSubMonitor(new ConfigMapMonitor[CfgNode](5) {
    override def keyProperty(t: CfgNode): String = t.getCfgNodeId.toString
    override def fetch(): util.List[CfgNode] = {
      nodeSV.getCfgNodeListByExample({
        val example = new CfgNode_Example()
//        example.createCriteria().andCfgNode(1.toByte)
        example
      })
    }
  })


  // 添加任务发布子监听
  dbMonitor.addSubMonitor(new ConfigMapMonitor[CfgLabel](5) {
    override def keyProperty(t: CfgLabel): String = t.getId.toString
    override def fetch(): util.List[CfgLabel] = {
      labelSV.getCfgLabelListByExample({
        val example = new CfgLabel_Example()
//        example.createCriteria().andStatusEqualTo(2.toByte)
        example
      })
    }
  })

  // 添加任务发布子监听
  dbMonitor.addSubMonitor(new ConfigMapMonitor[CfgDeployment](5) {
    override def keyProperty(t: CfgDeployment): String = t.getId.toString
    override def fetch(): util.List[CfgDeployment] = {
      deploymentSV.getCfgDeploymentListByExample({
        val example = new CfgDeployment_Example()
                example.createCriteria().andStatusEqualTo(2.toByte)
        example
      })
    }
  })

  // 添加任务发布内容子监听
  val deploymentDetailMonitor = new ConfigMapMonitor[CfgDeploymentDetail](5) {
    override def keyProperty(t: CfgDeploymentDetail): String = t.getId.toString
    override def fetch(): util.List[CfgDeploymentDetail] = {
      deploymentDetailSV.getCfgDeploymentDetailListByExample({
        val example = new CfgDeploymentDetail_Example()
        example.createCriteria().andStatusNotEqualTo(3.toByte)
        example.or().andStatusIsNull()
        example
      })
    }
  }
  deploymentDetailMonitor.addFetcher(classOf[CfgDeployment], new Fetcher[CfgDeploymentDetail, String] {
    override def fetch(t: CfgDeploymentDetail): String = t.getDeploymentId.toString}
  )

  deploymentDetailMonitor.addFetcher(classOf[CfgJobTemplateDef], new Fetcher[CfgDeploymentDetail, String] {
    override def fetch(t: CfgDeploymentDetail): String = t.getJobTemplateId.toString}
  )

  deploymentDetailMonitor.addFetcher(classOf[CfgJob], new Fetcher[CfgDeploymentDetail, String] {
    override def fetch(t: CfgDeploymentDetail): String = t.getJobId.toString}
  )

  deploymentDetailMonitor.addFetcher(classOf[CfgNodeDef], new Fetcher[CfgDeploymentDetail, String] {
    override def fetch(t: CfgDeploymentDetail): String =
      if(t.getNodeTemplateId == null) "" else t.getNodeTemplateId.toString}
  )

  dbMonitor.addSubMonitor(deploymentDetailMonitor)


  class JobInfo(@transient val cfgJobND : JobND) {
    @transient  private val jobTemplateND = cfgJobND.getInput(classOf[CfgJobTemplateDef])

    var operate: JobOperate = modify

    @transient val job = cfgJobND.getObject
    val jobId = job.getId
    val jobCode = job.getCode
    val templateCode = jobTemplateND.getObject.getCode
    val templateName = jobTemplateND.getObject.getName
    val jobKey = templateCode + "." + jobCode
    val jobNodes = {
      val jobInfo = this
      var prevJobNode: Option[JobNode] = None
      val jobTemplateNodeRelND = jobTemplateND.getOutputs(classOf[CfgJobTemplateNodeDef]).asScala
      val jobAttrs = cfgJobND.getOutputs(classOf[CfgJobAttr]).asScala.map(_.getObject)
      jobTemplateNodeRelND.sortWith((a, b)=> a.getObject.getExecuteOrder < b.getObject.getExecuteOrder).map(relND =>{
        val nodeTemplateND = relND.getInput(classOf[CfgNodeDef])
        val nodeTemplate = nodeTemplateND.getObject
        val nodeTemplateAttrs = nodeTemplateND.getOutputs(classOf[CfgNodeAttrDef]).asScala.map(_.getObject)
        val jobNodeAttr = nodeTemplateAttrs.flatMap(attrTemplate => {
          jobAttrs.filter(_.getAttrId == attrTemplate.getId)
        })
        val jobNode = new JobNode(nodeTemplate, jobTemplateND.getObject, nodeTemplateAttrs, relND.getObject, jobNodeAttr, jobInfo, prevJobNode)
        prevJobNode = Some(jobNode)
        jobNode
      })
    }

    val extractNode = jobNodes.find(_.stage.equals("extract"))

    def selectKey = selector.get.selectKey

    val selector : Option[JobSelector] = {
      extractNode match {
        case Some(extractNode) => Some(new JobSelector(extractNode))
        case _ => None
      }
    }

    lazy val jobMeta = JobMeta(jobId, jobCode, jobKey)
    lazy val jobTemplateMeta = JobTemplateMeta(templateCode, templateName)

    lazy val jobContainerMeta = if(extractNode.isDefined) {
      //说明：任务下的所有node信息以及extractNode暂时不需要持久化，减少配置信息量
      JobContainerMeta(jobTemplateMeta, jobMeta, null, null, selectKey)
    }else {
      JobContainerMeta(jobTemplateMeta, jobMeta, jobNodes.map(_.nodeMeta).toArray, null,selectKey)
    }

    override def toString: String = {
      s"""|{"id" : "${jobId}",
        | "code" : "${jobCode}",
        | "key" : "${jobKey}",
        | "operate" : "${operate}",
        | "template" : "${templateCode}",
        | "nodes" : ${jobNodes.mkString("[",", ","]")},
        | "selector" : ${selector.get}
        |}
       """.stripMargin
    }

  }

  class JobGroup( selectKey : String,  dataSourceType : String,
                  var jobInfos: scala.collection.Map[String, JobInfo] = scala.collection.Map.empty[String, JobInfo]){

    def addJobInfo(jobInfo : JobInfo): Unit ={
      jobInfos += (jobInfo.jobKey -> jobInfo)
    }

    def binlogTables = dataSourceType match {
      case "binlog" => {
        Some(jobInfos.values.map(_.extractNode.get.get("sourceTable")).toList)
      }
      case _ => {
        None
      }
    }


    override def toString: String =
      s"""
        |{ "selectKey":"${selectKey}",
        |  "dataSourceType":"${dataSourceType}",
        |  ${if(binlogTables.isDefined) "\"tables\" : [\"" + binlogTables.get.mkString("\",\"") + "\"]," else ""}
        |  "jobKeys":${jobInfos.values.map(jobInfo =>{ "[\"" + jobInfo.jobKey + "\"]" }).mkString("[", ",", "]")}
        |}
      """.stripMargin
  }

  class JobSelector( @transient extractNode : JobNode){
    val selectKey = {
      val attrs = extractNode.rows(0)
      var keyAttrs = extractNode.dataSourceType match {
        case "binlog" => List(attrs("sourceDB"))
        case "mysql" => {
          val baseList = List(attrs("sourceDB"), attrs("sourceTable"), attrs("scanInterval"), attrs("batchSize"), attrs("dataScanScope"))
          if(attrs("scanTimeField") == null || attrs("scanTimeField") == "" || attrs("scanTimeField") == "update_time_"){
            baseList
          }else {
            baseList ::: List(attrs("scanTimeField"))
          }
        }
        case "other" => List(extractNode.jobInfo.jobKey)
        case "kafka" => List(attrs("sourceKafka"), attrs("sourceTopic"))
        case "hbase" => List(attrs("sourceHBase"), attrs("sourceTable"))
        case "redis" => List(attrs("sourceRedis"), attrs("sourceKey"))
        case _ => new RuntimeException("暂不支持该数据源!")
      }
      List.concat(List(extractNode.dataSourceType, extractNode.nodeCode), keyAttrs.asInstanceOf[List[String]]).mkString(".")
    }

    override def toString: String = s"""{"selectKey":"${selectKey}"} """
  }


  class JobNode(@transient val nodeTemplate: CfgNodeDef,
                @transient val jobTemplate: CfgJobTemplateDef,
                @transient val nodeTemplateAttrs: mutable.Buffer[CfgNodeAttrDef],
                @transient val jobTemplateNodesRel: CfgJobTemplateNodeDef,
                @transient val jobAttrs: mutable.Buffer[CfgJobAttr],
                @transient val jobInfo: JobInfo,
                @transient val prevJobNode: Option[JobNode] = None) {
    val isMutix = jobTemplateNodesRel.getMutix == 1
    val nodeCode = nodeTemplate.getCode
    lazy val name = nodeTemplate.getName
    lazy val stage = nodeTemplate.getType
    lazy val dataSourceType = nodeTemplate.getDatasourceType
    lazy val executeMethod = nodeTemplate.getExecuteMethod
    lazy val executeUri = nodeTemplate.getExecuteUri

    var workerId:Option[String] = Option.empty

    def nodeKey = jobInfo.templateCode + "." + jobInfo.jobCode + "." + nodeCode

    override def toString: String = {
      s"""|{"code" : "${nodeCode}",
         | "mutix" : ${isMutix},
         | "stage" : "${stage}",
         | "dataSourceType" : "${dataSourceType}",
         | "executeMethod" : "${executeMethod}",
         | "executeUri" : "${executeUri.trim}",
         | "rows" : ${compact(render(rows))}
          |}
       """.stripMargin
    }

    def nodeMeta = NodeMeta(nodeCode, name, nodeKey, stage, dataSourceType, executeMethod, executeUri, rows,
      if(workerId.isDefined) workerId.get else null)

    def jobNodeContainerMeta = JobNodeMeta(jobInfo.jobContainerMeta, nodeMeta,
      if(prevJobNode.isDefined) prevJobNode.get.jobBaseNodeMeta else null)


    def jobBaseNodeMeta = JobBaseNodeMeta(jobInfo.jobTemplateMeta, jobInfo.jobMeta, nodeMeta)

    val rows = {
      val attrDefaultValues = nodeTemplateAttrs.map(attr => (attr.getCode, attr.getDefVal)).toMap[String, String]
      val rows = if(isMutix) {
        jobAttrs.groupBy(attr => groupByRowIndex(attr)).values.toList
      }else {
        List(jobAttrs)
      }

      val fullRows = rows.map(row => {
        val toMap = row.map(attr => {
          val origAttrCode = attr.getAttrCode
          val targetAttrCode = if(origAttrCode.contains("(") && origAttrCode.contains(")")) {
            origAttrCode.substring(0, origAttrCode.indexOf("("))
          }else origAttrCode
          (targetAttrCode, attr.getAttrVal)
        }).toMap
        toMap.++(attrDefaultValues.filterKeys(!toMap.contains(_)))
      })
      fullRows
    }

    def rowSize = rows.length


    @transient def get(code : String, rowNo : Int = 0): String = {
      if(rowNo >= rowSize) throw new RuntimeException(new IndexOutOfBoundsException())
      if(!rows(rowNo).contains(code)) throw new RuntimeException(s"attr ${code} in [${nodeCode}] not find !")
      rows(rowNo).get(code).getOrElse("")
    }

    @transient def getInt(code : String, rowNo : Int = 0): Int = {
      val retString = get(code, rowNo)
      if(StringUtils.isNoneBlank(retString)){
        retString.trim.toInt
      }else {
        0
      }
    }

    @transient def getString(code : String, rowNo : Int = 0) = get(code, rowNo)

    @transient def getLong(code : String, rowNo : Int = 0): Long = {
      val retString = get(code, rowNo)
      if(StringUtils.isNoneBlank(retString)){
        retString.trim.toLong
      }else {
        0
      }
    }

    @transient def getShort(code : String, rowNo : Int = 0): Short = {
      val retString = get(code, rowNo)
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

    @transient def groupByRowIndex(cfgJobAttr : CfgJobAttr) : Int ={
      val value = cfgJobAttr.getAttrCode
      if(value.contains("(") && value.contains(")")){
//        cfgJobAttr.setAttrCode(value.substring(0, value.indexOf("(")))
        value.substring(value.indexOf("(") + 1, value.indexOf(")")).toInt
      }else {
        0
      }
    }

  }
}
