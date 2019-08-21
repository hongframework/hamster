import JsonExample.json
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

object ScalaObject {
  def main(args: Array[String]): Unit = {
//    val attrs = Map("a" -> "a1", "b" -> "b1", "c" -> "c1")
//    val defaultAttrs = Map("a" -> "a", "b" -> "b", "c" -> "c", "d" -> "d", "e" -> "f")
//
//    val result = attrs.++(defaultAttrs.filterKeys(!attrs.contains(_)))
//    println(result)
//    println(attrs)
//    println(defaultAttrs)
//
//    print("1".toLong)
//    print(1.toByte)
//    print(1.toString)
//    println(compact(render(result)))
//
//    print(List.concat(List(1,2),List(3, 4)))

    println((1,(2,3)) == (1, 2, 3))
  }
}
object JsonExample extends App {


  case class Winner(id: Long, numbers: List[Int])
  case class Lotto(id: Long, winningNumbers: List[Int], winners: List[Winner], drawDate: Option[java.util.Date])

  val winners = List(Winner(23, List(2, 45, 34, 23, 3, 5)), Winner(54, List(52, 3, 12, 11, 18, 22)))
  val lotto = Lotto(5, List(2, 45, 34, 23, 7, 5, 3), winners, None)

  val json =
    ("lotto" ->
      ("lotto-id" -> lotto.id) ~
        ("winning-numbers" -> lotto.winningNumbers) ~
        ("draw-date" -> lotto.drawDate.map(_.toString)) ~
        ("winners" ->
          lotto.winners.map { w =>
            (("winner-id" -> w.id) ~
              ("numbers" -> w.numbers))}))

  println(compact(render(json)))
}