package scalaTemel

object Functions {
  def main(args: Array[String]): Unit = {

    println(topla(24,25))

    println(carp(13,5))

    ismin("Enes")

    println(sayiTopla(1,3,5,6,7))

    val ikisayiTopla= (x:Int, y:Int) => x + y
    println(ikisayiTopla(4,6))

    println(carpmaVetoplama(3,8))

    println(carpmaVetoplama2(5,9))

    println(carpmaBolme(6,4))

    val (carpX,toplaX)=carpmaVetoplama(2.4,5)
    println(carpX,toplaX)

    val (x,y)=(4,6)
    println(x+y)




  }

  def carpmaBolme(s1:Int,s2:Int):(Double,Double)={
    val carpma= s1 * s2
    val bolme = s1.toDouble / s2.toDouble   //Int to Double casting
    (carpma,bolme)
  }

  def carpmaVetoplama(s1:Double,s2:Double):(Double,Double)={
    val carp:  Double  = s1 * s2
    val topla: Double  = s1 + s2
    (carp,topla)
  }

  def carpmaVetoplama2(s1:Int,s2:Int):(Int,Int)={
    (s1*s2, s1+s2)
  }


  def ismin(x:String): Unit ={
    println(f"Senin adın $x")
  }


  def topla (s1:Int, s2:Int): Int ={
    s1+s2
  }

  def carp(s1:Int, s2:Int): Int={
    s1*s2
  }

  def sayiTopla(args:Int*):Int={
    var toplam=0
    for (i <- args) toplam += i
    toplam
  }


}
