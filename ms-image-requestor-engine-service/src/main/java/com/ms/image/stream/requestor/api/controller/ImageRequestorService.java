package com.ms.image.stream.requestor.api.controller;

import com.ms.image.stream.requestor.api.model.CreateImageStreamAPIRequest;
import com.ms.image.stream.requestor.api.model.CreateImageStreamAPIResponse;
import com.ms.image.stream.requestor.api.model.InquiryImageStreamAPIRequest;
import com.ms.image.stream.requestor.api.model.InquiryImageStreamAPIResponse;
import com.ms.image.stream.requestor.api.service.createImageStream.CreateImageStream;
import com.ms.image.stream.requestor.api.service.inquiryImageStream.InquiryImageStream;
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
public class ImageRequestorService {
    @Autowired
    private CreateImageStream createImageStream;
    @Autowired
    private InquiryImageStream inquiryImageStream;

    @RequestMapping(value = "/createimagestream", consumes = "application/json",
            produces = "application/json", method = RequestMethod.POST)
    public CreateImageStreamAPIResponse createOrder(
            //@Valid
            @RequestBody CreateImageStreamAPIRequest apiRequest) {
        return createImageStream.createImageStream(apiRequest);
    }

    @RequestMapping(value = "/inquiryimagestream", consumes = "application/json",
            produces = "application/json", method = RequestMethod.POST)
    public InquiryImageStreamAPIResponse InquiryCustomerMaster(
            //@Valid
            @RequestBody InquiryImageStreamAPIRequest apiRequest) {
        return inquiryImageStream.inquiryImageStream(apiRequest);
    }

}
