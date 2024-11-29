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

import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
            String eventCategory = eventCategorySpinner.getSelectedItem().toString();

            // Validate inputs
            if (eventName.isEmpty() || eventVenue.isEmpty() || eventDate.isEmpty() || eventFacilitator.isEmpty() || eventDescription.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (eventCategory.equals("Select Category")) {
                Toast.makeText(this, "Please select a valid category", Toast.LENGTH_SHORT).show();
                return;
            }

            // Parse date
            Date parsedDate;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                parsedDate = sdf.parse(eventDate);
            } catch (Exception e) {
                Toast.makeText(this, "Invalid date format (MM/DD/YYYY expected)", Toast.LENGTH_SHORT).show();
                return;
            }

            // Prepare Firestore data
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference eventsCollection = db.collection("AnimoTrackEvents");

            Map<String, Object> eventData = new HashMap<>();
            eventData.put("category", eventCategory);
            eventData.put("eventDescription", eventDescription);
            eventData.put("eventDate", new Timestamp(parsedDate));
            eventData.put("eventFacilitator", eventFacilitator);
            eventData.put("eventName", eventName);
            eventData.put("eventVenue", eventVenue);
            eventData.put("eventDrawableId", R.drawable.calendar_event1);

            // Add to Firestore
            eventsCollection.add(eventData)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Event created successfully!", Toast.LENGTH_SHORT).show();
                        clearFields();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to create event: " + e.getMessage(), Toast.LENGTH_SHORT).show());
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
