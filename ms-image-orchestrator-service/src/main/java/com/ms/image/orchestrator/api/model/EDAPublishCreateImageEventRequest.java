package com.ms.image.orchestrator.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;


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

        @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSS", locale = "en_SG", timezone = "Asia/Singapore")
        @JsonSerialize(using = ZonedDateTimeSerializer.class)
        private ZonedDateTime requestDateTime;
    }

}
