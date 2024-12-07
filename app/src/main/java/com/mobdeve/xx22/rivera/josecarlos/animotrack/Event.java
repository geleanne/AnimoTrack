package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class Event {
    private final int imageId;
    private final String name;
    private String category;
    private String collegeDept;
    boolean isBookmarked;

    public Event(int eventImageId, String eventName) {
        this.imageId = eventImageId;
        this.name = eventName;
        this.isBookmarked = false;
    }

    public Event(int eventImageId, String eventName, String category) {
        this.imageId = eventImageId;
        this.name = eventName;
        this.category = category;
        this.isBookmarked = false;
    }

    public Event(int eventImageId, String eventName, String category, String collegeDept) {
        this.imageId = eventImageId;
        this.name = eventName;
        this.category = category;
        this.collegeDept = collegeDept;
        this.isBookmarked = false;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public String getEventCollege() {
        return this.collegeDept;
    }
}
