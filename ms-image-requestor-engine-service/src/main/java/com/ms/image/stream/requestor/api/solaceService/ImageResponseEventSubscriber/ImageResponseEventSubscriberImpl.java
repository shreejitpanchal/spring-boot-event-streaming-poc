package com.ms.image.stream.requestor.api.solaceService.ImageResponseEventSubscriber;//package com.ms.image.stream.requestor.api.solaceService.ImageResponseEventSubscriber;


import com.ms.image.stream.requestor.api.solaceService.commonServices.UpdateImageStreamResponse;
import com.solacesystems.jcsmp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.concurrent.CountDownLatch;

@Service
public class ImageResponseEventSubscriberImpl implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ImageResponseEventSubscriberImpl.class);
    public String imagePresentFlag;
    String fileLocation;
    @Value("${app.image.requestor.temp.file.holder.path}")
    private String subFileUploadDir;
    @Value("${eda.poc.image.service.provider.response}")
    private String queueName;
    @Autowired
    private SpringJCSMPFactory solaceFactory;
    @Autowired
    private UpdateImageStreamResponse updateImageStreamResponse;

    public void run(String... strings) throws Exception {
        final JCSMPSession session = solaceFactory.createSession();
        session.connect();

        final EndpointProperties endpointProps = new EndpointProperties();
        endpointProps.setPermission(EndpointProperties.PERMISSION_CONSUME);
        endpointProps.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);

        final Queue queue = JCSMPFactory.onlyInstance().createQueue(queueName);
        session.provision(queue, endpointProps, JCSMPSession.FLAG_IGNORE_ALREADY_EXISTS);

        final CountDownLatch latch = new CountDownLatch(1);
        logger.info("ImageResponseEventSubscriberImpl Attempting to bind to the queue '%s' .%n", queueName);

        // Create a Flow be able to bind to and consume messages from the Queue.
        final ConsumerFlowProperties flow_prop = new ConsumerFlowProperties();
        flow_prop.setEndpoint(queue);
        flow_prop.setAckMode(JCSMPProperties.SUPPORTED_MESSAGE_ACK_CLIENT);

        EndpointProperties endpoint_props = new EndpointProperties();
        endpoint_props.setAccessType(EndpointProperties.ACCESSTYPE_EXCLUSIVE);

        final FlowReceiver cons = session.createFlow(new XMLMessageListener() {
            @Override
            public void onReceive(BytesXMLMessage msg) {
                imagePresentFlag = "false";
                String localSubFileUploadDir = "";
                if (msg instanceof TextMessage) {
                    logger.info("TextMessage received: '%s'%n", ((TextMessage) msg).getText());
                    logger.info("Unsupported type for this interface should be ByteMessage Type");
                } else {
                    try {
                        logger.info("Property Value of JMS_Solace_HTTP_field_transcationid -->" + msg.getProperties().getString("JMS_Solace_HTTP_field_transcationid"));
                        logger.info("Property Value of Solace_fileName.fileExtension -->" + msg.getProperties().getString("Solace_fileName.fileExtension"));
                        logger.info("Property Value of Solace_imagePresentFlag -->" + msg.getProperties().getString("Solace_imagePresentFlag"));
                        imagePresentFlag = msg.getProperties().getString("Solace_imagePresentFlag");

                        localSubFileUploadDir = subFileUploadDir + msg.getProperties().getString("Solace_fileName.fileExtension");
                    } catch (SDTException e) {
                        throw new RuntimeException(e);
                    }
                    logger.info("Message received.");
                    msg.ackMessage();
                    if (imagePresentFlag.equals("true")) {
                        logger.info("::: Image found at remote site :::");
                        File file = new File(localSubFileUploadDir);
                        try
                                (FileOutputStream fos = new FileOutputStream(file);
                                 BufferedOutputStream bos = new BufferedOutputStream(fos);
                                 DataOutputStream dos = new DataOutputStream(bos)) {
                            dos.write(((BytesMessage) msg).getData()); // Write Bytes to File Stream
                            logger.info("Successfully written data to the file");

                            // update to DB
                            updateImageStreamResponse.updateImage(msg, "true");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        logger.info("::: No Image found at remote site :::");
                        // update to DB
                        updateImageStreamResponse.updateImage(msg, "false");
                    }
                }
                msg.ackMessage();
            }

            @Override
            public void onException(JCSMPException e) {
                logger.info("ImageResponseEventSubscriberImpl Consumer received exception: %s%n", e);
                // latch.countDown(); // unblock main thread
            }
        }, flow_prop, endpoint_props);

        // Start the consumer on the Response Queue
        logger.info("ImageResponseEventSubscriberImpl Connected. Awaiting message ...");
        cons.start();

        try {
            latch.await(); // block here until message received, and latch will flip
        } catch (InterruptedException e) {
            logger.error("Err:ImageResponseEventSubscriberImpl - " + e.getStackTrace());
        }
        // Close consumer
        cons.close();
        logger.info("Exiting ImageResponseEventSubscriberImpl");
        session.closeSession();
    }
}
