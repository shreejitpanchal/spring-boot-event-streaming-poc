package com.ms.image.stream.requestor.api.service.createImageStream;

import com.ms.image.stream.requestor.api.entity.ImageRequestorService;
import com.ms.image.stream.requestor.api.model.CreateImageStreamAPIRequest;
import com.ms.image.stream.requestor.api.model.CreateImageStreamAPIResponse;
import com.ms.image.stream.requestor.api.repository.ImageServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Persist Records to DB creating order
 *
 * @author Shreejit Panchal
 * @version 1.0
 * @date 11/07/2023
 */
@Service
public class CreateImageStreamPersistToDB {

    Logger logger = LoggerFactory.getLogger(CreateImageStreamPersistToDB.class);
    @Autowired
    private ImageServiceRepository imageServiceRepository;
    @Value("${eda.order.initial.status.value}")
    private String initialStatusValue;

    public boolean saveOrderRecord(CreateImageStreamAPIRequest apiRequest, CreateImageStreamAPIResponse.imageResponse imageResponseDtl, String imageId, LocalDateTime currrentDateTime) {
        try {
            logger.info("Step-2 === Init imageService DB === Start print time" + currrentDateTime);

            imageServiceRepository.save(ImageRequestorService.builder()
                    .imageId(imageId)
                    .userId(apiRequest.getImageRequest().getUserId())
                    .customerName(apiRequest.getImageRequest().getCustomerName())
                    .imageFileName(apiRequest.getImageRequest().getImageFileName())
                    .imageType(apiRequest.getImageRequest().getImageType())
                    .requestDateTime(apiRequest.getImageRequest().getRequestDateTime())
                    .createdDateTime(LocalDateTime.now().atZone(ZoneId.of("Asia/Singapore")))
                    .createdBy("API")
                    .imageStreamStatus(initialStatusValue)
                    .imageStreamDesc("Published Event to EDA Stream")
                    .correlationId(apiRequest.getCorrelationId())
                    .transactionId(apiRequest.getTransactionId())
                    .build());

            logger.info("Step-2.1 === Init ImageService DB === End");

        } catch (Exception e) {
            logger.error("Step-2-Err === createImage InvokeEDA Method Error ==> error:" + e.getMessage());
        }
        return false;
    }

}
