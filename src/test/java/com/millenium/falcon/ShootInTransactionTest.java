package com.millenium.falcon;

import com.millenium.falcon.repository.TargetRepository;
import com.millenium.falcon.service.GunService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
//Could simplify some actions over cleanups
@DirtiesContext
public class ShootInTransactionTest extends AbstractIntegrationTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @SpyBean
    private GunService gunService;

    @Autowired
    private TargetRepository repository;

    @Value("${queue.target.name}")
    private String destination;

    @Test
    public void transactedTest() throws InterruptedException {
        String targetName = "Death Star";
        int cordX = 5;
        int cordY = 7;
        String msg = targetName + "|" + cordX + "|" + cordY;
        assertFalse(repository.existsById(targetName).block());

        doThrow(RuntimeException.class)
                .doCallRealMethod()
                .when(gunService)
                .shoot(any());

        this.jmsTemplate.convertAndSend(destination, msg);
        jmsTemplate.setReceiveTimeout(5_000);


        verify(gunService, times(2)).shoot(any());

       assertTrue(repository.existsById(targetName).block());
    }

}
