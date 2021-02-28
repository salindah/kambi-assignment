package com.test.kambi.util;

import com.test.kambi.model.Event;
import com.test.kambi.model.MainBetOffer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A utility class that do some formatting work and print the odd records.
 */
public class PrintUtils {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static String currentDateTimeString(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        return dateTimeFormatter.format(currentDateTime);
    }

    private static String formatOddValue(int value){
        float oddValueDivided = value / 1000f;
        return String.format("%.2f", oddValueDivided);
    }

    public static void printEventName(Event event) {
        if (event != null && event.getName() != null) {
            System.out.println("Event: " + event.getName());
        }
    }

    public static void printOddsListDetails(MainBetOffer mainBetOffer) {
        if (mainBetOffer != null) {
            StringBuilder outcomeDetails = new StringBuilder();
            outcomeDetails.append("[").append(currentDateTimeString()).append("] | ");
            mainBetOffer.getOutcomes()
                    .forEach(outcome -> outcomeDetails
                            .append(outcome.getLabel())
                            .append(":\t").append(formatOddValue(outcome.getOdds()))
                            .append(" | "));
            System.out.println(outcomeDetails.toString());
        }
    }
}
