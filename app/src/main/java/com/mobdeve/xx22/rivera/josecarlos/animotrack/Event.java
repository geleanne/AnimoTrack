package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class Event {
    private int imageId;
    private String name;

    public Event(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public int getImageId() {
        return this.imageId;
    }

    public String getName() {
        return this.name;
    }
}
