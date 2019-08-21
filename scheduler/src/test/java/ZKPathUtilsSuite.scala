import com.hframework.datacenter.hamster.zookeeper.ZKPathUtils._

object ZKPathUtilsSuite {
  def main(args: Array[String]): Unit = {
    println(s"getRootPath: [ ${getRootPath} ]")
    println(s"getConfigRootPath: [ ${getConfigRootPath} ]")

    println(s"getJobsConfigRootPath: [ ${getJobsConfigRootPath} ]")
    println(s"getJobConfigPath: [ ${getJobConfigPath("jg1","joba")} ]")
    println(s"getJobNodeConfigPath: [ ${getJobNodeConfigPath("jg1", "joba","extract")} ]")

    println(s"getJobGroupsConfigRootPath: [ ${getJobGroupsConfigRootPath} ]")
    println(s"getJobGroupConfigPath: [ ${getJobGroupConfigPath("jobgroupa")} ]")
    println(s"getJobGroupJobConfigPath: [ ${getJobGroupJobConfigPath("jobgroupa","joba")} ]")
  }

}
