package com.ms.image.orchestrator.api.solaceService.ImageResponseEventSubscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.image.orchestrator.api.model.EDAPublishCreateImageEventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageRequestSubscriberOrch {

    private static final Logger logger = LoggerFactory.getLogger(ImageRequestSubscriberOrch.class);
    ObjectMapper jsonMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    @Autowired
    private EDAPublishCreateImageEventRequest edaPublishCreateImageEventRequest;
    @Autowired
    private ImageRequestSubscriberSendEvent imageRequestSubscriberSendEvent;

    public void invokeImageRequestMapper(String rawJson) {
        try {
            edaPublishCreateImageEventRequest = jsonMapper.readValue(rawJson, EDAPublishCreateImageEventRequest.class);
        } catch (JsonProcessingException e) {
            logger.info("Error :::" + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("=== Print TransactionId : " + edaPublishCreateImageEventRequest.getTransactionId());
        logger.info("=== Print ImageFileName : " + edaPublishCreateImageEventRequest.getImage().getImageFileName());

        imageRequestSubscriberSendEvent.sendEvent(edaPublishCreateImageEventRequest);

    }
}
