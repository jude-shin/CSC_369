package example

import scala.io.Source
import scala.collection.mutable.Map

object App {
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
    var storeToRecords: Map[Int, List[Record]] = records.groupby(_.getId)

    // AGGREGATE //
    // each store now has a total sum
    var storeTotal: List[Record] = storeToRecords.map {
      // For each of the stores, sum up their contents, creating a new record
      case (id: Double, rs: List[Record]) => 
        Record(id, rs.head.getState, rs.fold(0.0)((total: Double, r: Record) => total+r.getSales))
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
