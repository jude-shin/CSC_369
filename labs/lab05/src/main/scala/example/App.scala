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
    val lineItems = Source.fromFile(lineItemPath).getLines.toList.lineItemLines.map(LineItem.parse)
    val sales = Source.fromFile(salePath).getLines.toList.saleLines.map(Sale.parse)
    val products = Source.fromFile(productPath).getLines.toList.productLines.map(Product.parse)
    val stores = Source.fromFile(storePath).getLines.toList.storeLines.map(Store.parse)

    // =========================================================================

    // JOIN ITEMS //
    var records = Record.joinall(lineItems)(sales)(products)(stores)

    // GROUP //
    // Group all of the records into a (list of (list of records))
    
    // AGGREGATE //
    // if the id is the same, then add the total and return a new one
    // TODO: this might be the .reduce function? 

    // SORT //
    // Sort everything

    // PRINT //
    // print the output


  }
}
