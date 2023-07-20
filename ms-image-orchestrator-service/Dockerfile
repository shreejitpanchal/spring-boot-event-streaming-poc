FROM openjdk:17-jdk-alpine
COPY target/*.jar ms-image-orchestrator-service.jar
EXPOSE 9992
ENTRYPOINT ["java","-jar","/ms-image-orchestrator-service.jar"]