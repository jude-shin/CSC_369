package example

import scala.io.Source

object App {
  def q1() = {
    val intsPath: String= "inputs/q1"
    val ints: List[String] = Source.fromFile(intsPath).getLines.toList
    val res: Int = ints.flatMap(_.split(" ").toList).count(_.toInt%3 == 0)

    println(res)
  }

  def q2() = {

  }

  def q3() = {

  }

  def main(args: Array[String]) {
    q1()
  }
}
