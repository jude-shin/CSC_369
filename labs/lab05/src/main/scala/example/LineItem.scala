package example

object LineItem {
  // Takes in the comma seperated input from LineItem and creates an dataclass
  def parse(input: String): LineItem = {
    var i = input.split(",").map(_.trim)

    return LineItem(
      i(0).toInt, 
      i(1).toInt, 
      i(2).toInt, 
      i(3).toInt)
  }
}

// lineItemId, saleId, productId, quantity
case class LineItem(
  lineItemId: Int, 
  saleId: Int, 
  productId: Int, 
  quantity: Int) {
    override def toString: String =
      s"($lineItemId, $saleId, $productId, $quantity)"

  }
