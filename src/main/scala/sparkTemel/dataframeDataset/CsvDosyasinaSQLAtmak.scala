package sparkTemel.dataframeDataset

import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Logger,Level}

object CsvDosyasinaSQLAtmak {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark=SparkSession.builder()
      .master("local")
      .appName("CsvSqlImport")
      .getOrCreate()

    val sc=spark.sparkContext

    import spark.implicits._

    val dfFromFile=spark.read.format("csv")
      .option("header",true)
      .option("inferSchema",true)
      .load("C:\\Users\\enes\\Desktop\\Bitirme Projesi\\crawl edilen datalar/konut_datalari.csv")


    dfFromFile.createOrReplaceTempView("tablo")

    spark.sql(            //bu şekilde sql komutlarını direk kullanabiliriz
      """

        select * from tablo


        """).show(10)

  }

}
