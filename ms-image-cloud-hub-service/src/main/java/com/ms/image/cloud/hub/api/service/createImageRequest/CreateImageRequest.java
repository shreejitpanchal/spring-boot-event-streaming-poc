package com.ms.image.cloud.hub.api.service.createImageRequest;


import com.ms.image.cloud.hub.api.model.CreateImageRequestAPIRequest;
import com.ms.image.cloud.hub.api.model.CreateImageRequestAPIResponse;

public interface CreateImageRequest {
    CreateImageRequestAPIResponse createImageRequest(CreateImageRequestAPIRequest apiRequest);
}
