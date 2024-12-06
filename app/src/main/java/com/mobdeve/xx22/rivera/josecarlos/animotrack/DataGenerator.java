package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import java.util.ArrayList;


public class DataGenerator {
    private final static Event event1 = new Event(R.drawable.event1, "UAAP SEASON 87 MENS VOLLEYBALL", "Sports", "All"); // Sports
    private final static Event event2 = new Event(R.drawable.event2, "In Sync: Group Studies", "Academics", "CCS"); // Academic
    private final static Event event3 = new Event(R.drawable.event3, "LLM Tuning Methods for Specific Tasks.", "Seminars", "CCS"); // Seminar
    private final static Event event4 = new Event(R.drawable.event4, "Annual Recruitment Week 2024: The Archer Calls", "Organizations", "All"); //
    private final static Event event5 = new Event(R.drawable.event5, "DLSU Chorale Annual Recruitment Week", "Cultural", "All");
    private final static Event event6 = new Event(R.drawable.event6, "International Lasallian Days of Peace", "Cultural", "CLA");
    private final static Event event7 = new Event(R.drawable.event7, "Banner Making Contest", "Sports", "All");
    private final static Event event8 = new Event(R.drawable.event8, "Animo Christmas Concert", "Cultural", "All");
    private final static Event event9 = new Event(R.drawable.event9, "Arts, Crafts & Powerpuffs!", "Cultural", "COB");
    private final static Event event10 = new Event(R.drawable.event10, "EmoSyonaryo Escape Room", "Organizations", "CLA" );
    private final static Event event11 = new Event(R.drawable.event11, "Catch That Merch", "Organizations", "CCS");
    private final static Event event12 = new Event(R.drawable.event12, "Cyber Leap: InnovateTech Hub", "Academics", "CCS");

//    private final static Event event1 = new Event(R.drawable.event1, "UAAP SEASON 87 MENS VOLLEYBALL"); // Sports
//    private final static Event event2 = new Event(R.drawable.event2, "In Sync: Group Studies"); // Academic
//    private final static Event event3 = new Event(R.drawable.event3, "LLM Tuning Methods for Specific Tasks."); // Seminar
//    private final static Event event4 = new Event(R.drawable.event4, "Annual Recruitment Week 2024: The Archer Calls"); //
//    private final static Event event5 = new Event(R.drawable.event5, "DLSU Chorale Annual Recruitment Week");
//    private final static Event event6 = new Event(R.drawable.event6, "International Lasallian Days of Peace");
//    private final static Event event7 = new Event(R.drawable.event7, "Banner Making Contest");
//    private final static Event event8 = new Event(R.drawable.event8, "Animo Christmas Concert");
//    private final static Event event9 = new Event(R.drawable.event9, "Arts, Crafts & Powerpuffs!");
//    private final static Event event10 = new Event(R.drawable.event10, "EmoSyonaryo Escape Room");
//    private final static Event event11 = new Event(R.drawable.event11, "Catch That Merch");
//    private final static Event event12 = new Event(R.drawable.event12, "Cyber Leap: InnovateTech Hub");

