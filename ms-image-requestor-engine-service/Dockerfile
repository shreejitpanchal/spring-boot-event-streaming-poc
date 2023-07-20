FROM openjdk:17-jdk-alpine
COPY target/*.jar ms-image-requestor-engine-service.jar
EXPOSE 9991
ENTRYPOINT ["java","-jar","/ms-image-requestor-engine-service.jar"]