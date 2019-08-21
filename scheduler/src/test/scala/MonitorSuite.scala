import com.hframework.common.frame.ServiceFactory
import com.hframework.datacenter.hamster.monitor.zk.{ZKChildrenMonitor, ZKNodeMonitor}
import com.hframework.datacenter.hamster.monitor.zk.listeners.{ZKChildrenListener, ZKNodeListener, ZKNodeSimpleTestListener, ZKNodeWrapperTestListener}
import com.hframework.datacenter.hamster.zookeeper.ZKPathUtils.{getDeploymentConfigRootPath, getJobsConfigRootPath}
import org.junit.runner.RunWith
import org.junit.{Before, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import scala.collection.mutable

@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(locations = Array("classpath:spring-config.xml"))
class MonitorSuite {
  @Autowired protected var ctx: ApplicationContext = null

  @Before
  @throws[Exception]
  def init(): Unit = {
    ServiceFactory.initContext(ctx)
  }

  @Test
  def test_ZKNodeWrapperTestListener: Unit ={
    val jobsMonitor = new ZKNodeMonitor("job_configs", getJobsConfigRootPath)
    jobsMonitor.addListener(new ZKNodeWrapperTestListener("jobtemplate") {//jobtemplate
      override def getParentListener: ZKNodeListener = new ZKNodeWrapperTestListener("jobInfo") {//jobInfo
        override def getParentListener: ZKNodeListener = new ZKNodeWrapperTestListener("jobNode") {//jobNode
          override def getParentListener: ZKNodeListener = new ZKNodeSimpleTestListener("jobNode-detail")
        }
      }
    })
    jobsMonitor.setup
    while(true) {
      Thread.sleep(1000L)
    }

  }

  @Test
  def test_Deployes: Unit ={
    val jobsMonitor = new ZKNodeMonitor("deploy_configs", getDeploymentConfigRootPath)
    jobsMonitor.addListener(new ZKNodeWrapperTestListener("deploy_config") {//jobtemplate
    override def getParentListener: ZKNodeListener = new ZKNodeWrapperTestListener("jobNode") {//jobInfo
    override def getParentListener: ZKNodeListener = new ZKNodeSimpleTestListener("result")
    }

    })
    jobsMonitor.setup
    while(true) {
      Thread.sleep(1000L)
    }

  }

  @Test
  def test_NodeMonitor: Unit ={
    val monitor = new ZKNodeMonitor("default", "/hamster/config/deploys/10_test_sync/data_synchronize.user_log_sync.mysql_binlog")
    monitor.addListener(new ZKNodeSimpleTestListener("result"))
    monitor.setup
    while(true) {
      Thread.sleep(1000L)
    }

  }

  @Test
  def test_ChildrenMonitor: Unit ={
    val monitor = new ZKChildrenMonitor("111","", true)
    monitor.addListener(new ZKChildrenListener {
      override def childrenChange(add: List[String], del: List[String], all: List[String], versionNo: String = ""): Unit = {
        println(s"receive process signal => add:[${add.mkString("[",", ","]")}], del:[${del.mkString("[",", ","]")}]")
      }
    })
    monitor.reloadChildren((1 to 10).map(_.toString).toBuffer)
    monitor.reloadChildren((2 to 11).map(_.toString).toBuffer)
    monitor.reloadChildren((3 to 12).map(_.toString).toBuffer)
    monitor.reloadChildren((5 to 14).map(_.toString).toBuffer)
    monitor.reloadChildren((6 to 15).map(_.toString).toBuffer)
    monitor.reloadChildren((4 to 13).map(_.toString).toBuffer)
    monitor.reloadChildren((7 to 16).map(_.toString).toBuffer)
    monitor.reloadChildren((8 to 17).map(_.toString).toBuffer)
    monitor.reloadChildren((9 to 18).map(_.toString).toBuffer)
    monitor.reloadChildren((10 to 19).map(_.toString).toBuffer)
    monitor.reloadChildren((11 to 20).map(_.toString).toBuffer)
    monitor.reloadChildren((12 to 21).map(_.toString).toBuffer)
    monitor.reloadChildren((13 to 22).map(_.toString).toBuffer)

  }
}
