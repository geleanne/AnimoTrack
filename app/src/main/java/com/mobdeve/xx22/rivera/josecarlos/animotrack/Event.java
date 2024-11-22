package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class Event {
    private int imageId;
    private String name;
    private String category;

    public Event(int imageId, String name, String category) {
        this.imageId = imageId;
        this.name = name;
        this.category = category;
    }

    public int getImageId() {
        return this.imageId;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }
}
