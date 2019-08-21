import com.hframework.utils.scala.Logging
import com.hframework.datacenter.hamster.worker.tasks.select.{QuartzServer, ScanBatch}
import org.junit.Test

class QuartzServerSuite extends Logging{

  @Test
  def test_Interval(){
    val server = new QuartzServer("deployCode", "selectorKey")
    server.start(Some(3), None)
    while(true) {
      Thread.sleep(10 * 1000L)
      server.waitForQuartzSignal
    }
  }

  @Test
  def test_Cron(){
    val server = new QuartzServer("deployCode", "selectorKey")
    server.start(None, Some("*/3 * * * * ? "))
    while(true) {
      Thread.sleep(10 * 1000L)
      server.waitForQuartzSignal
    }
  }

  @Test
  def test_reload(){
    val server = new QuartzServer("deployCode", "selectorKey")
    server.start(Some(3), None)
    Thread.sleep(10 * 1000L)
    server.waitForQuartzSignal
    server.reload(None, Some("*/5 * * * * ? "))
    while(true) {
      Thread.sleep(10 * 1000L)
      server.waitForQuartzSignal
    }
  }

  @Test
  def test_ack(){
    val server = new QuartzServer("deployCode", "selectorKey")
    server.start(Some(3), None)
    val signal = server.waitForQuartzSignal
    val scanBatch = ScanBatch(signal, "sql1", false, 1, "timestamp-1", 1, "timestamp-2", 2,"","",1L,1L, splitQuery = false)
    server.addBatch(scanBatch)
    val scanSignal2 = server.waitForQuartzSignal
    val scanBatch2 = ScanBatch(scanSignal2, "sql2", false, 1, "timestamp-1", 1, "timestamp-2", 2,"","",1L,1L, splitQuery = false)
    server.addBatch(scanBatch2)
    server.signalFinish(signal)
    server.ack(scanBatch.batchId)
  }

  @Test
  def test_scanScope(){
    val server = new QuartzServer("deployCode", "selectorKey")
    server.start(Some(3), None, Some(List("-1m","1m")))
    Thread.sleep(10 * 1000L)
    while(true) {
      Thread.sleep(10 * 1000L)
      server.waitForQuartzSignal
    }
  }


}