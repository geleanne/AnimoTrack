package com.mobdeve.xx22.rivera.josecarlos.animotrack;

public class CategorizedEvent {
    private final String category;
    private final Event event;

    public CategorizedEvent(String category, Event event) {
        this.category = category;
        this.event = event;
    }

    public String getCategory() {
        return this.category;
    }

    public Event getEvent() {
        return this.event;
    }
}
