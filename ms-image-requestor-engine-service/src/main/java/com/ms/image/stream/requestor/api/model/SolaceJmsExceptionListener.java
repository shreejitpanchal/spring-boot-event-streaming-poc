package com.ms.image.stream.requestor.api.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

@Component
public class SolaceJmsExceptionListener implements ExceptionListener {

    private static final Logger logger = LoggerFactory.getLogger(SolaceJmsExceptionListener.class);

    @Override
    public void onException(JMSException e) {
        logger.error(e.getLinkedException().getMessage());
        e.printStackTrace();
    }
}
