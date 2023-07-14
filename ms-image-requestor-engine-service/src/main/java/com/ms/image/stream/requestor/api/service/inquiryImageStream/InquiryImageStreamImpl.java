package com.ms.image.stream.requestor.api.service.inquiryImageStream;

import com.ms.image.stream.requestor.api.entity.ImageRequestorService;
import com.ms.image.stream.requestor.api.model.InquiryImageStreamAPIRequest;
import com.ms.image.stream.requestor.api.model.InquiryImageStreamAPIResponse;
import com.ms.image.stream.requestor.api.repository.ImageRequestorServiceRepository;
import com.solacesystems.jcsmp.BytesMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private Optional<ImageRequestorService> ImageRequestorDTO;
    @Autowired
    private ImageRequestorServiceRepository imageRequestorServiceRepository;

    @Override
    public InquiryImageStreamAPIResponse inquiryImageStream(InquiryImageStreamAPIRequest apiRequest) {
        logger.info("Inquiry API === inquiryImage Request ==> Start");
        logger.info("Step-1 === transactionId:" + apiRequest.getTransactionId());

        //ImageRequestorDTO = imageRequestorServiceRepository.findImageByTransactionId(apiRequest.getTransactionId());
        //logger.info("Step-2 === Locate Database record - ImageRequestorDTO.isPresentFlag =" + ImageRequestorDTO.isPresent());

        logger.info("Inquiry API === inquiryOrder Request ==> End");

        if (apiRequest.getTransactionId() == null || apiRequest.getTransactionId().isEmpty()) {
            return InquiryImageStreamAPIResponse.builder().
                    imageRequestorService(imageRequestorServiceRepository.findAllImageRequest()).build();
        }
        return  InquiryImageStreamAPIResponse.builder().
                imageRequestorService(imageRequestorServiceRepository.findImageByTransactionId(apiRequest.getTransactionId())).build();
    }
}
