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

    // Get the paths from the application arguments
    // Note that this has to be in the project directory
    val lineItemPath = "inputs/lineItem"
    val salePath = "inputs/sale"
    val productPath = "inputs/product"
    val storePath = "inputs/store"

    // Get the text inside the paths and 
    // converts the string to a list delimited by a comma and 
    // trimming the whitespace after
    val lineItems = Source.fromFile(lineItemPath).mkString.split(",").map(_.trim)
    val sales = Source.fromFile(salePath).mkString.split(",").map(_.trim)
    val products = Source.fromFile(productPath).mkString.split(",").map(_.trim)
    val stores = Source.fromFile(storePath).mkString.split(",").map(_.trim)

  }
}
