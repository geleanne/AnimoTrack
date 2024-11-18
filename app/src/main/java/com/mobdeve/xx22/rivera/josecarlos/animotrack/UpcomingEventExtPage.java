package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UpcomingEventExtPage extends AppCompatActivity {

    RecyclerView recyclerViewUpcomingEventsExtended;
    ImageButton backArrow;
    ImageButton profileButton; // Declare profileButton
    ImageButton bookmarkButton; // Declare bookmarkButton
    ImageButton homeButton; // Declare homeButton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_extend);

        recyclerViewUpcomingEventsExtended = findViewById(R.id.recyclerViewUpcomingEventsExtended);
        backArrow = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton); // Initialize profileButton
        bookmarkButton = findViewById(R.id.bookmarksButton); // Initialize bookmarkButton
        homeButton = findViewById(R.id.homeButton); // Initialize homeButton

        ArrayList<UpcomingEvent> events = DataGenerator.generateUpcomingEventsData();

        // Set up the RecyclerView for events
        UpcomingEventExtAdapter upcomingEventAdapter = new UpcomingEventExtAdapter(this, events);
        recyclerViewUpcomingEventsExtended.setAdapter(upcomingEventAdapter);
        recyclerViewUpcomingEventsExtended.setLayoutManager(new LinearLayoutManager(this));


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close current activity and return to the previous one
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, ProfilePage.class);
                startActivity(intent); // Start the LoginPage activity
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, BookmarkPage.class);
                startActivity(intent); // Start the BookmarksPage activity
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpcomingEventExtPage.this, MainActivity.class);
                startActivity(intent); // Start the HomePage activity
            }
        });
    }
}
