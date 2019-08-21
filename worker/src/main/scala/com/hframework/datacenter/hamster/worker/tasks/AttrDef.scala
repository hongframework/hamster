package com.hframework.datacenter.hamster.worker.tasks
case class AttrDef(code: String){
  override def toString: String = code
  def apply(code: String): AttrDef = new AttrDef(code)
}
object AttrDef {
  object Binlog{
    val sourceDB = AttrDef("sourceDB").toString
    val sourceTable = AttrDef("sourceTable").toString
    val operType  = AttrDef("operType").toString
    val filterExpression  = AttrDef("filterExpression").toString
    val sourceColumns  = AttrDef("sourceColumns").toString
    //    val logfileName  = AttrDef("logfileName").toString
    //    val logfileOffset  = AttrDef("logfileOffset").toString
    //    val logfileTimestamp  = AttrDef("logfileTimestamp").toString
  }

  object MysqlScan{
    val sourceDB = AttrDef("sourceDB").toString
    val sourceTable = AttrDef("sourceTable").toString
    val scanInterval = AttrDef("scanInterval").toString//数据扫描间隔
    val scanTimeField = AttrDef("scanTimeField").toString//数据扫描字段
    val dataScanScope = AttrDef("dataScanScope").toString//数据扫描范围
    val batchSizeCfg = AttrDef("batchSize").toString
    val operType  = AttrDef("operType").toString
    val filterExpression  = AttrDef("filterExpression").toString
    val sourceColumns  = AttrDef("sourceColumns").toString
  }

  object Executor{
    val sourceDB = AttrDef("sourceDB").toString
    val scanInterval = AttrDef("scanInterval").toString//数据扫描间隔
    val batchInterval = AttrDef("batchInterval").toString//批次处理间隔
    val sql = AttrDef("sql").toString//SQL语句
  }

  object Expand {
    val relationDB = AttrDef("relationDB").toString
    val relationTable = AttrDef("relationTable").toString
    val originColumn = AttrDef("originColumn").toString
    val relatedColumn = AttrDef("relatedColumn").toString
    val sourceColumn = AttrDef("sourceColumn").toString
    val ifRetainOrigin = AttrDef("ifRetainOrigin").toString
  }


  object Aggregation{
    val targetColumn = AttrDef("targetColumn").toString //目标字段
    val bucketFields = AttrDef("bucketFields").toString //统计分组字段
    val bucketTimeField = AttrDef("bucketTimeField").toString //统计时间字段
    val bucketTimeInterval = AttrDef("bucketTimeInterval").toString //时间间隔
    val metricFields = AttrDef("metricFields").toString
    val metricFunction = AttrDef("metricFunction").toString
    val filterExpression = AttrDef("filterExpression").toString

    val _timestamp = AttrDef("timestamp_").toString
    val _time_interval = AttrDef("_time_interval").toString
    val _key_code = AttrDef("_key_code").toString
    val _key_sting = AttrDef("_key_sting").toString
  }
  object Prune{
    val sourceColumn = AttrDef("sourceColumn").toString
    val targetColumn = AttrDef("targetColumn").toString
  }


  object Kafka{
    val targetKafka = AttrDef("targetKafka").toString
    val targetTopic = AttrDef("targetTopic").toString
    val partitionField = AttrDef("partitionField").toString
    val partitionStrategy = AttrDef("partitionStrategy").toString
  }

  object KafkaConsumerProps {
    val sourceKafka = AttrDef("sourceKafka").toString
    val sourceTopic = AttrDef("sourceTopic").toString
    val messageType = AttrDef("messageType").toString
    val timeColumn = AttrDef("timeColumn").toString
    val sourceColumns = AttrDef("sourceColumns").toString
    val filterExpression = AttrDef("filterExpression").toString
  }

  object Mysql{
    val targetDB = AttrDef("targetDB").toString
    val targetTable = AttrDef("targetTable").toString
    val updateMethod = AttrDef("updateMethod").toString
    val keyField = AttrDef("keyField").toString
  }
  object MysqlDelete{
    val sourceDB = AttrDef("sourceDB").toString
    val sourceTable = AttrDef("sourceTable").toString
    val scanInterval = AttrDef("scanInterval").toString//数据扫描间隔
    val scanTimeField = AttrDef("scanTimeField").toString//数据扫描字段
    val filterExpression  = AttrDef("filterExpression").toString
    val batchSizeCfg = AttrDef("batchSize").toString
    val historyInterval = AttrDef("historyInterval").toString //历史时间间隔
    val backupChoice = AttrDef("backupChoice").toString
  }

}
