package com.test.kambi.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.kambi.exception.NoLiveEventsFoundException;
import com.test.kambi.exception.NoOddsFoundException;
import com.test.kambi.exception.NoTargetEventFoundException;
import com.test.kambi.model.Event;
import com.test.kambi.model.LiveEvent;
import com.test.kambi.model.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class ResponseHandlerTest {

    private ResponseHandler responseHandler;
    private ObjectMapper mapper;
    private long targetEventId = 200044;

    @Before
    public void setUp() {
        responseHandler = new ResponseHandler();
        mapper = new ObjectMapper();
    }

    @Test(expected = NoLiveEventsFoundException.class)
    public void handleResponseBody_noLiveEventFoundExceptionTest() throws IOException {
        ResponseBody responseBody = new ResponseBody();
        String response = mapper.writeValueAsString(responseBody);
        responseHandler.handleResponseBody(response, targetEventId);
    }

    @Test(expected = NoTargetEventFoundException.class)
    public void handleResponseBody_noTargetEventFoundExceptionTest() throws IOException {
        ResponseBody responseBody = new ResponseBody();

        List<LiveEvent> liveEvents = new ArrayList<>();

        LiveEvent liveEvent1 = new LiveEvent();
        Event event1 = new Event();
        event1.setId(200045);
        event1.setTags(Arrays.asList("COMPETITION", "OPEN_FOR_LIVE"));
        liveEvent1.setEvent(event1);

        LiveEvent liveEvent2 = new LiveEvent();
        Event event2 = new Event();
        event2.setId(200046);
        event2.setTags(Arrays.asList("COMPETITION", "OPEN_FOR_LIVE"));
        liveEvent2.setEvent(event2);

        liveEvents.add(liveEvent1);
        liveEvents.add(liveEvent2);

        responseBody.setLiveEvents(liveEvents);
        String response = mapper.writeValueAsString(responseBody);
        responseHandler.handleResponseBody(response, targetEventId);
    }

    @Test(expected = NoOddsFoundException.class)
    public void handleResponseBody_noOddsFoundExceptionTest() throws IOException {
        ResponseBody responseBody = new ResponseBody();
        List<LiveEvent> liveEvents = new ArrayList<>();

        LiveEvent liveEvent1 = new LiveEvent();
        Event event1 = new Event();
        event1.setId(200044);
        event1.setTags(Arrays.asList("MATCH", "OPEN_FOR_LIVE"));
        liveEvent1.setEvent(event1);

        LiveEvent liveEvent2 = new LiveEvent();
        Event event2 = new Event();
        event2.setId(200046);
        event2.setTags(Arrays.asList("COMPETITION", "OPEN_FOR_LIVE"));
        liveEvent2.setEvent(event2);

        liveEvents.add(liveEvent1);
        liveEvents.add(liveEvent2);

        responseBody.setLiveEvents(liveEvents);
        String response = mapper.writeValueAsString(responseBody);
        responseHandler.handleResponseBody(response, targetEventId);
    }
}