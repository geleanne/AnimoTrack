package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import java.util.ArrayList;

public class DataGenerator {
    private final static Event event1 = new Event(R.drawable.event1, "UAAP SEASON 87 MENS VOLLEYBALL"); // Sports
    private final static Event event2 = new Event(R.drawable.event2, "In Sync: Group Studies"); // Academic
    private final static Event event3 = new Event(R.drawable.event3, "LLM Tuning Methods for Specific Tasks."); // Seminar
    private final static Event event4 = new Event(R.drawable.event4, "Annual Recruitment Week 2024: The Archer Calls"); //
    private final static Event event5 = new Event(R.drawable.event5, "DLSU Chorale Annual Recruitment Week");

    public static ArrayList<UpcomingEvent> generateUpcomingEventsData() {
        ArrayList<UpcomingEvent> tempList = new ArrayList<>();

        tempList.add(new UpcomingEvent(event1, "October 26", "Rizal Memorial Stadium"));
        tempList.add(new UpcomingEvent(event2, "October 16", "Gokongwei Building 104-106"));
        tempList.add(new UpcomingEvent(event3, "October 26", "Rizal Memorial Stadium"));
        tempList.add(new UpcomingEvent(event4, "October 7", "Zoom"));
        tempList.add(new UpcomingEvent(event5, "September 16", "Yuchengco Hall"));

        return tempList;
    }
}
