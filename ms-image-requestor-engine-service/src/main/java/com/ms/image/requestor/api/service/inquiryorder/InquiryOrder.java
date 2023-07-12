package com.ms.image.requestor.api.service.inquiryorder;

import com.ms.image.requestor.api.model.InquiryOrderAPIRequest;
import com.ms.image.requestor.api.model.InquiryOrderAPIResponse;

public interface InquiryOrder {
    InquiryOrderAPIResponse inquiryOrder(InquiryOrderAPIRequest apiResponse);
}
