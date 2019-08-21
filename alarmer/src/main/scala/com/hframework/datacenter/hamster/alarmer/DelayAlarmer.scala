package com.hframework.datacenter.hamster.alarmer

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

import com.hframe.hamster.node.cannal.bean.CanalCursor
import com.hframework.common.frame.ServiceFactory
import com.hframework.smartsql.client.DBClient
import com.hframework.utils.scala.Logging
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.monitor.zk.ZKNodeMonitor
import com.hframework.datacenter.hamster.monitor.zk.listeners._
import com.hframework.datacenter.hamster.zookeeper.ZKClient
import com.hframework.datacenter.hamster.zookeeper.ZKPathUtils._
import com.hframework.hamster.cfg.domain.model.{CfgDatasource, CfgDatasource_Example, CfgDataview_Example}
import com.hframework.hamster.cfg.service.interfaces.{ICfgDatasourceSV, ICfgDataviewSV}
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.exception.ExceptionUtils

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.util.control.Breaks._

case class ScanBatch(var signal: ScanSignal, sqlKey: String, hasNext: Boolean, pageNo: Long,
                     dataBeginTime: String, startId: Long, dataEndTime: String, endId: Long, from: String, to: String, fromTs: String, toTs: String, var batchId:Long = -1)

case class ScanSignal(var signalId: Long, var from: String, var to: String, var  fromTs: Long, var  toTs: Long,
                      val sqlKeys: mutable.Set[String] = mutable.Set[String](), @volatile var lastDownBatch: Option[ScanBatch] = None)

/**
  * @author songge
  * @version 2018-12-03
  */
class DelayAlarmer extends Logging {
  // 根据cursor路径查询其deploys路径和jobs路径
  val cursorJobMap = new mutable.HashMap[String, (String, String)]

  //监控/otter/canal/destinations节点分支下名为cursor的节点上的timestamp
  val canalTimestampMonitor = new ZKNodeMonitor("canal_root", getCanalRootPath)
  canalTimestampMonitor.addListener(new ZKNodeWrapperListener("selector_root") {
    // root
    override def getParentListener: ZKNodeListener = new ZKNodeWrapperListener("database", false) {
      //selector
      override def getParentListener: ZKNodeListener = new ZKNodeWrapperSpecificListener("specific", false, "cursor") {
        // dbKey
        override def getParentListener: ZKNodeListener = new ZKNodeSimpleListener {
          // cursor
          override def nodeCreate(selectorPaths: List[String]): Unit = {
            selectorPaths.foreach(selectorPath => {
              logger.info(s"[$selectorPath] add ! ")
              nodeDataChange(selectorPath)
            })
          }

          override def nodeDelete(selectorPath: String): Unit = {
            logger.info(s"[$selectorPath] remove ! ")
          }
          override def nodeDataChange(selectorPath: String): Unit = {
            try {
              val cursorData = ZKClient.readData(selectorPath, classOf[CanalCursor])
              val timestamp = (cursorData.getTimestamp / 1000).toString
              val cursorName = selectorPath.split("/").dropRight(2).last
              val selectKey = cursorName.split("\\.").dropRight(1).mkString(".")
              val indexName = cursorName.split("\\.").last + "+" + selectKey
              putInfoToDB(indexName, timestamp)
            }
            catch {
              case _ => logger.error(s"canal cursor change error: ${ExceptionUtils.getFullStackTrace(_)}")
            }
          }
        }
      }
    }
  })

  val hamsterTimestampMonitor = new ZKNodeMonitor("hamster_root", getQuartzRootPath)
  hamsterTimestampMonitor.addListener(new ZKNodeWrapperListener("dbSource") {
    // root
    override def getParentListener: ZKNodeListener = new ZKNodeWrapperListener("selector", false) {
      //selector
      override def getParentListener: ZKNodeListener = new ZKNodeWrapperSpecificListener("specific", false, "cursor") {
        // dbKey
        override def getParentListener: ZKNodeListener = new ZKNodeSimpleListener {
          // cursor
          override def nodeCreate(selectorPaths: List[String]): Unit = {
            selectorPaths.foreach(selectorPath => {
              logger.info(s"[$selectorPath] add ! ")
              nodeDataChange(selectorPath)
            })
          }

          override def nodeDelete(selectorPath: String): Unit = {
            logger.info(s"[$selectorPath] remove ! ")
          }
          override def nodeDataChange(selectorPath: String): Unit = {
            try {
              val cursorData = ZKClient.readData(selectorPath, classOf[ScanBatch])
              val timestamp = cursorData.toTs
              val selectKey = selectorPath.split("/").dropRight(1).last
              val indexName  = selectorPath.split("/").dropRight(2).last + "+" + selectKey
              val nodeCacheOutput = nodeCache.keySet().asScala.map(tableName => {
                (tableName, nodeCache.get(tableName).getOutNodes.keys)
              })
              logger.debug(s"nodeCache: $nodeCacheOutput")
              if (timestamp != null)
                putInfoToDB(indexName, timestamp)
            }
            catch {
              case _ => logger.error(s"hamster cursor change error: ${ExceptionUtils.getFullStackTrace(_)}")
            }
          }
        }
      }
    }
  })

