package uk.gov.ons.business

import uk.gov.ons.business.model.{BusinessIndexRecord, DataRecord}

trait TestData {

  val sampleSourceRecord = DataRecord("Sample", "c3f0e235", "ONS Newport", None, None, None, None, Some("Government Buildings, Cardiff Rd, Duffryn"), Some("NP10 8XG"), None, Map(), None, None)

  val sampleBusinessIndexRecord = BusinessIndexRecord("#1", "ONSNEWPORT", Map("Source" -> sampleSourceRecord))

}
