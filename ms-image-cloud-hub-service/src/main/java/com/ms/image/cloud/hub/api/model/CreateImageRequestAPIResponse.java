package com.ms.image.cloud.hub.api.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateImageRequestAPIResponse {

    private String correlationId;
    private String transactionId;
    private String status;
    private String description;

}
