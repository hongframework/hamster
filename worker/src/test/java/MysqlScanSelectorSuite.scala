import com.hframework.common.frame.ServiceFactory
import com.hframework.common.util.file.FileUtils
import com.hframework.smartsql.client.DBClient
import com.hframework.utils.scala.JacksonScalaUtils
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta, JobNodeMetas}
import com.hframework.datacenter.hamster.worker.HamsterWorker
import com.hframework.datacenter.hamster.worker.tasks.extract.MysqlBinlogExtractor
import com.hframework.datacenter.hamster.worker.tasks.select.{MysqlScanSelector, SqlParser}
import com.hframework.datacenter.hamster.worker.tasks.select.MysqlScanSelector.{getSubTables, globalEmptyArray}
import com.hframework.datacenter.hamster.worker.tasks.term.MysqlBinlogTerminator
import com.hframework.datacenter.hamster.zookeeper.{ZKClient, ZKPathUtils}
import org.junit.runner.RunWith
import org.junit.{Before, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import scala.collection.JavaConverters._

@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(locations = Array("classpath:spring-config.xml"))
class MysqlScanSelectorSuite {
  @Autowired protected var ctx: ApplicationContext = null

  def getAllSubTables(tables: List[String]): List[(String, List[String])] ={
    val dbTables = DBClient.executeQueryList("show tables", globalEmptyArray).asScala.map(_.asScala.head.asInstanceOf[String])

    val allCfgTableSubs = tables.map(table => {
      val isNormalTable = table.matches("[a-zA-Z0-9_]+")
      (table, if(!isNormalTable) getSubTables(dbTables, table) else List(table))
    })
    allCfgTableSubs
  }

  @Test
  def test_split_table_view: Unit ={
    MysqlScanSelector.registerDatabase(20)
    val (allCfgTables, mainCfgTables, viewSql) = MysqlScanSelector.parseDbObject("v_test_split_table", 20)
    println(allCfgTables)
    val allCfgTableSubs = getAllSubTables(allCfgTables)
    println(allCfgTableSubs)

    val scanTimeField = "update_time_"

    val viewSqlSegment = SqlParser.parse(viewSql.get.replaceAll("%","%%"))//where name like %变现通% 需要转换为 %%变现通%%，否则format时报错
    viewSqlSegment.addWhere(mainCfgTables.map(table => s"${table}.${scanTimeField} BETWEEN '%s' AND '%s'").mkString(" OR "))
    viewSqlSegment.addSelect(mainCfgTables.map(MysqlScanSelector.addSelectString(_)).mkString(", "))
    viewSqlSegment.addOrderBy(mainCfgTables.map(table => s"$table.${scanTimeField}, $table.id_").mkString(","))
    viewSqlSegment.addLimit("%s, %s")
    val concatSql = viewSqlSegment.runtimeSql
    println(concatSql)

    val result = MysqlScanSelector.getSqlsByTemplateSql(concatSql, allCfgTableSubs)
    println("inst = " + result.map(_._1).mkString("\n inst = "))

  }

  @Before
  @throws[Exception]
  def init(): Unit = {
    ServiceFactory.initContext(ctx)
  }

  def readFile(fileName: String): Array[String] = {
    val resource = this.getClass.getClassLoader.getResource(fileName)
    val context = FileUtils.readFileToArray(resource.getPath)
    context.asScala.toArray
  }

  def start_selector: Unit ={
//    val taskNodePath = "/hamster/config/jgroups/mysql.mysql_scan.20.v_deal_load_deal.1s.100"
//    val dependencyExtractIds = ZKClient.getChildren(taskNodePath).asScala.toList
//    val selectAndTermNode = ZKClient.readData(taskNodePath, classOf[JobNodeMetas])
//    val selectNode = selectAndTermNode.jobNodeMetas.head
//
//    val dependencyExtractJobNode = dependencyExtractIds.map(extractId => {
//      val extractInfo = extractId.split('.')
//      val extractPath = ZKPathUtils.getJobNodeConfigPath(extractInfo(0),extractInfo(1),extractInfo(2))
//      ZKClient.readData(extractPath, classOf[JobNodeMeta])
//    })

//    val deployPath = "/hamster/config/deploys/13_test_mysql_stat/_select_term.mysql.mysql_scan.20.v_deal_load_deal.1s.100"
//    val deployMeta = ZKClient.readData(deployPath, classOf[DeployJobNodeMeta])
    val selectNode = JacksonScalaUtils.fromJson(
      readFile("scan_selector/job_node_meta.json").mkString("\n"), classOf[JobNodeMeta])

    val dependencyExtractJobNode = List(JacksonScalaUtils.fromJson(readFile("scan_selector/child_job_node_meta.json").mkString("\n"), classOf[JobNodeMeta]))

    val deployMeta = JacksonScalaUtils.fromJson(readFile("scan_selector/deploy_node_meta.json").mkString("\n"), classOf[DeployJobNodeMeta])

    HamsterWorker.workerId = "-888"

    val selector = new MysqlScanSelector(selectNode.jobMeta.selectKey, deployMeta, selectNode, dependencyExtractJobNode)
    selector.start()

  }

  def start_binlog_extractor: Unit ={
    val taskNodePath = "/hamster/config/jobs/data_subscribe/lcs_candy/mysql_binlog"
    val extractNode = ZKClient.readData(taskNodePath, classOf[JobNodeMeta])

    val deployPath = "/hamster/config/deploys/10_test_sync/lcs_candy_mysql_binlog"
    val deployMeta = ZKClient.readData(deployPath, classOf[DeployJobNodeMeta])

    val extractor = new MysqlBinlogExtractor(extractNode.jobMeta.selectKey, deployMeta, extractNode)
    HamsterWorker.taskRunners.put(extractor.jobNodeMeta.nodeMeta.nodeKey, extractor)
    extractor.start()

  }

  def start_binlog_terminator: Unit ={
    val taskNodePath = "/hamster/config/jgroups/binlog.mysql_binlog.19"
    val dependencyExtractIds = ZKClient.getChildren(taskNodePath).asScala.toList
    val selectNode = ZKClient.readData(taskNodePath, classOf[JobNodeMeta])

    val dependencyExtractJobNode = dependencyExtractIds.map(extractId => {
      val extractInfo = extractId.split('.')
      val extractPath = ZKPathUtils.getJobNodeConfigPath(extractInfo(0),extractInfo(1),extractInfo(2))
      ZKClient.readData(extractPath, classOf[JobNodeMeta])
    })

    HamsterWorker.workerId = "-888"
    val deployPath = "/hamster/config/deploys/10_test_sync/lcs_candy_mysql_binlog"
    val deployMeta = ZKClient.readData(deployPath, classOf[DeployJobNodeMeta])
    val selector = new MysqlBinlogTerminator(selectNode.jobMeta.selectKey, deployMeta, selectNode, dependencyExtractJobNode)
    HamsterWorker.taskRunners.put(selectNode.nodeMeta.nodeKey + "_terminator", selector)
    selector.start()

  }

  @Test
  def test_selector ={
    HamsterWorker.workerId = "-888"
    start_selector
    while(true) Thread.sleep(500)
  }

  @Test
  def test_start_selector_extractor ={
    HamsterWorker.workerId = "-888"
    start_selector
    start_binlog_extractor
    while(true) Thread.sleep(500)
  }

  @Test
  def test_start_terminator ={
    HamsterWorker.workerId = "-888"
    start_binlog_terminator
    while(true) Thread.sleep(500)
  }

  @Test
  def test_start_selector_terminator ={
    HamsterWorker.workerId = "-888"
    start_selector
    start_binlog_terminator
    while(true) Thread.sleep(500)
  }

}