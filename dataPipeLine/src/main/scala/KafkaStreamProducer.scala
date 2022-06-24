package in.governance.samagra.apps.streaming

import java.io.FileNotFoundException
import java.util.Properties
import scala.io.Source

object KafkaStreamProducer extends App{
  val url = getClass.getResource("/application.properties")
  val properties: Properties = new Properties()
  if (url != null) {
    val source = Source.fromURL(url)
    properties.load(source.bufferedReader())
  }
  else {
    println("properties file cannot be loaded at path " )
    throw new FileNotFoundException("Properties file cannot be loaded")
  }
  val table = properties.getProperty("HADOOP_USER_NAME")
  val zquorum = properties.getProperty("APPLICATION_NAME")
  val port = properties.getProperty("TOPIC_NAME")
  println(table)
  println(zquorum)
  println(port)

}
