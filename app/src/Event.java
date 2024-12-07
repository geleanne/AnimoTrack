package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class Event {
    private int imageId; // For event image
    private final String name; // Event title
    private String category; // Event category
    private String collegeDept; // Event college department
    boolean isBookmarked; // Bookmark status

    // Main Constructor
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

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
