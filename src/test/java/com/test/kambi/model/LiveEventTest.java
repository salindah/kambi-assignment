package com.test.kambi.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LiveEventTest {

    private long targetEventId = 105405;

    @Test
    public void isTargetEvent() {
        LiveEvent liveEvent = new LiveEvent();
        long eventId = 105405;
        Event event = new Event();
        event.setTags(Arrays.asList("MATCH", "OPEN_FOR_LIVE"));
        event.setId(eventId);
        liveEvent.setEvent(event);
        Assert.assertTrue(liveEvent.isTargetEvent(targetEventId));
    }

    @Test
    public void isNotTargetEvent1() {

        LiveEvent liveEvent = new LiveEvent();
        long eventId = 100205;
        Event event = new Event();
        event.setTags(Arrays.asList("MATCH", "OPEN_FOR_LIVE"));
        event.setId(eventId);
        liveEvent.setEvent(event);
        Assert.assertFalse(liveEvent.isTargetEvent(targetEventId));
    }

    @Test
    public void isNotTargetEvent2() {

        LiveEvent liveEvent = new LiveEvent();
        long eventId = 100205;
        Event event = new Event();
        event.setTags(Arrays.asList("COMPETITION", "OPEN_FOR_LIVE"));
        event.setId(eventId);
        liveEvent.setEvent(event);
        Assert.assertFalse(liveEvent.isTargetEvent(targetEventId));
    }

    @Test
    public void getOddsListTest() {
        LiveEvent liveEvent = new LiveEvent();

        MainBetOffer mainBetOffer = new MainBetOffer();
        List<Outcome> outcomeList = new ArrayList<>();
        Outcome outcome1 = new Outcome();
        outcome1.setId(234567);
        outcome1.setOdds(3304);

        Outcome outcome2 = new Outcome();
        outcome2.setId(234568);
        outcome2.setOdds(3305);

        outcomeList.add(outcome1);
        outcomeList.add(outcome2);
        mainBetOffer.setOutcomes(outcomeList);

        liveEvent.setMainBetOffer(mainBetOffer);

        Optional<List<Integer>> optionalOddsList = liveEvent.getOddsList();
        Assert.assertNotNull(optionalOddsList);
        Assert.assertTrue(optionalOddsList.isPresent());
        Assert.assertEquals(outcomeList.size(), optionalOddsList.get().size());

        Assert.assertEquals(3304, optionalOddsList.get().get(0).intValue());
        Assert.assertEquals(3305, optionalOddsList.get().get(1).intValue());
    }

    @Test
    public void getOddsListEmptyTest() {
        LiveEvent liveEvent = new LiveEvent();

        Optional<List<Integer>> optionalOddsList = liveEvent.getOddsList();
        Assert.assertNotNull(optionalOddsList);
        Assert.assertFalse(optionalOddsList.isPresent());
    }
}