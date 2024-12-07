package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class CreatedEventPage extends AppCompatActivity {

    private final ArrayList<UpcomingEvent> createdEventsList = new ArrayList<>();
    private CreatedEventAdapter adapter;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.created_events);

        ImageButton backArrow = findViewById(R.id.back_arrow);
        ImageButton profileButton = findViewById(R.id.profileButton);
        ImageButton bookmarkButton = findViewById(R.id.bookmarksButton);
        ImageButton homeButton = findViewById(R.id.homeButton);
        ImageView addEventButton = findViewById(R.id.add_event_button);
        ImageView notificationButton = findViewById(R.id.notificationsButton);

        RecyclerView recyclerViewCreatedEvents = findViewById(R.id.recycler_view_created_events);
        recyclerViewCreatedEvents.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CreatedEventAdapter(this, createdEventsList);
        recyclerViewCreatedEvents.setAdapter(adapter);

        fetchCreatedEvents();

        backArrow.setOnClickListener(v -> finish());

        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreatedEventPage.this, ProfilePage.class);
            startActivity(intent);
        });

        bookmarkButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreatedEventPage.this, BookmarkPage.class);
            startActivity(intent);
        });

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreatedEventPage.this, MainActivity.class);
            startActivity(intent);
        });

        addEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreatedEventPage.this, CreateEventActivity.class);
            startActivity(intent);
        });

        notificationButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreatedEventPage.this, NotificationsPage.class);
            startActivity(intent);
        });
    }

    private void fetchCreatedEvents() {
        db.collection("UserCreatedEvents")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    createdEventsList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        try {
                            String eventName = doc.getString("eventName");
                            Timestamp eventTimestamp = doc.getTimestamp("eventDate");
                            String eventDate = "";
                            if (eventTimestamp != null) {
                                eventDate = eventTimestamp.toDate().toString();
                            }
                            String eventVenue = doc.getString("eventVenue");
                            String eventFacilitator = doc.getString("eventFacilitator");
                            String eventDescription = doc.getString("eventDescription");
                            boolean isBookmarked = doc.getBoolean("isBookmarked") != null && doc.getBoolean("isBookmarked");

                            // default event image
                            int eventImageId = doc.contains("eventDrawableId") ? Objects.requireNonNull(doc.getLong("eventDrawableId")).intValue() : R.drawable.default_poster_squared;

                            // Log the event data for debugging
                            Log.d("FetchEvents", "Event Name: " + eventName);

                            // Validate required fields
                            if (eventName != null && eventVenue != null) {
                                UpcomingEvent event = new UpcomingEvent(
                                        new Event(eventImageId, eventName, "General"),
                                        eventDate, eventVenue, "Unknown College", eventFacilitator, eventDescription, isBookmarked
                                );
                                createdEventsList.add(event);
                            } else {
                                Log.w("FetchEvents", "Skipping invalid event data: " + doc.getId());
                            }
                        } catch (Exception e) {
                            Log.e("FetchEvents", "Error parsing event data.", e);
                        }
                    }
                    Log.d("FetchEvents", "Events fetched: " + createdEventsList.size());
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e("FetchEvents", "Error loading events from Firestore.", e);
                    Toast.makeText(this, "Error loading events", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged(); // Refresh the list
    }
}
