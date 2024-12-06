package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

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
}
