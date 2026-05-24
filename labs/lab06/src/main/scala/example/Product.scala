package example

object Product {
  // Takes in the comma seperated input from Product and creates an dataclass
  def parse(input: String): Product = {
    var i = input.split(",").map(_.trim)

    return Product(
      i(0).toInt, 
      i(1), 
      i(2).toDouble)
  }
}

// productId, description, price
case class Product(
  private val productId: Int, 
  private val description: String, 
  private val price: Double) {
    override def toString: String =
      s"($productId, $description, $price)"
  }
