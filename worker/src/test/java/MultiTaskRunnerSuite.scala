import com.hframework.common.frame.ServiceFactory
import com.hframework.common.util.file.FileUtils
import com.hframework.utils.scala.JacksonScalaUtils
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.{HamsterWorker, MultiTaskRunner}
import org.junit.runner.RunWith
import org.junit.{Before, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import scala.collection.JavaConverters._

@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(locations = Array("classpath:spring-config.xml"))
class MultiTaskRunnerSuite {
  @Autowired protected var ctx: ApplicationContext = null

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

  @Test
  def test_taskRunner ={
    HamsterWorker.workerId = "-999"

    //获取用户发布对象
    val deployMeta = JacksonScalaUtils.fromJson(
      readFile("multi_run/deploy_node_meta.json").mkString("\n"), classOf[DeployJobNodeMeta])

    //获取任务描述对象
    val mysql_binlog = JacksonScalaUtils.fromJson(
      readFile("multi_run/mysql_binlog.json").mkString("\n"), classOf[JobNodeMeta])
    val prune_trans = JacksonScalaUtils.fromJson(
      readFile("multi_run/prune_trans.json").mkString("\n"), classOf[JobNodeMeta])
    val kafka_producer = JacksonScalaUtils.fromJson(
      readFile("multi_run/kafka_producer.json").mkString("\n"), classOf[JobNodeMeta])

    val taskRunner = new MultiTaskRunner(mysql_binlog.jobMeta.jobInfo.jobKey, deployMeta, null,
      List(mysql_binlog, prune_trans, kafka_producer))
    taskRunner.start()
    while(true) Thread.sleep(500)
  }


}