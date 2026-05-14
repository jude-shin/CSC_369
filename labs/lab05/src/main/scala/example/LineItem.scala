package example

object LineItem {
  // Takes in the comma seperated input from LineItem and creates an dataclass
  def parse(input: String): LineItem = {
    var i = input.split(",").map(_.trim).map(_.toInt)

    return LineItem(i(0), i(1), i(2), i(3))
  }
}

// lineItemId, saleId, productId, quantity
case class LineItem(lineItemId: Int, saleId: Int, productId: Int, quantity: Int) {
  override def toString(): String = {
    return "(" + lineItemId + ", " + saleId + ", " + productId + ", " + quantity + ")"
  }
}
