package com.github.kaczors.home.light;

import lombok.Data;

@Data
class ActionRequest {
    private Action action;
    private long actionTimeout = 0;
}
