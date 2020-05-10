package scalaTemel

import scala.collection.mutable.ArrayBuffer

object Arrays {
  def main(args: Array[String]): Unit = {



    val sayiArray=new Array[Int](20)
    sayiArray(10)=120
    sayiArray.foreach(println)

    var index=0
    sayiArray.foreach(x => {
      if(x==120) println(index)
      else index += 1
    })



    val insanlar=Array("ali","osman")
    val yeniinsanlar= insanlar ++ Array("enes")

    yeniinsanlar.foreach(x => {
      println(x)
    })


    println("\n")
    val meyveler=ArrayBuffer[String]("elma","armut")
    meyveler.insert(0,"nar")
    meyveler.insert(2,"Muz")
    meyveler.insert(1,"Ananas","kiraz")
    meyveler += "portakal"
    meyveler ++= Array("çilek","karpuz")
    meyveler.foreach(println)

    println("\n")

    var meyveBuyuk= for(i <- meyveler) yield i.toUpperCase()

    meyveBuyuk.foreach(println)




  }
}
