package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class JoinedEvent {
    private Event event;
    private String eventDate;
    private String eventVenue;
    private String eventFacilitator;
    private String eventDescription;
    private boolean isJoined;

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
        return isJoined;  // Getter for the joined status
    }
}
