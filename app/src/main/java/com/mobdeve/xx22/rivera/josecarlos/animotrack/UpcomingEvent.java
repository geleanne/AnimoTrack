package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class UpcomingEvent {
    private final Event event;
    private final String eventDate;
    private final String eventVenue;
    private final String eventCollege;
    private final String eventFacilitator;
    private final String eventDescription;
    private String category;
    private final boolean isBookmarked;

    public UpcomingEvent(Event event, String eventDate, String eventVenue, String eventCollege, String eventFacilitator, String eventDescription, boolean isBookmarked) {
        this.event = event;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.eventCollege = eventCollege;
        this.eventFacilitator = eventFacilitator;
        this.eventDescription = eventDescription;
        this.isBookmarked = isBookmarked;
    }

    public UpcomingEvent(Event event, String eventDate, String eventVenue, String eventCollege, String eventFacilitator, String eventDescription, String category, boolean isBookmarked) {
        this.event = event;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.eventCollege = eventCollege;
        this.eventFacilitator = eventFacilitator;
        this.eventDescription = eventDescription;
        this.category = category;
        this.isBookmarked = isBookmarked;
    }

    public Event getEvent() {
        return this.event;
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

    public String getEventCollege() {
        return this.eventCollege;
    }

    public String getEventFacilitator() {
        return this.eventFacilitator;
    }

    public String getEventDescription() {
        return this.eventDescription;
    }

    public String getCategory() {
        return this.category;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UpcomingEvent event = (UpcomingEvent) obj;
        return this.event.getName().equals(event.event.getName());
    }

    @Override
    public int hashCode() {
        return this.event.getName().hashCode();
    }
}
