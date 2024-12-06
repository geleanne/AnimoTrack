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

public class NotificationsPage extends AppCompatActivity {

    private RecyclerView notificationsRecyclerView;
    private NotificationsAdapter adapter;
    private ArrayList<NotificationsItem> notificationsList;
    ImageButton backArrow; // Declare backArrow
    ImageButton profileButton; // Declare profileButton
    ImageButton bookmarkButton; // Declare bookmarkButton
    ImageButton homeButton; // Declare homeButton
    ImageButton eventsButton;
    ImageView addEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_page);

        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);

        // Initialize the buttons
        addEventButton = findViewById(R.id.add_event_button);
        backArrow = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        eventsButton = findViewById(R.id.eventsButton);

        // Initialize the notifications list.
        notificationsList = new ArrayList<>();
        // Example: Add sample notifications
        notificationsList.add(new NotificationsItem("Event Updated", "The venue for Event A has changed."));
        notificationsList.add(new NotificationsItem("New Event Added", "Event B has been added to the calendar."));

        adapter = new NotificationsAdapter(this, notificationsList);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationsRecyclerView.setAdapter(adapter);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close current activity and return to the previous one
            }
        });

        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsPage.this, CreatedEventPage.class);
                startActivity(intent); // Start the CreatedEventsPage activity
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsPage.this, ProfilePage.class);
                startActivity(intent); // Start the LoginPage activity
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsPage.this, BookmarkPage.class);
                startActivity(intent); // Start the BookmarksPage activity
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsPage.this, MainActivity.class);
                startActivity(intent); // Start the HomePage activity
            }
        });

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsPage.this, CreateEventActivity.class);
                startActivity(intent); // Start the AddEventPage activity
            }
        });
    }
}
