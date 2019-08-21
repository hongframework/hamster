import com.hframework.common.frame.ServiceFactory
import com.hframework.common.util.file.FileUtils
import com.hframework.common.util.message.JsonUtils
import com.hframework.datacenter.hamster.workes.bean.BatchDataSet
import com.hframework.utils.scala.JacksonScalaUtils
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.HamsterWorker
import com.hframework.datacenter.hamster.worker.tasks.load.MysqlWriteLoader
import org.junit.runner.RunWith
import org.junit.{Before, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import scala.collection.JavaConverters._

@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(locations = Array("classpath:spring-config.xml"))
class MysqlWriteLoaderSuite {
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
  def test_stat_data_loader ={
    HamsterWorker.workerId = "-999"
    //获取任务描述对象
    val jobNodeMeta = JacksonScalaUtils.fromJson(
      readFile("mysql_write/job_node_meta.json").mkString("\n"), classOf[JobNodeMeta])
    //获取用户发布对象
    val deployMeta = JacksonScalaUtils.fromJson(
      readFile("mysql_write/deploy_node_meta.json").mkString("\n"), classOf[DeployJobNodeMeta])
    //获取测试数据
    val taskData = DataSetReader.read(readFile("mysql_write/user_log_stat.json").mkString("\n"))

    val extractor = new MysqlWriteLoader(jobNodeMeta.jobMeta.selectKey, deployMeta, jobNodeMeta)
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