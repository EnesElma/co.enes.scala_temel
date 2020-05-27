package machineLearning.preprocessing

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.ml.feature.{OneHotEncoderEstimator, StandardScaler, StringIndexer, VectorAssembler}
import org.apache.spark.ml.regression.LinearRegression


object BasitLineerRegression {
  def main(args: Array[String]): Unit = {


    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark=SparkSession.builder()
      .appName("PreProcessOps")
      .master("local")
      .getOrCreate()

    import spark.implicits._

    val df=spark.read.format("csv")
      .option("header","true")
      .option("sep",",")
      .option("inferschema","true")
      .load("D:\\SparkScala/Advertising.csv")




    val df2=df.withColumn("Advertisement",(col("TV")+col("Radio")+col("Newspaper")))
      .withColumnRenamed("Sales","label")
      .drop("TV","Radio","Newspaper")

    //veri ön hazırlığı
    val vectorAssembler=new VectorAssembler()
      .setInputCols(Array("Advertisement"))
      .setOutputCol("features")

    //regresyon modeli:
    import org.apache.spark.ml.regression.{LinearRegression,LinearRegressionModel}
    val lineerRegressionObject=new LinearRegression()
      .setLabelCol("label")
      .setFeaturesCol("features")

    //Pipeline:
    import org.apache.spark.ml.Pipeline
    val pipelineObj=new Pipeline()
      .setStages(Array(vectorAssembler,lineerRegressionObject))

    //Veri setini ayırma:
    val Array(trainDF,testDF) = df2.randomSplit(Array(0.8,0.2),142L)

    //Modeli eğitme:
    val pipelineModel=pipelineObj.fit(trainDF)
    pipelineModel.stages.foreach(println(_))


    val resultDF=pipelineModel.transform(testDF)

    resultDF.show()

    val lrModel=pipelineModel.stages.last.asInstanceOf[LinearRegressionModel]

    println(s"R kare: ${lrModel.summary.r2}")
    println(s"Düzeltilmiş R kare: ${lrModel.summary.r2adj}")
    println(s"RMSE: ${lrModel.summary.rootMeanSquaredError}")
    println(s"Katsayılar: ${lrModel.coefficients}")                   //beta1 katsayısı
    println(s"Sabit: ${lrModel.intercept}")                           //beta0 sabiti
    println(s"p değerler: ${lrModel.summary.pValues.mkString(",")}")
    println(s"t değerler: ${lrModel.summary.tValues.mkString(",")}")

    // y = beta0 + (beta1*advertisement)

    val predictDf=Seq(100).toDF("Advertisement")
    val dfpredictVec=vectorAssembler.transform(predictDf)
    lrModel.transform(dfpredictVec).show()

  }
}
