package example
object App { // Object means that everything in this is "static" like java
  def max(x: Int, y: Int): Int = {
    if (x > y) {
      x;
    }

    y;
  }

  def main(args: Array[String]) {
    println("Hello, World!")
    println(max(3, 5))
  }
}
