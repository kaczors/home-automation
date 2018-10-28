package com.github.kaczors.home.light;

interface Switch {

    void acceptAction(Action action);

    Status getStatus();
}
