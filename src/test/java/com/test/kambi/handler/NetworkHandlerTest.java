package com.test.kambi.handler;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class NetworkHandlerTest {

    private NetworkHandler networkHandler;

    @Before
    public void setUp() {
        networkHandler = new NetworkHandler();
    }

//
//    @Test
//    public void getHttpClientTest() {
//
//        Optional<HttpClient> httpClientOptional = networkHandler.getHttpClient();
//        Assert.assertNotNull(httpClientOptional);
//        Assert.assertTrue(httpClientOptional.isPresent());
//        Assert.assertNotNull(httpClientOptional.get());
//    }
//
//    @Test
//    public void getHttpRequestObjectTest() {
//
//        Optional<HttpRequest> httpRequestOptional = networkHandler.getHttpRequestObject();
//        Assert.assertNotNull(httpRequestOptional);
//        Assert.assertTrue(httpRequestOptional.isPresent());
//        Assert.assertNotNull(httpRequestOptional.get());
//    }

}