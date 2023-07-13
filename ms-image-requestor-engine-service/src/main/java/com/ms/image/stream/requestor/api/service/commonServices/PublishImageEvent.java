//package com.ms.image.stream.requestor.api.service.commonServices;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ms.image.stream.requestor.api.model.CreateImageStreamAPIRequest;
//import com.ms.image.stream.requestor.api.model.EDAPublishCreateImageEventRequest;
//import com.ms.image.stream.requestor.api.solace.HelperServices.PublishEventOnTopicHandler;
//import com.solacesystems.jcsmp.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
///**
// * Atomic Service to Generate Event onto Solace Messaging PUBSUB+
// *
// * @author Shreejit Panchal
// * @version 1.0
// * @date 11/07/2023
// */
//@Service
//public class PublishImageEvent {
//    private final PublishEventOnTopicHandler pubEventHandler = new PublishEventOnTopicHandler();
//    Logger logger = LoggerFactory.getLogger(PublishImageEvent.class);
//    ObjectMapper jsonMapper = new ObjectMapper();
//    @Autowired
//    private EDAPublishCreateImageEventRequest edaPublishCreateImageEventRequest;
//    @Value("${eda.order.create.publish.topic}")
//    private String topicName;
//    @Autowired
//    private SpringJCSMPFactory solaceFactory;
//    @Autowired(required = false)
//    private SpringJCSMPFactoryCloudFactory springJCSMPFactoryCloudFactory;
////    @Autowired(required = false)
////    private SolaceServiceCredentials solaceServiceCredentials;
//    @Autowired(required = false)
//    private JCSMPProperties jcsmpProperties;
//
//    public boolean sendEvent(CreateImageStreamAPIRequest apiRequest, String imageId) {
//        EDAPublishCreateImageEventRequest.image edaPublishImageDtl = new EDAPublishCreateImageEventRequest.image();
//
//        logger.info("SendEvent API === Request ==> Start");
//
//        topicName += apiRequest.getImageRequest().getImageType() + "/" + imageId; // Add Image Type/ImageId in Topic Hierarchy of the event
//        Topic topic = JCSMPFactory.onlyInstance().createTopic(topicName);
//        logger.info("SendEvent Step 1 === Request - CustomerName : " + apiRequest.getImageRequest().getCustomerName());
//
//        edaPublishCreateImageEventRequest = EDAPublishCreateImageEventRequest.builder()
//                    .image(edaPublishImageDtl.builder()
//                            .imageId(imageId)
//                            .userId(apiRequest.getImageRequest().getUserId())
//                            .customerName(apiRequest.getImageRequest().getCustomerName())
//                            .imageFileName(apiRequest.getImageRequest().getImageFileName())
//                            .imageType(apiRequest.getImageRequest().getImageType())
//                            .requestDateTime(apiRequest.getImageRequest().getRequestDateTime())
//                            .build())
//                    .transactionId(apiRequest.getTransactionId())
//                    .correlationId(apiRequest.getTransactionId())
//                    .build();
//
//        try {
//            String eventJsonString = jsonMapper.writerWithDefaultPrettyPrinter()
//                                    .writeValueAsString(edaPublishCreateImageEventRequest);
//            logger.info("SendEvent Step 2 === EDA Topic publish on : " + topicName + "\n jsonPayload : " + eventJsonString);
//
//            final JCSMPSession session = solaceFactory.createSession();
//            XMLMessageProducer pubEventObj = session.getMessageProducer(pubEventHandler);
//            TextMessage jcsmpMsg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
//            jcsmpMsg.setText(eventJsonString);
//            jcsmpMsg.setDeliveryMode(DeliveryMode.PERSISTENT);
//
//            pubEventObj.send(jcsmpMsg, topic);
//            logger.info("SendEvent Step 2.1 === EDA Publish Successful ==> End");
//            return true;
//
//        } catch (Exception e) {
//            logger.info("Step-2-Err ===Error in SendEvent Error :" + e.getMessage());
//            return false;
//        }
//    }
//}
