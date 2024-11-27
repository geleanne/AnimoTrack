package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreatedEvent extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton backButton;
    private TextView titleTextView;
    private ArrayList<UpcomingEvent> createdEventsList;
    private UpcomingEventAdapter eventAdapter;
    private DatabaseReference eventsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String fullName = getIntent().getStringExtra("fullName");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.created_events);

        recyclerView = findViewById(R.id.recycler_view_bookmarked_events);
        backButton = findViewById(R.id.back_arrow);
        titleTextView = findViewById(R.id.toolbar_title);

        createdEventsList = new ArrayList<>();
        eventAdapter = new UpcomingEventAdapter(this, createdEventsList);

        // Initialize Firebase database reference
        eventsRef = FirebaseDatabase.getInstance().getReference("created_events");

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eventAdapter);

        // Fetch events from Firebase and display
        fetchCreatedEvents();

        // Handle back button click
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreatedEvent.this, MainActivity.class);
            intent.putExtra("fullName", fullName);
            startActivity(intent);
        });
    }

    private void fetchCreatedEvents() {
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                createdEventsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Assuming the event data is stored as a map
                    String eventName = snapshot.child("eventName").getValue(String.class);
                    String eventVenue = snapshot.child("eventVenue").getValue(String.class);
                    String eventDate = snapshot.child("eventDate").getValue(String.class);
                    String eventFacilitator = snapshot.child("eventFacilitator").getValue(String.class);
                    String eventDescription = snapshot.child("eventDescription").getValue(String.class);

                    UpcomingEvent event = new UpcomingEvent(
                            new Event(0, eventName),  // Assuming event images are handled later
                            eventDate,
                            eventVenue,
                            eventFacilitator,
                            eventDescription,
                            false  // Assuming no bookmark initially
                    );
                    createdEventsList.add(event);
                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CreatedEvent.this, "Failed to load events", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
