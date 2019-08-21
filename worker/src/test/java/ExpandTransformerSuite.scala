import com.hframe.hamster.node.task.common.TaskData
import com.hframework.common.frame.ServiceFactory
import com.hframework.common.util.file.FileUtils
import com.hframework.common.util.message.JsonUtils
import com.hframework.datacenter.hamster.workes.bean.BatchDataSet
import com.hframework.utils.scala.JacksonScalaUtils
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta}
import com.hframework.datacenter.hamster.worker.HamsterWorker
import com.hframework.datacenter.hamster.worker.tasks.load.MysqlWriteLoader
import com.hframework.datacenter.hamster.worker.tasks.transform.ExpandTransformer
import com.hframework.datacenter.hamster.zookeeper.ZKClient
import org.junit.runner.RunWith
import org.junit.{Before, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import scala.collection.JavaConverters._

/**
  * @author songge
  * @version 2018-11-16
  */
@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(locations = Array("classpath:spring-config.xml"))
class ExpandTransformerSuite {
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
  def test_func_stat_data_transform = {
    HamsterWorker.workerId = "-888"

    val jobNodeMeta = JacksonScalaUtils.fromJson(readFile("exp_trans/job_node_meta.json").mkString("\n"), classOf[JobNodeMeta])

    val deployMeta = JacksonScalaUtils.fromJson(readFile("exp_trans/deploy_node_meta.json").mkString("\n"), classOf[DeployJobNodeMeta])

    val taskData = DataSetReader.read(readFile("exp_trans/data.json").mkString("\n"))

    val extractor = new ExpandTransformer(jobNodeMeta.jobMeta.selectKey, deployMeta, jobNodeMeta)
    HamsterWorker.taskRunners.put(extractor.jobNodeMeta.nodeMeta.nodeKey, extractor)
    extractor.start()
    val result = extractor.execute(1L, null, taskData.getMessage.asInstanceOf[BatchDataSet])
    if (result.isDefined) {
      println(JsonUtils.writeValueAsString(result.get))
    }
    else {
      print("OK!")
    }
  }

}
