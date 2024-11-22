package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import java.util.ArrayList;

public class DataGenerator {
    private final static Event event1 = new Event(R.drawable.event1, "UAAP SEASON 87 MENS VOLLEYBALL", "Sports"); // Sports
    private final static Event event2 = new Event(R.drawable.event2, "In Sync: Group Studies", "Academics"); // Academic
    private final static Event event3 = new Event(R.drawable.event3, "LLM Tuning Methods for Specific Tasks.", "Seminars"); // Seminar
    private final static Event event4 = new Event(R.drawable.event4, "Annual Recruitment Week 2024: The Archer Calls", "Organizations"); //
    private final static Event event5 = new Event(R.drawable.event5, "DLSU Chorale Annual Recruitment Week", "Cultural");

    public static ArrayList<UpcomingEvent> generateUpcomingEventsData() {
        ArrayList<UpcomingEvent> tempList = new ArrayList<>();

        tempList.add(new UpcomingEvent(event1, "October 26", "Rizal Memorial Stadium", "DLSU Office of Sports Development (OSD)", "Cheer for the DLSU Green Spikers as they compete against the best in the UAAP Season 87 Men’s Volleyball tournament. Witness thrilling matches, intense rallies, and the unmatched passion of collegiate volleyball in this much-anticipated season."));
        tempList.add(new UpcomingEvent(event2, "October 16", "Gokongwei Building 104-106", "Peer Tutors", "Enhance your academic journey by joining In Sync: Group Studies, a collaborative learning session designed to foster teamwork and productivity. Meet fellow students, exchange ideas, and master challenging topics in an encouraging environment tailored for effective group study."));
        tempList.add(new UpcomingEvent(event3, "October 26", "Rizal Memorial Stadium", "College of Computer Studies (CCS)", "Delve into advanced techniques for fine-tuning Large Language Models (LLMs) to perform specific tasks effectively. This seminar will provide hands-on experience and expert insights, empowering participants to harness AI capabilities for practical applications across various domains."));
        tempList.add(new UpcomingEvent(event4, "October 7", "Zoom", "Council of Student Organizations (CSO)", "Answer The Archer Calls during this year’s Annual Recruitment Week! Explore diverse student organizations, find your passion, and connect with like-minded individuals. Whether you're into academics, arts, sports, or advocacy, there's a home for you at DLSU."));
        tempList.add(new UpcomingEvent(event5, "September 16", "Yuchengco Hall", "DLSU Chorale", "Share your love for music and be part of a legacy of excellence by joining the DLSU Chorale. Discover opportunities for growth, camaraderie, and international performances during this annual recruitment week, open to all aspiring singers in the Lasallian community."));

        return tempList;
    }

    public static ArrayList<CategorizedEvent> generateCategorizedEventData() {
        ArrayList<CategorizedEvent> tempList = new ArrayList<CategorizedEvent>();

        tempList.add(new CategorizedEvent("Sports", event1));
        tempList.add(new CategorizedEvent("Academics", event2));
        tempList.add(new CategorizedEvent("Seminars", event3));
        tempList.add(new CategorizedEvent("Organizations", event4));
        tempList.add(new CategorizedEvent("Cultural", event5));

        return tempList;
    }

    public static ArrayList<CategorizedEvent> getEventsByCategory(String category) {
        ArrayList<CategorizedEvent> filteredList = new ArrayList<>();
        for (CategorizedEvent event : generateCategorizedEventData()) {
            if (event.getCategory().equalsIgnoreCase(category)) {
                filteredList.add(event);
            }
        }
        return filteredList;
    }

}
