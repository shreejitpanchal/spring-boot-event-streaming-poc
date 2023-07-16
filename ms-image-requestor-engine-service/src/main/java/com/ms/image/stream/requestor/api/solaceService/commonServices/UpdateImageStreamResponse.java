package com.ms.image.stream.requestor.api.solaceService.commonServices;

import com.ms.image.stream.requestor.api.entity.ImageRequestorService;
import com.ms.image.stream.requestor.api.repository.ImageRequestorServiceRepository;
import com.solacesystems.jcsmp.BytesMessage;
import com.solacesystems.jcsmp.BytesXMLMessage;
import com.solacesystems.jcsmp.SDTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateImageStreamResponse {
    private static final Logger logger = LoggerFactory.getLogger(UpdateImageStreamResponse.class);

    @Autowired
    private Optional<ImageRequestorService> ImageRequestorDTO;
    @Autowired
    private ImageRequestorServiceRepository imageRequestorServiceRepository;
    private String transactionID;
    public void updateImage(BytesXMLMessage msg,String imagePresentFlag){
        logger.info("updateImageStreamResponse API === updateImage Request ==> Start - imagePresentFlag ="+imagePresentFlag);
        try {
            transactionID = msg.getProperties().getString("JMS_Solace_HTTP_field_transcationid");
            logger.info("Property Value of transcationid -->" + transactionID);
            ImageRequestorDTO = imageRequestorServiceRepository.findImageByOptionalTransactionId(transactionID);
            logger.info("Step-2 === Locate Database record - ImageRequestorDTO.isPresentFlag =" + ImageRequestorDTO.isPresent());
            if(imagePresentFlag.equals("true"))
            {
                ImageRequestorDTO.get().setImageStreamStatus("EDA Response Received");
                ImageRequestorDTO.get().setImageStreamDesc("Remote Response : Image Loaded");
                ImageRequestorDTO.get().setImageRawData(((BytesMessage) msg).getData());
            }
            else {
                ImageRequestorDTO.get().setImageStreamStatus("EDA Response Received");
                ImageRequestorDTO.get().setImageStreamDesc("Remote Response : Image not found at Remote");
            }
            imageRequestorServiceRepository.save(ImageRequestorDTO.get());
            logger.info("Step-3 === updateImage Successful ==> End");
        } catch (SDTException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            logger.info("Step-2.Error === Error in Update Image to DB :" + e.getMessage());
        }

    }
}
