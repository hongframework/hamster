import com.hframework.utils.scala.JacksonScalaUtils
import com.hframework.datacenter.hamster.monitor.db._
import org.junit.Test

class MetaDataSuite {

  @Test
  def test(): Unit ={
    val container = JobContainerMeta(
      JobTemplateMeta("data_statistics","数据统计"),
      JobMeta(1L, "user_statistics",""),
      Array(NodeMeta("mysql_binlog", "mysqlbinlog解析","nodeKey" ,"extract","mysql", "java", "com.*****.*.java",
        (Map("1" -> "1","2" -> "2")::Nil)),
        NodeMeta("mysql_binlog", "kafka入库","nodeKey" ,"extract","mysql", "java", "com.*****.*.java",
          (Map("1" -> "1","2" -> "2")::Nil))),
      NodeMeta("mysql_binlog", "mysqlbinlog解析","nodeKey" ,"extract","mysql", "java", "com.*****.*.java",
        (Map("1" -> "1","2" -> "2")::Nil)),
      "selectKey"
    )

    val nodeContaier = JobNodeMeta(container,
      NodeMeta("mysql_binlog", "mysqlbinlog解析","nodeKey" ,"extract","mysql", "java", "com.*****.*.java",
      (Map("1" -> "1","2" -> "2")::Nil)))

    println(container)
    val json = JacksonScalaUtils.toJson(nodeContaier)
    println(json)
    val bean = JacksonScalaUtils.fromJson(json, classOf[JobNodeMeta])
    println(JacksonScalaUtils.toJson(bean))

  }


}
