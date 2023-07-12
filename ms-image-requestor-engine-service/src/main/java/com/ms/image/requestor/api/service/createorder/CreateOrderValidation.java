package com.ms.image.requestor.api.service.createorder;

import com.ms.image.requestor.api.model.CreateOrderAPIRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

/**
 * Validation class for creating order
 *
 * @author Shreejit Panchal
 * @version 1.0
 * @date 20/03/2023
 */
@Service
public class CreateOrderValidation {

    Logger logger = LoggerFactory.getLogger(CreateOrderValidation.class);
    @Value("#{'${eda.order.field.mobile.types}'.split(',')}")
    private List<String> mobileTypes;

    public boolean validateInput(CreateOrderAPIRequest apiRequest) {
        if (!mobileTypes.contains(apiRequest.getOrderRequest().getProductType().toLowerCase(Locale.ROOT))) {
            logger.info("Invalid MobileType");
            return true;
        }
        return false;
    }

}
