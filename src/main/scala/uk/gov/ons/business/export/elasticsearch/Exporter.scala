package uk.gov.ons.business.export.elasticsearch

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import uk.gov.ons.business.export.elasticsearch
import uk.gov.ons.business.export.elasticsearch.Constants._
import uk.gov.ons.business.model.BusinessIndexRecord
import uk.gov.ons.business.io.BusinessIndexReader

class Exporter {

  def export(businessIndexPath: String, elasticsearchNodes: String): Unit = {
    val sparkConfig = new SparkConf().setAppName("business-index-exporter")
    if (!sparkConfig.contains("spark.master")) {
      sparkConfig.setMaster(sys.env.getOrElse("SPARK_MODE", "local[*]"))
    }

    implicit val context = new SparkContext(sparkConfig)
    implicit val sqlContext = new SQLContext(context)

    val businessIndex: RDD[BusinessIndexRecord] = new BusinessIndexReader().read(businessIndexPath)
    new elasticsearch.Writer(elasticsearchNodes, s"$index/$documentType").write(businessIndex)

    context.stop()
  }

}
