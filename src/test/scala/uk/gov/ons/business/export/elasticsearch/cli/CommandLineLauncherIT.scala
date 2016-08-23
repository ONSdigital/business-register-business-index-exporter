package uk.gov.ons.business.export.elasticsearch.cli

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.SpanSugar._
import org.scalatest.{FlatSpec, Matchers}
import uk.gov.ons.business.model.BusinessIndexRecord
import uk.gov.ons.business.{ElasticsearchServerSuiteMixin, TestData}

class CommandLineLauncherIT extends FlatSpec with ScalaFutures with ElasticsearchServerSuiteMixin with TestData with Matchers {

  private val mapper = new ObjectMapper().registerModule(DefaultScalaModule)

  behavior of "Elasticsearch export tool"

  it should "export records from business index to Elasticsearch" in {

    val businessIndexLocation: String = getClass.getResource("/fixtures/business-index").getPath

    val result = new CommandLineLauncher().launch(Array(
      "--businessIndexPath", businessIndexLocation,
      "--elasticsearch.nodes", "localhost:9200"
    ))

    whenReady(result, timeout(15 seconds)) { _ =>
      // check document exists in Elasticsearch
      val response = server.client().prepareGet("business-register", "business-index-record", sampleBusinessIndexRecord.id).get()
      mapper.readValue(response.getSourceAsBytes, classOf[BusinessIndexRecord]) should be(sampleBusinessIndexRecord)
    }
  }

}
