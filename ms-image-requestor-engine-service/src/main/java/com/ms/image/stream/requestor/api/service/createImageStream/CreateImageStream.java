package com.ms.image.stream.requestor.api.service.createImageStream;

import com.ms.image.stream.requestor.api.model.CreateImageStreamAPIRequest;
import com.ms.image.stream.requestor.api.model.CreateImageStreamAPIResponse;

public interface CreateImageStream {
    CreateImageStreamAPIResponse createImageStream(CreateImageStreamAPIRequest apiRequest);
}
