package com.ms.image.stream.requestor.api.solaceService.ImageResponseEventSubscriber;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.solacesystems.jcsmp.JCSMPFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import javax.jms.*;

public class ImageResponseEventSubscriberImpl implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ImageResponseEventSubscriberImpl.class);

    @Bean
    public ImageResponseEventSubscriberImpl jmsMessageListener() {
        return new ImageResponseEventSubscriberImpl();
    }

    @Override
    public void onMessage(Message message) {
        String messageData;
        if (message instanceof TextMessage) {
            logger.info("<-- TextMessage Type Detected -->");
            TextMessage textMessage = (TextMessage) message;
            try {
                messageData = textMessage.getText();
                logger.info("Payload -->"+messageData);
                logger.info("Successfully parsed solace message to object.");
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        else if(message instanceof BytesMessage)
        {
            logger.info("<-- ByteMessage Type Detected -->");
            try {
                BytesMessage byteMessage = (BytesMessage) message;
                byte[] byteData = new byte[(int) byteMessage.getBodyLength()];
                byteMessage.readBytes(byteData);
                byteMessage.reset();
                messageData =  new String(byteData);
                //logger.info("step8 -->"+byteData.toString());
                logger.info("Payload 8-->"+messageData);
                ObjectMapper objectMapper = new ObjectMapper();
                logger.info("8 Successfully parsed solace message to object.");
                // messageService.processSolaceMessage(customer);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        else {
            logger.info(message.toString());
            logger.info("Invalid message. Skipping ....");
        }

    }
}
