package com.test.kambi.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;

@RunWith(JUnit4.class)
public class EventTest {


    @Test
    public void isMatchEventTest() {
        Event event = new Event();
        event.setTags(Arrays.asList("MATCH", "OPEN_FOR_LIVE"));
        Assert.assertTrue(event.isMatchEvent());
    }

    @Test
    public void isNotMatchEventTest1() {
        Event event = new Event();
        event.setTags(Collections.emptyList());
        Assert.assertFalse(event.isMatchEvent());
    }

    @Test
    public void isNotMatchEventTest2() {
        Event event = new Event();
        event.setTags(Arrays.asList("COMPETITION", "OPEN_FOR_LIVE"));
        Assert.assertFalse(event.isMatchEvent());
    }

}