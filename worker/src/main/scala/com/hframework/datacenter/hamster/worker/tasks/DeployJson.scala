package com.hframework.datacenter.hamster.worker.tasks

import java.util.concurrent.ConcurrentHashMap

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import com.hframework.datacenter.hamster.monitor.db.DeployJobNodeMeta
import scala.collection.JavaConverters._

object DeployJson {

  val BATCH_SQL_ADD_WHERE = "batch.sql.add.where."


  val cache = new ConcurrentHashMap[String, JSONObject]()

  def getDeployJsonParseIfAbsent(deployMeta: DeployJobNodeMeta): JSONObject ={
    val configJson = {
      val json = deployMeta.deploymentInfo.getDeployJson
      if(json == null) "" else json
    }
    if(!cache.containsKey(configJson)) {
      val formatJson = if(configJson != null && configJson.trim.isDefinedAt(0)){
        if(!configJson.trim.startsWith("{") && !configJson.trim.endsWith("}")){
          s"{${configJson.trim}}"
        }else configJson.trim
      }else "{}"
      val jsonObject = JSON.parseObject(formatJson)
      cache.putIfAbsent(configJson, jsonObject)
    }
    cache.get(configJson)
  }

  def getAddWhereSql(deployMeta: DeployJobNodeMeta, table: String) : Option[String] = {
    val addWheres = getSqlConfigs(deployMeta, table, BATCH_SQL_ADD_WHERE)
    if(addWheres.isDefinedAt(0)) {
      val addWhere = addWheres(0)
      if(addWhere.matches(".*\\s(or|OR)\\s.*")){
        Some(s"($addWhere)")
      }else Some(s"$addWhere")
    } else None
  }

  def getSqlConfigs(deployMeta: DeployJobNodeMeta, table: String, prefix: String) : List[String] = {
    val deployObject: JSONObject = getDeployJsonParseIfAbsent(deployMeta)
    val key = s"$prefix$table"
    if(deployObject.containsKey(key)) {
      val result = deployObject.get(key)
      if(result.isInstanceOf[JSONArray]){
        result.asInstanceOf[JSONArray].iterator().asScala.map(_.toString).toList
      }else {
        List(result.toString)
      }
    }else Nil

  }
}
