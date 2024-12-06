package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class CreatedEvent {
    private Event event;
    private String eventDateTime;
    private String eventVenue;
    private String eventFacilitator;
    private String eventDescription;
    private boolean isBookmarked;

    public CreatedEvent(Event event, String eventDateTime, String eventVenue, String eventFacilitator, String eventDescription, boolean isBookmarked) {
        this.event = event;
        this.eventDateTime = eventDateTime;
        this.eventVenue = eventVenue;
        this.eventFacilitator = eventFacilitator;
        this.eventDescription = eventDescription;
        this.isBookmarked = isBookmarked;
    }

    public Event getEventTitle() {
        return this.event;
    }

    public String getEventDateTime() {
        return this.eventDateTime;
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

    public boolean isBookmarked() {
        return isBookmarked;  // Getter for the favorite status
    }
}
