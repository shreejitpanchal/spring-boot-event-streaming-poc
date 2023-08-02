package com.ms.image.cloud.hub.api.service.createImageRequest;


import com.ms.image.cloud.hub.api.model.CreateImageRequestAPIRequest;
import com.ms.image.cloud.hub.api.model.CreateImageRequestAPIResponse;
import com.ms.image.cloud.hub.api.service.commonServices.PostRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Create Image Processing Service
 *
 * @author Shreejit Panchal
 * @version 1.0
 * @date 11/07/2023
 */
@Service
public class CreateImageRequestImpl implements CreateImageRequest {

    Logger logger = LoggerFactory.getLogger(CreateImageRequestImpl.class);
    @Value("${app.image.orchestrator.hub.path}")
    private String imageHubFolder;
    public boolean imagePresentFlag;
    @Autowired
    public PostRequest postRequest;
    @Override
    public CreateImageRequestAPIResponse createImageRequest(CreateImageRequestAPIRequest apiRequest) {

        logger.info("API === createImage Request ==> Start");

        imagePresentFlag=true;
        byte[] data = new byte[0];

        String localFileHolder = imageHubFolder + apiRequest.getImageFileName() + "." + apiRequest.getImageType();
        logger.info("Loading Image from Hub via path ==>" + localFileHolder);
        Path path = Paths.get(localFileHolder);

        try {
            data = Files.readAllBytes(path);
        } catch (Exception e) {
            imagePresentFlag=false;
            logger.error("ImageRequestSubscriberSendEvent Error while reading file - " + e);
        }

        if(imagePresentFlag) {
            logger.info(":: Image Loaded Successfully ::");
            postRequest.sendPost(apiRequest, data);
        }
        else {
            logger.info(":: Image not found ::");
        }


        logger.info("Step-3 === createImageStream Error ==> End");
        return CreateImageRequestAPIResponse.builder()
                .correlationId(apiRequest.getCorrelationId())
                .build();
    }

}
