FROM openjdk:17-jdk-alpine
RUN mkdir -p /project/imagehub
COPY project/imagehub/*.* /project/imagehub
COPY target/*.jar ms-image-cloud-hub-service.jar
EXPOSE 9993
ENTRYPOINT ["java","-jar","/ms-image-cloud-hub-service.jar"]