import com.hframework.common.util.SpelExpressionUtils
import com.hframework.datacenter.hamster.worker.SPELUtil
import org.junit.Test
import org.springframework.expression.spel.standard.SpelExpressionParser

import scala.collection.JavaConverters._

class SpelExpressionUtilsSuite {

  @Test
  def test: Unit = {
    var express = "add_time != orig.add_time &&  !(1>2) && '2015-12-12 12:12:21' > '2015-12-12 12:12:12' "
    println(SpelExpressionUtils.check(express,
      Map("add_time" -> "2015-12-12 12:12:12",
        "orig.add_time" -> "2015-12-12 12:12:11")
        .asInstanceOf[Map[String, Object]].asJava))
    println(SpelExpressionUtils.check(express,
      Map("add_time" -> "2015-12-12 12:12:12",
        "orig.add_time" -> "2015-12-12 12:12:12")
        .asInstanceOf[Map[String, Object]].asJava))
    println(SpelExpressionUtils.execute("count * 3", Map("count" -> 10)
      .asInstanceOf[Map[String, Object]].asJava))

    val parser = new SpelExpressionParser
    express = "1 /100.0"
    println(parser.parseExpression(express).getValue(classOf[String]))

    //    express = "${create_time + 8 * 3600}".trim
    express = "${a1 + a2 + abcde + abc + abcd}".trim
    val parseOp = SPELUtil.parseExpress(express)
    if(parseOp.isDefined) {
      println(SPELUtil.invokeExpress(parseOp.get._1, parseOp.get._2, Map("a1" -> "1","a2" -> "1","abcde" -> "1","abc" -> "1","abcd" -> "1")))
    }

    express = "175 /100.0"
    println(parser.parseExpression(express).getValue())

    val updateSql =
      s"""update t
         |set
         |where source_key_ =
         |1 "
         |""".stripMargin
    println(updateSql.replaceAll("\r\n"," "))
  }




}
