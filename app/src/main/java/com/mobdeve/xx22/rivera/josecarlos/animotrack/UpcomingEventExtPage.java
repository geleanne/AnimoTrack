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

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UpcomingEventExtPage extends AppCompatActivity {

    private UpcomingEventExtAdapter adapter;
    private ArrayList<UpcomingEvent> upcomingEvents;
    private FirestoreHelper firestoreHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_extend);

        String fullName = getIntent().getStringExtra("fullName");

        RecyclerView recyclerViewUpcomingEventsExtended = findViewById(R.id.recyclerViewUpcomingEventsExtended);
        ImageButton backArrow = findViewById(R.id.back_arrow);
        ImageButton profileButton = findViewById(R.id.profileButton);
        ImageButton bookmarkButton = findViewById(R.id.bookmarksButton);
        ImageButton homeButton = findViewById(R.id.homeButton);
        ImageButton eventsButton = findViewById(R.id.eventsButton);
        ImageView addButton = findViewById(R.id.add_event_button);

        ArrayList<UpcomingEvent> events = DataGenerator.generateUpcomingEventsData();

        UpcomingEventExtAdapter upcomingEventAdapter = new UpcomingEventExtAdapter(this, events);
        recyclerViewUpcomingEventsExtended.setAdapter(upcomingEventAdapter);
        recyclerViewUpcomingEventsExtended.setLayoutManager(new LinearLayoutManager(this));

        Spinner spinnerDepartments = findViewById(R.id.spinner_departments);

        firestoreHelper = new FirestoreHelper();

        upcomingEvents = new ArrayList<>();
        adapter = new UpcomingEventExtAdapter(this, upcomingEvents);
        recyclerViewUpcomingEventsExtended.setAdapter(adapter);
        recyclerViewUpcomingEventsExtended.setLayoutManager(new LinearLayoutManager(this));

        fetchEventsByCollege("All");

        spinnerDepartments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCollege = parent.getItemAtPosition(position).toString();
                fetchEventsByCollege(selectedCollege);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                fetchEventsByCollege("All");
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, UpcomingEventExtPage.class);
                intent.putExtra("fullName", fullName);finish();
            }
        });

        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, CreatedEventPage.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, ProfilePage.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, CreateEventActivity.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent);
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, BookmarkPage.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, MainActivity.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent);
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
