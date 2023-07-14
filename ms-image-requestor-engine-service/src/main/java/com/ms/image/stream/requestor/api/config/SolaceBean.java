//package com.ms.image.stream.requestor.api.config;
//
//import com.ms.image.stream.requestor.api.model.SolaceJmsExceptionListener;
//import com.ms.image.stream.requestor.api.solaceService.ImageResponseEventSubscriber.ImageResponseEventSubscriberImpl;
//import com.solacesystems.jms.SolConnectionFactory;
//import com.solacesystems.jms.SolJmsUtility;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import javax.jms.Connection;
//import javax.jms.MessageConsumer;
//import javax.jms.Queue;
//
///**
// * Config class to configure Solace Endpoints Queue/Topic Consumer
// *
// * @author Shreejit Panchal
// * @version 1.0
// * @date 11/07/2023
// */
//@Configuration
//public class SolaceBean {
//
//    private static final Logger logger = LoggerFactory.getLogger(SolaceBean.class);
//
//    @Autowired
//    private Environment environment;
//
//    @Autowired
//    private SolaceJmsExceptionListener exceptionListener;
//
//    @Bean
//    public SolConnectionFactory solConnectionFactory() throws Exception {
//        SolConnectionFactory connectionFactory = SolJmsUtility.createConnectionFactory();
//        connectionFactory.setHost(environment.getProperty("solace.java.host"));
//        connectionFactory.setVPN(environment.getProperty("solace.java.msgVpn"));
//        connectionFactory.setUsername(environment.getProperty("solace.java.clientUsername"));
//        connectionFactory.setPassword(environment.getProperty("solace.java.clientPassword"));
//        connectionFactory.setClientID(environment.getProperty("spring.application.name"));
//        return connectionFactory;
//    }
//
//    @Bean
//    public ImageResponseEventSubscriberImpl imageResponseJmsMessageListener() {
//        return new ImageResponseEventSubscriberImpl();
//    }
//
//    @Bean(destroyMethod = "close")
//    public Connection connection() {
//        Connection connection = null;
//        javax.jms.Session session;
//        try {
//            connection = solConnectionFactory().createConnection();
//            session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
//            Queue queue = session.createQueue(environment.getProperty("eda.poc.image.service.provider.response"));
//            MessageConsumer messageConsumer = session.createConsumer(queue);
//            messageConsumer.setMessageListener(imageResponseJmsMessageListener());
//            connection.setExceptionListener(exceptionListener);
//            connection.start();
//            logger.info("Connected. Awaiting message...");
//        } catch (Exception e) {
//            logger.info("JMS connection failed with Solace." + e.getMessage());
//            e.printStackTrace();
//        }
//        return connection;
//    }
//}
