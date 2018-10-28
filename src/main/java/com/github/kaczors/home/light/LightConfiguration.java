package com.github.kaczors.home.light;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LightConfiguration {

    @Bean
    public Switch aSwitch() {
        return new InMemorySwitch();
    }

    @Bean
    public FrontLight frontLight(Switch aSwitch) {
        return new FrontLight(aSwitch);
    }
}
