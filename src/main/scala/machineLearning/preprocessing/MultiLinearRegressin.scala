package machineLearning.preprocessing

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.Pipeline
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.ml.feature.{OneHotEncoderEstimator, StandardScaler, StringIndexer, VectorAssembler}
import org.apache.spark.ml.regression.{LinearRegression, LinearRegressionModel}

object MultiLinearRegressin {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark=SparkSession.builder()
      .appName("MultiLinearRegression")
      .master("local")
      .getOrCreate()

    import spark.implicits._

    val df=spark.read.format("csv")
      .option("header","true")
      .option("sep",",")
      .option("inferschema","true")
      .load("D:\\SparkScala/Advertising.csv")

    val newCols=Array("id","TV","Radio","Newspaper","label")

    val df2=df.toDF(newCols:_*)

    df2.show()

    val numericVal=Array("TV","Radio","Newspaper")
    val label=Array("label")

    df2.describe().show()

    //varsayımlar:?

    val vectorAssembler=new VectorAssembler()
      .setInputCols(numericVal)
      .setOutputCol("features")

    //regresyon nesnesi:
    val lrObj=new LinearRegression()
      .setLabelCol("label")
      .setFeaturesCol("features")

    //Pipeline:
    val pipelineObj=new Pipeline()
      .setStages(Array(vectorAssembler,lrObj))

    //veri ayırma:
    val Array(trainDF,testDF)= df2.randomSplit(Array(0.8,0.2),142L)

    val pipelineModel=pipelineObj.fit(trainDF)
    pipelineModel.transform(testDF).show()


    val lrModel = pipelineModel.stages(1).asInstanceOf[LinearRegressionModel]

    println(s"Katsayılar: ${lrModel.coefficients}")                   //beta1 katsayısı
    println(s"Sabit: ${lrModel.intercept}")                           //beta0 sabiti
    println(s"RMSE: ${lrModel.summary.rootMeanSquaredError}")
    println(s"R kare: ${lrModel.summary.r2}")
    println(s"Düzeltilmiş R kare: ${lrModel.summary.r2adj}")


    println(s"p değerler: ${lrModel.summary.pValues.mkString(",")}")
    println(s"t değerler: ${lrModel.summary.tValues.mkString(",")}")

    //p değerlerinden üçüncüsü olan newspapere ait değer 0.05 ten büyük olduğu için modelden çıkarılır


  }
}