  def putInfoToDB(indexName: String, executeTime: String): Boolean = {
    var result: Boolean = true
    if (selectorInfoCache.containsKey(indexName)) {
      try{
        val selectInfo = selectorInfoCache.get(indexName)
        val indexNameTmp = indexName.split("\\+")
        val deploy = indexNameTmp(0)
        val datastore = indexNameTmp(1)
        val valuesString = selectInfo.flatMap(e => {
          val (sourceTables, targetTables, updateNode) = (e._1, e._2, e._3)
          targetTables.map(targetTable => {
            val node = nodeCache.get(targetTable)
            sourceTables.map(sourceTable => {
              node.setExecuteTime(sourceTable, executeTime.toLong)
              val currentTime = System.currentTimeMillis / 1000
              val delayTime = currentTime - executeTime.toLong
              val totalDelay = node.getTotalDelay
              val totalExecuteTimeFormatted = {
                val totalExecuteTime = if (totalDelay == -1) { executeTime.toLong } else { currentTime - totalDelay }
                simpleDateFormat.format(new Date(totalExecuteTime * 1000))
              }
              val description = if (totalDelay == -1) "preNode has not been updated!" else ""
              val updateTimeFormatted = simpleDateFormat.format(new Date(currentTime * 1000))
              s"('$deploy', '$datastore', '$sourceTable', '$targetTable', '$totalExecuteTimeFormatted', '$updateTimeFormatted', $delayTime, $totalDelay, '$updateNode', '$description')"
            }).mkString(",")
          })
        }).mkString(",")
        val sql = s"INSERT INTO $jobTimestampTable (deploy, datastore, source_table, target_table, execute_time, update_time, delay_time, total_delay, update_node, description) VALUES $valuesString ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id),deploy=VALUES(deploy),datastore=VALUES(datastore),source_table=VALUES(source_table),target_table=VALUES(target_table),execute_time=VALUES(execute_time),update_time=VALUES(update_time),delay_time=VALUES(delay_time),total_delay=VALUES(total_delay),update_node=VALUES(update_node),description=VALUES(description)"
        logger.debug(s"execute sql on db $DbKey: $sql")
        DBClient.setCurrentDatabaseKey(DbKey)
        DBClient.executeUpdate(sql, globalEmptyArray)
      }
      catch {
        case e: Exception => {
          logger.error(s"put info to db error: ${ExceptionUtils.getFullStackTrace(e)}")
          result = false
        }
      }
    }
    else {
      logger.warn(s"cursor $indexName does not have info to put into db")
    }
    result
  }

  def getWriteTaskInfo(taskPath: String): List[String] = {
    var result = List[String]()
    if (ZKClient.exists(taskPath)) {
      val taskData = ZKClient.readData(taskPath, classOf[JobNodeMeta])
      result = List[String](taskData.nodeMeta.rows.head("targetTable"))
    }
    logger.info(s"writeTaskInfo of $taskPath: $result")
    result
  }

  def getScanTaskInfo(taskPath: String): List[String] = {
    var result = List[String]()
    if (ZKClient.exists(taskPath)) {
      val taskData = ZKClient.readData(taskPath, classOf[JobNodeMeta])
      taskData.nodeMeta.rows.foreach(row => {
        val dbId = row("sourceDB").toLong
        val dbObject = row("sourceTable")
        val dbViewConfig = ServiceFactory.getService(classOf[ICfgDataviewSV]).getCfgDataviewListByExample({
          val example = new CfgDataview_Example
          example.createCriteria().andViewNameEqualTo(dbObject).andCfgDatasourceIdEqualTo(dbId)
          example
        })
        val viewSql = if(dbViewConfig.asScala.nonEmpty) Some(dbViewConfig.asScala.head.getViewSql) else None
        if(viewSql.isDefined) {
          val viewConfig = dbViewConfig.get(0)
          val mainTables =  if(StringUtils.isNotBlank(viewConfig.getMainTables)) {
            viewConfig.getMainTables.split(",").dropWhile(_.isEmpty).map(_.trim).distinct.toList
          } else Nil
          result = result ::: mainTables
        }
        else {
          result = result ::: List(dbObject)
        }
      })
    }
    logger.info(s"scanTaskInfo of $taskPath: $result")
    result
  }

