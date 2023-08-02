package com.ms.image.cloud.hub.api.service.commonServices;

import com.ms.image.cloud.hub.api.model.CreateImageRequestAPIRequest;
import com.ms.image.cloud.hub.api.service.createImageRequest.CreateImageRequestImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class PostRequest {
    Logger logger = LoggerFactory.getLogger(PostRequest.class);

    public void sendPost(CreateImageRequestAPIRequest apiRequest, byte[] imageRawString) {
        logger.info("API === Post Request ==> Start");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(imageRawString, headers);

        Map map = new HashMap<String, String>();
        //map.put("Content-Type", "application/json");

        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("correlationId",apiRequest.getCorrelationId());
        req_payload.put("transactionId",apiRequest.getTransactionId());
        req_payload.put("imageId",apiRequest.getImageId());
        req_payload.put("userId",apiRequest.getUserId());
        req_payload.put("imageFileName",apiRequest.getImageFileName());
        req_payload.put("imageFileType",apiRequest.getImageType());
        req_payload.put("imageRawString",fileEntity.getBody());

        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
        String url = apiRequest.getCallback_url();

        ResponseEntity<?> response = new RestTemplate().postForEntity(url, request, String.class);
        response.getBody();
        System.out.println("PostMethod CallBackURL:::>"+apiRequest.getCallback_url());

        logger.info("API === Post Request ==> End");
    }
}
