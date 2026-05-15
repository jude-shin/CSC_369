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

    // Get the text inside the paths and 
    // converts the string to a list delimited by a comma and 
    // trimming the whitespace after
    val lineItemLines = Source.fromFile(lineItemPath).getLines.toList
    val saleLines = Source.fromFile(salePath).getLines.toList
    val productLines = Source.fromFile(productPath).getLines.toList
    val storeLines = Source.fromFile(storePath).getLines.toList

    // =========================================================================

    // PARSE ITEMS //
    // Each is a list of each respective dataclass
    lineItemLines.map(LineItem.parse)
    saleLines.map(Sale.parse)
    productLines.map(Product.parse)
    storeLines.map(Store.parse)

    // JOIN ITEMS //

    // CREATE RECORDS //
    // Toss all of the information we just gathered into a record?

    // AGGREGATE THE 
    // Aggregate all of the sales for each store

    // TREEMAP //
    // The key is going to be the state

  }
}
