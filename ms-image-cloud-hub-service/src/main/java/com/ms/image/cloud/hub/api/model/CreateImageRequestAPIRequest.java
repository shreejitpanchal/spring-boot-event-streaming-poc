package com.ms.image.cloud.hub.api.model;


import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CreateImageRequestAPIRequest {

    private String correlationId;
    private String transactionId;
    private String imageId;
    private String userId;
    private String imageFileName;
    private String imageType;
    private String callback_url;

}
