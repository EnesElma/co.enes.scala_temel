package scalaTemel

import scala.util.Random
object RandomNumbers {
  def main(args: Array[String]): Unit = {

    val r=Random


    println(r.nextInt)

    println(r.nextInt(100))

    println(r.nextFloat())  //0-1 arasında rastgele
    println(r.nextDouble())

    println(r.nextPrintableChar())

    val rString=Random.alphanumeric.take(5)
    println(rString.mkString)
    println(rString.mkString("123"))


    r.setSeed(100)     //her derlemede farklı sayı üretmeyi engeller
    println(r.nextInt)
    println(r.nextFloat())

  }
}
