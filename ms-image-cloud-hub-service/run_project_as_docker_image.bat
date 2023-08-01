@echo off
echo starting mvn clean install
CALL mvn clean install -DskipTests
echo ######################################################
echo Completed build, Run docker remove if exist
echo ######################################################
CALL docker rm ms-image-cloud-hub-service
CALL docker rmi ms-image-cloud-hub-service
echo ######################################################
echo Completed build, Run docker build
echo ######################################################
CALL docker build --tag=ms-image-cloud-hub-service:latest .
echo Completed build, Run docker run
echo ######################################################
CALL docker run -p 9993:9993 --name=ms-image-cloud-hub-service ms-image-cloud-hub-service:latest