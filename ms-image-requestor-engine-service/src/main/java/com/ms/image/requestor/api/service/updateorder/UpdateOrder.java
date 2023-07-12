package com.ms.image.requestor.api.service.updateorder;

import com.ms.image.requestor.api.model.CreateOrderAPIResponse;
import com.ms.image.requestor.api.model.UpdateOrderRequest;

public interface UpdateOrder {
    CreateOrderAPIResponse updateOrder(UpdateOrderRequest apiResponse);
}
