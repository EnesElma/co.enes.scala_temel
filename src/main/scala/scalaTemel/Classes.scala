package scalaTemel


object Classes {
  def main(args: Array[String]): Unit = {
    val selam=new Selamlama("Merhaba");
    selam.soyle("Fahrettin")



    /**************** Case Class ************/

    case class Point(x:Int , y:Int)
    val point1=Point(1,2)
    val point2=Point(2,3)
    if(point1==point2) println("Aynı") else println("Farklı")

    case class Ogrenci(isim:String, not:Float)
    val ogrenci=Ogrenci("Selami",78.4F)
    println(s"${ogrenci.isim}'nin notu ${ogrenci.not}")

    case class Kisi(isim:String="Latif", yas:Int=99, boy:Int=230)   //Default deger atama
    val kisi1=Kisi();
    val kisi2=Kisi("emrah",28)
    println(s"${kisi1.isim}  ${kisi1.yas}  ${kisi1.boy}")
    println(s"${kisi2.isim}  ${kisi2.yas}  ${kisi2.boy}")

  }
}
