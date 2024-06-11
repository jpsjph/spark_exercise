package com.spark.exercise.jobs

import com.holdenkarau.spark.testing.DatasetSuiteBase
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec


class TokenMapperJobTest extends AnyWordSpec with DatasetSuiteBase with Matchers {


  "TokenMapper" should {
    "mapping with the token file" when {
      "call run method " in {
        val args = Map("tokenInput" -> "src/test/resources/token.csv", "recordInput" -> "src/test/resources/records.csv", "outputPath" -> "target/output/")
        TokenMapperJob.run(spark, args)
        val res = spark.read.csv(args("outputPath"))
        res.count() should be(4)
      }
    }
  }
}
