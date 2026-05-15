package example

import scala.collection._

object Record {
  def joinall
  (lineItems: scala.collection.immutable.Set[LineItem])
  (sales: scala.collection.immutable.Set[Sale])
  (products: scala.collection.immutable.Set[Product])
  (stores: scala.collection.immutable.Set[Store])
  : scala.collection.immutable.Set[Record] = {

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
  }

}

case class Record(
  private val storeId: Int, 
  private val storeState: String, 
  private val totalSales: Double
) {
  override def toString: String =
    s"($storeId, $storeState, $totalSales)"
}
