package example

import scala.io.Source
import scala.collection.mutable

object App {
  def q1() = {
    val inputPath: String= "inputs/q1"
    val ints: List[String] = Source.fromFile(intsPath).getLines.toList
    val count: Int = ints.flatMap(_.split(" ").toList).count(_.toInt%3 == 0)

    println(count)
  }

  def q2() = {
    val inputPath: String= "inputs/q2"
    
    // 2D array (the second array [0] is the date, and [1] is the temperature)
    val dateTemps = Source.fromFile(inputPath).getLines.toList.map(_.split(" ").toList)

    // Map the temperatures to the dates
    val dates = mutable.Map[String, String]();
    for (dateTemp <- dateTemps) {
      d: String = dateTemp(0)
      // If the key is already in the map
      if (dates.contains(d)) {
        if (dateTemp(1) > dates(d)) {
          dates(d) = dateTemp(1)
        }
      }
      else {
        dates += (d -> dateTemp(1))
      }
    }

    // Print all of the key-value pairs
    dateTemps.foreach(println)
  }

  def q3() = {

  }

  def main(args: Array[String]) {
    // q1()
    q2()
  }
}
