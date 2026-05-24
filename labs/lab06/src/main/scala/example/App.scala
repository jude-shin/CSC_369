package example

import org.apache.spark.SparkContext._
import scala.io._
import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.spark.rdd._
import org.apache.log4j.Logger
import org.apache.log4j.Level
import scala.collection._

object App {
  def joinall
  (lineItems: List[LineItem])
  (sales: List[Sale])
  (products: List[Product])
  (stores: List[Store])
  : List[Record] = {

    // Job1: join the lineItems and the sales
    val job1 =
      for {
        LineItem(_, saleId, productId, quantity) <- lineItems 
        Sale(sId, _, _, storeId, _) <- sales
        if saleId == sId
      } yield (storeId, productId, quantity)

    // Job2: Join job1 and the products
    val job2 =
      for {
        (storeId, productId, quantity) <- job1
        Product(pId, _, price) <- products
        if productId == pId
      } yield (storeId, quantity * price)

    // Job3: Join job2 and the stores
    val job3 =
      for {
        (storeId, lineTotal) <- job2
        Store(sId, _, _, _, _, state, _) <- stores
        if storeId == sId
      } yield Record(storeId, state, lineTotal)

    return job3
  }


  def main(args: Array[String]) {
    // PARSE INPUTS //
    // Note that this has to be in the project directory
    val lineItemPath = "inputs/lineItem"
    val salePath = "inputs/sale"
    val productPath = "inputs/product"
    val storePath = "inputs/store"

    // Read the text from the source
    // Each line is parsed into a dataclass of the given type (comma delimeted)
    val lineItems: List[LineItem] = Source.fromFile(lineItemPath).getLines.toList.map(LineItem.parse)
    val sales: List[Sale] = Source.fromFile(salePath).getLines.toList.map(Sale.parse)
    val products: List[Product] = Source.fromFile(productPath).getLines.toList.map(Product.parse)
    val stores: List[Store] = Source.fromFile(storePath).getLines.toList.map(Store.parse)

    // =========================================================================

    // JOIN ITEMS //
    var records: List[Record]= Record.joinall(lineItems)(sales)(products)(stores)

    // GROUP BY ID//
    var storeToRecords: Map[Int, List[Record]] = records.groupBy(_.getId)

    // AGGREGATE //
    // each store now has a total sum
    var storeTotal: List[Record] = storeToRecords.map { 
      case (id, rs) =>
        Record(id, rs.head.getState, rs.foldLeft(0.0)((total, r) => total + r.getSales))
    }.toList

    // =========================================================================
    // Note that at this point we have a list of Records with their total sales
    // There should only be one record for one store

    // SORT //
    // Sort everything, first on state, then on total sale, finally by id
    val sorted: List[Record] = storeTotal.sortBy(r => (r.getState, r.getSales, r.getId))

    // PRINT //
    sorted.foreach(println)
  }
}
