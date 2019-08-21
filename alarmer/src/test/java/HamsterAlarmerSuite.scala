import com.hframework.datacenter.hamster.alarmer.AlarmEngine
import org.junit.Test

class HamsterAlarmerSuite {

  @Test
  def test(): Unit ={
    AlarmEngine("work.block.timeout(3).fib.max(3)")
    println(12312321/10000/65)

    println({
      val exponent = (Math.log(64 - 1 + 1)/Math.log(2)).toInt
      Math.pow(2, exponent) == 64 - 1 + 1
    })
  }
}
