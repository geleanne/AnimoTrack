package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class BookmarkEvent {
    private Event event;
    private String eventDate;
    private String eventVenue;
    private String eventDescription;
    private String eventOrganizer;

    public BookmarkEvent(Event event, String eventDate, String eventVenue) {
        this.event = event;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.eventDescription = eventDescription;
        this.eventOrganizer = eventOrganizer;
    }

    public Event getBookmarkEventTitle() {
        return this.event;
    }

    public String getBookmarkEventDate() {
        return this.eventDate;
    }

    public String getBookmarkEventVenue() {
        return this.eventVenue;
    }

    public String getBookmarkEventDescription() {
        return this.eventDescription;
    }

    public String getBookmarkEventOrganizer() {
        return this.eventOrganizer;
    }
}
