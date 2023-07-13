package com.ms.image.stream.requestor.api.service.createImageStream.supportingServices;

import com.ms.image.stream.requestor.api.model.CreateImageStreamAPIRequest;
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
 * @date 11/07/2023
 */
@Service
public class CreateImageStreamValidation {

    Logger logger = LoggerFactory.getLogger(CreateImageStreamValidation.class);
    @Value("#{'${eda.image.request.field.file.types}'.split(',')}")
    private List<String> fileTypes;

    public boolean validateInput(CreateImageStreamAPIRequest apiRequest) {
        if (!fileTypes.contains(apiRequest.getImageRequest() .getImageType().toLowerCase(Locale.ROOT))) {
            logger.info("Invalid image type");
            return true;
        }
        return false;
    }

}
