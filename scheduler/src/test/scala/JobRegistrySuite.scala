import com.hframework.common.frame.ServiceFactory
import com.hframework.datacenter.hamster.monitor.db.JobRegistry
import org.junit.{Before, Test}
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(locations = Array("classpath:spring-config.xml"))
class JobRegistrySuite {
  @Autowired protected var ctx: ApplicationContext = null

  @Before
  @throws[Exception]
  def init(): Unit = {
    ServiceFactory.initContext(ctx)
  }
  @Test
  def test(): Unit ={
    JobRegistry.start
    while (true) Thread.sleep(1000 * 1L)
  }
}
