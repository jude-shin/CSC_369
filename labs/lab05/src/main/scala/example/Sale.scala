package example

object Sale {
  // Takes in the comma seperated input from Sale and creates an dataclass
  def parse(input: String): Sale = {
    var i = input.split(",").map(_.trim)

    return Sale(
      i(0).toInt, 
      i(1), 
      i(2), 
      i(3).toInt, 
      i(4).toInt)
  }
}

// saleId, date, time, storeId, customerId
case class Sale(
  private saleId: Int, 
  private date: String, 
  private time: String, 
  private storeId: Int, 
  private customerId: Int) {
    override def toString: String =
      s"($saleId, $date, $time, $storeId, $customerId)"
  }
