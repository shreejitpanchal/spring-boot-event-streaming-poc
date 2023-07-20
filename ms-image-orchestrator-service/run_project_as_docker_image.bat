@echo off
echo starting mvn clean install
CALL mvn clean install -DskipTests
echo ######################################################
echo Completed build, Run docker remove if exist
echo ######################################################
CALL docker rm ms-image-orchestrator-service
CALL docker rmi ms-image-orchestrator-service
echo ######################################################
echo Completed build, Run docker build
echo ######################################################
CALL docker build --tag=ms-image-orchestrator-service:latest .
echo Completed build, Run docker run
echo ######################################################
CALL docker run -p 9992:9992 --name=ms-image-orchestrator-service ms-image-orchestrator-service:latest