package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirestoreHelper {
    private FirebaseFirestore db;

    public FirestoreHelper() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void saveEventToFirestore(UpcomingEvent event) {
        // Map the event data
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("title", event.getEventTitle().getName());
        eventData.put("date", event.getEventDate());
        eventData.put("venue", event.getEventVenue());
        eventData.put("facilitator", event.getEventFacilitator());
        eventData.put("description", event.getEventDescription());
        eventData.put("collegeDept", event.getEventCollege());
        eventData.put("isBookmarked", event.isBookmarked());

        // Save to Firestore
        db.collection("AnimoTrackEvents")
                .document(event.getEventTitle().getName())
                .set(eventData)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Event saved successfully!"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error saving event", e));
    }

    public void getEventsByCollege(String college, EventCallback callback) {
        db.collection("AnimoTrackEvents")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<UpcomingEvent> events = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String eventCollege = document.getString("collegeDept");
                        if (college.equals("All") || college.equals(eventCollege)) {
                            // Extract event details
                            String title = document.getString("title");
                            String date = document.getString("date");
                            String venue = document.getString("venue");
                            String facilitator = document.getString("facilitator");
                            String description = document.getString("description");
                            boolean isBookmarked = document.getBoolean("isBookmarked") != null && document.getBoolean("isBookmarked");

                            // fetch event image here
                            // Create Event and UpcomingEvent objects
                            Event event = new Event(R.drawable.default_poster_squared, title, "Category", eventCollege);
                            UpcomingEvent upcomingEvent = new UpcomingEvent(event, date, venue, eventCollege, facilitator, description, isBookmarked);
                            events.add(upcomingEvent);
                        }
                    }
                    callback.onEventsFetched(events);
                })
                .addOnFailureListener(callback::onError);
    }

    public interface EventCallback {
        void onEventsFetched(ArrayList<UpcomingEvent> events);
        void onError(Exception e);
    }

}
