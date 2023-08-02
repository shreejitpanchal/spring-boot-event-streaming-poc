package com.ms.image.cloud.hub.api.controller;

import com.ms.image.cloud.hub.api.model.CreateImageRequestAPIRequest;
import com.ms.image.cloud.hub.api.model.CreateImageRequestAPIResponse;
import com.ms.image.cloud.hub.api.service.createImageRequest.CreateImageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API Controller class
 *
 * @author Shreejit Panchal
 * @version 1.0
 * @date 11/07/2023
 */
@RestController
public class CloudHubService {
    @Autowired
    private CreateImageRequest createImageRequest;

    @RequestMapping(value = "/createimagerequest", consumes = "application/json",
            produces = "application/json", method = RequestMethod.POST)
    public CreateImageRequestAPIResponse createRequest(
            //@Valid
            @RequestBody CreateImageRequestAPIRequest apiRequest) {
        return createImageRequest.createImageRequest(apiRequest);
    }

}
