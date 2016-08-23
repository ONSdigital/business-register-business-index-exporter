package uk.gov.ons.business.export.elasticsearch

import org.apache.spark.rdd.RDD
import org.elasticsearch.spark._
import uk.gov.ons.business.export.BusinessIndexWriter
import uk.gov.ons.business.model.BusinessIndexRecord

import scala.collection.Map

class Writer(nodes: String, resource: String) extends BusinessIndexWriter {

  private val operationConfig: Map[String, String] = Map(
    "es.nodes" -> nodes,
    "es.index.auto.create" -> "false",
    "es.mapping.id" -> "id"
  )

  override def write(data: RDD[BusinessIndexRecord]): Unit = {
    try {
      data.saveToEs(resource, operationConfig)
    } catch {
      case exception: Throwable => throw new WriteException(s"Error occurred while writing business index records: ${exception.getMessage}")
    }
  }

}

class WriteException(message: String) extends Exception(message)
