package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class BookmarkEvent {
    private Event event;
    private String eventDate;
    private String eventVenue;
    private String eventFacilitator;
    private String eventDescription;
    private boolean isBookmarked;

    public BookmarkEvent(Event event, String eventDate, String eventVenue, String eventFacilitator, String eventDescription, boolean isBookmarked) {
        this.event = event;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.eventFacilitator = eventFacilitator;
        this.eventDescription = eventDescription;
        this.isBookmarked = isBookmarked;
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

    public boolean isBookmarked() {
        return isBookmarked;  // Getter for the favorite status
    }
}
