package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.Objects;

public class JoinedEventPage extends AppCompatActivity {

    public static ArrayList<JoinedEvent> joinedEvents = new ArrayList<>();
    private JoinedEventAdapter adapter;

    ImageButton backArrow;
    ImageButton profileButton;
    ImageButton bookmarkButton;
    ImageButton eventsButton;
    ImageButton homeButton;
    ImageView addEventButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joined_events);

        RecyclerView recyclerViewJoinedEvents = findViewById(R.id.recycler_view_joined_events);
        recyclerViewJoinedEvents.setLayoutManager(new LinearLayoutManager(this));

        adapter = new JoinedEventAdapter(this, joinedEvents);
        recyclerViewJoinedEvents.setAdapter(adapter);

        fetchJoinedEvents();

        backArrow = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        eventsButton = findViewById(R.id.eventsButton);
        addEventButton = findViewById(R.id.add_event_button);

        backArrow.setOnClickListener(v -> finish());

        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(JoinedEventPage.this, ProfilePage.class);
            startActivity(intent);
        });

        eventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(JoinedEventPage.this, CreatedEventPage.class);
            startActivity(intent);
        });

        bookmarkButton.setOnClickListener(v -> {
            Intent intent = new Intent(JoinedEventPage.this, BookmarkPage.class);
            startActivity(intent);
        });

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(JoinedEventPage.this, MainActivity.class);
            startActivity(intent);
        });

        addEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(JoinedEventPage.this, CreateEventActivity.class);
            startActivity(intent);
        });
    }

    private void fetchJoinedEvents() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("JoinedEvents")
                    .whereEqualTo("userId", userId)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        joinedEvents.clear(); // Clear old data
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            JoinedEvent event = new JoinedEvent(
                                    new Event(
                                            Objects.requireNonNull(doc.getLong("eventImageId")).intValue(),
                                            doc.getString("eventName"),
                                            doc.getString("category")
                                    ),
                                    doc.getString("eventDate"),
                                    doc.getString("eventVenue"),
                                    doc.getString("eventFacilitator"),
                                    doc.getString("eventDescription"),
                                    doc.getBoolean("isJoined")
                            );
                            joinedEvents.add(event);
                        }
                        adapter.notifyDataSetChanged();
                        // Notify adapter of new data
                    })
                    .addOnFailureListener(e -> {
                        // Handle the error appropriately
                        Log.e("Firestore", "Error loading joined events", e);
                    });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
//            fetchJoinedEvents();
            adapter.notifyDataSetChanged();
        }
    }
}
