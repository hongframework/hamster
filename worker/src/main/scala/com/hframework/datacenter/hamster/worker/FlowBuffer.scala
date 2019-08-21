package com.hframework.datacenter.hamster.worker
import java.text.SimpleDateFormat
import java.util.concurrent.ArrayBlockingQueue
import java.util.Date
import com.hframework.smartsql.client.DBClient
import scala.collection.JavaConverters._
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.HamsterWorker.FlowArrayBlockingQueue
import com.hframework.datacenter.hamster.workes.bean.DataField
import com.hframework.datacenter.hamster.worker.tasks.Task
import scala.collection.mutable
import com.hframework.datacenter.hamster.worker.tasks.delete.MysqlDeleter.registerDatabase

case class FlowData(workerId:String,selectKey:String,jobKey:String,nodeKey:String,deployCode:String,jobCode:String,executeUri:String,var flow:Int,currentTms:Long)

class FlowBuffer (flowQueue:ArrayBlockingQueue[FlowArrayBlockingQueue]) extends Task {


  var saveFlag = false
  var timeStart:Long = 0
  val globalEmptyArray =  Array.empty[Object]
  type DataKey = (String,String,String,Long)
  val flowDateaMap = new mutable.HashMap[DataKey,FlowData]()
  val fieldStr = List("workerId","selectKey","jobKey","nodeKey","deployCode","jobCode","executeUri","flow","process_time")
  val flowTable = "flowbuffer"
  val saveSqlStr = s"insert into $flowTable (${fieldStr.mkString(",")}) values "
  val dbKey = 20
  registerDatabase(dbKey)
//  val dataSet = new DataSet()
//  val dataField = fieldStr.map(i => new DataField(i,false))
//  dataSet.setFields(dataField.asJava)
  def parse(flowQueue:FlowArrayBlockingQueue):(DataKey,FlowData) = {
    val (deployMeta,jobNodeMeta,dataSets) = flowQueue
    val workerId =deployMeta.workerId
    val deployCode = deployMeta.deployCode
    val selectKey = deployMeta.selectKey
    val nodeKey = deployMeta.nodeKey
    val jobCode = if(jobNodeMeta.jobMeta.jobInfo != null ) jobNodeMeta.jobMeta.jobInfo.jobCode else ""
    val jobKey = if(deployMeta.jobKey != null) deployMeta.jobKey else ""
    val executeUri = (jobNodeMeta.nodeMeta.executeUri).split('.').last
    val flow = {
      val rowNumberList = dataSets.getDatas.asScala.map(i => i.getRows.size)
      rowNumberList.foldLeft(0){(z,i) =>z + i}
//      rowNumberList.sum
}
    val currentTms = System.currentTimeMillis() / 1000   //每秒聚合
    val flowDate = FlowData(workerId,selectKey,jobKey,nodeKey,deployCode,jobCode,executeUri,flow,currentTms)
    val dataKey = (workerId,deployCode,nodeKey,currentTms)
    (dataKey,flowDate)
  }
  def addFlow(dataKey: DataKey,flowData: FlowData):Unit={
    timeStart  = if(timeStart == 0) flowData.currentTms  else timeStart
    if (flowDateaMap.contains(dataKey)){
      flowDateaMap(dataKey).flow += flowData.flow
    }else{
      flowDateaMap.put(dataKey,flowData)
    }
    val timeDis = flowData.currentTms - timeStart
    if (flowDateaMap.size > 100 || timeDis > 3 ){
      timeStart = 0
      saveFlag = true
    }
  }

  def saveData:Unit= {
    if (saveFlag) {
      DBClient.setCurrentDatabaseKey(dbKey.toString)
      val dataValueStr: List[String] = flowDateaMap.values.map(flowData => {
        val fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val process_time = fm.format(new Date(flowData.currentTms.toLong * 1000))
        val dataList = List(flowData.workerId, flowData.selectKey, flowData.jobKey, flowData.nodeKey, flowData.deployCode, flowData.jobCode, flowData.executeUri, flowData.flow,process_time)
        val dataListStr = s" ('${dataList.mkString("','")}') "
        dataListStr
      }).toList
      val dataValueInsertStr = s" ${dataValueStr.mkString(",")} "
      val savaSqlFinal = saveSqlStr + dataValueInsertStr
      try {
        logger.info(s"[SQL]${savaSqlFinal} !")
        DBClient.executeUpdate(savaSqlFinal, globalEmptyArray)
      } catch {
        case e: Exception => {
          logger.error(s"${e.getMessage}")
        }

      }finally {
        saveFlag = false
        flowDateaMap.clear()
      }
    }
  }
  override protected def startInternal(): Unit = {
    while (isRunning) {
      this.synchronized {
        val flowQueueSize = flowQueue.size()
        if (flowQueueSize > 0) {
          val record = flowQueue.poll()
          if (record != null){
            @volatile var (dataKey,flowDate) = parse(record)
            addFlow(dataKey,flowDate)
            saveData
          }

        }
      }
    }
  }
  override def reload(): Unit = {}
  /** 销毁 */
  override protected def destroyInternal(): Unit = {
  }
  override def threadName: String = "FlowBuffer"

  override def name: String = "FlowBuffer"
  override def shutdown(): Unit ={
    super.shutdown()
  }

  }


