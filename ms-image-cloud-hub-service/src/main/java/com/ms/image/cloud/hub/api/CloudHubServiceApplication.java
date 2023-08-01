package com.ms.image.cloud.hub.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Microservice Application for Image Orchestrator Service
 *
 * @author Shreejit Panchal
 * @version 1.0
 * @date 11/07/2023
 */
@Slf4j
@SpringBootApplication
public class CloudHubServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudHubServiceApplication.class, args);
    }

}
