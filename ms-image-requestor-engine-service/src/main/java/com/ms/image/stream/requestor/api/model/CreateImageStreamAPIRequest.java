package com.ms.image.stream.requestor.api.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@Getter
public class CreateImageStreamAPIRequest {

    private String correlationId;
    private String transactionId;
    private imageRequest imageRequest;

    @Getter
    @Setter
    public class imageRequest {
        private String userId;
        private String customerName;
        private String imageFileName;
        private String imageType;
        // @NotNull(message = "Possible value jpg or bmp")
        private ZonedDateTime requestDateTime;
    }
}
