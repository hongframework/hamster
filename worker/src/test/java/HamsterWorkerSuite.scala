import com.hframework.datacenter.hamster.worker.HamsterWorker
import org.junit.Test

class HamsterWorkerSuite {

  @Test
  def test_HamsterWorker: Unit ={
    System.setProperty("nodeId", "-666")
    HamsterWorker.main(null)
  }
}
