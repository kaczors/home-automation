package com.github.kaczors.home.light;

import org.springframework.stereotype.Component;

@Component
class FrontLight {

    private final RemoteFrontLightAdapter remoteFrontLight;

    FrontLight(RemoteFrontLightAdapter remoteFrontLight) {
        this.remoteFrontLight = remoteFrontLight;
    }

    public void acceptAction(Action action) {
        remoteFrontLight.acceptAction(action);
    }


    public Status getStatus() {
        return remoteFrontLight.getStatus();
    }
}
