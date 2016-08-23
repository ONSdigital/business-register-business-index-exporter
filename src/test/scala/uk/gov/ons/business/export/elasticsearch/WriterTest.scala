package uk.gov.ons.business.export.elasticsearch

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.apache.spark.rdd.RDD
import org.scalatest.{FlatSpec, Matchers}
import uk.gov.ons.business.model.BusinessIndexRecord
import uk.gov.ons.business.{ElasticsearchServerSuiteMixin, TestData}
import uk.gov.ons.business.test.SparkContextsSuiteMixin

class WriterTest extends FlatSpec with SparkContextsSuiteMixin with ElasticsearchServerSuiteMixin with TestData with Matchers {

  private val mapper = new ObjectMapper().registerModule(DefaultScalaModule)

  behavior of "Elasticsearch writer"

  it should "not write business index record to Elasticsearch when index does not exist" in {
    the[WriteException] thrownBy {
      val businessIndex: RDD[BusinessIndexRecord] = context.makeRDD(sampleBusinessIndexRecord :: Nil)
      new Writer("localhost", "business-register/business-index-record").write(businessIndex)
    } should have message "Error occurred while writing business index records: Target index [business-register/business-index-record] does not exist and auto-creation is disabled [setting 'es.index.auto.create' is 'false']"
  }

  it should "write business index record to Elasticsearch when index exists" in {
    val client = server.client()

    // create index and type
    client.admin().indices().prepareCreate("business-register").addMapping("business-index-record").get()

    // write document
    val businessIndex: RDD[BusinessIndexRecord] = context.makeRDD(sampleBusinessIndexRecord :: Nil)
    new Writer("localhost", "business-register/business-index-record").write(businessIndex)

    // check document exists in Elasticsearch
    val response = client.prepareGet("business-register", "business-index-record", sampleBusinessIndexRecord.id).get()
    mapper.readValue(response.getSourceAsBytes, classOf[BusinessIndexRecord]) should be(sampleBusinessIndexRecord)
  }

}
