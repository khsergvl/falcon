package com.millenium.falcon;

import com.millenium.falcon.service.TargetShootAcknowledgementSupportService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@Configuration
public class ShootAcknowlegementConfig {

    @Bean
    public TargetShootAcknowledgementSupportService targetShootAcknowledgementSupportService() {
        return new TargetShootAcknowledgementSupportService() {
            private final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);
            @Override
            public CountDownLatch getCdl() {
                return COUNT_DOWN_LATCH;
            }
        };
    }

}
