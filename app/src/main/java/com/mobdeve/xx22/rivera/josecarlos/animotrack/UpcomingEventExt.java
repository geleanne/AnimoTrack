package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class UpcomingEventExt {
    private Event event;
    private String eventDate;
    private String eventVenue;
    private String eventDescription;
    private String eventOrganizer;

    public UpcomingEventExt(Event event, String eventDate, String eventVenue) {
        this.event = event;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.eventDescription = eventDescription;
        this.eventOrganizer = eventOrganizer;
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

    public String getEventDescription() {
        return this.eventDescription;
    }

    public String getEventOrganizer() {
        return this.eventOrganizer;
    }
}
