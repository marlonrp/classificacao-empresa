#!/bin/bash
echo "Generating jar file"
mvn clean install
echo "Starting jar file on port 8090"
cd target/
java -jar desafio-0.0.1-SNAPSHOT.jar