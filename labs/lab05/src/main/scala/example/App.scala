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
    val lineItemPath = args("/user/jshin53/input/lineItem")
    val salePath = args("/user/jshin53/input/sale")
    val productPath = args("/user/jshin53/input/product")
    val storePath = args("/user/jshin53/input/store")

    // Get the text inside the paths
    val lineItems = Source.fromFile(lineItemPath).mkString
    val sales = Source.fromFile(salePath).mkString
    val products = Source.fromFile(productPath).mkString
    val stores = Source.fromFile(storePath).mkString

    println(sales)

    // Convert the string to a list delimited by a comma
  }
}
