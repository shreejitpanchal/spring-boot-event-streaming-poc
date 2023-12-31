@echo off
echo starting mvn clean install
CALL mvn clean install -DskipTests
echo ######################################################
echo Completed build, Run docker remove if exist
echo ######################################################
CALL docker rm ms-image-requestor-engine-service
CALL docker rmi ms-image-requestor-engine-service
echo ######################################################
echo Completed build, Run docker build
echo ######################################################
CALL docker build --tag=ms-image-requestor-engine-service:latest .
echo Completed build, Run docker run
echo ######################################################
CALL docker run -p 9991:9991 --name=ms-image-requestor-engine-service ms-image-requestor-engine-service:latest