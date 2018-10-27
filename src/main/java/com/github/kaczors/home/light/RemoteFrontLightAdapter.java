package com.github.kaczors.home.light;

interface RemoteFrontLightAdapter {

    void acceptAction(Action action);

    Status getStatus();
}
