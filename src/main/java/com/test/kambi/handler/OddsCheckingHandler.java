package com.test.kambi.handler;

import java.util.List;

/**
 * This class is keeping track of the last received odds, and check whether they have changed or not.
 */
public class OddsCheckingHandler {

    private static OddsCheckingHandler oddsCheckingHandler;

    private List<Integer> previousOddsList;

    public static OddsCheckingHandler getInstance() {
        if (oddsCheckingHandler == null) {
            synchronized (OddsCheckingHandler.class) {
                oddsCheckingHandler = new OddsCheckingHandler();
            }
        }
        return oddsCheckingHandler;
    }

    public boolean isFirstTime() {
        return previousOddsList == null;
    }

    public void setPreviousOdds(List<Integer> currentOddsList) {

        synchronized(this){
            this.previousOddsList = currentOddsList;
        }
    }

    public boolean isOddsListUpdated(List<Integer> currentOddsList) {
        synchronized (this){
            if (this.previousOddsList != null && currentOddsList != null) {
                for (int index = 0; index < currentOddsList.size(); index++) {
                    if (!this.previousOddsList.get(index).equals(currentOddsList.get(index))) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
