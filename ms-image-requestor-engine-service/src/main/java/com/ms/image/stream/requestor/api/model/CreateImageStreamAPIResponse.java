package com.ms.image.stream.requestor.api.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateImageStreamAPIResponse {

    private String correlationId;
    private String transactionId;
    private imageResponse imageResponse;

    @Component
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class imageResponse {
        private String imageId;
        private String userId;
        private String customerName;
        private String imageFileName;
        private ZonedDateTime requestDateTime;
        private String imageStreamStatus;
        private String imageStreamDesc;
    }
}
