FROM openjdk:17-jdk-alpine
RUN mkdir -p /project/imagerequestor
COPY target/*.jar ms-image-requestor-engine-service.jar
EXPOSE 9991
ENTRYPOINT ["java","-jar","/ms-image-requestor-engine-service.jar"]