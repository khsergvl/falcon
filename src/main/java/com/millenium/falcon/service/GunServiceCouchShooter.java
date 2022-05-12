package com.millenium.falcon.service;

import com.millenium.falcon.model.Target;
import com.millenium.falcon.repository.TargetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GunServiceCouchShooter implements GunService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GunServiceCouchShooter.class);

    @Autowired
    private TargetRepository targetRepository;
    @Autowired(required = false)
    private TargetShootAcknowledgementSupportService targetShootAcknowledgementSupportService;

    @Override
    public void shoot(Target target) {
        targetRepository.save(target).subscribe((t) -> {
            LOGGER.info("Target {} destroyed", t.getName());
            if (targetShootAcknowledgementSupportService != null) {
                targetShootAcknowledgementSupportService.getCdl().countDown();
            }
        });
    }
}
