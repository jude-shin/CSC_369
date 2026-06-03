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
    // Parse the input files
    val studentsRdd = sc.textFile("input/asgn06/students/")
      .map(l => l.split(","))   // Comma delimited
      .map({                    // trim the result and turn into a tuple
        case Array(sid, sname, saddress, sphone) => (sid.trim.toInt, sname.trim)
        case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
      })

    val coursesRdd = sc.textFile("input/asgn06/courses/")
      .map(l => l.split(","))   // Comma delimited
      .map({                    // trim the result and turn into a tuple
        case Array(cname, cdifficulty) => (cname.trim, cdifficulty.trim.toInt)
        case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
      })

    val takenRdd = sc.textFile("input/asgn06/taken/")
      .map(l => l.split(","))   // Comma delimited
      .map({                    // trim the result and turn into a tuple
        case Array(sid, cname, grade) => (cname.trim, sid.trim.toInt)
        case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
      })
   
    // Most difficult course value
    val mostDifficultArray = coursesRdd
      .sortBy(t => (-t._2, t._1))   // Sort starting with the most difficult
      .take(1)  // Take the first element
    
    val mostDifficultValue = mostDifficultArray(0)._2

    // Find the course tuples with that highest difficulty
    val difficultCourses = coursesRdd
      .filter(_._2 == mostDifficultValue)

    // Get all student ids that have at least one of the most difficult courses
    // Join those against the students to get the names
    // Printing each of the names
    difficultCourses
      .join(takenRdd)
      .map({ 
        case (cname, (difficulty, sid)) => (sid, ())
      })
      .distinct
      .join(studentsRdd) //  (sid, ((), sname))
      .collect()
      .foreach({
        case (sid, ((), sname)) => println(sname)
      })
  }

  def q2(sc: SparkContext) = {
    // Parse the input files
    val studentsRdd = sc.textFile("input/asgn06/students/")
      .map(l => l.split(","))   // Comma delimited
      .map({                    // trim the result and turn into a tuple
        case Array(sid, sname, saddress, sphone) => (sid.trim.toInt, sname.trim)
        case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
      })

    val coursesRdd = sc.textFile("input/asgn06/courses/")
      .map(l => l.split(","))   // Comma delimited
      .map({                    // trim the result and turn into a tuple
        case Array(cname, cdifficulty) => (cname.trim, cdifficulty.trim.toInt)
        case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
      })

    val takenRdd = sc.textFile("input/asgn06/taken/")
      .map(l => l.split(","))   // Comma delimited
      .map({                    // trim the result and turn into a tuple
        case Array(sid, cname, grade) => (cname.trim, sid.trim.toInt)
        case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
      })
  
    // Join so the difficulty is included in the courses taken
    val job1 = takenRdd
      .join(coursesRdd)
      .map({
        case (cname, (sid, cdifficulty)) => (sid, cdifficulty)
      })
  
    // Left join with students (ensures all students are covered)
    // Filter out all the Nulls to be 0 as the average difficulty
    val allStudents = studentsRdd
      .leftOuterJoin(job1)
      .map({
        // check for the None from the partial join
        case (sid, (sname, None)) => ((sid, sname), (0.0, 1))
        // convert the original Some() to just a double
        case (sid, (sname, Some(difficulty))) => ((sid, sname), (difficulty.toDouble, 1))
      })

    // Group students by their key sid
    // Get the average by aggregating against all elements
    val studentAverageDifficulties = allStudents
      .reduceByKey((x, y) => (x._1+y._1, x._2+y._2)) // keep track of sum and count
      .mapValues({ case(x, y) => x*1.0/y})  // divide the sum and the count

  
    // Print everything
    studentAverageDifficulties.collect().foreach(println)
  }

  def q3(sc: SparkContext) = {
    // Parse the input files
    val coursesRdd = sc.textFile("input/asgn06/courses/")
      .map(l => l.split(","))   // Comma delimited
      .map({                    // trim the result and turn into a tuple
        case Array(cname, cdifficulty) => (cname.trim, cdifficulty.trim.toInt)
        case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
      })

      // Sort by difficulty descending
      // Take the first 5 from that list (highest difficulty)
      // Only print the name of the course
      val top5 = coursesRdd
        .sortBy(t => (-t._2, t._1))
        .take(5)
        .foreach({ case (cname, cdifficulty) => println(cname) })
  }

  def q4(sc: SparkContext) = {
    // Parse the input files
    val studentsRdd = sc.textFile("input/asgn06/students/")
      .map(l => l.split(","))   // Comma delimited
      .map({                    // trim the result and turn into a tuple
        case Array(sid, sname, saddress, sphone) => (sid.trim.toInt, sname.trim)
        case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
      })

    val takenRdd = sc.textFile("input/asgn06/taken/")
      .map(l => l.split(","))   // Comma delimited
      .map({                    // trim the result and turn into a tuple
        case Array(sid, cname, grade) => (sid.trim.toInt, (lToNGrade(grade.trim), 1.0))
        case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
      })
    
    // Get average gpa for students who have taken courses
    val averageGrade = takenRdd
      .reduceByKey((gr, ct) => (gr._1+gr._2, ct._1+ct._2))
      .mapValues({ case (gr, ct) => gr/ct})

    // Left join, turning None into 0.0 average gpa
    val allGpas = studentsRdd 
      .leftOuterJoin(averageGrade)
      .map({
        case (sid, (sname, None)) => (sname, 0.0)
        case (sid, (sname, Some(average))) => (sname, average.toDouble)
      })

    // Sort by the gpa descending, then sname ascending
    val sortedGpas = allGpas
      .sortBy({ case (sname, average) => (-average, sname) })

    // Print everything
    sortedGpas.collect().foreach(println)
  }

  def lToNGrade(l: String): Double = {
    return l match {
      case "A" => 4
      case "a" => 4
      case "B" => 3
      case "b" => 3
      case "C" => 2
      case "c" => 2
      case "D" => 1
      case "d" => 1
      case "F" => 0
      case "f" => 0
      case _ => Double.NaN
    }
  }

  def main(args: Array[String]) {
    // Don't log a bunch of the junk
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    // Spark setup
    val conf = new SparkConf().setAppName("App")
    val sc = new SparkContext(conf)
    
    // =========================================================================
    // q1(sc)
    // q2(sc)
    // q3(sc)
    q4(sc)
  }
}
