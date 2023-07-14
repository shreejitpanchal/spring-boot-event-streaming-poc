package com.ms.image.orchestrator.api.service.createImageStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
public class CreateImageStreamImpl implements CreateImageStream {

    Logger logger = LoggerFactory.getLogger(CreateImageStreamImpl.class);
    String imageId;
    @Autowired
    private CreateImageStreamAPIResponse apiResponse;
    @Autowired
    private ImageRequestorServiceRepository imageRequestorServiceRepository;

    @Autowired
    private CreateImageStreamPersistToDB createImageStreamPersistToDB;

    @Autowired
    private CreateImageStreamSendEvent createImageStreamSendEvent;

    @Autowired
    private CreateImageStreamValidation createImageStreamValidation;
    @Value("${eda.order.initial.status.value}")
    private String initialStatusValue;

    @Override
    public CreateImageStreamAPIResponse createImageStream(CreateImageStreamAPIRequest apiRequest) {

        logger.info("API === createImageStream Request ==> Start");
        logger.info("Step-1 === CustomerName: " + apiRequest.getImageRequest().getCustomerName() + " ImageFileName: " + apiRequest.getImageRequest().getImageFileName());
        CreateImageStreamAPIResponse.imageResponse imageResponseDtl = new CreateImageStreamAPIResponse.imageResponse();

        LocalDateTime currrentDateTime = LocalDateTime.now();

        if (createImageStreamValidation.validateInput(apiRequest)) {
            logger.info("Step-1-Err === createImageStream ImageType validation Error ==> End");
            return CreateImageStreamAPIResponse.builder()
                    .correlationId(apiRequest.getCorrelationId())
                    .imageResponse(imageResponseDtl.builder()
                            .imageStreamStatus("Error")
                            .imageStreamDesc("Invalid ImageType")
                            .build())
                    .build();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        imageId = formatter.format(new Date()) + (new Random().nextInt(5000));

        if (createImageStreamPersistToDB.saveOrderRecord(apiRequest, imageResponseDtl, imageId, currrentDateTime))
            return CreateImageStreamAPIResponse.builder()
                    .imageResponse(imageResponseDtl.builder().imageStreamStatus("Error").imageStreamDesc("DB-Persist-Err").build())
                    .correlationId(apiRequest.getCorrelationId())
                    .build();

        if (createImageStreamSendEvent.sendEvent(apiRequest, imageId))
            return CreateImageStreamAPIResponse.builder()
                    .imageResponse(imageResponseDtl.builder().imageStreamStatus("Error").imageStreamDesc("Send-Event-Err").build())
                    .correlationId(apiRequest.getCorrelationId())
                    .build();


        logger.info("Step-3 === createImageStream Successful ==> End");
        return CreateImageStreamAPIResponse.builder()
                .imageResponse(imageResponseDtl.builder()
                        .userId(imageId)
                        .customerName(apiRequest.getImageRequest().getCustomerName())
                        .imageFileName(apiRequest.getImageRequest().getImageFileName())
                        .requestDateTime(apiRequest.getImageRequest().getRequestDateTime())
                        .imageStreamStatus(initialStatusValue)
                        .imageStreamDesc("Published Event to EDA Successfully")
                        .build())
                .build();
    }

}
