package uk.gov.ons.business.export.elasticsearch.cli

class LauncherOptionsParser extends scopt.OptionParser[LauncherOptions]("business-index-exporter") {
  head("ONS Business Index exporter tool")

  help("help")
  version("version")

  opt[String]("businessIndexPath").valueName("<path>").text("path to existing business index (required)").required()
    .action((filePath, arguments) => {
      arguments.copy(businessIndexPath = filePath)
    })

  opt[String]("elasticsearch.nodes").valueName("<nodes>").text("list of Elasticsearch nodes to connect to; each node can have its port specified individually; if port is not specified then default 9200 is used (optional, default = localhost)")
    .action((nodes, arguments) => {
      arguments.copy(elasticsearch = arguments.elasticsearch.copy(nodes = nodes))
    })
}
