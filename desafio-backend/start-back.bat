@echo off
echo "Generating jar file"
call mvn clean install
echo "Starting jar file on port 8090"
call cd target
call java -jar desafio-0.0.1-SNAPSHOT.jar