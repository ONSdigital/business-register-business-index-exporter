#!/bin/bash

# Constants

PROJECT_NAME="business-index-exporter"

# Variables

scriptsDirectory=$(dirname $0)
libsDirectory=${scriptsDirectory}/../lib

libraries=$(ls -m ${libsDirectory}/*.jar | grep -v ${PROJECT_NAME} | tr -d ' \n')

# Job submission

spark-submit --master yarn-cluster --jars ${libraries} ${libsDirectory}/*${PROJECT_NAME}*.jar $@