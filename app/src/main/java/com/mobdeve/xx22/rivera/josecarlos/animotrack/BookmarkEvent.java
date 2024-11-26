package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class BookmarkEvent {
    private Event event;
    private String eventDate;
    private String eventVenue;
    private String eventFacilitator;
    private String eventDescription;
    private boolean isFavorite;

    public BookmarkEvent(Event event, String eventDate, String eventVenue, String eventFacilitator, String eventDescription, boolean isFavorite) {
        this.event = event;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.eventFacilitator = eventFacilitator;
        this.eventDescription = eventDescription;
        this.isFavorite = isFavorite;
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

    public boolean isFavorite() {
        return isFavorite;  // Getter for the favorite status
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;  // Setter for the favorite status
    }
}
