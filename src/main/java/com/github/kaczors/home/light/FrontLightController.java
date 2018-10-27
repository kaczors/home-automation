package com.github.kaczors.home.light;

import lombok.extern.java.Log;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping(value = "/api/front-light", produces = MediaType.APPLICATION_JSON_VALUE)
class FrontLightController {

    private final FrontLight frontLight;

    FrontLightController(FrontLight frontLight) {
        this.frontLight = frontLight;
    }

    @PostMapping("actions")
    public void addAction(@RequestBody ActionRequest actionRequest) {
        log.info("Action requested " + actionRequest);
        frontLight.acceptAction(actionRequest.getAction());
    }

    @GetMapping("status")
    public Status getStatus() {
        return frontLight.getStatus();
    }

}
