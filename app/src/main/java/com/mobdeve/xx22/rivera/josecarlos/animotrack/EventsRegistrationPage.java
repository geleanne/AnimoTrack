package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EventsRegistrationPage extends AppCompatActivity {

    private TextView eventNameTextView;
    private TextView eventDateTextView;
    private TextView eventVenueTextView;
    private TextView eventFacilitatorTextView;
    private TextView eventDescriptionTextView;
    private ImageView eventImageView;

    ImageButton backArrow; // Declare backArrow
    ImageButton profileButton; // Declare profileButton
    ImageButton bookmarkButton; // Declare bookmarkButton
    ImageButton homeButton; // Declare homeButton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_registration); // Make sure your layout file has the correct views

        backArrow = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);

        // Initialize views
        eventNameTextView = findViewById(R.id.event_name); // Adjust with actual TextView ID
        eventDateTextView = findViewById(R.id.event_date); // Adjust with actual TextView ID
        eventVenueTextView = findViewById(R.id.event_venue); // Adjust with actual TextView ID
        eventImageView = findViewById(R.id.event_image); // Adjust with actual ImageView ID
        eventFacilitatorTextView = findViewById(R.id.event_facilitator);
        eventDescriptionTextView = findViewById(R.id.event_description);


        // Retrieve data from Intent
        String eventName = getIntent().getStringExtra("event_name");
        String eventDate = getIntent().getStringExtra("event_date");
        String eventVenue = getIntent().getStringExtra("event_venue");
        String eventFacilitator = getIntent().getStringExtra("event_facilitator");
        String eventDescription = getIntent().getStringExtra("event_description");
        // Get the image resource ID from the intent
        int eventImageId = getIntent().getIntExtra("event_image", R.drawable.event1);

        // Set the data to the TextViews
        eventNameTextView.setText(eventName);
        eventDateTextView.setText(eventDate);
        eventVenueTextView.setText(eventVenue);
        eventFacilitatorTextView.setText(eventFacilitator);
        eventDescriptionTextView.setText(eventDescription);
        eventImageView.setImageResource(eventImageId);

        // Optionally set the event image if you pass the image resource ID
        // Assuming you have the image ID passed as an integer
         // Use a default image if not provided


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close current activity and return to the previous one
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventsRegistrationPage.this, ProfilePage.class);
                startActivity(intent); // Start the LoginPage activity
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventsRegistrationPage.this, BookmarkPage.class);
                startActivity(intent); // Start the BookmarksPage activity
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventsRegistrationPage.this, MainActivity.class);
                startActivity(intent); // Start the HomePage activity
            }
        });
    }
}
