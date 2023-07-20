@echo off
echo starting mvn clean install
CALL mvn clean install -DskipTests
echo ######################################################
echo Completed build, Run docker build
echo ######################################################
CALL docker build --tag=ms-image-requestor-engine-service:latest .
echo ######################################################
echo Completed build, Run docker run
echo ######################################################
CALL docker run -p 9991:9991 --name=ms-image-requestor-engine-service ms-image-requestor-engine-service:latest