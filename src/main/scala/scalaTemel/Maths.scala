package scalaTemel

import scala.math._


object Maths {
  def main(args: Array[String]): Unit = {

    var num=100
    num +=1
    println(num)

    num *=3
    println(num)

    num=abs(-50)
    println(num)

    println(sqrt(16))
    println(pow(5,2))
    println(exp(0))

    println(round(2.54))
    println(floor(3.69))
    println(ceil(5.34))

    println(log(10))
    println(log10(10))
    
    println(min(4,6))
    println(max(3,98))

  }
}
