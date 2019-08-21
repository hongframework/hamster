import java.util
import java.util.Arrays

import com.hframework.common.util.RegexUtils
import com.hframework.datacenter.hamster.worker.tasks.select.SqlParser
import com.hframework.datacenter.hamster.worker.tasks.select.SqlParser.SQL_KEY_WORDS
import org.junit.Test

import scala.util.matching.Regex

class RegexSuite {

  @Test
  def test(): Unit ={
    println(RegexUtils.findItem("21号,从北京到上海啊1", "{启程日},从{出发地}到{目的地}啊".replaceAll("(?<=})\\{[^}]+\\}", "(.*)").replaceAll("\\{[^}]+\\}", "(.+)")).mkString(","))
    println(RegexUtils.findItem("从北京到上海啊", "从{出发地}到{目的地}啊".replaceAll("(?<=})\\{[^}]+\\}", "(.*)").replaceAll("\\{[^}]+\\}", "(.+)")).mkString(","))
    println(RegexUtils.findItem("从北京到上海啊", "从{出发地}到{目的地}啊".replaceAll("(?<=})\\{[^}]+\\}", "(.*)").replaceAll("\\{[^}]+\\}", "(.+)")).mkString(","))
    println(RegexUtils.findItem("北京到上海", "从{出发地}到{目的地}".replaceAll("(?<=})\\{[^}]+\\}", "(.*)").replaceAll("\\{[^}]+\\}", "(.+)")).mkString(","))
    println(RegexUtils.findItem("北京去上海111", "{出发地}去{目的地}".replaceAll("(?<=})\\{[^}]+\\}", "(.*)").replaceAll("\\{[^}]+\\}", "(.+)")).mkString(","))
    println(RegexUtils.findItem("到上海", "到{目的地}".replaceAll("\\{[^}]+\\}", "(.+)")).mkString(","))
    println(RegexUtils.find("{启程日},从{出发地}到{目的地}啊", "(?<=\\{)[^}]+(?=\\})").mkString(","))
    println(RegexUtils.findItem("给我来5张星期三早上北京到上海的灰机票", "{人数}张{启程日}{出发地}到{目的地}".replaceAll("(?<=})\\{[^}]+\\}", "(.*)").replaceAll("\\{[^}]+\\}", "(.+)")).mkString(","))
  }

  def findAndPrintln(regex: Regex, sql: String) = {
//    println(s"${regex.regex}|$sql")
    println(s" ==> ${regex.findAllIn(sql).mkString(", ")}")
  }

  @Test
  def test2(): Unit ={
    val sql = "select user_select_id,user_id from (select * from (user_select_where)) b where id > 9 and age < 30 group by g_id having count(1) > 5 order by id limit 10"
    println(sql)
//    println(SqlParser.findOutQuote(sql).mkString("|"))
    println(SqlParser.getOutQuotes(sql).mkString("|"))
    findAndPrintln(SqlParser.SQL_SELECT_R, sql.concat(SqlParser.TERM_STR))
    findAndPrintln(SqlParser.SQL_FROM_R, sql.concat(SqlParser.TERM_STR))
    findAndPrintln(SqlParser.SQL_WHERE_R, sql.concat(SqlParser.TERM_STR))
    findAndPrintln(SqlParser.SQL_ORDER_BY_R, sql.concat(SqlParser.TERM_STR))
    findAndPrintln(SqlParser.SQL_GROUP_BY_R, sql.concat(SqlParser.TERM_STR))
    findAndPrintln(SqlParser.SQL_LIMIT_R, sql.concat(SqlParser.TERM_STR))
  }

  @Test
  def test3(): Unit ={
    val sql = """SELECT
                |  fdl.id          AS "load_id",
                |  fdl.deal_id,
                |  fdl.user_id,
                |  fdl.money,
                |  fdl.create_time,
                |  fdl.is_repay,
                |  fdl.source_type,
                |  fdl.short_alias,
                |  fd.name         AS "deal_name",
                |  fd.repay_time,
                |  fd.rate
                |FROM firstb2b_deal_load_sync fdl
                |  LEFT JOIN firstb2b_deal_sync fd
                |    ON fdl.deal_id = fd.id
                |WHERE fdl.update_time_ BETWEEN '2000-01-01 00:00:00'
                |    AND '2018-01-25 15:48:58'
                |ORDER BY fdl.update_time_, fdl.id_
                |LIMIT 0, 101""".stripMargin.replaceAll("\r\n"," ")
    println(sql)
    println(SqlParser.getOutQuotes(sql).mkString("|"))
    findAndPrintln(SqlParser.SQL_SELECT_R, sql.concat(SqlParser.TERM_STR))
    findAndPrintln(SqlParser.SQL_FROM_R, sql.concat(SqlParser.TERM_STR))
    findAndPrintln(SqlParser.SQL_WHERE_R, sql.concat(SqlParser.TERM_STR))
    findAndPrintln(SqlParser.SQL_ORDER_BY_R, sql.concat(SqlParser.TERM_STR))
    findAndPrintln(SqlParser.SQL_GROUP_BY_R, sql.concat(SqlParser.TERM_STR))
    findAndPrintln(SqlParser.SQL_LIMIT_R, sql.concat(SqlParser.TERM_STR))
  }

}
