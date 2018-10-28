package com.github.kaczors.home.light;

import lombok.extern.java.Log;

@Log
class InMemorySwitch implements Switch {

    private Status status = Status.OFF;

    @Override
    public void acceptAction(Action action) {
        log.info("Switch action: " + action);
        status = action == Action.TURN_ON ? Status.ON : Status.OFF;
    }

    @Override
    public Status getStatus() {
        return status;
    }
}
