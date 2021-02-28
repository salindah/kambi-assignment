package com.test.kambi.handler;

import com.test.kambi.exception.BadResponseException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * The class is responsible for connecting to the API and returning the response.
 * comments.
 */
public class NetworkHandler {

    private static final String LIVE_EVENTS_URL = "https://eu-offering.kambicdn.org/offering/v2018/ubse/event/live/open.json";

    private static final String MESSAGE_TYPE = "application/json";

    private static final Integer TIMEOUT_IN_SECONDS = 5;

    private static final Integer STATUS_CODE_OK = 200;

    private HttpClient httpClient;

    private HttpRequest httpRequest;

    public NetworkHandler(){
        initialize();
    }

    //Only used for testing purposes.
    public NetworkHandler(HttpClient httpClient, HttpRequest httpRequest){
        this.httpClient = httpClient;
        this.httpRequest = httpRequest;
    }

    private void initialize() {
        this.httpClient = HttpClient.newHttpClient();
        this.httpRequest = HttpRequest.newBuilder()
                .GET()
                .timeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS))
                .header("accept", MESSAGE_TYPE)
                .uri(URI.create(LIVE_EVENTS_URL))
                .build();
    }

    public Optional<String> getApiResponseBody() throws InterruptedException, ExecutionException, TimeoutException {

        CompletableFuture<HttpResponse<String>> response =
                httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.thenApply(HttpResponse::statusCode).get(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        //Doesn't check all the possible scenarios here. Only interested in '200 OK' response.
        if(!(STATUS_CODE_OK == statusCode)){
            throw new BadResponseException("Bad response returned from the server.");
        }
        return Optional.of(response.thenApply(HttpResponse::body).get(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS));
    }
}
