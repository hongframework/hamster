import com.hframework.common.frame.ServiceFactory
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.monitor.db.JobRegistry.JobNode
import com.hframework.datacenter.hamster.worker.HamsterWorker
import com.hframework.datacenter.hamster.worker.tasks.Task
import com.hframework.datacenter.hamster.worker.tasks.extract.MysqlBinlogExtractor
import com.hframework.datacenter.hamster.worker.tasks.select.MysqlBinlogSelector
import com.hframework.datacenter.hamster.worker.tasks.term.MysqlBinlogTerminator
import com.hframework.datacenter.hamster.zookeeper.{ZKClient, ZKPathUtils}
import org.junit.{Before, Test}
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import scala.collection.JavaConverters._

@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(locations = Array("classpath:spring-config.xml"))
class MysqlBinlogSelectorSuite {
  @Autowired protected var ctx: ApplicationContext = null

  @Before
  @throws[Exception]
  def init(): Unit = {
    ServiceFactory.initContext(ctx)
  }


  def start_binlog_selector: Unit ={
    val taskNodePath = "/hamster/config/jgroups/binlog.mysql_binlog.19"
    val dependencyExtractIds = ZKClient.getChildren(taskNodePath).asScala.toList
    val selectNode = ZKClient.readData(taskNodePath, classOf[JobNodeMeta])

    val dependencyExtractJobNode = dependencyExtractIds.map(extractId => {
      val extractInfo = extractId.split('.')
      val extractPath = ZKPathUtils.getJobNodeConfigPath(extractInfo(0),extractInfo(1),extractInfo(2))
      ZKClient.readData(extractPath, classOf[JobNodeMeta])
    })

    HamsterWorker.workerId = "-999"

    val taskRunnerClass = Class.forName("com.hframework.datacenter.hamster.worker.tasks.select.MysqlBinlogSelector")
    if(classOf[Task].isAssignableFrom(taskRunnerClass)) {
      val constructor = taskRunnerClass.getDeclaredConstructors.head

      if(constructor.getParameterTypes.toList == List(classOf[String], classOf[JobNodeMeta], classOf[scala.List[JobNodeMeta]])) {
        val taskRunner = constructor.newInstance(selectNode.jobMeta.selectKey, selectNode, dependencyExtractJobNode)
        HamsterWorker.taskRunners.put(selectNode.jobMeta.selectKey, taskRunner.asInstanceOf)
        taskRunner.asInstanceOf[Thread].start()
      }
    }

//    val selector = new MysqlBinlogSelector(selectNode.jobMeta.selectKey, selectNode, dependencyExtractJobNode)
//    HamsterWorker.taskRunners.put(selectNode.jobMeta.selectKey, selector)
//    selector.start()

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

    HamsterWorker.workerId = "-999"
    val deployPath = "/hamster/config/deploys/10_test_sync/lcs_candy_mysql_binlog"
    val deployMeta = ZKClient.readData(deployPath, classOf[DeployJobNodeMeta])
    val selector = new MysqlBinlogTerminator(selectNode.jobMeta.selectKey, deployMeta, selectNode, dependencyExtractJobNode)
    HamsterWorker.taskRunners.put(selectNode.nodeMeta.nodeKey + "_terminator", selector)
    selector.start()

  }

  @Test
  def test_start_selector ={
    HamsterWorker.workerId = "-999"
    start_binlog_selector
    while(true) Thread.sleep(500)
  }

  @Test
  def test_start_selector_extractor ={
    HamsterWorker.workerId = "-999"
    start_binlog_selector
    start_binlog_extractor
    while(true) Thread.sleep(500)
  }

  @Test
  def test_start_terminator ={
    HamsterWorker.workerId = "-999"
    start_binlog_terminator
    while(true) Thread.sleep(500)
  }

  @Test
  def test_start_selector_terminator ={
    HamsterWorker.workerId = "-999"
    start_binlog_selector
    start_binlog_terminator
    while(true) Thread.sleep(500)
  }

}