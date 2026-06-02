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
    val mostDifficultValue: Int = coursesRdd
      .sort(t => (-t._2, t._1))   // Sort starting with the most difficult
      .take(1)  // Take the first element
      (0)._2    // From that (only) element, get the difficulty number

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
  }

  def q3(sc: SparkContext) = {
  }

  def q4(sc: SparkContext) = {
  }

  def main(args: Array[String]) {
    // Don't log a bunch of the junk
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    // Spark setup
    val conf = new SparkConf().setAppName("App")
    val sc = new SparkContext(conf)
    
    // =========================================================================
    q1(sc)
    // q2(sc)
    // q3(sc)
    // q4(sc)
  }



    // // Parse the input files
    // val studentsRdd = sc.textFile("input/asgn06/students/")
    //   .map(l => l.split(","))   // Comma delimited
    //   .map({                    // trim the result and turn into a tuple
    //     case Array(sid, sname, saddress, sphone) => (sid.trim, sname.trim, saddress.trim, sphone.trim)
    //     case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
    //   })

    // val coursesRdd = sc.textFile("input/asgn06/courses/")
    //   .map(l => l.split(","))   // Comma delimited
    //   .map({                    // trim the result and turn into a tuple
    //     case Array(cname, cdifficulty) => (cname.trim, cdifficulty.trim.toInt)
    //     case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
    //   })

    // val takenRdd = sc.textFile("input/asgn06/taken/")
    //   .map(l => l.split(","))   // Comma delimited
    //   .map({                    // trim the result and turn into a tuple
    //     case Array(sid, cname, grade) => (sid.trim, cname.trim, grade)
    //     case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
    //   })


}
