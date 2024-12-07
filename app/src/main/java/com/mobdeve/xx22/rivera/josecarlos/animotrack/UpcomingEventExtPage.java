package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UpcomingEventExtPage extends AppCompatActivity {

    private RecyclerView recyclerViewUpcomingEventsExtended;
    private UpcomingEventExtAdapter adapter;
    private ArrayList<UpcomingEvent> upcomingEvents;
    private ImageButton backArrow, profileButton,bookmarkButton, homeButton, eventsButton;
    private ImageView addButton;
    private Spinner spinnerDepartments;
    private FirestoreHelper firestoreHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_extend);
        // Initialize the GestureDetector

        String fullName = getIntent().getStringExtra("fullName");

        recyclerViewUpcomingEventsExtended = findViewById(R.id.recyclerViewUpcomingEventsExtended);
        backArrow = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton); // Initialize profileButton
        bookmarkButton = findViewById(R.id.bookmarksButton); // Initialize bookmarkButton
        homeButton = findViewById(R.id.homeButton); // Initialize homeButton
        eventsButton = findViewById(R.id.eventsButton);
        addButton = findViewById(R.id.add_event_button);

        ArrayList<UpcomingEvent> events = DataGenerator.generateUpcomingEventsData();

        // Set up the RecyclerView for events
        UpcomingEventExtAdapter upcomingEventAdapter = new UpcomingEventExtAdapter(this, events);
        recyclerViewUpcomingEventsExtended.setAdapter(upcomingEventAdapter);
        recyclerViewUpcomingEventsExtended.setLayoutManager(new LinearLayoutManager(this));

        spinnerDepartments = findViewById(R.id.spinner_departments);

        // Initialize FirestoreHelper
        firestoreHelper = new FirestoreHelper();

        // Set up RecyclerView
        upcomingEvents = new ArrayList<>();
        adapter = new UpcomingEventExtAdapter(this, upcomingEvents);
        recyclerViewUpcomingEventsExtended.setAdapter(adapter);
        recyclerViewUpcomingEventsExtended.setLayoutManager(new LinearLayoutManager(this));

        // Load initial data (default filter is "All")
        fetchEventsByCollege("All");

        // Set up Spinner
        spinnerDepartments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCollege = parent.getItemAtPosition(position).toString();
                fetchEventsByCollege(selectedCollege);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Default to "All" if nothing is selected
                fetchEventsByCollege("All");
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, UpcomingEventExtPage.class);
                intent.putExtra("fullName", fullName);finish(); // Close current activity and return to the previous one
            }
        });

        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, CreatedEventPage.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the UpcomingEventPage activity
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, ProfilePage.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the LoginPage activity
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, CreateEventActivity.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the CreateEventPage activity
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, BookmarkPage.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the BookmarksPage activity
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, MainActivity.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the HomePage activity
            }
        });
    }

    private void fetchEventsByCollege(String college) {
        firestoreHelper.getEventsByCollege(college, new FirestoreHelper.EventCallback() {
            @Override
            public void onEventsFetched(ArrayList<UpcomingEvent> events) {
                upcomingEvents.clear();
                upcomingEvents.addAll(events);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {
                Log.e("Firestore", "Error fetching events: ", e);
            }
        });
    }
}
