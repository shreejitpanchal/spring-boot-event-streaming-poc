package com.ms.image.orchestrator.api.solaceService.ImageResponseEventSubscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.image.orchestrator.api.model.EDAPublishCreateImageEventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ImageRequestSubscriberOrchv2 {

    private static final Logger logger = LoggerFactory.getLogger(ImageRequestSubscriberOrchv2.class);
    ObjectMapper jsonMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    @Autowired
    private EDAPublishCreateImageEventRequest edaPublishCreateImageEventRequest;
    @Autowired
    private ImageRequestSubscriberSendEvent imageRequestSubscriberSendEvent;
    @Value("${app.image.cloud.hub.http.post.url}")
    private String cloudHubURL;

    @Value("${app.image.orchestrator.hub.callback.url}")
    private String callBackURL;
    public void invokeImageRequestMapper(String rawJson) {
        try {
            edaPublishCreateImageEventRequest = jsonMapper.readValue(rawJson, EDAPublishCreateImageEventRequest.class);
        } catch (JsonProcessingException e) {
            logger.info("Error :::" + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("=== Print TransactionId : " + edaPublishCreateImageEventRequest.getTransactionId());
        logger.info("=== Print ImageFileName : " + edaPublishCreateImageEventRequest.getImage().getImageFileName());


        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("correlationId",edaPublishCreateImageEventRequest.getCorrelationId());
        req_payload.put("transactionId",edaPublishCreateImageEventRequest.getTransactionId());
        req_payload.put("imageId",edaPublishCreateImageEventRequest.getImage().getImageId());
        req_payload.put("userId",edaPublishCreateImageEventRequest.getImage().getUserId());
        req_payload.put("imageFileName",edaPublishCreateImageEventRequest.getImage().getImageFileName());
        req_payload.put("imageType",edaPublishCreateImageEventRequest.getImage().getImageType());
        req_payload.put("callback_url", callBackURL);

        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
        String url = cloudHubURL;

        ResponseEntity<?> response = new RestTemplate().postForEntity(url, request, String.class);

    }
}
