import com.hframework.common.util.SpelExpressionUtils
import org.junit.Test

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.util.Random

class SortBySuite {

  @Test
  def buffer(): Unit ={
    val buffer = Array(26, 25, 28, 27, 30, 29, 32, 31, 34, 33, 36, 35, 39, 37, 40, 42, 41, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 73, 72, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85).toBuffer
    val orderd = buffer.sortBy(_.toInt)
    println(buffer)
    println(orderd)
  }
  @Test
  def test_insert_buffer(): Unit ={
    val buffer = mutable.Buffer.empty[Int]
    for(i <- 0 to 1000) {
      val result = insertProcessAckToBufferAndSorted(buffer, Random.nextInt(100))
      println(s"$buffer: $result")
    }
  }

  def insertProcessAckToBufferAndSorted(buffer: mutable.Buffer[Int], element: Int): Boolean = {
    val bufferLength = buffer.length
    for(i <- 1 to bufferLength) {
      val tempElement = buffer(bufferLength - i)
      if(tempElement < element) {
        buffer.insert(bufferLength - i + 1, element)
        return true
      }else if(tempElement == element){
        return false
      }
    }
    buffer.insert(0, element)
    return true
  }
}
