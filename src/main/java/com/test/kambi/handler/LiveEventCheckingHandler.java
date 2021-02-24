package com.test.kambi.handler;

import com.test.kambi.task.EventCheckerTask;

import java.util.Timer;

public class LiveEventCheckingHandler {

    private static final long FREQUENCY_IN_MILLISECONDS = 10000;

    public void start(long targetEventId) {

        NetworkHandler.getInstance().initialize();
        Timer timer = new Timer();
        timer.schedule(new EventCheckerTask(targetEventId), 0, FREQUENCY_IN_MILLISECONDS);
    }
}
