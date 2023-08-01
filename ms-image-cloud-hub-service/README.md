## Solace EDA-POC Spring Boot Demo using Solace JAVA API

## Author : Shreejit Panchal
## Microservice : ms-image-cloud-hub-service

## Git Repo
[Git Repo Path] https://github.com/shreejitpanchal/spring-boot-event-streaming-poc/tree/main/ms-image-cloud-hub-service


## Prerequisites
1: Enable Lombok before mvn clean, Please follow instructions to enable lombok in Intellij :: 
https://projectlombok.org/setup/intellij 

3: Maven Steps for Build & Install below:
``` bash
cd ms-image-cloud-hub-service
mvn clean install -DskipTests
```

## Running the Samples

To try individual samples, go into the project directory and run the sample using maven.

``` bash
cd ms-image-orchestrator-service
mvn spring-boot:run

Browse Swagger API to fire input
http://localhost:9993/swagger-ui.html


```

### Setting up your preferred IDE

Using a modern Java IDE provides cool productivity features like auto-completion, on-the-fly compilation, assisted refactoring and debugging which can be useful when you're exploring the samples and even modifying the samples. Follow the steps below for your preferred IDE.

This repository uses Maven projects. If you would like to import the projects into your favorite IDE you should be able to import them as Maven Projects. For examples, in eclipse choose "File -> Import -> Maven -> Existing Maven Projects -> Next -> Browse for your repo -> Select which projects -> Click Finish"

## License

This project is licensed under the Apache License, Version 2.0. - See the [LICENSE](LICENSE) file for details.
