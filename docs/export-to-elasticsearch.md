# Export to Elasticsearch

This tool exports business index stored as object file to Elasticsearch.

# How to run it

Project main class is used to run export process. The following arguments are supported:

- `--businessIndexPath` - path to business index directory (required)
- `--elasticsearch.nodes` - list of Elasticsearch nodes to connect to (optional, default = localhost)

As a result `business-register` index and `business-index-record` type will be created in Elasticsearch.

Note: index `business-register` is created automatically if doesn't exists and doesn't have to be created upfront.

## How to run it locally

Export process can be launched locally by executing following command:

```
sbt "run <arguments>"
```

For example following commands might be used to export business index from `/tmp/output/business-index-20160721/business-index` directory to `192.168.0.99:9200` Elasticsearch node.

```
sbt "run --businessIndexPath /tmp/output/business-index-20160721/business-index
         --elasticsearch.nodes 192.168.0.99:9200
```


```
spark-submit --master local[*]
             --packages com.github.scopt:scopt_2.10:3.5.0,net.databinder.dispatch:dispatch-core_2.10:0.11.3,org.elasticsearch:elasticsearch-hadoop:2.3.3
             ./target/scala-2.10/business-index-exporter_2.10-1.0.0-SNAPSHOT.jar
             --businessIndexPath /tmp/output/business-index-20160721/business-index
             --elasticsearch.nodes 192.168.0.99:9200
```

In above scenario all business index records would be searchable as `business-register/business-index-record` resource.