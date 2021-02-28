package com.test.kambi.task;

import com.test.kambi.handler.NetworkHandler;
import com.test.kambi.handler.ResponseHandler;

import java.io.IOException;
import java.util.Optional;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * This class acts as the 'task' that runs on scheduled events by the Timer.
 * It coordinate the 'Connecting to API' and 'Processing the response'.
 */
public class EventCheckerTask extends TimerTask {

    private long targetEventId;

    private NetworkHandler networkHandler;

    private ResponseHandler responseHandler;

    public EventCheckerTask(NetworkHandler networkHandler, ResponseHandler responseHandler, long targetEventId) {
        this.networkHandler = networkHandler;
        this.targetEventId = targetEventId;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        try {
            Optional<String> responseBody = networkHandler.getApiResponseBody();
            if (responseBody.isPresent()) {
                responseHandler.handleResponseBody(responseBody.get(), this.targetEventId);
            } else {
                System.out.println("Empty response returned from the server.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while parsing the response body.");
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("An error occurred while communicating with the API server.");
        } catch (TimeoutException e) {
            System.out.println("Timeout occurred while reading the response.");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
