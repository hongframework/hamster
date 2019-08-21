import com.hframework.datacenter.hamster.worker.tasks.select.SqlParser
import org.junit.Test

class SqlParserSuite {

  @Test
  def test(): Unit ={
    val sql = "select user_select_id,user_id from (select * from (user_select_where)) b where id > 9 and age < 30 group by g_id having count(1) > 5 order by id limit 10"

    val root = SqlParser.parse(sql)
    println(root)
  }

  @Test
  def test_left_join(): Unit ={
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
                |FROM firstb2b_deal_load_sync as fdl
                |  LEFT JOIN firstb2b_deal_sync as fd
                |    ON fdl.deal_id = fd.id
                |WHERE fdl.update_time_ BETWEEN '2000-01-01 00:00:00'
                |    AND '2018-01-25 15:48:58'
                |ORDER BY fdl.update_time_, fdl.id_
                |LIMIT 0, 101""".stripMargin.replaceAll("\r\n"," ")

    val root = SqlParser.parse(sql)
    root.addWhere("firstb2b_deal_load_sync.update_time_ BETWEEN '2000-01-01 00:00:00' AND '2018-01-25 15:48:58' " +
      "OR  firstb2b_deal_sync.update_time_ BETWEEN '2000-01-01 00:00:00' AND '2018-01-25 15:48:58'")
    root.addSelect("firstb2b_deal_load_sync.update_time_ as \"fdls_update_time_\", firstb2b_deal_sync.update_time_  as \"fds_update_time_\"")
    println(root.sql)
    println(root.runtimeSql)
  }

  @Test
  def test_join(): Unit ={
    val sql = """SELECT
                |  fdl.*
                |FROM firstb2b_deal_load_sync fdl, firstb2b_deal_sync fd
                |ORDER BY fdl.update_time_, fdl.id_
                |LIMIT 0, 101""".stripMargin.replaceAll("\r\n"," ")

    val root = SqlParser.parse(sql)
    root.addWhere("firstb2b_deal_load_sync.update_time_ BETWEEN '2000-01-01 00:00:00' AND '2018-01-25 15:48:58' " +
      "OR  firstb2b_deal_sync.update_time_ BETWEEN '2000-01-01 00:00:00' AND '2018-01-25 15:48:58'")
    root.addSelect("firstb2b_deal_load_sync.update_time_ as \"fdls_update_time_\", firstb2b_deal_sync.update_time_  as \"fds_update_time_\"")
    println(root.sql)
    println(root.runtimeSql)
  }

  @Test
  def test_really(): Unit ={
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
                |    ON fdl.deal_id = fd.id;""".stripMargin.replaceAll("\r\n"," ")

    val root = SqlParser.parse(sql)
    root.addWhere("firstb2b_deal_load_sync.update_time_ BETWEEN '2000-01-01 00:00:00' AND '2018-01-25 15:48:58' " +
      "OR  firstb2b_deal_sync.update_time_ BETWEEN '2000-01-01 00:00:00' AND '2018-01-25 15:48:58'")
    root.addSelect("firstb2b_deal_load_sync.update_time_ as \"fdls_update_time_\", firstb2b_deal_sync.update_time_  as \"fds_update_time_\"")
    println(root.sql)
    println(root.runtimeSql)
  }

}
