package com.test.kambi.handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Optional;

@RunWith(JUnit4.class)
public class NetworkHandlerTest {

    NetworkHandler networkHandler;

    @Before
    public void setUp() {
        networkHandler = NetworkHandler.getInstance();
        networkHandler.initialize();
    }

    @Test
    public void getInstanceTest() {
        Assert.assertNotNull(NetworkHandler.getInstance());
    }

    @Test
    public void getHttpClientTest() {

        Optional<HttpClient> httpClientOptional = networkHandler.getHttpClient();
        Assert.assertNotNull(httpClientOptional);
        Assert.assertTrue(httpClientOptional.isPresent());
        Assert.assertNotNull(httpClientOptional.get());
    }

    @Test
    public void getHttpRequestObjectTest() {

        Optional<HttpRequest> httpRequestOptional = networkHandler.getHttpRequestObject();
        Assert.assertNotNull(httpRequestOptional);
        Assert.assertTrue(httpRequestOptional.isPresent());
        Assert.assertNotNull(httpRequestOptional.get());
    }

}