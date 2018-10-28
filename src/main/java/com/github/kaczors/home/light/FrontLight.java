package com.github.kaczors.home.light;

import com.github.kaczors.home.timer.SingleSlotTimer;

class FrontLight {

    private final Switch remoteFrontLight;

    private final SingleSlotTimer timer = new SingleSlotTimer();

    FrontLight(Switch remoteFrontLight) {
        this.remoteFrontLight = remoteFrontLight;
    }

    void acceptActionRequest(ActionRequest actionRequest) {
        Action action = actionRequest.getAction();
        long actionTimeout = actionRequest.getActionTimeout();

        remoteFrontLight.acceptAction(action);
        if (Action.TURN_OFF == action) {
            timer.cancel();
        } else if (actionTimeout > 0) {
            timer.schedule(() -> remoteFrontLight.acceptAction(Action.TURN_OFF), actionTimeout);
        }
    }


    Status getStatus() {
        return remoteFrontLight.getStatus();
    }
}
