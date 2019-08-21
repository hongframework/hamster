import com.hframe.hamster.node.task.common.TaskData
import com.hframework.common.frame.ServiceFactory
import com.hframework.common.util.file.FileUtils
import com.hframework.common.util.message.JsonUtils
import com.hframework.datacenter.hamster.workes.bean.BatchDataSet
import com.hframework.utils.scala.JacksonScalaUtils
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.HamsterWorker
import com.hframework.datacenter.hamster.worker.tasks.load.MysqlWriteLoader
import com.hframework.datacenter.hamster.worker.tasks.transform.AggregationTransformer
import com.hframework.datacenter.hamster.zookeeper.ZKClient
import org.junit.runner.RunWith
import org.junit.{Before, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import scala.collection.JavaConverters._

@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(locations = Array("classpath:spring-config.xml"))
class AggregationTransformerSuite {
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
  def test_stat_data_transform ={
    HamsterWorker.workerId = "-999"
    val taskNodePath = "/hamster/config/jobs/stat_binlog_mysql/user_agg_second/agg_trans"
    val jobNodeMeta = ZKClient.readData(taskNodePath, classOf[JobNodeMeta])

    val deployPath = "/hamster/config/deploys/11_test_stat/stat_binlog_mysql.user_agg_second.agg_trans"
    val deployMeta = ZKClient.readData(deployPath, classOf[DeployJobNodeMeta])

    val extractor = new AggregationTransformer(jobNodeMeta.jobMeta.selectKey, deployMeta, jobNodeMeta)
    HamsterWorker.taskRunners.put(extractor.jobNodeMeta.nodeMeta.nodeKey, extractor)
    extractor.start()
    val json = readFile("user_log.json").mkString("\n")
    println(json)
    val taskData = DataSetReader.read(json)
    val result = extractor.execute(1L, null, taskData.getMessage.asInstanceOf[BatchDataSet])
    println(JsonUtils.writeValueAsString(result.get))
    while(true) Thread.sleep(500)
  }

  @Test
  def test_func_stat_data_transform ={
    HamsterWorker.workerId = "-999"
    //获取任务描述对象
    val jobNodeMeta = JacksonScalaUtils.fromJson(
      readFile("agg_trans/job_node_meta.json").mkString("\n"), classOf[JobNodeMeta])
    //获取用户发布对象
    val deployMeta = JacksonScalaUtils.fromJson(
      readFile("agg_trans/deploy_node_meta.json").mkString("\n"), classOf[DeployJobNodeMeta])
    //获取测试数据
    val taskData = DataSetReader.read(readFile("agg_trans/user_log_stat.json").mkString("\n"))

    val extractor = new AggregationTransformer(jobNodeMeta.jobMeta.selectKey, deployMeta, jobNodeMeta)
    HamsterWorker.taskRunners.put(extractor.jobNodeMeta.nodeMeta.nodeKey, extractor)
    extractor.start()
    val result = extractor.execute(1L, null, taskData.getMessage.asInstanceOf[BatchDataSet])
    if(result.isDefined) {
      println(JsonUtils.writeValueAsString(result.get))
    }else {
      print("ok!")
    }

  }

}