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
    val studentsRdd = sc.textFile("input/asgn06/students/")
      .flatMap(l => l.split(","))   // Comma delimited
      .map({                    // trim the result and turn into a tuple
        case Array(sid, sname, saddress, sphone) => (sid.trim, sname.trim, saddress.trim, sphone.trim)
        case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
      })

    val studentsRdd = sc.textFile("input/asgn06/cousrses/")
      .flatMap(l => l.split(","))   // Comma delimited
      .map({                    // trim the result and turn into a tuple
        case Array(cname, cdifficulty) => (cname.trim, cdifficulty.trim)
        case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
      })

    val studentsRdd = sc.textFile("input/asgn06/taken/")
      .flatMap(l => l.split(","))   // Comma delimited
      .map({                    // trim the result and turn into a tuple
        case Array(sid, cname, grade) => (sid.trim, cname.trim, grade)
        case _ => throw new IllegalArgumentException("You are the problem... You should never be here!")
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
}
