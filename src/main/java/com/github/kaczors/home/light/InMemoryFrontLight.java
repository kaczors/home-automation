package com.github.kaczors.home.light;

import org.springframework.stereotype.Component;

@Component
public class InMemoryFrontLight implements RemoteFrontLightAdapter {

    private Status status = Status.OFF;

    @Override
    public void acceptAction(Action action) {
        status = action == Action.TURN_ON ? Status.ON : Status.OFF;
    }

    @Override
    public Status getStatus() {
        return status;
    }
}
