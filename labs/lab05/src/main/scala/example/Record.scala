package example

object Record {
  def joinall
  (lineItems: List[LineItem])
  (sales: List[Sale])
  (products: List[Product])
  (stores: List[Store])
  : List[Record] = {

    // Job1: join the lineItems and the sales
    val job1 =
      for {
        LineItem(_, saleId, productId, quantity) <- lineItems 
        Sale(sId, _, _, storeId, _) <- sales
        if saleId == sId
      } yield (storeId, productId, quantity)

    // Job2: Join job1 and the products
    val job2 =
      for {
        (storeId, productId, quantity) <- job1
        Product(pId, _, price) <- products
        if productId == pId
      } yield (storeId, quantity * price)

    // Job3: Join job2 and the stores
    val job3 =
      for {
        (storeId, lineTotal) <- job2
        Store(sId, _, _, _, _, state, _) <- stores
        if storeId == sId
      } yield Record(storeId, state, lineTotal)

    return job3
  }
}

case class Record (
  private val id: Int, 
  private val state: String, 
  private val sales: Double
) {

  override def toString: String =
    s"($id, $state, $sales)"

  def aggregate(other: Record): Record = {
    // Called assuming that the records have the same id (and thus the same state)
    if (id != other.getId) throw new ArithmeticException("aggregation failed: Record ids do not match")

    return Record(id, state, sales+other.getSales)
    
  }
  
  // Getters
  def getId: Int = id 
  def getSales: Int = sales
}