  def getBinlogTaskInfo(taskPath: String): List[String] = {
    var result = List[String]()
    if (ZKClient.exists(taskPath)) {
      val taskData = ZKClient.readData(taskPath, classOf[JobNodeMeta])
      result = taskData.nodeMeta.rows.map(row => row("sourceDB") + "." + row("sourceTable"))
    }
    logger.info(s"binlogTaskInfo of $taskPath: $result")
    result
  }

  val deploysMonitor = new ZKNodeMonitor("hamster_deploys", getDeploymentConfigRootPath)
  deploysMonitor.addListener(new ZKNodeWrapperListener("deploy_key") {
      override def getParentListener: ZKNodeListener = new ZKNodeWrapperSpecificListener("specific", false, "mysql_write") {
        override def getParentListener: ZKNodeListener = new ZKNodeSimpleListener {
          override def nodeCreate(deploysPaths: List[String]): Unit = {
            deploysPaths.foreach(deployPath => {
              logger.info(s"listener on [$deployPath] add ! ")
              nodeDataChange(deployPath)
            })
          }

          override def nodeDelete(deployPath: String): Unit = {
            logger.info(s"[$deployPath] remove ! ")
          }
          override def nodeDataChange(deployPath: String): Unit = {
            try {
              val deployName = deployPath.split("/").dropRight(1).last
              val deployData = ZKClient.readData(deployPath, classOf[DeployJobNodeMeta])
              val jobPath = getJobsConfigRootPath + "/" + deployData.jobKey.replace(".", "/")
              val indexName = deployName + "+" + deployData.selectKey
              val selectTermPath = deployPath.split("/").dropRight(1).mkString("/") + "/_select_term." + deployData.selectKey
              val workerId = ZKClient.readData(selectTermPath, classOf[DeployJobNodeMeta]).workerId
              var targetTableList = List[String]()
              var sourceTableList = List[String]()
              ZKClient.getChildren(jobPath).asScala.foreach(task => {
                val taskPath = jobPath + "/" + task
                task match {
                  case "mysql_write" => targetTableList = targetTableList ::: getWriteTaskInfo(taskPath)
                  case "mysql_scan" => sourceTableList = sourceTableList ::: getScanTaskInfo(taskPath)
                  case "mysql_binlog" => sourceTableList = sourceTableList ::: getBinlogTaskInfo(taskPath)
                  case _ =>
                }
              })

              // 更新节点和连接关系
              sourceTableList.foreach(sourceTable => {
                targetTableList.foreach(targetTable => {
                  if (!nodeCache.containsKey(sourceTable)) {
                    nodeCache.put(sourceTable, new TableNode(sourceTable))
                    logger.info(s"create new node $sourceTable: ${nodeCache.get(sourceTable)}")
                  }
                  nodeCache.get(sourceTable).addOutNode(targetTable)
                  if (!nodeCache.containsKey(targetTable)) {
                    nodeCache.put(targetTable, new TableNode(targetTable))
                    logger.info(s"create new node $targetTable: ${nodeCache.get(targetTable)}")
                  }
                  nodeCache.get(targetTable).addInNode(sourceTable)
                })
              })

              var selectInfo = if (selectorInfoCache.containsKey(indexName)) {
                val selectInfoTmp = selectorInfoCache.get(indexName)
                selectorInfoCache.remove(indexName)
                selectInfoTmp
              } else List[(List[String], List[String], String)]()
              selectInfo = (sourceTableList, targetTableList, workerId) :: selectInfo
              selectorInfoCache.put(indexName, selectInfo)
            }
            catch {
              case e: Exception => logger.error(s"hamster cursor change error: ${ExceptionUtils.getFullStackTrace(e)}")
            }
          }
        }
      }
  })

  class TableNode() {
    private var tableName: String = _
    // 当前节点的executeTime，为所有入边上的最小值(仅当所有入边都被更新过才会更新这个值)
    private var executeTime: Long = -1
    // 流入节点，以及边上的executeTime
    val inNodes = mutable.Map[String, Long]()
    // 流出节点
    val outNodes = mutable.Map[String, String]()

    def this(tableName: String) = {
      this()
      this.tableName = tableName
    }

    def ifExistInNode(tableName: String) = {
      this.inNodes.contains(tableName)
    }

    def addInNode(tableName: String) = {
      if (!this.inNodes.contains(tableName))
        this.inNodes += (tableName -> -1)
      else
        logger.warn(s"Trying to add a existing inNode $tableName on ${this.tableName}!")
    }

    def delInNode(tableName: String) = {
      if (this.inNodes.contains(tableName))
        this.inNodes.remove(tableName)
      else
        logger.warn(s"Trying to delete an nonexistent inNode $tableName on ${this.tableName}!")
    }

