package scalaTemel

object Loops {
  def main(args: Array[String]): Unit = {

    var i=0
    while (i<5){
      println(i)
      i +=1
    }

    for(i <- 0 to 10 by 2){
      println(i)
    }

    val kelime= "scala"

    for(i <- 0 until kelime.length){
      print(kelime(i))
    }

    val myArray=Array("bir","iki",3,4,"beş")

    for (i <- myArray){
      print(i+" ")
    }


    val tekSayilar=for{ i <- 1 to 30 if(i%2==1) }yield i

    tekSayilar.foreach(print)

    val meyveler=List("elma","ayva","nar")

    meyveler.foreach( x => {println(x.toUpperCase())})
  }
}
