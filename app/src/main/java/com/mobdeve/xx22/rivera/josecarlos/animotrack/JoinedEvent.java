package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class JoinedEvent {
    private final Event event;
    private final String eventDate;
    private final String eventVenue;
    private final String eventFacilitator;
    private final String eventDescription;
    private final boolean isJoined;

    public JoinedEvent(Event event, String eventDate, String eventVenue, String eventFacilitator, String eventDescription, boolean isJoined) {
        this.event = event;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.eventFacilitator = eventFacilitator;
        this.eventDescription = eventDescription;
        this.isJoined = isJoined;
    }

    public Event getEventTitle() {
        return this.event;
    }

    public String getEventDate() {
        return this.eventDate;
    }

    public String getEventVenue() {
        return this.eventVenue;
    }

    public String getEventFacilitator() {
        return this.eventFacilitator;
    }

    public String getEventDescription() {
        return this.eventDescription;
    }

    public boolean isJoined() {
        return isJoined;
    }
}
