package com.test.kambi.task;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.kambi.handler.NetworkHandler;
import com.test.kambi.handler.OddsCheckingHandler;
import com.test.kambi.model.LiveEvent;
import com.test.kambi.model.ResponseBody;
import com.test.kambi.util.PrintUtils;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class EventCheckerTask extends TimerTask {

    private final long targetEventId;

    public EventCheckerTask(long targetEventId) {
        this.targetEventId = targetEventId;
    }

    @Override
    public void run() {
        try {
            Optional<String> responseOptional = getApiResponse();
            if (responseOptional.isPresent()) {
                String body = responseOptional.get();
                ObjectMapper mapper = new ObjectMapper();
                ResponseBody responseBody = mapper.readValue(body, ResponseBody.class);

                if (responseBody.getLiveEventsOptional().isPresent()) {
                    Optional<LiveEvent> eventOptional = responseBody.getLiveEventsOptional().get()
                            .stream()
                            .filter(liveEvent -> liveEvent.isTargetEvent(targetEventId))
                            .findFirst();
                    if (eventOptional.isPresent()) {
                        LiveEvent targetEvent = eventOptional.get();

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
                        }
                    } else {
                        System.out.println("No event entries were found in the response.");
                    }
                }
            }
        } catch (JsonParseException | JsonMappingException e) {
            System.out.println("An error occurred while parsing the response body.");
        } catch (InterruptedException | IOException | ExecutionException e) {
            System.out.println("An error occurred while communicating with the API server.");
        }
    }

    private Optional<String> getApiResponse() throws InterruptedException, ExecutionException {

        Optional<HttpClient> httpClientOptional = NetworkHandler.getInstance().getHttpClient();
        Optional<HttpRequest> httpRequestOptional = NetworkHandler.getInstance().getHttpRequestObject();
        if (httpClientOptional.isPresent() && httpRequestOptional.isPresent()) {
            HttpClient httpClient = httpClientOptional.get();
            HttpRequest httpRequest = httpRequestOptional.get();

            CompletableFuture<HttpResponse<String>> response =
                    httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
            return Optional.of(response.thenApply(HttpResponse::body).get());
        }
        return Optional.empty();
    }
}