    def addOutNode(tableName: String) = {
      if (!this.outNodes.contains(tableName)) {
        this.outNodes += (tableName -> "")
      }
      else
        logger.warn(s"Trying to add a existing outNode $tableName on ${this.tableName}!")
    }

    def delOutNode(tableName: String) = {
      if (this.outNodes.contains(tableName))
        this.outNodes.remove(tableName)
      else
        logger.warn(s"Trying to delete an nonexistent outNode $tableName on ${this.tableName}!")
    }

    def calculateTotalDelay: Long = {
      // 用栈实现DF遍历来计算到当前节点为止任务最大的总延迟值
      var totalDelay: Long = 0
      val nodeStack = mutable.Stack[(String, Long)]()
      nodeStack.push((this.tableName, 0))
      breakable {
        while (nodeStack.nonEmpty) {
          val (visitNodeName, postTotalDelay) = nodeStack.pop
          val visitNode = nodeCache.get(visitNodeName)
          val inNodes = visitNode.getInNodes
          val visitNodeDelay = visitNode.calculateDelay
          val currentTotalDelay = {
            if (visitNodeDelay != -1)
              postTotalDelay + visitNodeDelay
            else
              postTotalDelay
          }
          if (inNodes.nonEmpty) {
            if (visitNodeDelay == -1) {
              totalDelay = -1
              break
            }
            inNodes.foreach(inNode => {
              nodeStack.push((inNode._1, currentTotalDelay))
            })
          }
          else {
            totalDelay = if (currentTotalDelay > totalDelay) currentTotalDelay else totalDelay
          }
        }
      }
      totalDelay
    }

    def getTableName: String = { this.tableName }

    def getInNodes : mutable.Map[String, Long] = { this.inNodes }

    def getOutNodes : mutable.Map[String, String] = { this.outNodes }

    def calculateDelay : Long = {
      if (this.executeTime != -1) System.currentTimeMillis / 1000 - this.executeTime
      else -1
    }

    def getTotalDelay: Long = {
      this.calculateTotalDelay
    }

    def setExecuteTime(inNodeName:String, executeTime: Long) = {
      // 设置指定来源的executeTime，当所有来源的executeTime都被设置，则当前节点的executeTime取其中最小(早)的一个
      this.inNodes.remove(inNodeName)
      this.inNodes += (inNodeName -> executeTime)
      var minExecuteTime = Long.MaxValue
      var flag: Boolean = true
      this.inNodes.foreach(e => {
        val (inNode, executeTime) = e
        flag = {
          if (executeTime == -1)
            false
          else {
            minExecuteTime = if (minExecuteTime > executeTime) executeTime else minExecuteTime
            flag
          }
        }
      })
      this.executeTime = {
        if (flag) minExecuteTime
        else -1
      }
    }

    def getExecuteTime: Long = { this.executeTime }

    def getSpecificExecuteTime(inNodeName: String): Long = {
      if (this.inNodes.contains(inNodeName))
        this.inNodes(inNodeName)
      else
        -1
    }
  }

  val DbKey = "hamster_dw"
  val jobTimestampTable = "job_exe_meta"
  val dataSource: CfgDatasource = ServiceFactory.getService(classOf[ICfgDatasourceSV]).getCfgDatasourceListByExample({
    val exmpale = new CfgDatasource_Example
    exmpale.createCriteria.andDbEqualTo("hamster_dw")
    exmpale.or.andDbEqualTo("hamster_data")
    exmpale
  }).get(0)
  DBClient.registerDatabase(DbKey, s"jdbc:mysql://${dataSource.getUrl}/${dataSource.getDb}?useUnicode=true&tinyInt1isBit=false", dataSource.getUsername, dataSource.getPassword)
  val simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  val globalEmptyArray =  Array.empty[Object]
  val bootstrapInvoke = new AtomicBoolean(true)
  val selectorInfoCache = new ConcurrentHashMap[String, List[(List[String], List[String], String)]]()
  val nodeCache = new ConcurrentHashMap[String, TableNode]()

  def start: Unit = {
    initZK
    deploysMonitor.setup
    Thread.sleep(1000L*10)
    canalTimestampMonitor.setup
    hamsterTimestampMonitor.setup
  }

  def initZK: Unit = {
    ZKClient.createPersistentIfNotExists(getDeploymentConfigRootPath, createParents = true)
    ZKClient.createPersistentIfNotExists(getCanalRootPath, createParents = true)
    ZKClient.createPersistentIfNotExists(getQuartzRootPath, createParents = true)
  }

  def shutdown(): Unit = {
    deploysMonitor.shutdown
    canalTimestampMonitor.shutdown
    hamsterTimestampMonitor.shutdown
  }
}
