package com.ms.image.orchestrator.api.solaceService.ImageResponseEventSubscriber;

import com.ms.image.orchestrator.api.model.EDAPublishCreateImageEventRequest;
import com.ms.image.orchestrator.api.solaceService.commonServices.ConfigPublishEventHandler;
import com.solacesystems.jcsmp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Shreejit Panchal
 * @version 1.0
 * @date 11/07/2023
 */
@Service
public class ImageRequestSubscriberSendEvent {
    @Value("${app.image.orchestrator.hub.path}")
    private String imageHubFolder;
    Logger logger = LoggerFactory.getLogger(ImageRequestSubscriberSendEvent.class);
    @Value("${eda.poc.image.service.reply.publish.topic}")
    private String topicName;
    @Autowired
    private SpringJCSMPFactory solaceFactory;

    public boolean imagePresentFlag;
    @Autowired
    private ConfigPublishEventHandler configPublishEventHandler;

    public boolean sendEvent(EDAPublishCreateImageEventRequest apiRequest) {
        imagePresentFlag=true;
        byte[] data = new byte[0];
        logger.info("ImageRequestSubscriberSendEvent API === Request ==> Start");
        String localTopicName = topicName + apiRequest.getImage().getImageId() + "/" + apiRequest.getImage().getUserId() + "/" + apiRequest.getImage().getImageFileName() + "/" + apiRequest.getImage().getImageType(); // Add ImageFileName and Image Type in Topic Hierarchy of the event
        final Topic topic = JCSMPFactory.onlyInstance().createTopic(localTopicName);

        String localFileHolder = imageHubFolder + apiRequest.getImage().getImageFileName() + "." + apiRequest.getImage().getImageType();
        logger.info("Loading Image from Hub via path ==>" + localFileHolder);
        Path path = Paths.get(localFileHolder);

        try {
            data = Files.readAllBytes(path);
        } catch (Exception e) {
            imagePresentFlag=false;
            logger.error("ImageRequestSubscriberSendEvent Error while reading file - " + e.getStackTrace());
        }

        logger.info("ImageRequestSubscriberSendEvent Step 2 === EDA Topic publish on : " + localTopicName);

        try {

            final JCSMPSession session = solaceFactory.createSession();
            XMLMessageProducer pubEventObj = session.getMessageProducer(configPublishEventHandler);
            BytesMessage jcsmpMsg = JCSMPFactory.onlyInstance().createMessage(BytesMessage.class);
            jcsmpMsg.setData(data);

            SDTMap map = pubEventObj.createMap();
            map.putString("JMS_Solace_HTTP_field_transcationid", apiRequest.getTransactionId());
            map.putString("Solace_transcationid", apiRequest.getTransactionId());
            map.putString("Solace_fileName.fileExtension", apiRequest.getImage().getImageFileName() + "." + apiRequest.getImage().getImageType());
            if(imagePresentFlag) {
                logger.info(":: Image Loaded Successfully ::");
                map.putString("Solace_imagePresentFlag", "true");
            }
            else {
                logger.info(":: Image not found ::");
                map.putString("Solace_imagePresentFlag", "false");
            }

            jcsmpMsg.setProperties(map);
            jcsmpMsg.setDeliveryMode(DeliveryMode.PERSISTENT);

            pubEventObj.send(jcsmpMsg, topic);
            logger.info("ImageRequestSubscriberSendEvent Step 2.1 === EDA Publish Successful ==> End");
            return true;

        } catch (Exception e) {
            logger.info("ImageRequestSubscriberSendEvent Step-2-Err ===Error in SendEvent Error :" + e.getMessage());
            return false;
        }
    }
}
