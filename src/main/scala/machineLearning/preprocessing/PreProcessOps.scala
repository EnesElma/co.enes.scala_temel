package machineLearning.preprocessing

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.ml.feature.{OneHotEncoderEstimator, StandardScaler, StringIndexer, VectorAssembler}

object PreProcessOps {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark=SparkSession.builder()
      .appName("PreProcessOps")
      .master("local")
      .getOrCreate()

    val df=spark.read.format("csv")
      .option("header","true")
      .option("sep",",")
      .option("inferschema","true")
      .load("D:/simple_data.csv")

    df.show(5)
    println("\n")

    //Veri setine nitelik ekleme:

    val df1=df.withColumn("Ekonomik_durum",
      when(col("aylik_gelir").gt(7000),"iyi")   //gt : greater then
        .otherwise("kötü")
    )
    df1.show(5)



    ////////////////////////////// String Indexer ////////////////////////////////////////////////////////////

    //meslek indexleme:
    val meslekIndexer=new StringIndexer()
      .setInputCol("meslek")
      .setOutputCol("meslekIndex")
      .setHandleInvalid("skip")

    val meslekIndexerModel = meslekIndexer.fit(df1)
    val meslekIndexDF=meslekIndexerModel.transform(df1)

    //meslekIndexDF.show()

    //şehir indexleme:
    val sehirIndexer=new StringIndexer()
      .setInputCol("sehir")
      .setOutputCol("sehirIndex")
      .setHandleInvalid("skip")

    val sehirIndexerModel = sehirIndexer.fit(meslekIndexDF)
    val sehirIndexDF=sehirIndexerModel.transform(meslekIndexDF)

    sehirIndexDF.show()


    ////////////////////////////// OneHotEncoderEstimator Aşaması ////////////////////////////////////////////////////////////


    val encoder= new OneHotEncoderEstimator()
      .setInputCols(Array[String]("meslekIndex","sehirIndex"))
      .setOutputCols(Array[String]("meslekIndexEncoded","sehirIndexEncoded"))

    val encoderModel=encoder.fit(sehirIndexDF)
    val oneHotEncodeDF= encoderModel.transform(sehirIndexDF)

    oneHotEncodeDF.show()


    ////////////////////////////// VectorAssembler Aşaması ////////////////////////////////////////////////////////////

    val vectorAssembler=new VectorAssembler()
      .setInputCols(Array[String]("meslekIndexEncoded","sehirIndexEncoded","yas","aylik_gelir"))
      .setOutputCol("vectorizedFeatures")

    val vectorAssemblerDF=vectorAssembler.transform(oneHotEncodeDF)

    vectorAssemblerDF.show(false)



    ////////////////////////////// LabelIndexer Aşaması ////////////////////////////////////////////////////////////

    val labelIndexer=new StringIndexer()
      .setInputCol("Ekonomik_durum")
      .setOutputCol("label")

    val labelIndexerModel=labelIndexer.fit(vectorAssemblerDF)
    val labelIndexerDF=labelIndexerModel.transform(vectorAssemblerDF)

    labelIndexerDF.show(false)


    ////////////////////////////// StandartScaler Aşaması ////////////////////////////////////////////////////////////

    import scala.math._

    val yasinEtkisi=sqrt(pow((35-33),2))
    val maasEtkisi=sqrt(pow((18000-3500),2))
    val oklidMesafesi = sqrt(pow((35-33),2) +pow((18000-3500),2))

    println(oklidMesafesi)
    println(yasinEtkisi)
    println(maasEtkisi)

    val scalar=new StandardScaler()
      .setInputCol("vectorizedFeatures")
      .setOutputCol("features")

    val scalarModel=scalar.fit(labelIndexerDF)
    val scalarDF=scalarModel.transform(labelIndexerDF)

    scalarDF.show(false)


    //////////////////////////////Basit Machine Learning Modeli ////////////////////////////////////////////////////////////

    val Array(trainDF,testDF) = scalarDF.randomSplit(Array(0.8,0.2),142L)

    import org.apache.spark.ml.classification.LogisticRegression

    val lrObj=new LogisticRegression()
      .setLabelCol("label")
      .setFeaturesCol("features")
      .setPredictionCol("prediction")

    val lrModel=lrObj.fit(trainDF)
    lrModel.transform(testDF).show(false)

  }
}
