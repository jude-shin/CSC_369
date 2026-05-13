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

    // Get the paths from the application arguments
    val lineItemPath = args(0)
    val salePath = args(1)
    val productPath = args(2)
    val storePath = args(3)


    // Convert the string to a list delimited by a comma
  }
}
