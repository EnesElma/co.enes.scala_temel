package sparkTemel.dataframeDataset

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{functions => F}

object DateTimeOps {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark=SparkSession.builder()
      .appName("Date")
      .master("local")
      .getOrCreate()

    val sc=spark.sparkContext
    import spark.implicits._

    val df=spark.read.format("csv")
      .option("header",true)
      .option("sep",";")
      .option("inferSchema",true)
      .load("D:\\OnlineRetail.csv")
      .select("InvoiceDate").distinct()


    df.show(10)

    //Varsayılan format: yyyy-MM-dd HH:mm:ss
    val mevcutFormat="d.MM.yyyy HH:mm"      //veya dd.MM.yyyy HH:mm   nasıl görünüyorsa öyle düzenle

    val df2=df.withColumn("InvoiceDate",F.trim($"InvoiceDate"))   //Boşlukları temizleme
      .withColumn("NormalTarih",F.to_date($"InvoiceDate",mevcutFormat))   //Normal formatta sadece tarih
        .withColumn("StandartTarih",F.to_timestamp($"InvoiceDate",mevcutFormat))

    df2.show(10)


  }
}
