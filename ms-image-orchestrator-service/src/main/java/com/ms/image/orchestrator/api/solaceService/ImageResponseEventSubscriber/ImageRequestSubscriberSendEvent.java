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
 * Persist Records to DB creating order
 *
 * @author Shreejit Panchal
 * @version 1.0
 * @date 11/07/2023
 */
@Service
public class ImageRequestSubscriberSendEvent {

    Logger logger = LoggerFactory.getLogger(ImageRequestSubscriberSendEvent.class);

    @Value("${eda.poc.image.service.reply.publish.topic}")
    private String topicName;
    @Autowired
    private SpringJCSMPFactory solaceFactory;
//    @Autowired(required = false)
//    private JCSMPProperties jcsmpProperties;

    @Autowired
    private ConfigPublishEventHandler configPublishEventHandler;

    public boolean sendEvent(EDAPublishCreateImageEventRequest apiRequest) {
        logger.info("ImageRequestSubscriberSendEvent API === Request ==> Start");
        topicName += apiRequest.getImage().getImageId() + "/" + apiRequest.getImage().getUserId() + "/" + apiRequest.getImage().getImageFileName() + "/" + apiRequest.getImage().getImageType(); // Add ImageFileName and Image Type in Topic Hierarchy of the event
        final Topic topic = JCSMPFactory.onlyInstance().createTopic(topicName);
        String absolutePath ="C:/temp/imagehub/"+apiRequest.getImage().getImageFileName()+"."+apiRequest.getImage().getImageType();
        logger.info("absolutePath log ==>"+absolutePath);
        Path path = Paths.get(absolutePath);
        byte[] data = new byte[0];

        try {
            data = Files.readAllBytes(path);
        } catch (Exception e) {
            logger.error("ImageRequestSubscriberSendEvent Error while reading file - " + e.getStackTrace());
        }

        logger.info("ImageRequestSubscriberSendEvent Step 2 === EDA Topic publish on : " + topicName );

        try {

            final JCSMPSession session = solaceFactory.createSession();
            XMLMessageProducer pubEventObj = session.getMessageProducer(configPublishEventHandler);
            BytesMessage jcsmpMsg = JCSMPFactory.onlyInstance().createMessage(BytesMessage.class);
            jcsmpMsg.setData(data);

            SDTMap map = pubEventObj.createMap();
            map.putString("JMS_Solace_HTTP_field_transcationid", apiRequest.getTransactionId());
            map.putString("Solace_fileName.fileExtension", apiRequest.getImage().getImageFileName()+"."+apiRequest.getImage().getImageType());

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
