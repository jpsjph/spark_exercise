package com.spark.exercise.jobs

import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.SparkSession

trait BaseJob extends LazyLogging {
  def sparkSession: SparkSession = {
    SparkSession.builder()
      .appName(getClass.getName)
      .getOrCreate()
  }

  def init(spark: SparkSession, args: Map[String, String]) {
    logger.info("Initializing...")
  }

  def run(spark: SparkSession, args: Map[String, String])

  def cleanUp(spark: SparkSession, args: Map[String, String]) {
    logger.info("Cleaning up...")
  }

  final def main(inputArgs: Array[String]) {
    val args = processArgs(inputArgs)
    val spark = sparkSession

    try {
      init(spark, args)
      run(spark, args)
    } catch {
      case ex: Throwable =>
        throw ex
    } finally {
      cleanUp(spark, args)
      spark.stop()
    }
    logger.info(getClass.getName + " is complete.")
  }

  val processArgs: Array[String] => Map[String, String] = (args: Array[String]) => args.toList.foldLeft(Map.empty[String, String])(
    (acc, keyVal) => keyVal match {
      case keyValueRegEx(key, value) => acc + (key -> value)
      case _ => acc
    }
  )

  val keyValueRegEx = """([^(=|\s)]*)=([^(=|\s)]*)""".r
}
