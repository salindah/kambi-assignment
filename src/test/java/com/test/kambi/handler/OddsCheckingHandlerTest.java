package com.test.kambi.handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class OddsCheckingHandlerTest {

    private OddsCheckingHandler oddsCheckingHandler;

    @Before
    public void setUp() {
        oddsCheckingHandler = OddsCheckingHandler.getInstance();
        List<Integer> previousOdds = Arrays.asList(1120, 2120, 3000);
        oddsCheckingHandler.setPreviousOdds(previousOdds);
    }

    @Test
    public void getInstanceTest() {
        OddsCheckingHandler oddsCheckingHandler = OddsCheckingHandler.getInstance();
        Assert.assertNotNull(oddsCheckingHandler);
    }

    @Test
    public void isFirstTimeTest() {
        Assert.assertFalse(oddsCheckingHandler.isFirstTime());
    }

    @Test
    public void isOddsListNotUpdatedTest() {
        List<Integer> currentOdds = Arrays.asList(1120, 2120, 3000);
        Assert.assertFalse(oddsCheckingHandler.isOddsListUpdated(currentOdds));
    }

    @Test
    public void isOddsListUpdatedTest() {
        List<Integer> currentOdds = Arrays.asList(1124, 2125, 3000);
        Assert.assertTrue(oddsCheckingHandler.isOddsListUpdated(currentOdds));
    }
}
