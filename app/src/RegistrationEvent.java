package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class RegistrationEvent {
    private Event event;
    private String eventDate;
    private String eventVenue;
    private boolean isBookmarked;

    public RegistrationEvent(Event event, String eventDate, String eventVenue) {
        this.event = event;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
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

    public boolean isBookmarked() {
        return isBookmarked;
    }
}
