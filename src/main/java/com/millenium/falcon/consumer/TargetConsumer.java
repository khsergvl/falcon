package com.millenium.falcon.consumer;

import com.millenium.falcon.model.Target;
import com.millenium.falcon.service.GunService;
import oracle.jms.AQjmsTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;

@Component
@Transactional
public class TargetConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetConsumer.class);

    public TargetConsumer(GunService gunService) {
        this.gunService = gunService;
    }

    private final GunService gunService;

    @JmsListener(containerFactory = "JMSListenerFactory", destination = "${queue.target.name}", id = "${queue.target.name}")
    public void consumeTarget(AQjmsTextMessage msg) throws JMSException {
        String text = msg.getText();
        LOGGER.info("Message : {}", text);
        String [] parsedTarget = text.split("\\|");

        Target target = new Target();
        target.setName(parsedTarget[0]);
        target.setCordX(Integer.parseInt(parsedTarget[1]));
        target.setCordX(Integer.parseInt(parsedTarget[2]));
        gunService.shoot(target);
    }
}
