package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class UpcomingEvent {
    private Event event;
    private String eventDate;
    private String eventVenue;
    private String eventCollege;
    private String eventFacilitator;
    private String eventDescription;
    private boolean isBookmarked;

    public UpcomingEvent(Event event, String eventDate, String eventVenue, String eventCollege, String eventFacilitator, String eventDescription, boolean isBookmarked) {
        this.event = event;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.eventCollege = eventCollege;
        this.eventFacilitator = eventFacilitator;
        this.eventDescription = eventDescription;
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

    public boolean isBookmarked() {
        return isBookmarked;  // Getter for the favorite status
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
