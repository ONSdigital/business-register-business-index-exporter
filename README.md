# Overview

The purpose of this project is to provide set of tools to export business index to other formats. At the moment the following export destinations are available:

- Elasticsearch

# How to build it

Project can be built using either `sbt` installed locally or `bin/sbt` script. First approach will be used in all examples presented here.

## Cross project dependencies
This project depends on the following projects being built and published:

- ["uk.gov.ons.business-register" %% "business-core" % "1.0.0-SNAPSHOT"](https://github.com/ONSdigital/business-register-test-utils)
- ["uk.gov.ons.business-register" %% "test-utils" % "1.0.0-SNAPSHOT" % "test"](https://github.com/ONSdigital/business-register-business-libs)
 
## How to build it without dependencies

To build the project please execute following command:

```
sbt package
```

Above command will create `business-index-exporter_2.10-1.0.0-SNAPSHOT.jar` JAR file in `target/scala-2.10` directory.

## How to prepare application distribution

To build the project with all runtime dependencies in `lib` directory please execute following command:

```
sbt stage
```

Above command will create application distribution in `target/universal/stage` directory. Having created this, application can be launched locally using `target/universal/stage/bin/business-index-exporter` script or remotely using `target/universal/stage/scripts/run-in-cluster.sh` script.

## How to prepare distribution package  

To prepare distribution package please execute following command:

```
sbt dist
```

Above command will create distribution package `business-index-exporter-1.0.0-SNAPSHOT.zip` in `target/universal` directory.

# How to export business index to Elasticsearch

[Process of exporting business index to Elasticsearch](docs/export-to-elasticsearch.md).