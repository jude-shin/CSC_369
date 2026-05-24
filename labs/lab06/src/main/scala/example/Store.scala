package example

object Store {
  // Takes in the comma seperated input from Store and creates an dataclass
  def parse(input: String): Store = {
    var i = input.split(",").map(_.trim)

    return Store(
      i(0).toInt, 
      i(1), 
      i(2), 
      i(3), 
      i(4),
      i(5), 
      i(6))
  }
}

// storeId, name, address, city, zip, state, phone.
case class Store(
  private val storeId: Int, 
  private val name: String, 
  private val address: String, 
  private val city: String, 
  private val zip: String,
  private val state: String, 
  private val phone: String) {
    override def toString: String =
      s"($storeId, $name, $address, $city, $zip, $state, $phone)"
  }
