package com.millenium.falcon.service;

import com.millenium.falcon.model.Target;
import com.millenium.falcon.repository.TargetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(timeout = 2)
public class GunServiceFalconShooter implements GunService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GunServiceFalconShooter.class);

    @Autowired
    private TargetRepository targetRepository;

    @Override
    public Target shoot(Target target) {
        return targetRepository.save(target);
    }
}
