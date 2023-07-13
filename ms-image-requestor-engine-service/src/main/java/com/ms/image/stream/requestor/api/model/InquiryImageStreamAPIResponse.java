package com.ms.image.stream.requestor.api.model;

import com.ms.image.stream.requestor.api.entity.ImageRequestorService;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InquiryImageStreamAPIResponse {

    private List<ImageRequestorService> imageRequestorService;
}
