package example

object Record {
  // TODO: parse into comparable strings, ints, and doubles and other
}

case class Record (
  val id: Int, 
  val state: String, 
  val sales: Double
) {

  override def toString: String =
    s"$state, $id, $sales"

  def aggregate(other: Record): Record = {
    // Called assuming that the records have the same id (and thus the same state)
    if (id != other.id) throw new ArithmeticException("aggregation failed: Record ids do not match")

    return Record(id, state, sales+other.id)
  }
}
