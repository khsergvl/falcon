package com.millenium.falcon.controller;

import com.millenium.falcon.model.Target;
import com.millenium.falcon.service.GunService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShootController {
    private GunService gunService;

    public ShootController(GunService gunService) {
        this.gunService = gunService;
    }

    @PostMapping(value = "/shoot")
    public Target makeShoot(@RequestBody Target target) {
        return gunService.shoot(target);
    }
}
