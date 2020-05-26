package machineLearning.preprocessing

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object DataExplore {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark=SparkSession.builder()
      .appName("DataExplore")
      .master("local")
      .getOrCreate()



    val adultTrainDF=spark.read.format("csv")
      .option("header","true")
      .option("sep",",")
      .option("inferschema","true")
      .load("D:\\SparkScala/adult.data")


    val adultTestDF=spark.read.format("csv")
      .option("header","true")
      .option("sep",",")
      .option("inferschema","true")
      .load("D:\\SparkScala/adult.test")

    println("AdultTrainDF:")
    adultTrainDF.show(5)

    println("AdultTestDF:")
    adultTestDF.show(5)

    //DataFrame leri birleştirme:

    //union = şemaları aynı olan veri setlerini birleştirir
    val adultWholeDF=adultTrainDF.union(adultTestDF)

    adultWholeDF.show(10)

    //birleştirme başarılıysa satır sayısı toplamları eşit olmalı:
    println("AdultTrainDF satır sayısı:")
    println(adultTrainDF.count())
    println("AdultTestDF satır sayısı:")
    println(adultTestDF.count())
    println("AdultWholeDF satır sayısı:")
    println(adultWholeDF.count())



    //Veri setinin şeması
    adultTrainDF.printSchema()

    //Numerik değişkenlerin istatistikleri

    println("Numerik değişken istatistikleri:")
    adultWholeDF.describe("age","fnlwgt","education_num","capital_gain","capital_loss","hours_per_week").show()


  }
}
