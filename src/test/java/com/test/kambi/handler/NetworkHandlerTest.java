package com.test.kambi.handler;

import com.test.kambi.exception.BadResponseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class NetworkHandlerTest extends Mockito {

    private NetworkHandler networkHandler;

    private HttpClient httpClient;

    private HttpRequest httpRequest;

    @Before
    public void setUp() {
        httpClient = mock(HttpClient.class);
        httpRequest = mock(HttpRequest.class);
    }


    @Test
    public void getApiResponseBodyTest() throws InterruptedException, ExecutionException, TimeoutException {

        CompletableFuture<HttpResponse<String>> completableFuture
                = CompletableFuture.supplyAsync(() -> getHttpResponse(false));
        when(httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())).thenReturn(completableFuture);

        networkHandler = new NetworkHandler(httpClient, httpRequest);

        Optional<String> responseBody = networkHandler.getApiResponseBody();
        Assert.assertNotNull(responseBody);
        Assert.assertTrue(responseBody.isPresent());
        Assert.assertEquals("RESPONSE_BODY", responseBody.get());
    }

    @Test(expected = BadResponseException.class)
    public void getApiResponseBody_badResponseTest() throws InterruptedException, ExecutionException, TimeoutException {

        CompletableFuture<HttpResponse<String>> completableFuture
                = CompletableFuture.supplyAsync(() -> getHttpResponse(true));
        when(httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())).thenReturn(completableFuture);

        networkHandler = new NetworkHandler(httpClient, httpRequest);

        networkHandler.getApiResponseBody();
    }

    private HttpResponse<String> getHttpResponse(boolean badResponse) {
        return new HttpResponse<String>() {
            @Override
            public int statusCode() {
                return badResponse ? 404 : 200;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<java.lang.String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public java.lang.String body() {
                return badResponse ? null : "RESPONSE_BODY";
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };
    }
}