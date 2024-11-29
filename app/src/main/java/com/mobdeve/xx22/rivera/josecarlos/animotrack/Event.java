package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class Event {
    private final int imageId; // For event image
    private final String name; // Event title
//    String date;
    private String category; // Event category
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

//    public Event(String eventName, String eventDate, int eventImageId) {
//        this.name = eventName;
//        this.date = eventDate;
//        this.imageId = eventImageId;
//        this.isBookmarked = false;
//    }

    public int getImageId() {
        return this.imageId;
    }

    public String getName() {
        return this.name;
    }

//    public String getDate() {
//        return this.date;
//    }

    public String getCategory() {
        return this.category;
    }


}
