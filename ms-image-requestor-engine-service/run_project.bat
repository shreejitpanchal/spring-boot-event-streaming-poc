@echo off
echo starting mvn clean install
CALL mvn clean install -DskipTests
echo ######################################################
echo Completed build, Will run spring-boot:run
echo ######################################################
CALL mvn spring-boot:run