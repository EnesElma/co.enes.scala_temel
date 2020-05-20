package sparkTemel.dataframeDataset

import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Logger,Level}
import org.apache.spark.sql.DataFrame




object DataframeGiris {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)


    val spark=SparkSession.builder()
      .appName("DataFrameGiris")
      .master("local")
      .getOrCreate()

    import spark.implicits._
    val sc=spark.sparkContext


    /*
    val dfFromList= sc.parallelize(List(1,2,3,4,5,6)).toDF("rakamlar")
    dfFromList.printSchema()


    val dfFromSpark=spark.range(10,100,5).toDF("besli")
    dfFromSpark.printSchema()

    */

    val dfFromFile=spark.read.format("csv")
      .option("header",true)
      .option("inferSchema",true)
      .load("C:\\Users\\enes\\Desktop\\Bitirme Projesi\\crawl edilen datalar/konut_datalari.csv")

    //dfFromFile.printSchema()
    //dfFromFile.show(10,false)


    //println("Satır sayısı: "+dfFromFile.count())

    //dfFromFile.select("il","ilce", "ucret").show(10)

    //dfFromFile.sort("katNo").show(10)

    dfFromFile.select("ilce","binaYasi").sort("binaYasi").show(10)
  }
}
