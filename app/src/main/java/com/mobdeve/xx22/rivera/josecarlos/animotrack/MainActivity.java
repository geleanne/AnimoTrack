package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewUpcomingEvents;
    ImageButton profileButton; // Declare profileButton
    ImageButton bookmarkButton; // Declare bookmarkButton
    ImageButton homeButton; // Declare homeButton
    ImageButton upcomingEventsExtButton; // Declare upcomingEventsExtButton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewUpcomingEvents = findViewById(R.id.recyclerViewUpcomingEvents);
        profileButton = findViewById(R.id.profileButton); // Initialize profileButton
        bookmarkButton = findViewById(R.id.bookmarksButton); // Initialize bookmarkButton
        homeButton = findViewById(R.id.homeButton); // Initialize homeButton
        upcomingEventsExtButton = findViewById(R.id.upcomingEventsExtButton); // Initialize upcomingEventsExtButton

        ArrayList<UpcomingEvent> events = DataGenerator.generateUpcomingEventsData();

        // Set up the RecyclerView for events
        UpcomingEventAdapter upcomingEventAdapter = new UpcomingEventAdapter(this, events);
        recyclerViewUpcomingEvents.setAdapter(upcomingEventAdapter);
        recyclerViewUpcomingEvents.setLayoutManager(new LinearLayoutManager(this));

        upcomingEventsExtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpcomingEventExtPage.class);
                startActivity(intent); // Start the UpcomingEventsPage activity
            }
        });

        // Set OnClickListener for the profileButton
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfilePage.class);
                startActivity(intent); // Start the LoginPage activity
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookmarkPage.class);
                startActivity(intent); // Start the BookmarksPage activity
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent); // Start the HomePage activity
            }
        });
    }
}


//        ImageView myImageButton = findViewById(R.id.my_image_button);
//        myImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Perform your action here, similar to a button click
//                // For example, you can start a new activity:
//                Intent intent = new Intent(MainActivity.this, LoginPage.class);
//                startActivity(intent);
//            }
//        });

