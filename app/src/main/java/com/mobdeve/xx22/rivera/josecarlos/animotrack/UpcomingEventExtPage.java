package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
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
    private ImageButton backArrow;
    private ImageButton profileButton; // Declare profileButton
    private ImageButton bookmarkButton; // Declare bookmarkButton
    private ImageButton homeButton; // Declare homeButton
    private ImageButton eventsButton;
    private Spinner spinnerDepartments;
    private FirestoreHelper firestoreHelper;
    private GestureDetector gestureDetector;
    private int currentEventIndex = 0; // Track the current event


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


        // Initialize GestureDetector to detect swipe gestures
        gestureDetector = new GestureDetector(this, new GestureListener());

        ArrayList<UpcomingEvent> events = DataGenerator.generateUpcomingEventsData();

        // Set up the RecyclerView for events
        UpcomingEventExtAdapter upcomingEventAdapter = new UpcomingEventExtAdapter(this, events);
        recyclerViewUpcomingEventsExtended.setAdapter(upcomingEventAdapter);
        recyclerViewUpcomingEventsExtended.setLayoutManager(new LinearLayoutManager(this));


        // Add touch listener for swipe gestures
        recyclerViewUpcomingEventsExtended.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);  // Pass touch events to GestureDetector
            }
        });

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




    // GestureListener for detecting swipe gestures
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > 100) { // Swipe left
                currentEventIndex++;
                if (currentEventIndex >= upcomingEvents.size()) {
                    currentEventIndex = 0;  // Loop back to the first event
                }
                showEventAt(currentEventIndex);
                Toast.makeText(UpcomingEventExtPage.this, "Swiped Left - Event Index: " + currentEventIndex, Toast.LENGTH_SHORT).show();
                return true;
            } else if (e2.getX() - e1.getX() > 100) { // Swipe right
                currentEventIndex--;
                if (currentEventIndex < 0) {
                    currentEventIndex = upcomingEvents.size() - 1;  // Loop back to the last event
                }
                showEventAt(currentEventIndex);
                Toast.makeText(UpcomingEventExtPage.this, "Swiped Right - Event Index: " + currentEventIndex, Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    }

    // Method to show event details based on currentEventIndex
    private void showEventAt(int index) {
        // Set up your RecyclerView or views to display the current event
        if (index >= 0 && index < upcomingEvents.size()) {
            UpcomingEvent currentEvent = upcomingEvents.get(index);
            // Update UI with currentEvent data (event title, date, etc.)
            // For example:
            TextView eventTitle = findViewById(R.id.eventTitle);
            eventTitle.setText(currentEvent.getEventTitle().getName());
            // If you want to refresh your RecyclerView to show the current event:
            adapter.notifyDataSetChanged();
        }
    }
}
