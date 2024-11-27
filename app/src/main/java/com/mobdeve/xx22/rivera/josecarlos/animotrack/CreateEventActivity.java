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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText eventNameEditText, eventVenueEditText, eventDateEditText, eventFacilitatorEditText, eventDescriptionEditText;
    private Spinner eventCategorySpinner;
    private FirebaseDatabase database;
    private DatabaseReference eventsRef;
    Button buttonSubmitEvent;
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
        buttonSubmitEvent = findViewById(R.id.buttonSubmitEvent);

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
                // Save the event to Firebase Realtime Database
                String eventId = eventsRef.push().getKey(); // Create a unique event ID
                Event event = new Event(0, eventName); // Assuming 0 is a placeholder for an image
                UpcomingEvent upcomingEvent = new UpcomingEvent(event, eventDate, eventVenue, eventFacilitator, eventDescription, false);

                // Save to Firestore "SubmittedEvents" collection
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference submittedEventsRef = db.collection("SubmittedEvents");

                if (eventId != null) {
                    // Save the event data to Firebase Realtime Database
                    eventsRef.child(eventId).setValue(upcomingEvent)
                            .addOnSuccessListener(aVoid -> {
                                // Now, save the event data to Firestore
                                Map<String, Object> eventData = new HashMap<>();
                                eventData.put("eventName", eventName);
                                eventData.put("eventVenue", eventVenue);
                                eventData.put("eventDate", eventDate);
                                eventData.put("eventFacilitator", eventFacilitator);
                                eventData.put("eventDescription", eventDescription);
                                eventData.put("eventCategory", eventCategory);

                                // Save the event data to Firestore under "SubmittedEvents" collection
                                submittedEventsRef.add(eventData)
                                        .addOnSuccessListener(documentReference -> {
                                            Toast.makeText(CreateEventActivity.this, "Event Created Successfully", Toast.LENGTH_SHORT).show();
                                            clearFields();
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(CreateEventActivity.this, "Failed to create event in Firestore", Toast.LENGTH_SHORT).show());
                            })
                            .addOnFailureListener(e -> Toast.makeText(CreateEventActivity.this, "Failed to create event in Realtime Database", Toast.LENGTH_SHORT).show());
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
