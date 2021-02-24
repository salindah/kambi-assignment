package com.test.kambi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveEvent {

    private Event event;

    private MainBetOffer mainBetOffer;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public MainBetOffer getMainBetOffer() {
        return mainBetOffer;
    }

    public void setMainBetOffer(MainBetOffer mainBetOffer) {
        this.mainBetOffer = mainBetOffer;
    }

    public boolean isTargetEvent(long eventId) {
        return eventId == this.getEvent().getId() && this.getEvent().isMatchEvent();
    }

    public Optional<List<Integer>> getOddsList() {
        if (this.getMainBetOffer() == null) {
            return Optional.empty();
        }
        List<Integer> oddsList = this.getMainBetOffer().getOutcomes()
                .stream()
                .map(Outcome::getOdds)
                .collect(Collectors.toList());

        return Optional.of(oddsList);
    }
}
