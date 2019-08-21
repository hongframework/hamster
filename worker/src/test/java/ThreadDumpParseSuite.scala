import com.hframework.common.util.file.FileUtils
import org.junit.Test

import scala.collection.JavaConverters._
class ThreadDumpParseSuite {

  def readFile(fileName: String): Array[String] = {
    val resource = this.getClass.getClassLoader.getResource(fileName)
    val context = FileUtils.readFileToArray(resource.getPath)
    context.asScala.toArray
  }

  def groupThread(fileName: String): Map[String, List[Array[String]]] = {
    val rows = readFile(fileName).drop(3)
    var fromIndex = 0
    var threadIndexs = collection.mutable.Buffer.empty[(Int, Int)]
    for (rowAndIndex <- rows.zipWithIndex) {
      if(rowAndIndex._1.trim.isEmpty){
        threadIndexs = threadIndexs.+:(fromIndex, rowAndIndex._2)
        fromIndex = rowAndIndex._2 + 1
      }
    }

    val threads = threadIndexs.map(indexs => rows.slice(indexs._1, indexs._2)).toList
    threads.groupBy(thread => {
      if(thread.size > 1){
        thread(1)
      }else {
        "Default"
      }
    })
  }

  def pruneGroup(fileName: String): Map[String, List[Array[String]]] = {
    val groupThreads = groupThread("js_90.log")
    val pgt = groupThreads.map(gt => gt._1 -> gt._2.map(thread => {
      val type1 = if(gt._1.indexOf(":") > 0) gt._1.substring(gt._1.indexOf(":")) else gt._1
      val newHead = thread(0).substring(0, thread(0).lastIndexOf("\"") + 1) + type1
      if(thread.size >2) {
        thread.drop(2).filterNot(row => row.contains("<")).+:(newHead)
      }else {
        thread
      }
    })).map(gt => gt._1 -> gt._2.filter(thread => thread.find(row => row.contains("com.hframe")).isDefined))
    pgt
  }

  @Test
  def printContent = {
    println(readFile("js_90.log").mkString("\n"))
  }
  @Test
  def groupField = {
    val groupThreads = groupThread("js_90.log")
    for (groupThread <- groupThreads) {
      println(s"----${groupThread._1}-----")
      for (thread <- groupThread._2) {
        println(thread.mkString("\n"))
        println()
      }
    }
    println(groupThreads.values.map(_.size).reduce(_ + _))
  }

  @Test
  def pruneGroupTest = {
    val pgt = pruneGroup("js_90.log")
    for (groupThread <- pgt) {
      if(groupThread._1 != "Default") {
        println(s"----${groupThread._1}-----")
        for (thread <- groupThread._2) {
          println(thread.mkString("\n"))
          println()
        }
      }
    }
  }

  @Test
  def pruneGroupCountTest = {
    val pgt = pruneGroup("js_90.log")
    val pgtCnt = pgt.filter(_._1 != "Default").map(groupThread => {
      val threads = groupThread._2
      val threadsCount = threads.groupBy(thread => thread.mkString("\n")).map(entry => entry._1 ->  entry._2.size)
      groupThread._1 -> threadsCount
    })
    for (groupThread <- pgtCnt) {
      println(s"----${groupThread._1}-----")
      val threads = groupThread._2
      val threadsCount = groupThread._2
      for (thread <- threadsCount) {
        println(s"(${thread._2})${thread._1}")
        println()
      }
    }
    println("=============WARN===============")
    for (groupThread <- pgtCnt) {
      val threadsCount = groupThread._2
      for (thread <- threadsCount.filter(_._2 > 1)) {
        println(s"(${thread._2})${thread._1}")
        println()
      }
    }
  }

}
