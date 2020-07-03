package Akka_with_Crawler.src.main.scala.data

import org.apache.spark.sql
import org.apache.spark.sql.SparkSession


/***
 * Original data operators
 */
class DataSource {

  val DataFilePath = "src/main/scala/Akka_with_Crawler/src/main/resources/all_listed_company.json"

  val spark = SparkSession.builder()
    .appName("AKKA with Crawler")
    .master("local[*]")
    .getOrCreate()

  private def readData(): sql.DataFrame = {
    //    spark.read.option("multiline", "true").json(this.DataFilePath)
//    this.spark.read.json(spark.sparkContext.wholeTextFiles(this.DataFilePath).values)
    this.spark.read.json(this.DataFilePath)
  }


  def dataNumber(): Float = {
    val data = this.readData()
    data.count()
  }


  def stockSymbolData(): sql.DataFrame = {
    val data = this.readData()
    data.select("stock_symbol")
  }


  def companyData(): sql.DataFrame = {
    val data = this.readData()
    data.select("company")
  }


  def close_spark(): Unit = {
    /*
    https://stackoverflow.com/questions/50504677/java-lang-interruptedexception-when-creating-sparksession-in-scala
     */
    this.spark.sparkContext.stop()
    this.spark.close()
  }

}

