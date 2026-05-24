package example

case class Record (
  private val id: Int, 
  private val state: String, 
  private val sales: Double
) {

  override def toString: String =
    s"$state, $id, $sales"

  def aggregate(other: Record): Record = {
    // Called assuming that the records have the same id (and thus the same state)
    if (id != other.getId) throw new ArithmeticException("aggregation failed: Record ids do not match")

    return Record(id, state, sales+other.getSales)
  }
}
