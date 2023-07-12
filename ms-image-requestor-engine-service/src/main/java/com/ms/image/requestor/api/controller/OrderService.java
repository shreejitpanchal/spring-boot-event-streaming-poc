package com.ms.image.requestor.api.controller;

import com.ms.image.requestor.api.model.*;
import com.ms.image.requestor.api.service.createorder.CreateOrder;
import com.ms.image.requestor.api.service.inquiryorder.InquiryOrder;
import com.ms.image.requestor.api.service.updateorder.UpdateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderService {
    @Autowired
    private CreateOrder createOrder;
    @Autowired
    private UpdateOrder updateOrder;
    @Autowired
    private InquiryOrder inquiryOrder;

    @RequestMapping(value = "/createorder", consumes = "application/json",
            produces = "application/json", method = RequestMethod.POST)
    public CreateOrderAPIResponse createOrder(
            @Valid
            @RequestBody CreateOrderAPIRequest apiRequest) {
        return createOrder.createOrder(apiRequest);
    }

    @RequestMapping(value = "/updateorder", consumes = "application/json",
            produces = "application/json", method = RequestMethod.POST)
    public CreateOrderAPIResponse updateCustomerMaster(
            @Valid
            @RequestBody UpdateOrderRequest apiRequest) {
        return updateOrder.updateOrder(apiRequest);
    }

    @RequestMapping(value = "/inquiryorder", consumes = "application/json",
            produces = "application/json", method = RequestMethod.POST)
    public InquiryOrderAPIResponse InquiryCustomerMaster(
            @Valid
            @RequestBody InquiryOrderAPIRequest apiRequest) {
        return inquiryOrder.inquiryOrder(apiRequest);
    }

}
