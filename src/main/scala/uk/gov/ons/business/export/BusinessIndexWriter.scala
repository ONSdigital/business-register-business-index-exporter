package uk.gov.ons.business.export

import org.apache.spark.rdd.RDD
import uk.gov.ons.business.model.BusinessIndexRecord

trait BusinessIndexWriter {
  def write(data: RDD[BusinessIndexRecord]): Unit
}
