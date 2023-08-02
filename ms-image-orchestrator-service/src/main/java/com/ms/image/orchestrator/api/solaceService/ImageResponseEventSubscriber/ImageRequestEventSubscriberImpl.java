package com.ms.image.orchestrator.api.solaceService.ImageResponseEventSubscriber;//package com.ms.image.stream.requestor.api.solaceService.ImageResponseEventSubscriber;


import com.solacesystems.jcsmp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class ImageRequestEventSubscriberImpl implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ImageRequestEventSubscriberImpl.class);

    @Value("${eda.poc.image.service.provider.request}")
    private String queueName;
    @Autowired
    private SpringJCSMPFactory solaceFactory;
    @Autowired
    private ImageRequestSubscriberOrchv2 imageRequestSubscriberOrch;

    public void run(String... strings) throws Exception {
        final JCSMPSession session = solaceFactory.createSession();
        session.connect();

        final EndpointProperties endpointProps = new EndpointProperties();
        endpointProps.setPermission(EndpointProperties.PERMISSION_CONSUME);
        endpointProps.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);

        final Queue queue = JCSMPFactory.onlyInstance().createQueue(queueName);
        session.provision(queue, endpointProps, JCSMPSession.FLAG_IGNORE_ALREADY_EXISTS);

        final CountDownLatch latch = new CountDownLatch(1);
        logger.info("ImageRequestEventSubscriberImpl Attempting to bind to the queue '%s' .%n", queueName);

        // Create a Flow be able to bind to and consume messages from the Queue.
        final ConsumerFlowProperties flow_prop = new ConsumerFlowProperties();
        flow_prop.setEndpoint(queue);
        flow_prop.setAckMode(JCSMPProperties.SUPPORTED_MESSAGE_ACK_CLIENT);

        EndpointProperties endpoint_props = new EndpointProperties();
        endpoint_props.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);

        final FlowReceiver cons = session.createFlow(new XMLMessageListener() {
            @Override
            public void onReceive(BytesXMLMessage msg) {
                if (msg instanceof TextMessage) {
                    logger.info("TextMessage received: "+ ((TextMessage) msg).getText());
                    msg.ackMessage();
                    imageRequestSubscriberOrch.invokeImageRequestMapper(((TextMessage) msg).getText());
                } else {
                    msg.ackMessage();
                    logger.info("ByteMessage received");
                    logger.info("Unsupported type for this interface should be ByteMessage Type");
                }
                msg.ackMessage();
            }

            @Override
            public void onException(JCSMPException e) {
                logger.info("ImageRequestEventSubscriberImpl Consumer received exception: %s%n", e);
                // latch.countDown(); // unblock main thread
            }
        }, flow_prop, endpoint_props);

        // Start the consumer on the Response Queue
        logger.info("ImageRequestEventSubscriberImpl Connected. Awaiting message ...");
        cons.start();

        try {
            latch.await(); // block here until message received, and latch will flip
        } catch (InterruptedException e) {
            logger.error("Err:ImageRequestEventSubscriberImpl - " + e.getStackTrace());
        }
        // Close consumer
        cons.close();
        logger.info("Exiting ImageRequestEventSubscriberImpl");
        session.closeSession();
    }
}
