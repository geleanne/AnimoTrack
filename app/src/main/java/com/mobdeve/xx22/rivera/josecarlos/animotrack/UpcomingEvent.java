package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class UpcomingEvent {
    private Event event;
    private String eventDate;
    private String eventVenue;
    private String eventFacilitator;
    private String eventDescription;

    public UpcomingEvent(Event event, String eventDate, String eventVenue, String eventFacilitator, String eventDescription) {
        this.event = event;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.eventFacilitator = eventFacilitator;
        this.eventDescription = eventDescription;
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
}
