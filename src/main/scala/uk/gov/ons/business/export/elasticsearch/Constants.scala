package uk.gov.ons.business.export.elasticsearch

import scala.io.Source

trait Constants {

  val index = "business-register"

  val documentType = "business-index-record"
  val documentTypeMappings = Source.fromURL(getClass.getResource("/elasticsearch/mappings.json")).mkString

}

object Constants extends Constants