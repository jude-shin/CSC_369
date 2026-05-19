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

// TODO: the Record class should store the state as well for convienience
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

    // GROUP BY STORE //
    val storeToRecords: mutable.Map[Int, List[Record]] = mutable.Map[Int, List[Record]]()
    records.foreach(Record(id, name, sale) => storeToRecords+=(id, Record(id, name, sale)))

    // AGGREGATE //
    // each store now has a total sum
    val storeTotal: List[Record] = storeToRecords.map {
      // For each of the stores, sum up their contents, creating a new record
      case (id, records) => 
        Record(id, records.head.getName, records.fold(0)((total, r) => total+r.getSales))
    }

    // =========================================================================
    // Note that at this point we have a list of Records with their total sales
    // There should only be one record for one store

    // SORT //
    // Sort everything, first on state, then on total sale

    // PRINT //
    // print the output


  }
}
