package example

import org.apache.spark.SparkContext._
import scala.io._
import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.spark.rdd._
import org.apache.log4j.Logger
import org.apache.log4j.Level
import scala.collection._

object App {
  def main(args: Array[String]) {
    // Don't log a bunch of the junk
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    // Spark setup
    val conf = new SparkConf().setAppName("App")
    val sc = new SparkContext(conf)

    // Parse input files into RDDs
    // val lineItemPath = "/user/jshin53/input/lineItem/lineItem"
    val lineItemPath = "input/lineItem/lineItem"
    val salePath = "input/sale/sale"
    val productPath = "input/product/product"
    val storePath = "input/store/store"

    // (_, saleId, productId, quantity)
    val lineItems = sc.textFile(lineItemPath).map(l => l.split(",").map(_.trim))
    // (saleId, _, _, storeId, _) 
    val sales = sc.textFile(salePath).map(l => l.split(",").map(_.trim))
    // (productId, _, price)
    val products = sc.textFile(productPath).map(l => l.split(",").map(_.trim))
    // (storeId, _, _, _, _, state, _)
    val stores = sc.textFile(storePath).map(l => l.split(",").map(_.trim))

    // =========================================================================

    // JOINS

    // job1: join the lineItems and the sales on the saleId 
    // structure: (productId, (saleId, quantity, storeId))
    val lineItemTuple = lineItems.map({
      case Array(_, saleId, productId, quantity) => (saleId, (productId, quantity))
    })

    val saleTuple = sales.map({
      case Array(saleId, _, _, storeId, _) => (saleId, storeId)
    })
    val job1Tuple = lineItemTuple.join(saleTuple).map({
      case (saleId, ((productId, quantity), storeId)) => 
        (productId, (saleId, quantity, storeId))
    })

    // =========================================================================k

    // job2: join job1 and the products on the productId
    // structure: (storeId, (saleId, productId, quantity, price))
    val productTuple = products.map({
      case Array(productId, _, price) => (productId, price)
    })
    val job2Tuple = job1Tuple.join(productTuple).map({
      case (productId, ((saleId, quantity, storeId), price)) => 
        (storeId, (saleId, productId, quantity, price))
    })

    // =========================================================================k
    // job3: join job2 and the store on the storeId
    // structure: (saleId, productId, quantity, storeId, price, state)
    val storeTuple = products.map({
      case Array(storeId, _, _, _, _, state, _) => (storeId, state)
    })
    val joined = job2Tuple.join(storeTuple).map({
      case (storeId, ((saleId, productId, quantity, price), state)) => 
        (storeId, saleId, productId, quantity, price, state)
    })


    // // GROUP BY ID//
    // var storeToRecords: Map[Int, List[Record]] = records.groupBy(_.getId)

    // // AGGREGATE //
    // // each store now has a total sum
    // var storeTotal: List[Record] = storeToRecords.map { 
    //   case (id, rs) =>
    //     Record(id, rs.head.getState, rs.foldLeft(0.0)((total, r) => total + r.getSales))
    // }.toList

    // // =========================================================================
    // // Note that at this point we have a list of Records with their total sales
    // // There should only be one record for one store

    // // SORT //
    // // Sort everything, first on state, then on total sale, finally by id
    // val sorted: List[Record] = storeTotal.sortBy(r => (r.getState, r.getSales, r.getId))

    // // PRINT //
    joined.collect().foreach(println)
  }
}
