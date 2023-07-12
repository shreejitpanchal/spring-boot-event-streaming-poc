package com.ms.image.requestor.api.service.createorder;

import com.ms.image.requestor.api.model.CreateOrderAPIRequest;
import com.ms.image.requestor.api.model.CreateOrderAPIResponse;

public interface CreateOrder {
    CreateOrderAPIResponse createOrder(CreateOrderAPIRequest apiRequest);
}
