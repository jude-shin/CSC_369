package example

import scala.io.Source
import scala.collection.mutable

object App {
  def q1() = {
    val inputPath: String= "inputs/q1"
    
    // Convert each line to an element in a list
    val ints: List[String] = Source.fromFile(inputPath).getLines.toList

    // Each line is flattened into a 1D array of integers
    // Then that list is counted with a filtering condition (divisible by 3)
    val count: Int = ints.flatMap(_.split(" ").toList).count(_.toInt%3 == 0)
  
    // Print the result
    println(count)
  }

  def q2() = {
    val inputPath: String= "inputs/q2"
    
    // 2D array (the second array [0] is the date, and [1] is the temperature)
    val dateTemps = Source.fromFile(inputPath).getLines.toList.map(_.split(" ").toList)

    // Map the temperatures to the dates
    val dates = mutable.Map[String, String]();
    for (dateTemp <- dateTemps) {
      val d: String = dateTemp(0)
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
    dates.foreach(println)
  }

  def q3() = {
    val inputPath: String= "inputs/q3"
    
    // 2D array 
    // Nested array second array 
    //  [0]: name
    //  [1]: id
    //  [2]: grade
    //  [3]: course
    // Each line has the previous information
    val lines = Source.fromFile(inputPath).getLines.toList.map(_.split(", ").toList)

    // Map the (grade, course) tuples to a given student (name, id)
    val students = mutable.Map[(String, Int), List[(String, String)]]();

    for (line <- lines) {
      // Student tuples will be name, id pairs
      val student: (String, Int) = (line(0), line(1).toInt)
      val course: (String, String) = (line(2), line(3))

      // If the key is already in the map
      if (students.contains(student)) {
        students(student) = course :: students(student)
      }
      else { // Otherwise, create a new list
        students += (student -> List(course))
      }
    }
    
    // Print every student and the courses that they have taken
    for (student <- students) {
      println(s"${student._1._1}, ${student._1._2} ${student._2}")
    }
  }

  def main(args: Array[String]) {
    // q1()
    // q2()
    q3()
  }
}
