package com.github.kaczors.home.timer;

import java.util.Timer;
import java.util.TimerTask;

public class SingleSlotTimer {

    private Timer timer = new Timer();

    public synchronized void schedule(Runnable runnable, long delay) {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, delay);
    }

    public synchronized void cancel(){
        timer.cancel();
        timer = new Timer();
    }
}
