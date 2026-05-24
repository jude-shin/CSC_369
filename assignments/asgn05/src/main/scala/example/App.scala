package example

import org.apache.spark.SparkContext._
import scala.io._
import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.spark.rdd._
import org.apache.log4j.Logger
import org.apache.log4j.Level
import scala.collection._

object App {

  def q1(sc: SparkContext) = {
    // Raw input lines from a text file
    val integersRdd = sc.textFile("input/asgn05/q1_integers")

    // Each integer is parsed individually
    var rdd = integersRdd.flatMap(l => l.split(" ")).map(_.toInt)
  
    // Filter out all those who are divisible by 3
    rdd = rdd.filter(_%3 == 0)
  
    // Creates a map of the element and it's frequency; prints the result
    rdd.countByValue().foreach({
      case (int, ct) => println(s"$int appears $ct times")
      case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
    })
  }

  def q2(sc: SparkContext) = {
    // Raw input lines from a text file
    var employeeRdd = sc.textFile("input/asgn05/q2_employees")
    var departmentRdd = sc.textFile("input/asgn05/q2_departments")

    // Parse the inputs to tuples (String, String)
    employeeRdd = employeeRdd.map(l => l.split(",")(2).trim)
    departmentRdd = departmentRdd.map(l => l.split(",")(2).trim)

    // Cartesian product of the two inputs
    // result in the format ((ename, did), (did, dname))
    var rdd = employeeRdd.cartesian(departmentRdd)
  
    // Filter those who only have the same did (basically a join on did)
    rdd = rdd.filter({
      // ((ename, did), (did2, dname))
      // empl._2 and dept._1 is the did
      case (empl, dept) => empl._2 == dept._1
      case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
    })
  
    // Creates a list of all those joined; prints the output
    rdd.collect().foreach({
      // ((ename, did), (did2, dname))
      // empl._1 is the employee name
      // dept._2 is the department name
      case (empl, dept) => println(s"$empl._1, $dept._2")
      case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
    })
  }

  // def q3(sc: SparkContext) = {
  //   // Raw input lines from a text file
  //   val students = sc.textFile("input/asgn05/q3_students")
  // 
  //   // Parse each line
  //   // result in the format of (name, id, gradeCourses)
  //   var rdd = students.map(l => l.split(",", 3))

  //   // Parse out only the letter grade from the list of students
  //   rdd = rdd.map({
  //     // Split every grade based on the comma
  //     // Extract the grade letter for every course (the first character of the grade course pair)
  //     // Calculate the gpa based on those letters
  //     // result in the format of (name, id, grades)
  //     case Array(name, id, gradeCourses) => 
  //       (name, id, getGpa(gradeCourses.split(",").map(_.trim).map(_.substring(0, 1))))
  //     case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
  //   })

  //   // Print the result
  //   rdd.collect().foreach({
  //     // TODO: why is this an array?
  //     case Array(name, id, gpa) =>
  //       println(s"$name, $id, $gpa")
  //   })
  // }
  // 
  // // Returns the average GPA from a list of letter grades
  // def getGpa(gs: String*): Double = {
  //   // Maps all the letters to a numerical value, then sums, then divides by the 
  //   // number of strings that were given
  //   return gs.map(x => lToNGrade(x)).fold(0.0)({(x, y) => x+y}) / gs.length
  // }

  // def lToNGrade(l: String): Double = {
  //   return l match {
  //     case "A" => 4
  //     case "a" => 4
  //     case "B" => 3
  //     case "b" => 3
  //     case "C" => 2
  //     case "c" => 2
  //     case "D" => 1
  //     case "d" => 1
  //     case "F" => 0
  //     case "f" => 0
  //     case _ => Double.NaN
  //   }
  // }

  def main(args: Array[String]) {
    // Don't log a bunch of the junk
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    // Spark setup
    val conf = new SparkConf().setAppName("App")
    val sc = new SparkContext(conf)
    
    // =========================================================================
    // q1(sc)
    q2(sc)
    // q3(sc)
  }
}
