FROM openjdk:17-jdk-alpine
RUN mkdir -p /project/imagehub
COPY project/imagehub/*.* /project/imagehub
COPY target/*.jar ms-image-orchestrator-service.jar
EXPOSE 9992
ENTRYPOINT ["java","-jar","/ms-image-orchestrator-service.jar"]