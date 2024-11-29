package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilePage extends AppCompatActivity {
    ImageButton profileButton;
    ImageButton bookmarkButton;
    ImageButton homeButton;
    ImageButton eventsButton;
    ImageView addEventButton;
    Button logoutButton;
    Button deleteAccButton;
    TextView fullNameTextView;
    TextView numberJoinedEvents;
    TextView numberBookmarkedEvents;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        addEventButton = findViewById(R.id.add_event_button);
        eventsButton = findViewById(R.id.eventsButton);
        deleteAccButton = findViewById(R.id.deleteAccButton);
        logoutButton = findViewById(R.id.logoutButton);
        fullNameTextView = findViewById(R.id.fullNameTextView);
        numberJoinedEvents = findViewById(R.id.numberJoinedEvents);
        numberBookmarkedEvents = findViewById(R.id.numberBookmarkedEvents);

        int eventImageId = getIntent().getIntExtra("event_image", R.drawable.event1);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Regular user data fetching from 'AnimoTrackUsers' collection
            db.collection("AnimoTrackUsers").document(currentUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String fullName = documentSnapshot.getString("name");
                    if (fullName != null && !fullName.isEmpty()) {
                        fullNameTextView.setText(fullName); // Set the full name to TextView
                    }
                }
            })
            .addOnFailureListener(e -> {
                Toast.makeText(ProfilePage.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
            });
        }

        // if the user has bookmarked events, display and update the numberBookmarkedEvents TextView accordingly
        if (!BookmarkPage.bookmarkEvents.isEmpty()) {
            numberBookmarkedEvents.setText(String.valueOf(BookmarkPage.bookmarkEvents.size()));
        } else {
            numberBookmarkedEvents.setText("0");
        }

        // if the user has any events joined by confirming it by clicking the rsvp button, display and update the numberJoinedEvents TextView accordingly


//        MaterialCalendarView calendarView = findViewById(R.id.calendarView);
//
//        // Fetch decorated dates and event images from DataGenerator
//        List<CalendarDay> decoratedDates = DataGenerator.getDecoratedDates();
//        Map<CalendarDay, Integer> eventImages = DataGenerator.getEventImages();
//
//        // Apply decorators for each date with a corresponding image
//        for (Map.Entry<CalendarDay, Integer> entry : eventImages.entrySet()) {
//            calendarView.addDecorator(new EventImageDecorator(this, entry.getKey(), entry.getValue()));
//        }
//
//        // Highlight the current date
//        calendarView.setDateSelected(CalendarDay.today(), true);
//        calendarView.setCurrentDate(CalendarDay.today());
//        calendarView.setSelectedDate(CalendarDay.today());
//
//        // Set date selection behavior
//        calendarView.setOnDateChangedListener((widget, date, selected) -> {
//            if (decoratedDates.contains(date)) {
//                Toast.makeText(this, "Event available on this date.", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "No events scheduled for this day.", Toast.LENGTH_SHORT).show();
//            }
//        });


        MaterialCalendarView calendarView = findViewById(R.id.calendarView);

        // Fetch decorated dates and event images from DataGenerator
        List<CalendarDay> decoratedDates = DataGenerator.getDecoratedDates();
//        ArrayList<Event> events = DataGenerator.generateCalendarEventData();
        List<Integer> eventDrawables = DataGenerator.getEventDrawables();

        // Loop through the event drawables and decorate the calendar
        for (int drawable : eventDrawables) {
            // get the respective drawable and add it to the calendar
            calendarView.addDecorator(new EventImageDecorator(this, decoratedDates, drawable));
        }

        // highlight the current date on the calendar
        calendarView.setDateSelected(CalendarDay.today(), true);
        calendarView.setCurrentDate(CalendarDay.today());
        calendarView.setSelectedDate(CalendarDay.today());

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            // Do something when a date is selected
            // if the date has an event scheduled, go to the respective event registration page
            // else, display a message that there are no events scheduled for that day
            if (decoratedDates.contains(date)) {
                Intent intent = new Intent(ProfilePage.this, RegistrationEvent.class);
                startActivity(intent);
            } else {
                Toast.makeText(ProfilePage.this, "No events scheduled for this day.", Toast.LENGTH_SHORT).show();
            }
        });


        deleteAccButton.setOnClickListener(v -> {
            if (currentUser != null) {
                // Show confirmation dialog
                new AlertDialog.Builder(ProfilePage.this)
                        .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                        .setCancelable(false)
                        .setPositiveButton("Delete Account", (dialog, id) -> {
                            // Step 1: Delete user data from Firestore
                            db.collection("AnimoTrackUsers").document(currentUser.getUid())
                                    .delete()
                                    .addOnSuccessListener(aVoid -> {
                                        // Step 2: Delete user from Firebase Authentication
                                        currentUser.delete()
                                                .addOnSuccessListener(aVoid1 -> {
                                                    Toast.makeText(ProfilePage.this, "Account deleted successfully.", Toast.LENGTH_SHORT).show();
                                                    // Redirect to a landing or login page
                                                    Intent intent = new Intent(ProfilePage.this, LoginPage.class);
                                                    startActivity(intent);
                                                    finish();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(ProfilePage.this, "Error deleting account. Please try again.", Toast.LENGTH_SHORT).show();
                                                });
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(ProfilePage.this, "Error deleting user data. Please try again.", Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            } else {
                Toast.makeText(ProfilePage.this, "No user is logged in.", Toast.LENGTH_SHORT).show();
            }
        });

        logoutButton.setOnClickListener(v -> {
            new AlertDialog.Builder(ProfilePage.this)
                    .setMessage("Are you sure you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Log Out", (dialog, id) -> {
                        Intent intent = new Intent(ProfilePage.this, HomepageActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        eventsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, CreatedEvent.class);
            startActivity(intent); // Start the CreatedEvent activity
        });

        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, ProfilePage.class);
            startActivity(intent); // Start the ProfilePage activity
        });

        bookmarkButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, BookmarkPage.class);
            startActivity(intent); // Start the BookmarksPage activity
        });

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, MainActivity.class);
            startActivity(intent); // Start the MainActivity activity
        });

        addEventButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, CreateEventActivity.class);
            startActivity(intent); // Start the CreateEventActivity activity
        });
    }
}
