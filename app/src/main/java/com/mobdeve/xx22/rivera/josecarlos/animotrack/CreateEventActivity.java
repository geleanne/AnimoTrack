package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateEventActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText eventNameEditText, eventVenueEditText, eventDateEditText, eventFacilitatorEditText, eventDescriptionEditText;
    private Spinner eventCategorySpinner;
    private FirebaseDatabase database;
    private DatabaseReference eventsRef;
    ImageButton backArrow; // Declare backArrow

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        String fullName = getIntent().getStringExtra("fullName");

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance();
        eventsRef = database.getReference("created_events");

        // Initialize UI elements
        eventNameEditText = findViewById(R.id.eventNameEditText);
        eventVenueEditText = findViewById(R.id.eventVenueEditText);
        eventDateEditText = findViewById(R.id.eventDateEditText);
        eventFacilitatorEditText = findViewById(R.id.eventFacilitatorEditText);
        eventDescriptionEditText = findViewById(R.id.eventDescriptionEditText);
        eventCategorySpinner = findViewById(R.id.eventCategorySpinner);
        Button buttonSubmitEvent = findViewById(R.id.buttonSubmitEvent);

        backArrow = findViewById(R.id.back_arrow);

        // Set the adapter for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.event_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventCategorySpinner.setAdapter(adapter);

        // handle back arrow
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEventActivity.this, MainActivity.class);
                intent.putExtra("fullName", fullName);
                finish();
            }
        });

        // Handle Submit Button click
        buttonSubmitEvent.setOnClickListener(v -> {
            // Capture event details
            String eventName = eventNameEditText.getText().toString().trim();
            String eventVenue = eventVenueEditText.getText().toString().trim();
            String eventDate = eventDateEditText.getText().toString().trim();
            String eventFacilitator = eventFacilitatorEditText.getText().toString().trim();
            String eventDescription = eventDescriptionEditText.getText().toString().trim();
            String eventCategory = eventCategorySpinner.getSelectedItem().toString(); // Get the selected category

            // Validate inputs
            if (eventName.isEmpty() || eventVenue.isEmpty() || eventDate.isEmpty() || eventFacilitator.isEmpty() || eventDescription.isEmpty()) {
                Toast.makeText(CreateEventActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Save the event to Firebase
                String eventId = eventsRef.push().getKey();
                Event event = new Event(0, eventName); // Use a default image or dynamic assignment
                UpcomingEvent upcomingEvent = new UpcomingEvent(event, eventDate, eventVenue, eventFacilitator, eventDescription, false);
                if (eventId != null) {
                    eventsRef.child(eventId).setValue(upcomingEvent)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(CreateEventActivity.this, "Event Created Successfully", Toast.LENGTH_SHORT).show();
                                clearFields();
                            })
                            .addOnFailureListener(e -> Toast.makeText(CreateEventActivity.this, "Failed to create event", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    // Method to clear all the form fields after submission
    private void clearFields() {
        eventNameEditText.setText("");
        eventVenueEditText.setText("");
        eventDateEditText.setText("");
        eventFacilitatorEditText.setText("");
        eventDescriptionEditText.setText("");
        eventCategorySpinner.setSelection(0); // Reset spinner to default selection
    }
}
