package example

import scala.io.Source

/*
Job 1: join everything together to get proceeds for each store in history
(((sale <leftjoin> lineItem) <leftjoin> product) <leftjoin> store)

Job 2:
  mapper: parse (grouping and partitioning as well) upon ((State, storeId), price)
  combiner: aggregate everything
  reducer: aggregate everything again

*/
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
    var lineItemLines = Source.fromFile(lineItemPath).getLines.toList.lineItemLines.map(LineItem.parse)
    var saleLines = Source.fromFile(salePath).getLines.toList.saleLines.map(Sale.parse)
    var productLines = Source.fromFile(productPath).getLines.toList.productLines.map(Product.parse)
    var storeLines = Source.fromFile(storePath).getLines.toList.storeLines.map(Store.parse)

    // =========================================================================

    // JOIN ITEMS //

    // CREATE RECORDS //
    // Toss all of the information we just gathered into a record?

    // AGGREGATE THE 
    // Aggregate all of the sales for each store

    // TREEMAP //
    // The key is going to be the state

  }
}
