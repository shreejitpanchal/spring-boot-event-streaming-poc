package com.ms.image.orchestrator.api.solaceService.ImageResponseEventSubscriber;

import com.ms.image.orchestrator.api.model.EDAPublishCreateImageEventRequest;
import com.ms.image.orchestrator.api.model.EDARESTRequest;
import com.ms.image.orchestrator.api.solaceService.commonServices.ConfigPublishEventHandler;
import com.solacesystems.jcsmp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * @author Shreejit Panchal
 * @version 1.0
 * @date 11/07/2023
 */
@Service
public class ImageRequestSubscriberSendEventv2 {
    @Value("${app.image.orchestrator.hub.path}")
    private String imageHubFolder;
    Logger logger = LoggerFactory.getLogger(ImageRequestSubscriberSendEventv2.class);
    @Value("${eda.poc.image.service.reply.publish.topic}")
    private String topicName;
    @Autowired
    private SpringJCSMPFactory solaceFactory;

    public boolean imagePresentFlag;
    @Autowired
    private ConfigPublishEventHandler configPublishEventHandler;

    public boolean sendEvent(EDARESTRequest apiRequest) {

        logger.info("ImageRequestSubscriberSendEventv2 API === Request ==> Start");
        String localTopicName = topicName + apiRequest.getImageId() + "/" + apiRequest.getUserId() + "/" + apiRequest.getImageFileName() + "/" + apiRequest.getImageFileType(); // Add ImageFileName and Image Type in Topic Hierarchy of the event
        final Topic topic = JCSMPFactory.onlyInstance().createTopic(localTopicName);

        logger.info("ImageRequestSubscriberSendEvent Step 2 === EDA Topic publish on : " + localTopicName);

        try {

            final JCSMPSession session = solaceFactory.createSession();
            XMLMessageProducer pubEventObj = session.getMessageProducer(configPublishEventHandler);
            BytesMessage jcsmpMsg = JCSMPFactory.onlyInstance().createMessage(BytesMessage.class);
            byte[] data;
            data = Base64.getDecoder().decode(apiRequest.getImageRawString());
            // below code is required when Image byte array is getting transferred as String object.
            jcsmpMsg.setData(Base64.getDecoder().decode(apiRequest.getImageRawString()));
            //jcsmpMsg.setData(apiRequest.getImageRawString().getBytes());

            SDTMap map = pubEventObj.createMap();
            map.putString("JMS_Solace_HTTP_field_transcationid", apiRequest.getTransactionId());
            map.putString("Solace_transcationid", apiRequest.getTransactionId());
            map.putString("Solace_fileName.fileExtension", apiRequest.getImageFileName() + "." + apiRequest.getImageFileType());
            map.putString("Solace_imagePresentFlag", "true");

            logger.info(":: Image Loaded Successfully ::");



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
