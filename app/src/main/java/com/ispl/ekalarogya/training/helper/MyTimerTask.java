package com.ispl.ekalarogya.training.helper;

import com.ispl.ekalarogya.training.app_interfaces.OnTimerChangeListener;

import java.util.TimerTask;

/**
 * Created by sonu on 24/12/17.
 */

public class MyTimerTask extends TimerTask {
    private int timeCounter = 0;
    private OnTimerChangeListener onTimerChangeListener;

    public MyTimerTask(int timeCounter, OnTimerChangeListener onTimerChangeListener) {
        this.timeCounter = timeCounter;
        this.onTimerChangeListener = onTimerChangeListener;
    }

    @Override
    public void run() {
        if (timeCounter == 0) {
            onTimerChangeListener.stopTimer();
            onTimerChangeListener.updateTimerProgress(timeCounter);
            return;
        }
        onTimerChangeListener.updateTimerProgress(timeCounter);
        timeCounter--;
    }
}
