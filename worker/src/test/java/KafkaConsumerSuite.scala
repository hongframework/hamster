import com.hframework.common.frame.ServiceFactory
import com.hframework.common.util.file.FileUtils
import com.hframework.utils.scala.JacksonScalaUtils
import com.hframework.datacenter.hamster.monitor.db.{DeployJobNodeMeta, JobNodeMeta, JobNodeMetas}
import com.hframework.datacenter.hamster.worker.HamsterWorker
import com.hframework.datacenter.hamster.worker.tasks.select.KafkaSelector
import org.junit.runner.RunWith
import org.junit.{Before, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import scala.collection.mutable
import scala.collection.JavaConverters._

import scala.util.control.Breaks._
@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(locations = Array("classpath:spring-config.xml"))
class KafkaConsumerSuite {
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

  def start_selector: Unit ={
    val selectNode = JacksonScalaUtils.fromJson(
      readFile("kafka_selector/job_node_meta.json").mkString("\n"), classOf[JobNodeMeta])

    val deployMeta = JacksonScalaUtils.fromJson(readFile("kafka_selector/deploy_node_meta.json").mkString("\n"), classOf[DeployJobNodeMeta])

    HamsterWorker.workerId = "-888"

    val selector = new KafkaSelector(selectNode.jobMeta.selectKey, deployMeta, selectNode, Nil)
    selector.start()
    while (true) {
      Thread.sleep(10 * 1000L)
      val batchOffsets = selector.batchOffsetsMap.keySet
      println(s"batchOffsets in suite $batchOffsets")
      batchOffsets.foreach(batchId => {
        selector.ackTermi(batchId)
        println(s"batch ${batchId} acked")
      })
    }
  }

  val batchAckMap = new mutable.HashMap[Long, Boolean]()
  var notAckCount: Int = 0
  @Test
  def test_selector ={
    HamsterWorker.workerId = "-888"
    start_selector
    while(true) Thread.sleep(500)
  }

}