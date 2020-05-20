package sparkTemel.dataframeDataset

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.log4j.{Logger,Level}

object StringOps {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark=SparkSession.builder()
      .appName("StringOps")
      .master("local")
      .getOrCreate()

    import spark.implicits._
    val sc=spark.sparkContext


    val dfFromFile=spark.read.format("csv")
      .option("header",true)
      .option("inferSchema",true)
      .load("D:\\simple_dirty_data.csv")

    //Concat işlemi

    val df2=dfFromFile.select("meslek","sehir")
        .withColumn("meslek_sehir",
          concat(col("meslek"),lit(" - "),col("sehir")))

    df2.show(false)



  }
}
