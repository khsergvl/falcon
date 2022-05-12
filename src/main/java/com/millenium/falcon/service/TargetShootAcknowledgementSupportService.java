package com.millenium.falcon.service;

import java.util.concurrent.CountDownLatch;

public interface TargetShootAcknowledgementSupportService {
    CountDownLatch getCdl();
}
