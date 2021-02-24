package com.test.kambi;

import com.test.kambi.handler.LiveEventCheckingHandler;

public class MainApplication {

    public static void main(String[] args) {

        if(args.length < 1){
            System.out.println("Please specify the event ID as the first argument.");
            return;
        }
        long targetEventId = Long.parseLong(args[0]);
        LiveEventCheckingHandler liveEventCheckingHandler = new LiveEventCheckingHandler();
        liveEventCheckingHandler.start(targetEventId);
    }
}
