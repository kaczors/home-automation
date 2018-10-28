package com.github.kaczors.home.light;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.web.client.RestTemplate;

class Esp8266NetworkSwitch implements Switch {

    private final String statesApiUrl;
    private final String currentStateApiUrl;

    private RestTemplate restTemplate = new RestTemplate();

    Esp8266NetworkSwitch(String switchIp) {
        this.statesApiUrl = switchIp + "/states";
        this.currentStateApiUrl = statesApiUrl + "/current";
    }

    @Override
    public void acceptAction(Action action) {
        String state = Action.TURN_ON == action ? "on" : "off";
        restTemplate.postForObject(statesApiUrl, new SwitchState(state), Void.class);
    }

    @Override
    public Status getStatus() {
        SwitchState stateResponse = restTemplate
            .getForObject(currentStateApiUrl, SwitchState.class);
        return "on".equals(stateResponse.state) ? Status.ON : Status.OFF;
    }

    @Data
    @AllArgsConstructor
    private static class SwitchState {
        private String state;
    }


}

