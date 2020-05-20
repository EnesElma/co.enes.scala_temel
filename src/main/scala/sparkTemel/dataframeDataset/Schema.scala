package sparkTemel.dataframeDataset

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object Schema {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark=SparkSession.builder()
      .appName("Schema")
      .master("local")
      .getOrCreate()

    import spark.implicits._
    val sc=spark.sparkContext

    val dfFromFile=spark.read.format("csv")
      .option("header",true)
      .option("sep",";")
      .option("inferSchema",true)
      .load("D:\\OnlineRetail.csv")


    println("\nOrijinal DF")
    dfFromFile.show(5)
    println("\n")
    dfFromFile.printSchema()


    //Elle şema yapımı

    val retailManuelSchema= new StructType(
      Array(
        new StructField("InvoiceNo",StringType,true),
        new StructField("StockCode",StringType,true),
        new StructField("Description",StringType,true),
        new StructField("Quantity",IntegerType,true),
        new StructField("InvoiceDate",StringType,true),
        new StructField("UnitPrice",FloatType,true),
        new StructField("CustomerID",IntegerType,true),
        new StructField("Country",StringType,true),
      )

    )




    //yeni dataframe i elle oluşturduğumuz şema ile oluşturmayı deneyelim:

    val dfFromFile2=spark.read.format("csv")
      .option("header",true)
      .option("sep",";")
      .schema(retailManuelSchema)
      .load("D:\\OnlineRetail.csv")

    dfFromFile2.show(5)

    dfFromFile2.printSchema()


    //UnitPrice kolonundaki değerlerde virgül olduğu için float'a dönüştürülemiyor
    //bu nedenle hepsi null oldu
    //virgülleri noktaya dönüştürmemiz lazım öncelikle:

    val dfFromFile3=spark.read.format("csv")
      .option("header",true)
      .option("sep",";")
      .option("inferSchema",true)
      .load("D:\\OnlineRetail.csv")
      .withColumn("UnitPrice",regexp_replace($"UnitPrice",",","."))



    //Düzeltilen dataframe i kaydedelim:

    dfFromFile3.coalesce(1)
      .write
      .mode("overwrite")
      .option("sep",";")
      .option("header",true)
      .csv("D:\\OnlineRetailDuzeltilen")



    //Düzeltilen dataframe i elle oluşturduğumuz şema ile oluşturmayı deneyelim:

    val dfFromManuelSchema=spark.read.format("csv")
      .option("header",true)
      .option("sep",";")
      .schema(retailManuelSchema)
      .load("D:\\OnlineRetailDuzeltilen")

    dfFromManuelSchema.show(5)

    dfFromManuelSchema.printSchema()

  }
}
