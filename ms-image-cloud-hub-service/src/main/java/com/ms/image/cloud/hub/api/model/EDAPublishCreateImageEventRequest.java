package com.ms.image.cloud.hub.api.model;

import lombok.*;
import org.springframework.stereotype.Component;


@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EDAPublishCreateImageEventRequest {

    private String correlationId;
    private String transactionId;
    private image image;

    @Getter
    @Setter
    @Builder
    @Component
    @NoArgsConstructor
    @AllArgsConstructor
    public static class image {
        private String imageId;
        private String userId;
        private String customerName;
        private String imageFileName;
        private String imageType;

        //        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//        @JsonSerialize(using = ZonedDateTimeSerializer.class)
        private String requestDateTime;
    }

}
