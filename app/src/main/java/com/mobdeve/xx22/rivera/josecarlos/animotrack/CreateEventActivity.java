package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText eventNameEditText, eventVenueEditText, eventDateEditText, eventFacilitatorEditText, eventDescriptionEditText;
    private Spinner eventCollegeSpinner, eventCategorySpinner;
    Button buttonSubmitEvent;
    ImageButton backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        String fullName = getIntent().getStringExtra("fullName");

        // Initialize Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventsRef = database.getReference("created_events");

        // Initialize UI elements
        eventNameEditText = findViewById(R.id.eventNameEditText);
        eventVenueEditText = findViewById(R.id.eventVenueEditText);
        eventDateEditText = findViewById(R.id.eventDateEditText);
        eventFacilitatorEditText = findViewById(R.id.eventFacilitatorEditText);
        eventDescriptionEditText = findViewById(R.id.eventDescriptionEditText);
        eventCollegeSpinner = findViewById(R.id.eventCollegeSpinner);
        eventCategorySpinner = findViewById(R.id.eventCategorySpinner);
        buttonSubmitEvent = findViewById(R.id.buttonSubmitEvent);

        backArrow = findViewById(R.id.back_arrow);

        String[] eventCategories = getResources().getStringArray(R.array.event_categories);
        String[] eventColleges = getResources().getStringArray(R.array.event_college);

        List<String> categoryList = new ArrayList<>();
        List<String> collegeList = new ArrayList<>();

        collegeList.add("College");
        collegeList.addAll(Arrays.asList(eventColleges));

        categoryList.add("Category");
        categoryList.addAll(Arrays.asList(eventCategories));

        // Set the adapter for the spinner
        ArrayAdapter<String> collegeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, collegeList);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);

        collegeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        eventCollegeSpinner.setAdapter(collegeAdapter);
        eventCollegeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selectedView = (TextView) parent.getChildAt(0);
                if (selectedView != null) {
                    if (position == 0) {
                        selectedView.setTextColor(ContextCompat.getColor(CreateEventActivity.this, R.color.gray));
                    } else {
                        selectedView.setTextColor(ContextCompat.getColor(CreateEventActivity.this, R.color.black));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        eventCategorySpinner.setAdapter(categoriesAdapter);
        eventCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selectedView = (TextView) parent.getChildAt(0);
                if (selectedView != null) {
                    if (position == 0) {
                        selectedView.setTextColor(ContextCompat.getColor(CreateEventActivity.this, R.color.gray));
                    } else {
                        selectedView.setTextColor(ContextCompat.getColor(CreateEventActivity.this, R.color.black));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        eventCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selectedView = (TextView) parent.getChildAt(0);
                if (selectedView != null) {
                    if (position == 0) {
                        selectedView.setTextColor(ContextCompat.getColor(CreateEventActivity.this, R.color.gray));
                    } else {
                        selectedView.setTextColor(ContextCompat.getColor(CreateEventActivity.this, R.color.black));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEventActivity.this, MainActivity.class);
                intent.putExtra("fullName", fullName);
                finish();
            }
        });

        buttonSubmitEvent.setOnClickListener(v -> {
            String eventName = eventNameEditText.getText().toString().trim();
            String eventVenue = eventVenueEditText.getText().toString().trim();
            String eventDate = eventDateEditText.getText().toString().trim();
            String eventFacilitator = eventFacilitatorEditText.getText().toString().trim();
            String eventDescription = eventDescriptionEditText.getText().toString().trim();
            String eventCollege = eventCollegeSpinner.getSelectedItem().toString();
            String eventCategory = eventCategorySpinner.getSelectedItem().toString();
            Boolean isBookmarked = false;

            // Validate inputs
            if (eventName.isEmpty() || eventVenue.isEmpty() || eventDate.isEmpty() || eventFacilitator.isEmpty() || eventDescription.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (eventCollege.equals("College")) {
                Toast.makeText(this, "Please select a valid college", Toast.LENGTH_SHORT).show();
                return;
            }

            if (eventCategory.equals("Category")) {
                Toast.makeText(this, "Please select a valid category", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate date
            if (!isValidDate(eventDate)) {
                Toast.makeText(this, "Please input a valid date (MM/DD/YYYY format)", Toast.LENGTH_SHORT).show();
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
            CollectionReference eventsCollection = db.collection("UserCreatedEvents");

            Map<String, Object> eventData = new HashMap<>();
            eventData.put("category", eventCategory);
            eventData.put("eventDescription", eventDescription);
            eventData.put("eventDate", new Timestamp(parsedDate));
            eventData.put("eventFacilitator", eventFacilitator);
            eventData.put("eventName", eventName);
            eventData.put("eventVenue", eventVenue);
            eventData.put("isBookmarked", isBookmarked);
            eventData.put("eventDrawableId", R.drawable.default_poster_squared);

            // Add to Firestore
            eventsCollection.add(eventData)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Event created successfully!", Toast.LENGTH_SHORT).show();
                        clearFields();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to create event: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }

    // Method to check date validity
    private boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.get(Calendar.MONTH);
            return calendar.get(Calendar.MONTH) < 12;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to clear all the form fields (after)
    private void clearFields() {
        eventNameEditText.setText("");
        eventVenueEditText.setText("");
        eventDateEditText.setText("");
        eventFacilitatorEditText.setText("");
        eventDescriptionEditText.setText("");
        eventCollegeSpinner.setSelection(0);
        eventCategorySpinner.setSelection(0);
    }
}
