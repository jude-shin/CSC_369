package example

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
    // Load the data from disk
    val saleInput = ""
    val lineItemInput = ""
    val productInput = ""
    val storeInput = ""

    // Convert the string to a list delimited by a comma


  }
}
