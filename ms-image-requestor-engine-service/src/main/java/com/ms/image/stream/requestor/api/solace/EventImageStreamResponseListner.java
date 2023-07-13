package com.ms.image.stream.requestor.api.solace;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class EventImageStreamResponseListner implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(EventImageStreamResponseListner.class);

    @Bean
    public EventImageStreamResponseListner jmsMessageListener() {
        return new EventImageStreamResponseListner();
    }

    @Override
    public void onMessage(Message message) {
        String messageData;
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                messageData = textMessage.getText();
                logger.info(messageData);
                ObjectMapper objectMapper = new ObjectMapper();
                logger.info("Successfully parsed solace message to object.");
                // messageService.processSolaceMessage(customer);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else {
            logger.info(message.toString());
            logger.info("Invalid message. Skipping ....");
        }

    }
}
