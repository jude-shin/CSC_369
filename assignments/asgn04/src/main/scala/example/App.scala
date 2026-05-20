package example

import scala.io.Source

object App {
  def q1() = {
    val intsPath = "inputs/q1"
    val ints: List[LineItem] = Source.fromFile(intsPath).getLines.toList
    ints.flatmap(_.split(" ").toList).filter(x => x.asInstanceOf[Int]%3 == 0).foreach(println)
  }

  def q2() = {

  }

  def q3() = {

  }

  def main(args: Array[String]) {
  }
}
