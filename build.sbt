
import sbt.Keys.*

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

val sparkVersion = "3.5.1"
val logbackVersion = "1.2.11"
libraryDependencies += "org.scala-lang" % "scala-library" % "2.13.10"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "org.scalatest" %% s"scalatest" % "3.2.17",
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "com.holdenkarau" %% "spark-testing-base" % "3.5.1_1.5.3" % Test)


ThisBuild / assemblyMergeStrategy := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

lazy val root = (project in file("."))
  .settings(
    name := "spark_exercise",
    assembly /assemblyJarName := "spark_exercise.jar"
  )
