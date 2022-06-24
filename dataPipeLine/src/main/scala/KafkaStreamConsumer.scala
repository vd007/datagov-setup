package in.governance.samagra.apps.streaming

import in.governance.samagra.apps.streaming.KafkaStreamProducer.getClass
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions.{col, expr, from_json}
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{DoubleType, FloatType, IntegerType, StringType, StructField, StructType, TimestampType}
import org.apache.spark.sql.{DataFrame, SparkSession, types}
import org.apache.log4j.Logger
import java.io.FileNotFoundException
import java.util.Properties
import scala.io.Source

object KafkaStreamConsumer {
  val log = Logger.getLogger(getClass.getName)
  val url = getClass.getResource("/application.properties")
  val properties: Properties = new Properties()
  if (url != null) {
    val source = Source.fromURL(url)
    properties.load(source.bufferedReader())
  }
  else {
    log.error("properties file cannot be loaded at path " )
    throw new FileNotFoundException("Properties file cannot be loaded")
  }

  System.setProperty("HADOOP_USER_NAME",properties.getProperty("HADOOP_USER_NAME"))
  val spark = SparkSession.builder
    .appName(properties.getProperty("APPLICATION_NAME"))
    .master("local[2]")
    .config("spark.streaming.stopGracefullyOnShutdown","true")
    .config("spark.hadoop.fs.defaultFS", properties.getProperty("SPARK_HADOOP_FS_DEFAULTFS"))
    .getOrCreate()
  import spark.implicits._
  def readFromKafka() = {
    val taxiSchemaStruct = StructType(Array(
      StructField("vendorId", StringType),
      StructField("tpepPickupDatetime", StringType),
      StructField("tpepDropoffDatetime", StringType),
      StructField("passengerCount", IntegerType),
      StructField("tripDistance", DoubleType),
      StructField("ratecodeID", StringType),
      StructField("storeAndFWDFlag", StringType),
      StructField("pULocationID", IntegerType),
      StructField("dOLocationID", IntegerType),
      StructField("paymentType", IntegerType),
      StructField("fareAmount", DoubleType),
      StructField("extraAmount", DoubleType),
      StructField("mtaTax", DoubleType),
      StructField("tipAmount", DoubleType),
      StructField("tollsAmount", DoubleType),
      StructField("improvementSurcharge", DoubleType),
      StructField("totalAmount", DoubleType),
      StructField("congestionSurcharge",DoubleType)))

    val kafkaDF: DataFrame = spark.readStream
      .format(properties.getProperty("SOURCE_STREAM_FORMAT"))
      .option("kafka.bootstrap.servers", properties.getProperty("kAFKA_BOOTSTRAP_SERVERS"))
      .option("subscribe", properties.getProperty("TOPIC_NAME"))
      .option("startingOffsets", properties.getProperty("STARTING_OFFSET"))
      .load()
   val valueDF = kafkaDF.select(from_json(col("value").cast("string"),taxiSchemaStruct).as("data"))//.alias("value")
     .select("data.*")
    valueDF.printSchema()
      val yellowTaxiWriterQuery = valueDF.writeStream
        .format(properties.getProperty("TARGET_STREAM_FORMAT"))
      .queryName(properties.getProperty("QUERY_NAME"))
      .outputMode(properties.getProperty("OUTPUT_MODE"))
        .option("startingOffsets", properties.getProperty("STARTING_OFFSET"))
        .option("checkpointLocation",properties.getProperty("CHECKPOINT_LOCATION"))
        .option("path",properties.getProperty("HDFS_PATH"))
      .trigger(Trigger.ProcessingTime(properties.getProperty("PROCESS_TIME")))
      .start()
    yellowTaxiWriterQuery.awaitTermination()
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.WARN)
    readFromKafka()
  }

}