    public static ArrayList<UpcomingEvent> generateUpcomingEventsData() {
        ArrayList<UpcomingEvent> tempList = new ArrayList<>();

        tempList.add(new UpcomingEvent(event1, "October 26, 2024", "Rizal Memorial Stadium", "All", "DLSU Office of Sports Development (OSD)", "Cheer for the DLSU Green Spikers as they compete against the best in the UAAP Season 87 Men’s Volleyball tournament. Witness thrilling matches, intense rallies, and the unmatched passion of collegiate volleyball in this much-anticipated season.", false));
        tempList.add(new UpcomingEvent(event2, "October 16, 2024", "Gokongwei Building 104-106", "CCS", "Peer Tutors", "Enhance your academic journey by joining In Sync: Group Studies, a collaborative learning session designed to foster teamwork and productivity. Meet fellow students, exchange ideas, and master challenging topics in an encouraging environment tailored for effective group study.", false));
        tempList.add(new UpcomingEvent(event3, "October 26, 2024", "Rizal Memorial Stadium", "CCS", "College of Computer Studies (CCS)", "Delve into advanced techniques for fine-tuning Large Language Models (LLMs) to perform specific tasks effectively. This seminar will provide hands-on experience and expert insights, empowering participants to harness AI capabilities for practical applications across various domains.", false));
        tempList.add(new UpcomingEvent(event4, "October 7, 2024", "Zoom", "All", "Council of Student Organizations (CSO)", "Answer The Archer Calls during this year’s Annual Recruitment Week! Explore diverse student organizations, find your passion, and connect with like-minded individuals. Whether you're into academics, arts, sports, or advocacy, there's a home for you at DLSU.", false));
        tempList.add(new UpcomingEvent(event5, "September 16, 2024", "Yuchengco Hall", "All", "DLSU Chorale", "Share your love for music and be part of a legacy of excellence by joining the DLSU Chorale. Discover opportunities for growth, camaraderie, and international performances during this annual recruitment week, open to all aspiring singers in the Lasallian community.", false));
        tempList.add(new UpcomingEvent(event6, "November 29, 2024", "Zoom", "CLA", "USG OVPEA", "Join us for a series of peace-building workshops, interfaith dialogues, and community activities as we celebrate the International Lasallian Days of Peace. This annual event encourages reflection on peace, solidarity, and service to the community, with guest speakers and interactive activities aimed at fostering understanding and unity.", false));
        tempList.add(new UpcomingEvent(event7, "November 20-27, 2024", "Online", "All", "DLSU USG", "Showcase your creativity in this exciting Banner Making Contest! Participants will create banners on the theme of school spirit, unity, and Lasallian pride. The best designs will be displayed around campus during the week of festivities.", false));
        tempList.add(new UpcomingEvent(event8, "November 27, 2024", "Corazon Aquino Democratic Space", "All", "DLSU USG", "Get into the holiday spirit with a night of festive performances! The Animo Christmas Concert features various DLSU performing arts groups, from choral music to theatrical performances, celebrating the warmth of the Christmas season with an exciting array of songs and dances.", false));
        tempList.add(new UpcomingEvent(event9, "November 26, 2024", "Andrew Gonzales Building 1805", "COB", "DLSU USG", "Unleash your creativity at the Arts, Crafts & Powerpuffs! event, where you'll have the chance to create your own artwork, craft unique decorations, and meet the popular Powerpuff Girls cosplayers. Whether you’re into painting, sculpting, or DIY crafts, there's something for everyone in this colorful and fun event!", false));
        tempList.add(new UpcomingEvent(event10, "November 18-20, 2024", "Velasco Hall, Room 207", "CLA", "DLSU Psychology Society", "Test your emotional intelligence and problem-solving skills in this thrilling escape room challenge. Decode clues and escape within 45 minutes to win exciting prizes!", false));
        tempList.add(new UpcomingEvent(event11, "December 5-15, 2024", "Online", "CCS", "DLSU CSG", "Don’t miss out on this exciting pop-up merchandise market! Catch That Merch brings together local vendors, student-made products, and exclusive DLSU-themed items for sale. Perfect for anyone looking to grab unique gifts and school spirit gear, all while supporting local entrepreneurs and student projects.", false));
        tempList.add(new UpcomingEvent(event12, "December 1, 2024", "Gokongwei Building 105 ", "CCS", "DLSU CSG", "Explore the future of technology in this tech conference featuring innovative workshops, guest speakers from the tech industry, and hands-on experiences with cutting-edge gadgets and software. Discover how you can contribute to the digital future!", false));
        // tempList.add(new UpcomingEvent(event6, "", "", "", ""));

        return tempList;
    }

    public static ArrayList<CategorizedEvent> generateCategorizedEventData() {
        ArrayList<CategorizedEvent> tempList = new ArrayList<>();

        tempList.add(new CategorizedEvent("Sports", event1));
        tempList.add(new CategorizedEvent("Academics", event2));
        tempList.add(new CategorizedEvent("Seminars", event3));
        tempList.add(new CategorizedEvent("Organizations", event4));
        tempList.add(new CategorizedEvent("Cultural", event5));
        tempList.add(new CategorizedEvent("Cultural", event6));
        tempList.add(new CategorizedEvent("Sports", event7));
        tempList.add(new CategorizedEvent("Cultural", event8));
        tempList.add(new CategorizedEvent("Cultural", event9));
        tempList.add(new CategorizedEvent("Organizations", event10));
        tempList.add(new CategorizedEvent("Organizations", event11));
        tempList.add(new CategorizedEvent("Academics", event12));

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
    public void saveEventsToFirestore() {
        FirestoreHelper firestoreHelper = new FirestoreHelper();
        ArrayList<UpcomingEvent> events = generateUpcomingEventsData();
        for (UpcomingEvent event : events) {
            firestoreHelper.saveEventToFirestore(event);
        }
    }


//    public static ArrayList<CollegeEvent> generateCollegeEventData() {
//        ArrayList<CollegeEvent> tempList = new ArrayList<>();
//
//        tempList.add(new CollegeEvent("All", event1));
//        tempList.add(new CollegeEvent("CCS", event2));
//        tempList.add(new CollegeEvent("CCS", event3));
//        tempList.add(new CollegeEvent("All", event4));
//        tempList.add(new CollegeEvent("All", event5));
//        tempList.add(new CollegeEvent("CLA", event6));
//        tempList.add(new CollegeEvent("All", event7));
//        tempList.add(new CollegeEvent("All", event8));
//        tempList.add(new CollegeEvent("COB", event9));
//        tempList.add(new CollegeEvent("CLA", event10));
//        tempList.add(new CollegeEvent("CCS", event11));
//        tempList.add(new CollegeEvent("CCS", event12));
//
//        return tempList;
//    }
}
