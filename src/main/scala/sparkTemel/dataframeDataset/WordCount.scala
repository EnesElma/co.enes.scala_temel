package sparkTemel.dataframeDataset

import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Logger,Level}

object WordCount {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark=SparkSession.builder()
      .master("local")
      .appName("WordCount")
      .getOrCreate()

    import spark.implicits._

    val hikayeDS=spark.read.textFile("D:\\omer_seyfettin.txt")
    val hikayeDF=hikayeDS.toDF("value")


    //hikayeDS.show(10,false)


    val kelimeler=hikayeDS.flatMap(x=>x.split(" "))

    println(kelimeler.count())  //toplam kelime sayısı

    import org.apache.spark.sql.functions.{count}     //her kelimeden kaç tane olduğunu buluyoruz
    kelimeler.groupBy("value")
      .agg(count('value).as("kelimeSayisi"))
      .orderBy($"kelimeSayisi".desc)
      .show(10)






  }

}
