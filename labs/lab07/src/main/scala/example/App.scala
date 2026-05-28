package example

import org.apache.spark.SparkContext._
import scala.io._
import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.spark.rdd._
import org.apache.log4j.Logger
import org.apache.log4j.Level
import scala.collection._

// TODO: all you have to do is sort everything now!

object App {
  def main(args: Array[String]) {
    // Don't log a bunch of the junk
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    // Spark setup
    val conf = new SparkConf().setAppName("App")
    val sc = new SparkContext(conf)

    // Parse input files into RDDs
    // /user/jshin53/input/...
    val lineItemPath = "input/lineItem/lineItem"
    val salePath = "input/sale/sale"
    val productPath = "input/product/product"
    val storePath = "input/store/store"

    // (_, saleId, productId, quantity)
    val lineItems = sc.textFile(lineItemPath).map(l => l.split(",").map(_.trim))
    // (saleId, date, _, storeId, _) 
    val sales = sc.textFile(salePath).map(l => l.split(",").map(_.trim))
    // (productId, _, price)
    val products = sc.textFile(productPath).map(l => l.split(",").map(_.trim))
    // (storeId, name, _, city, _, state, _)
    val stores = sc.textFile(storePath).map(l => l.split(",").map(_.trim))

    // =========================================================================

    // JOIN ALL OF THE FILES
    // job1: join the lineItems and the sales on the saleId 
    // structure: (productId, (saleId, quantity, storeId))
    val lineItemTuple = lineItems
      .map({
        case Array(_, saleId, productId, quantity) => (saleId, (productId, quantity))
        case badrow => ("", ("", ""))
      })
      .filter(_._1 != "")
    val saleTuple = sales
      .map({
        case Array(saleId, date, _, storeId, _) => (saleId, (storeId, date))
        case badrow => ("", ("", ""))
      })
      .filter(_._1 != "")
    val job1Tuple = lineItemTuple.join(saleTuple)
      .map({
        case (saleId, ((productId, quantity), (storeId, date))) => 
          (productId, (saleId, quantity, storeId, date))
      })

    // job2: join job1 and the products on the productId
    // structure: (storeId, (saleId, productId, quantity, price))
    val productTuple = products
      .map({
        case Array(productId, _, price) => (productId, price)
        case badrow => ("", "")
      })
      .filter(_._1 != "")
    val job2Tuple = job1Tuple.join(productTuple)
      .map({
        case (productId, ((saleId, quantity, storeId, date), price)) => 
          (storeId, (saleId, productId, quantity, date, price))
      })

    // job3: join job2 and the store on the storeId
    // structure: (storeId, saleId, productId, total, state)
    val storeTuple = stores
      .map({
        case Array(storeId, name, _, city, _, state, _) => (storeId, (name, city, state))
        case badrow => ("", ("", "", ""))
      })
      .filter(_._1 != "")
    val joined = job2Tuple.join(storeTuple)
      .map({
        case (storeId, ((saleId, productId, quantity, date, price), (name, city, state))) => 
          (storeId, saleId, productId, quantity, date, price, name, city, state)
      })
      .map({
        case (storeId, saleId, productId, quantity, date, price, name, city, state) =>
          val total = quantity.toInt * price.toDouble
          val month = date.split("/")(1)

          // NOTE: we don't have to keep track of the id's (we only care abt
          // the date, the total for that transaction, the name, city, and state)
          ((month, storeId), (name, city, total))
      })
    

    // =========================================================================k
    // SUM MONTH PER STORE //
    // For each month, there is a list of stores and their revenue for a date in that month
    // In each month, sum the total per store. 
    var monthTotals = joined 
      .reduceByKey({
        case ((name1, city1, total1), (name2, city2, total2)) => (name1, city1, total1+total2)
      })
  
    // Convert it to something that prints nicely
    var result = monthTotals
      .map({
        case ((month, storeId), (name, city, total)) => (month, (name, city, total))
      }).groupByKey()
  
    // Sort by the key ascending, and then secondary sort by the revenue
    var sorted = result 
      .sortByKey()  // acendding by defualt
      .mapValues(_.sortBy(_._3).reverse.take(10))   // _._3 is the total revenue (decending)
      .collect()
      .foreach(println)
  }
}
