package com.ms.image.stream.requestor.api.service.createImageStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.image.stream.requestor.api.model.CreateImageStreamAPIRequest;
import com.ms.image.stream.requestor.api.model.EDAPublishCreateImageEventRequest;
import com.ms.image.stream.requestor.api.service.commonServices.ConfigPublishEventHandler;
import com.solacesystems.jcsmp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Persist Records to DB creating order
 *
 * @author Shreejit Panchal
 * @version 1.0
 * @date 11/07/2023
 */
@Service
public class CreateImageStreamSendEvent {

    Logger logger = LoggerFactory.getLogger(CreateImageStreamSendEvent.class);
    ObjectMapper jsonMapper = new ObjectMapper();
    @Value("${eda.poc.image.service.request.publish.topic}")
    private String topicName;
    @Autowired
    private SpringJCSMPFactory solaceFactory;
    @Autowired(required = false)
    private JCSMPProperties jcsmpProperties;
    @Autowired
    private EDAPublishCreateImageEventRequest edaPublishCreateImageEventRequest;
    @Autowired
    private ConfigPublishEventHandler configPublishEventHandler;

    public boolean sendEvent(CreateImageStreamAPIRequest apiRequest, String imageId) {
        logger.info("CreateImageStreamSendEvent API === Request ==> Start");
        String localTopicName =topicName + imageId + "/" + apiRequest.getImageRequest().getUserId() + "/" + apiRequest.getImageRequest().getImageFileName() + "/" + apiRequest.getImageRequest().getImageType(); // Add ImageFileName and Image Type in Topic Hierarchy of the event
        final Topic topic = JCSMPFactory.onlyInstance().createTopic(localTopicName);

        EDAPublishCreateImageEventRequest.image edaPublishImageDtl = new EDAPublishCreateImageEventRequest.image();

        edaPublishCreateImageEventRequest = EDAPublishCreateImageEventRequest.builder()
                .image(edaPublishImageDtl.builder()
                        .imageId(imageId)
                        .userId(apiRequest.getImageRequest().getUserId())
                        .customerName(apiRequest.getImageRequest().getCustomerName())
                        .imageFileName(apiRequest.getImageRequest().getImageFileName())
                        .imageType(apiRequest.getImageRequest().getImageType())
                        .requestDateTime(apiRequest.getImageRequest().getRequestDateTime())
                        .build())
                .transactionId(apiRequest.getTransactionId())
                .correlationId(apiRequest.getCorrelationId())
                .build();

        try {
            String eventJsonString = jsonMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(edaPublishCreateImageEventRequest);
            logger.info("CreateImageStreamSendEvent Step 2 === EDA Topic publish on : " + localTopicName + "\n jsonPayload : " + eventJsonString);

            final JCSMPSession session = solaceFactory.createSession();
            XMLMessageProducer pubEventObj = session.getMessageProducer(configPublishEventHandler);
            TextMessage jcsmpMsg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
            jcsmpMsg.setText(eventJsonString);
            jcsmpMsg.setDeliveryMode(DeliveryMode.PERSISTENT);

            pubEventObj.send(jcsmpMsg, topic);
            logger.info("CreateImageStreamSendEvent Step 2.1 === EDA Publish Successful ==> End");
            return true;

        } catch (Exception e) {
            logger.info("CreateImageStreamSendEvent Step-2-Err ===Error in SendEvent Error :" + e.getMessage());
            return false;
        }
    }
}
