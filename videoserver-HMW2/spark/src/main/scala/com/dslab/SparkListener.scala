package com.dslab

import scala.util.matching.Regex
import scala.collection.mutable.Queue
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent

object SparkListener {

  def main(args : Array[String]) {

    // Create context with 30 second batch interval
    val conf = new SparkConf().setAppName("SparkListener")
    val ssc = new StreamingContext(conf, Seconds(5))

    /* Configure Kafka */
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> sys.env("KAFKA_ADDRESS") ,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> sys.env("KAFKA_GROUP_ID"),
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array(sys.env("KAFKA_MAIN_TOPIC"))
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent, //Location strategy
      Subscribe[String, String](topics, kafkaParams)
    )

    val pattern1 = new Regex("([0-9]*)$")
    val pattern4 = new Regex("([0-9]*\\.[0-9]*)$")
    var q = new Queue[Float]
    val Y = 7
    var max_read = 0
    var max_write_ins = 0
    var max_write_upd = 0
    var max_write_del = 0
    var max_write = 0

    //singola statistica!
    val stats = stream.map(_.value).flatMap(stat => stat.split(" "))

    stats.print()

   /* //statistiche di tipo 4 (queries per sec)
    val stat_4 = stats.filter( stat => stat.contains("Queriespersec"))

    stat_4.foreachRDD { rdd =>
      //siamo in un batch con 1 RDD
      var somma = 0
      var n = 0
      rdd.foreach { record =>
        //singola statistica
        val value = ((pattern4 findFirstIn record).mkString("")).toFloat
        somma+=value
        n+=1
      }
      val media = somma / n
      println("Numero medio di richieste al secondo nel batch: "+ media)

      if(q.size == Y){
        var somma_q = 0
        var media_q = 0
        var incremento_perc = 0
        //calcolo media degli ultimi Y batch temporali
        q.foreach((element:Float) => somma_q+=element )

        media_q = somma_q / Y

        incremento_perc = (media - media_q)/(media_q)*100
        if(incremento_perc > 20){
            println("ALERT: Registrato un incremento del numero medio di richieste pari a "+incremento_perc+"%. Sono state effettuate "+ max_write + " query di scrittura e " + max_read + " query di lettura")
        }
        q.dequeue()
      }
      q.enqueue(media)
    }


    //statistiche di tipo 1 (queries di lettura e di scrittura)

    //statistiche di tipo 1 lettura
    val stat_1_read = stats.filter( stat => stat.contains("Com_select"))

    stat_1_read.foreachRDD { rdd =>
      //siamo in un batch con 1 RDD
      rdd.foreach { record =>
        //singola statistica
        val value = ((pattern1 findFirstIn record).mkString("")).toInt
        if(value > max_read){
          max_read=value
        }
      }
    }
    println("Query di lettura nel database: "+ max_read)

    //statistiche di tipo 1 scrittura
    val stat_1_write = stats.filter( stat => (stat.contains("Com_insert") || stat.contains("Com_update") || stat.contains("Com_delete")))

    stat_1_write.foreachRDD { rdd =>
      //siamo in un batch con 1 RDD
      rdd.foreach { record =>
        //singola statistica
        val value = ((pattern1 findFirstIn record).mkString("")).toInt

        if(record.contains("Com_insert") && value > max_write_ins){
            max_write_ins=value
        }
        else if (record.contains("Com_update") && value > max_write_upd){
            max_write_upd=value
        }
        else if (record.contains("Com_delete") && value > max_write_del) {
          max_write_del=value
        }

      }
      max_write = max_write_ins + max_write_upd + max_write_del
    }

    print("Query di scrittura nel database: "+ max_write)*/


    // Start the computation
    ssc.start()
    ssc.awaitTermination()
  }
}
