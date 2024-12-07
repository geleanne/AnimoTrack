package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class BookmarkEvent {
    private final Event event;
    private final String eventDate;
    private final String eventVenue;
    private final String eventFacilitator;
    private final String eventDescription;
    private final String collegeDept;
    private final boolean isBookmarked;

    public BookmarkEvent(Event event, String eventDate, String eventVenue, String eventFacilitator, String eventDescription, String collegeDept, boolean isBookmarked) {
        this.event = event;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.eventFacilitator = eventFacilitator;
        this.eventDescription = eventDescription;
        this.collegeDept = collegeDept;
        this.isBookmarked = isBookmarked;
    }

    public Event getEventTitle() {
        return this.event;
    }

    public String getEventDate() {
        return this.eventDate;
    }

    public String getCollegeDept() {
        return collegeDept;
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
        return isBookmarked;
    }
}
