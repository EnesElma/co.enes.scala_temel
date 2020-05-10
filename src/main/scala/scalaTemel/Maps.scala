package scalaTemel



object Maps {
  def main(args: Array[String]): Unit = {

    val ulkeBaskent= Map("Japonya" ->"Tokyo",
      "Hindistan"->"Delhi","Türkiye"->"Ankara")
    println(ulkeBaskent)

    val ulkebaskent2= Map(("Tr","Ankara"),("Abd","Washington"))
    println(ulkebaskent2)

    val key="Türkiye"
    if(ulkeBaskent.contains(key)) println(ulkeBaskent(key))
    else println(s"$key ülkesi bulunamadı")

    //ulkeBaskent("Almanya")="Berlin"  Map immutable olduğu için bu kod hata verir


    //Mutable map örneği;
    val ulkeBaskentMut= collection.mutable.Map("Japonya" ->"Tokyo",
      "Hindistan"->"Delhi","Türkiye"->"Ankara")

    ulkeBaskentMut += ("ispanya" -> "Madrid")
    ulkeBaskentMut("Almanya")="Berlin"


    println("\n")

    ulkeBaskentMut.foreach(x =>{
      println(s"${x._1} --- ${x._2} ")
      //_1 Keyler için kullanılır _2 değerler için kullanılır
    })


    println("\n")

    val ogrenciler= collection.mutable.Map(1302->"Enes"
      ,145->"Berkay",1456->"Mazhar")

    ogrenciler(1983)="Celal"

    println(ogrenciler)
    //println(ogrenciler(1000)) 1000 numaralı öğrenci yoksa kod hata verir
    println(ogrenciler.get(1000))   //key yoksa none döner hata vermez
    println(ogrenciler.get(1456))

    println(ogrenciler.keys)
    println(ogrenciler.values)

  }
}
