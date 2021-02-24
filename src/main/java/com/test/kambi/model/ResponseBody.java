package com.test.kambi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseBody {

    private List<LiveEvent> liveEvents;

    public List<LiveEvent> getLiveEvents() {
        return liveEvents;
    }

    public void setLiveEvents(List<LiveEvent> liveEvents) {
        this.liveEvents = liveEvents;
    }

    public Optional<List<LiveEvent>> getLiveEventsOptional() {
        return Optional.ofNullable(liveEvents);
    }
}
