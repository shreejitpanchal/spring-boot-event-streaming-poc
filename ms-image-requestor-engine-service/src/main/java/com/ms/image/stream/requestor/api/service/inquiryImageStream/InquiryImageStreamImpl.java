package com.ms.image.stream.requestor.api.service.inquiryImageStream;

import com.ms.image.stream.requestor.api.entity.ImageRequestorService;
import com.ms.image.stream.requestor.api.model.InquiryImageStreamAPIRequest;
import com.ms.image.stream.requestor.api.model.InquiryImageStreamAPIResponse;
import com.ms.image.stream.requestor.api.repository.ImageServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Inquiry Image Service for Inquire based on Customer Name
 *
 * @author Shreejit Panchal
 * @version 1.0
 * @date 11/07/2023
 */
@Service
public class InquiryImageStreamImpl implements InquiryImageStream {

    Logger logger = LoggerFactory.getLogger(InquiryImageStreamImpl.class);

    @Autowired
    private ImageServiceRepository imageServiceRepository;

    @Autowired
    private Optional<ImageRequestorService> customerMasterDTO;

    @Override
    public InquiryImageStreamAPIResponse inquiryImageStream(InquiryImageStreamAPIRequest apiRequest) {
        logger.info("Inquiry API === inquiryOrder Request ==> Start");
        logger.info("Step-1 === Customer Name:" + apiRequest.getCustomerName());

        //List<CustomerMaster> customerMasterCollector = customerMasterRepository.findCustomerByCustomerName(apiRequest.getCustomerName().toUpperCase());
        logger.info("Inquiry API === inquiryOrder Request ==> End");

        if (apiRequest.getCustomerName() == null || apiRequest.getCustomerName().isEmpty()) {
            return null;// InquiryImageStreamAPIResponse.builder().imageRequestorService(imageServiceRepository.findAllCustomer()).build();
        }
        return null;// InquiryImageStreamAPIResponse.builder().imageRequestorService(imageServiceRepository.findCustomerByCustomerName(apiRequest.getCustomerName().toUpperCase())).build();
    }

}
