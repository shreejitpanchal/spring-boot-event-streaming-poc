package com.ms.image.stream.requestor.api.service.inquiryImageStream;

import com.ms.image.stream.requestor.api.model.InquiryImageStreamAPIRequest;
import com.ms.image.stream.requestor.api.model.InquiryImageStreamAPIResponse;

public interface InquiryImageStream {
    InquiryImageStreamAPIResponse inquiryImageStream(InquiryImageStreamAPIRequest apiResponse);
}
