package com.ms.image.orchestrator.api.model;

import lombok.*;
import org.springframework.stereotype.Component;


@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EDARESTRequest {

    private String correlationId;
    private String transactionId;
    private String imageId;
    private String userId;
    private String imageFileName;
    private String imageFileType;
    private String imageRawString;
}
