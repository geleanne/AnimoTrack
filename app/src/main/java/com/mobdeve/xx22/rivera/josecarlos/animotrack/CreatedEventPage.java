package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class CreatedEventPage extends AppCompatActivity {

    private ImageButton backArrow, profileButton, bookmarkButton, homeButton;
    private ImageView addEventButton;
    private TextView titleTextView;
    private RecyclerView recyclerViewCreatedEvents;

    private ArrayList<UpcomingEvent> createdEventsList = new ArrayList<>();
    private CreatedEventAdapter adapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.created_events);

        // Initialize views
        titleTextView = findViewById(R.id.toolbar_title);
        backArrow = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        addEventButton = findViewById(R.id.add_event_button);

        // Initialize views
        recyclerViewCreatedEvents = findViewById(R.id.recycler_view_created_events);
        recyclerViewCreatedEvents.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CreatedEventAdapter(this, createdEventsList);
        recyclerViewCreatedEvents.setAdapter(adapter);

        // Set user info in the toolbar
//        setUserInfo();

        // Fetch created events from Firestore
        fetchCreatedEvents();

        // Set button listeners
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
    }

//    private void setUserInfo() {
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) {
//            String userId = currentUser.getUid();
//            db.collection("AnimoTrackUsers").document(userId)
//                    .get()
//                    .addOnSuccessListener(documentSnapshot -> {
//                        if (documentSnapshot.exists()) {
//                            String fullName = documentSnapshot.getString("name");
//                            titleTextView.setText("Would you like to add an event,\n" + fullName + "?");
//                        } else {
//                            Log.w("SetUserInfo", "User data not found.");
//                        }
//                    })
//                    .addOnFailureListener(e -> {
//                        Log.e("SetUserInfo", "Error fetching user data.", e);
//                    });
//        }
//    }

    private void fetchCreatedEvents() {
        db.collection("AnimoTrackEvents")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    createdEventsList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        try {
                            String eventName = doc.getString("eventName");
                            Timestamp eventTimestamp = doc.getTimestamp("eventDate");  // Retrieve timestamp
                            String eventDate = "";
                            if (eventTimestamp != null) {
                                eventDate = eventTimestamp.toDate().toString();  // Convert timestamp to string
                            }
                            String eventVenue = doc.getString("eventVenue");
                            String eventFacilitator = doc.getString("eventFacilitator");
                            String eventDescription = doc.getString("eventDescription");
                            boolean isBookmarked = doc.getBoolean("isBookmarked") != null && doc.getBoolean("isBookmarked");

                            // Use default image if not set
                            int eventImageId = doc.contains("eventDrawableId") ? Objects.requireNonNull(doc.getLong("eventDrawableId")).intValue() : R.drawable.default_poster_squared;

                            // Log the event data for debugging
                            Log.d("FetchEvents", "Event Name: " + eventName);

                            // Validate required fields
                            if (eventName != null && eventDate != null && eventVenue != null) {
                                UpcomingEvent event = new UpcomingEvent(
                                        new Event(eventImageId, eventName, "General"),
                                        eventDate, eventVenue, eventFacilitator, eventDescription, isBookmarked
                                );
                                createdEventsList.add(event);
                            } else {
                                Log.w("FetchEvents", "Skipping invalid event data: " + doc.getId());
                            }
                        } catch (Exception e) {
                            Log.e("FetchEvents", "Error parsing event data.", e);
                        }
                    }
                    Log.d("FetchEvents", "Events fetched: " + createdEventsList.size()); // Check how many events are fetched
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
