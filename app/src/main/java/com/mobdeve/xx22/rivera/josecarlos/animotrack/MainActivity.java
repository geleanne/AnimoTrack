package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewUpcomingEvents;
    ImageView addEventButton;
    ImageButton profileButton;
    ImageButton bookmarkButton;
    ImageButton homeButton;
    ImageButton eventsButton;
    ImageButton upcomingEventsExtButton;
    ImageButton orgsIconImageButton;
    ImageButton acadsIconImageButton;
    ImageButton seminarIconImageButton;
    ImageButton sportsIconImageButton;
    ImageButton culturalIconImageButton;
    TextView greetNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewUpcomingEvents = findViewById(R.id.recyclerViewUpcomingEvents);
        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        eventsButton = findViewById(R.id.eventsButton);
        upcomingEventsExtButton = findViewById(R.id.upcomingEventsExtButton);
        orgsIconImageButton = findViewById(R.id.orgsIconImageButton);
        acadsIconImageButton = findViewById(R.id.acadsIconImageButton);
        seminarIconImageButton = findViewById(R.id.seminarIconImageButton);
        sportsIconImageButton = findViewById(R.id.sportsIconImageButton);
        culturalIconImageButton = findViewById(R.id.culturalIconImageButton);
        addEventButton = findViewById(R.id.add_event_button);
        greetNameTextView = findViewById(R.id.greetNameTextView);

        // get the name of the user from the signed in user and display it in the greetNameTextView
        String fullName = getIntent().getStringExtra("fullName");

        if (fullName != null && !fullName.isEmpty()) {
            greetNameTextView.setText("Hello, " + fullName + "!");
        }

        ArrayList<UpcomingEvent> events = DataGenerator.generateUpcomingEventsData();

        // Limit the number of events displayed
        int maxEventsToShow = 5;
        if (events.size() > maxEventsToShow) {
            events = new ArrayList<>(events.subList(0, maxEventsToShow));
        }

        // Set up the RecyclerView for events
        UpcomingEventAdapter upcomingEventAdapter = new UpcomingEventAdapter(this, events);
        recyclerViewUpcomingEvents.setAdapter(upcomingEventAdapter);
        recyclerViewUpcomingEvents.setLayoutManager(new LinearLayoutManager(this));

        upcomingEventsExtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpcomingEventExtPage.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the UpcomingEventsPage activity
            }
        });

        // Set OnClickListener for the buttons
        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreatedEvent.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the CreatedEvent activity
            }
        });

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the CreateEventActivity
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfilePage.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the LoginPage activity
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookmarkPage.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the BookmarksPage activity
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the HomePage activity
            }
        });

        // --------------------------------- //
        // Set OnClickListener for the categories
        orgsIconImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryPage.class);
                intent.putExtra("category", "Organizations");
                startActivity(intent);
            }
        });

        acadsIconImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryPage.class);
                intent.putExtra("category", "Academics");
                startActivity(intent);
            }
        });

        seminarIconImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryPage.class);
                intent.putExtra("category", "Seminars");
                startActivity(intent);
            }
        });

        sportsIconImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryPage.class);
                intent.putExtra("category", "Sports");
                startActivity(intent);
            }
        });

        culturalIconImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryPage.class);
                intent.putExtra("category", "Cultural");
                startActivity(intent);
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

