package com.test.kambi.handler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Optional;

public class NetworkHandler {

    private static final String LIVE_EVENTS_URL = "https://eu-offering.kambicdn.org/offering/v2018/ubse/event/live/open.json";

    private static final String MESSAGE_TYPE = "application/json";

    private static final Integer TIMEOUT_IN_SECONDS = 8;

    private static NetworkHandler networkHandler;

    private HttpClient httpClient;

    private HttpRequest httpRequest;

    private NetworkHandler() {
    }

    public static NetworkHandler getInstance() {
        if (networkHandler == null) {
            synchronized(NetworkHandler.class){
                networkHandler = new NetworkHandler();
            }
        }
        return networkHandler;
    }

    public void initialize() {
        this.httpClient = HttpClient.newHttpClient();
        this.httpRequest = HttpRequest.newBuilder()
                .GET()
                .timeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS))
                .header("accept", MESSAGE_TYPE)
                .uri(URI.create(LIVE_EVENTS_URL))
                .build();
    }

    public Optional<HttpClient> getHttpClient() {
        return Optional.of(this.httpClient);
    }

    public Optional<HttpRequest> getHttpRequestObject() {
        return Optional.of(this.httpRequest);
    }
}
