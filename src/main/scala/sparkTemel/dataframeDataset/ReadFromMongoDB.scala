package sparkTemel.dataframeDataset

import com.mongodb.spark.MongoSpark
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession



object ReadFromMongoDB {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark=SparkSession.builder()
      .appName("ReadFromMongpDB")
      .master("local")
      .config("spark.mongodb.input.uri","mongodb://localhost:27017/mylib.test")
      .config("spark.mongodb.output.uri","mongodb://localhost:27017/mylib.test")
      .getOrCreate()


    //Read from mongoDB:

    val df2 = MongoSpark.load(spark)
    df2.show()







  }
}
