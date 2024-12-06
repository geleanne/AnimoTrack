package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_extend);

        String fullName = getIntent().getStringExtra("fullName");

        recyclerViewUpcomingEventsExtended = findViewById(R.id.recyclerViewUpcomingEventsExtended);
        backArrow = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton); // Initialize profileButton
        bookmarkButton = findViewById(R.id.bookmarksButton); // Initialize bookmarkButton
        homeButton = findViewById(R.id.homeButton); // Initialize homeButton
        eventsButton = findViewById(R.id.eventsButton);

        ArrayList<UpcomingEvent> events = DataGenerator.generateUpcomingEventsData();

        // Set up the RecyclerView for events
        UpcomingEventExtAdapter upcomingEventAdapter = new UpcomingEventExtAdapter(this, events);
        recyclerViewUpcomingEventsExtended.setAdapter(upcomingEventAdapter);
        recyclerViewUpcomingEventsExtended.setLayoutManager(new LinearLayoutManager(this));

        upcomingEvents = DataGenerator.generateUpcomingEventsData();
        adapter = new UpcomingEventExtAdapter(this, upcomingEvents);
        recyclerViewUpcomingEventsExtended.setAdapter(adapter);
        

        // Reference the Spinner
        Spinner departmentSpinner = findViewById(R.id.spinner_departments);

        // Create an ArrayAdapter using the event categories from strings.xml
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.event_categories,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(spinnerAdapter);

        // Specify the layout for dropdown items
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the Spinner
        departmentSpinner.setAdapter(spinnerAdapter);

        // Handle Spinner selection
        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDepartment = parent.getItemAtPosition(position).toString();
                filterEvents(selectedDepartment);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
//                adapter.updateData(DataGenerator.generateUpcomingEventsData());
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

    // Method to filter events
    private void filterEvents(String collegeDept) {
        ArrayList<UpcomingEvent> filteredEvents = new ArrayList<>();
        for (UpcomingEvent event : DataGenerator.generateUpcomingEventsData()) {
            if (event.getEventCollege().equalsIgnoreCase(collegeDept)) {
                filteredEvents.add(event);
            }
        }

        if (filteredEvents.isEmpty()) {
            Toast.makeText(this, "No events found for " + collegeDept, Toast.LENGTH_SHORT).show();
        }

        adapter.updateData(filteredEvents);
    }
}
