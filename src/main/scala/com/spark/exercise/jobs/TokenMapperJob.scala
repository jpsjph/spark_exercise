package com.spark.exercise.jobs

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions.broadcast
import org.apache.spark.sql.functions.trim

object TokenMapperJob extends BaseJob {

  override def run(spark: SparkSession, args: Map[String, String]): Unit = {
    import spark.implicits._
    val tokenPath = args("tokenInput")
    val recordPath = args("recordInput")
    val outputPath = args("outputPath")

    val tokenDF = spark.read.option("header", value = true).option("inferSchema", value = true).csv(tokenPath)
    val recordDF = spark.read.option("header", value = true).option("inferSchema", value = true).csv(recordPath)

    val resultDF = recordDF.join(broadcast(tokenDF), trim($"name") === trim($"token") || trim($"lastName") === trim($"token"))
    resultDF.write.mode(SaveMode.Overwrite).csv(outputPath)
  }
}
