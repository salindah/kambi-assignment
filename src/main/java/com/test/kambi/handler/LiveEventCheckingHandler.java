package com.test.kambi.handler;

import com.test.kambi.task.EventCheckerTask;

import java.util.Timer;

/**
 * This class is responsible for scheduling a task to poll the API and display results
 * for the specified frequency.
 */
public class LiveEventCheckingHandler {

    private static final long FREQUENCY_IN_MILLISECONDS = 10000;

    public void start(long targetEventId) {

        NetworkHandler networkHandler = new NetworkHandler();
        ResponseHandler responseHandler = new ResponseHandler();
        Timer timer = new Timer();
        timer.schedule(new EventCheckerTask(networkHandler, responseHandler, targetEventId),
                0, FREQUENCY_IN_MILLISECONDS);
    }
}
