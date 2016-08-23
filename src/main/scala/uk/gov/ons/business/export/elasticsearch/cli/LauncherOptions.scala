package uk.gov.ons.business.export.elasticsearch.cli

case class LauncherOptions(businessIndexPath: String, elasticsearch: ElasticsearchConfig = ElasticsearchConfig())

case class ElasticsearchConfig(nodes: String = "localhost")