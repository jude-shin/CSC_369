package example
object App { // Object means that everything in this is "static" like java

  def sum(a:Int*):Int = {
    var sum = 0;
    for( el <- a){
      sum = sum + el;
    }
    return sum;
  }

  println(sum(2,3,4,5,6));
  def max(x: Int, y: Int): Int = {
    if (x > y) {
      x;
    }

    y;
  }

  def main(args: Array[String]) {
    println("Hello, World!")
    println(sum(5, 5, 5, 5, 10, 15, 5, 20))
  }
}
