package com.github.kaczors.home.light;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LightConfiguration {

    @Bean
    public Switch aSwitch(@Value("${front-light.switch.ip}") String switchIp) {
        return new Esp8266NetworkSwitch(switchIp);
//        return new InMemorySwitch();
    }

    @Bean
    public FrontLight frontLight(Switch aSwitch) {
        return new FrontLight(aSwitch);
    }
}
