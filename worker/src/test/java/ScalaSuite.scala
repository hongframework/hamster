import java.util.{Calendar, Date, UUID}
import java.util.concurrent.atomic.AtomicLong

import com.hframe.hamster.node.cannal.bean.EventType
import com.hframework.common.util.DateUtils
import com.hframework.common.util.file.FileUtils
import com.hframework.datacenter.hamster.exceptions.TaskRunningException
import org.junit.Test

import scala.collection.mutable

class ScalaSuite {

  @Test
  def test11(): Unit ={
    println("dwd_log_p2p_\\d+".matches("[a-zA-Z0-9_]+"))
    println("dwd_log_p2p_201903".matches("dwd_log_p2p_\\d+"))

    println("[a-zA-Z_\\$\\{\\}]+\\.\\w+".r.findAllIn("user_log_${yyyy_MM_dd}.create_time_ as dwd_user_log_\\d+_create_time_, dwd_user_log_\\d+.update_time_ as dwd_user_log_\\d+_update_time_, dwd_user_log_\\d+.id_ as dwd_user_log_\\d+_id_").toList)

    val parent = "user_log_${yyyy_MM_dd}"
    println("111user_log_${yyyy_MM_dd}111".replaceAll(parent.replace("$", "\\$").replace("{", "\\{").replace("}", "\\}"), "111"))
  }

  /**
    *  通过二进制魏运算匹配报警策略，位定义如下：
    *  | ----------------------------------------------------------------------|
    *  |     1            1          1     1       1       1      1      1     |
    *  |     ↑           ↑         ↑    ↑      ↑      ↑     ↑     ↑     |
    *  | max-times  block-restart   pow   fib   delay   block   rest   work    |
    *  |-----------------------------------------------------------------------|
    */
  @Test
  def bit(): Unit ={
    val bitDefines = Array("work", "rest", "block", "delay", "fib", "pow\\(\n+\\)", "restart", "max\\(\\d+\\)")
    val item = "max(11)"
    val (index, num) = if(bitDefines.contains(item)) {
      (bitDefines.indexOf(item), null)
    }else {
      val matchOp = bitDefines.find(exp => item.matches(exp))
      if(matchOp.isDefined) {
        val exp = matchOp.get
        (bitDefines.indexOf(exp), item.substring(item.indexOf("(") + 1, item.indexOf(")")))
      }else {
        throw new RuntimeException(s"$item is not supported !")
      }
    }

    println(index, num, Math.pow(2, index).toInt.toBinaryString)
  }

  @Test
  def test(): Unit ={
    val nextAckId = new AtomicLong(-1)
    val inits = mutable.Buffer.empty[Long]
    inits += 1L
    inits += 2L
    inits += 3L
    inits += 4L
    inits.sorted

    nextAckId.set(0 + 1)
    println(nextAckId.get() == inits.head)
    println(inits.head)
    println(inits.mkString(", "))

    inits.remove(0)
    println(inits.mkString(", "))


    println(null.asInstanceOf[String])
    println(null.asInstanceOf[String] == null)

    println((0 until 1).toList)
    println((0 to 1).toList)

    println(List("0000001","0000003","0000033","0000013","0000005","0000002").sorted)

    println(("100.1".toDouble + "100.21".toDouble).toString)
    println(("100.0".toDouble + "100.0".toDouble).toString)
    println(("100".toDouble + "100".toDouble).toString.toInt)
  }

  @Test
  def test_format(): Unit ={
    println(formatByBucketInterval(1543680109L * 1000, "1d")   )
  }

  def formatByBucketInterval(executeTime: Long, bucketInterval: String): String = {
    if(bucketInterval.matches("\\d+(s|m|h|d|w|M|y)")) {
      val interval = bucketInterval.take(bucketInterval.length - 1).toInt
      val unit = bucketInterval.last
      val internalString = unit match {
        case 's' => DateUtils.getDate(new Date(executeTime), "yyyyMMddHHmmss")
        case 'm' => DateUtils.getDate(new Date(executeTime), "yyyyMMddHHmm")
        case 'h' => DateUtils.getDate(new Date(executeTime), "yyyyMMddHH")
        case 'd' => DateUtils.getDate(new Date(executeTime), "yyyyMMdd")
        case 'w' => {
          val cal = Calendar.getInstance()
          cal.setFirstDayOfWeek(Calendar.MONDAY)
          cal.setTimeInMillis(executeTime)
          val weekOfMonth = cal.get(Calendar.WEEK_OF_MONTH)
          DateUtils.getDate(new Date(executeTime), "yyyyMM") + weekOfMonth
        }
        case 'M' => DateUtils.getDate(new Date(executeTime), "yyyyMM")
        case 'y' => DateUtils.getDate(new Date(executeTime), "yyyy")
      }
      internalString
    }else {
      new TaskRunningException(s"bucketInterval[${bucketInterval}] illegal!")
      ""
    }
  }

  @Test
  def test_transpose: Unit ={

    println(List(1,2,3):::(List(4,5,6)):::(List(7,8,9)))
    println(3 ::(List(4,5,6)):::(List(7,8,9)))
    println(List(1,2,3).:::(List(4,5,6)).:::(List(7,8,9)))
  }

  @Test
  def test_groupBy:Unit = {
    val data = List(("2018-12-10",EventType.INSERT,1),
      ("2018-12-10",EventType.UPDATE,2),
      ("2018-12-11",EventType.INSERT,3),
      ("2018-12-12",EventType.UPDATE,4),
      ("2018-12-12",EventType.DELETE,4),
      ("2018-12-10",EventType.INSERT,5),
      ("2018-12-11",EventType.INSERT,6))
    println(data.groupBy(item => (item._1, item._2)).toList.map(_._1))

    println(data.groupBy(item => (item._1, item._2)).toList.sortWith((i1, i2) =>{
      val key1 = i1._1
      val key2 = i2._1

      if(key1._1.compareTo(key2._1) != 0) {
        key1._1.compareTo(key2._1) < 0
      }else {
        if(key1._2 == EventType.INSERT){
          true
        }else if(key2._2 == EventType.INSERT){
          false
        }else {
          true
        }
      }

    }).filterNot(_._1._2 == EventType.DELETE).map(_._1))

  }
}
