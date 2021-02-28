package com.test.kambi.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.kambi.handler.NetworkHandler;
import com.test.kambi.handler.ResponseHandler;
import com.test.kambi.model.Event;
import com.test.kambi.model.LiveEvent;
import com.test.kambi.model.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class EventCheckerTaskTest extends Mockito {

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void runTest() throws InterruptedException, ExecutionException, TimeoutException, IOException {

        NetworkHandler networkHandler = mock(NetworkHandler.class);

        Optional<String> responseBody = getDummyResponse();
        when(networkHandler.getApiResponseBody()).thenReturn(responseBody);

        ResponseHandler responseHandler = mock(ResponseHandler.class);

        EventCheckerTask eventCheckerTask = new EventCheckerTask(networkHandler, responseHandler, 2312312);
        eventCheckerTask.run();
        verify(responseHandler).handleResponseBody(anyString(), anyLong());
    }

    private Optional<String> getDummyResponse() throws JsonProcessingException {
        ResponseBody responseBody = new ResponseBody();
        List<LiveEvent> liveEvents = new ArrayList<>();

        LiveEvent liveEvent1 = new LiveEvent();
        Event event1 = new Event();
        event1.setId(200044);
        event1.setTags(Arrays.asList("MATCH", "OPEN_FOR_LIVE"));
        liveEvent1.setEvent(event1);

        liveEvents.add(liveEvent1);
        responseBody.setLiveEvents(liveEvents);

        return Optional.of(mapper.writeValueAsString(responseBody));
    }
}