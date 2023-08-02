package com.ms.image.orchestrator.api.controller;


import com.ms.image.orchestrator.api.model.EDAPublishCreateImageEventRequest;
import com.ms.image.orchestrator.api.model.EDARESTRequest;
import com.ms.image.orchestrator.api.solaceService.ImageResponseEventSubscriber.ImageRequestSubscriberSendEventv2;
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
public class ImageOrchestratorService {
    @Autowired
    private ImageRequestSubscriberSendEventv2 imageRequestSubscriberSendEventv2;
    @RequestMapping(value = "/simulatorEndpointResponse", consumes = "application/json",
            produces = "application/json", method = RequestMethod.POST)
    public void simRequest(@RequestBody EDARESTRequest apiRequest) {
        System.out.println("---> sim url activiated <---");
        imageRequestSubscriberSendEventv2.sendEvent(apiRequest);
    }

}
