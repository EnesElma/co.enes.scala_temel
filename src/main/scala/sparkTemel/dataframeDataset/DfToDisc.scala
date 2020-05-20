package sparkTemel.dataframeDataset

import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Logger,Level}
import org.apache.spark.sql.functions._

object DfToDisc {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)


    val spark=SparkSession.builder()
      .appName("DataFrameGiris")
      .master("local")
      .getOrCreate()

    import spark.implicits._
    val sc=spark.sparkContext


    //elimizde ki bir veriyi temizleme:

    val dfFromFile=spark.read.format("csv")
      .option("header",true)
      .option("sep",",")
      .option("inferSchema",true)
      .load("D:\\simple_dirty_data.csv")

    dfFromFile.show()

    val df2= dfFromFile.withColumn("isim",trim(initcap($"isim")))    //isim kolonundaki değerlerin ilk harflerini büyüt ve boşlukları at
      .withColumn("cinsiyet",
        when($"cinsiyet".isNull,"U")    //cinsiyet bilgisi girilmemişse yerine "U" koy
          .otherwise($"cinsiyet"))      //aksi takdirde cinsiyet direk yaz
      .withColumn("sehir",
        when($"sehir".isNull,"Bilinmiyor")    //şehir bilgisi yoksa yerine "bilinmiyor" koy
          .otherwise($"sehir"))               //aksi takdirde sehir direk yaz
      .withColumn("sehir",trim(upper($"sehir")))  //sehir kolonundaki harfleri büyüt ve boşlukları at



    //temizlenen dataframe'i diske kaydetme:

    df2.coalesce(1)   //tek parça olarak kaydetmek için
      .write
      .mode("Overwrite")
      .option("header",true)
      .csv("D:\\simple_data")



    //temizlenip kaydedilen veriyi oku:

    val df3=spark.read.format("csv")
      .option("header",true)
      .option("inferSchema",true)
      .load("D:\\simple_data")

    df3.show(false)

  }
}
