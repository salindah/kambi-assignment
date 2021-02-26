package com.test.kambi.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.kambi.exception.NoLiveEventsFoundException;
import com.test.kambi.exception.NoOddsFoundException;
import com.test.kambi.exception.NoTargetEventFoundException;
import com.test.kambi.model.LiveEvent;
import com.test.kambi.model.ResponseBody;
import com.test.kambi.util.PrintUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ResponseHandler {

    public void handleResponseBody(String response, long targetEventId) throws IOException {

        ResponseBody responseBody = parseResponseBody(response);
        if (responseBody.getLiveEventsOptional().isPresent()) {
            Optional<LiveEvent> targetEventOp = responseBody.getLiveEventsOptional().get()
                    .stream()
                    .filter(liveEvent -> liveEvent.isTargetEvent(targetEventId))
                    .findFirst();
            if (targetEventOp.isPresent()) {
                LiveEvent targetEvent = targetEventOp.get();
                if (targetEvent.getOddsList().isPresent()) {
                    List<Integer> currentOddsList = targetEvent.getOddsList().get();
                    OddsCheckingHandler oddsCheckingHandler = OddsCheckingHandler.getInstance();
                    if (oddsCheckingHandler.isFirstTime()) {
                        PrintUtils.printEventName(targetEvent.getEvent());
                        PrintUtils.printOddsListDetails(targetEvent.getMainBetOffer());
                        oddsCheckingHandler.setPreviousOdds(currentOddsList);
                    } else {
                        if (oddsCheckingHandler.isOddsListUpdated(currentOddsList)) {
                            PrintUtils.printOddsListDetails(targetEvent.getMainBetOffer());
                            oddsCheckingHandler.setPreviousOdds(currentOddsList);
                        }
                    }
                } else {
                    throw new NoOddsFoundException("No odds were found in the event.");
                }
            } else {
                throw new NoTargetEventFoundException("No target event was found in the response.");
            }
        } else {
            throw new NoLiveEventsFoundException("No live event entries were found in the response.");
        }
    }

    private ResponseBody parseResponseBody(String response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, ResponseBody.class);
    }
}
