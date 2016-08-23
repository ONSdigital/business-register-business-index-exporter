# Overview

The purpose of this project is to provide set of tools to export business index to other formats. At the moment the following export destinations are available:

- Elasticsearch

# How to build it

Project can be built using either `sbt` installed locally or `bin/sbt` script. First approach will be used in all examples presented here.

## How to build it without dependencies

To build the project please execute following command:

```
sbt package
```

Above command will create `business-index-exporter_2.10-1.0.0-SNAPSHOT.jar` JAR file in `target/scala-2.10` directory.

## How to build it with dependencies

To build the project with all runtime dependencies (uber-jar) please execute following command:

```
sbt assembly
```

Above command will create `business-index-exporter-assembly-1.0.0-SNAPSHOT.jar` JAR file in `target/scala-2.10` directory.

# How to export business index to Elasticsearch

[Process of exporting business index to Elasticsearch](docs/export-to-elasticsearch.md).