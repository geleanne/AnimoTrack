package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrationEventPage extends AppCompatActivity {
    private TextView eventNameTextView;
    private TextView eventDateTextView;
    TextView eventCollegeTextView;
    private ImageView eventImageView;
    private boolean isBookmarked = false;
    private boolean isJoined = false;


    ImageButton bookmarkBtn;
    ImageButton backArrow;
    ImageButton profileButton;
    ImageButton bookmarkButton;
    ImageButton homeButton;
    ImageButton eventsButton;
    ImageView notificationsButton;
    Button joinedButton;

    private GestureDetector gestureDetector;
    private ArrayList<UpcomingEvent> eventsList;
    private int currentEventIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_registration);

        backArrow = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        bookmarkBtn = findViewById(R.id.bookmarkBtn);
        eventsButton = findViewById(R.id.eventsButton);
        joinedButton = findViewById(R.id.joinButton);
        notificationsButton = findViewById(R.id.notificationsButton);

        eventNameTextView = findViewById(R.id.event_name);
        eventDateTextView = findViewById(R.id.event_date);
        TextView eventVenueTextView = findViewById(R.id.event_venue);
        eventCollegeTextView = findViewById(R.id.event_college);
        eventImageView = findViewById(R.id.event_image);
        TextView eventDescriptionTextView = findViewById(R.id.event_description);

        String eventName = getIntent().getStringExtra("event_name");
        String eventDate = getIntent().getStringExtra("event_date");
        String eventVenue = getIntent().getStringExtra("event_venue");
        String eventFacilitator = getIntent().getStringExtra("event_facilitator");
        String eventDescription = getIntent().getStringExtra("event_description");
        String eventCollegeDept = getIntent().getStringExtra("event_college");
        int eventImageId = getIntent().getIntExtra("event_image", R.drawable.event1);

        // Set the data to the TextViews
        eventNameTextView.setText(eventName);
        eventDateTextView.setText(eventDate);
        eventVenueTextView.setText(eventVenue);
        eventCollegeTextView.setText(eventCollegeDept);
        eventDescriptionTextView.setText(eventDescription);
        eventImageView.setImageResource(eventImageId);

        // Load events data
        eventsList = DataGenerator.generateUpcomingEventsData();

        // Display the first event
        updateEventDisplay();

        // Gesture detector
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(e2.getY() - e1.getY())) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("AnimoTrackEvents").document(eventName)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String eventCollege = documentSnapshot.getString("collegeDept");
                        eventCollegeTextView.setText(eventCollege != null ? eventCollege : "Unknown College");
                    } else {
                        eventCollegeTextView.setText("Event not found");
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error fetching event: ", e));

        // Check if the event is already bookmarked
        isBookmarked = false; // Reset the status before checking
        for (BookmarkEvent bookmarkedEvent : BookmarkPage.bookmarkEvents) {
            if (bookmarkedEvent.getEventTitle().getName().equals(eventName)) {
                isBookmarked = true;
                break;
            }
        }

        // Update bookmark button state
        if (isBookmarked) {
            bookmarkBtn.setImageResource(R.drawable.shaded_bookmark2);
        } else {
            bookmarkBtn.setImageResource(R.drawable.unshaded_bookmark2);
        }

        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBookmarked = !isBookmarked;

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null) {
                    String userId = currentUser.getUid();

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference bookmarkedEventsCollection = db.collection("BookmarkedEvents");

                    if (isBookmarked) {
                        bookmarkBtn.setImageResource(R.drawable.shaded_bookmark2);
                        Toast.makeText(RegistrationEventPage.this, "Event added to bookmarks!", Toast.LENGTH_SHORT).show();

                        BookmarkEvent newEvent = new BookmarkEvent(
                                new Event(eventImageId, eventName),
                                eventDate,
                                eventVenue,
                                eventFacilitator,
                                eventDescription,
                                eventCollegeDept,
                                true
                        );

                        if (!BookmarkPage.bookmarkEvents.contains(newEvent)) {
                            BookmarkPage.bookmarkEvents.add(newEvent);

                            Map<String, Object> eventData = new HashMap<>();
                            eventData.put("userId", userId);
                            eventData.put("eventImageId", eventImageId);
                            eventData.put("eventName", eventName);
                            eventData.put("eventDate", eventDate);
                            eventData.put("eventVenue", eventVenue);
                            eventData.put("eventFacilitator", eventFacilitator);
                            eventData.put("eventDescription", eventDescription);
                            eventData.put("collegeDept", eventCollegeDept);  // Add collegeDept here
                            eventData.put("isBookmarked", true);

                            bookmarkedEventsCollection.document(eventName)
                                    .set(eventData)
                                    .addOnSuccessListener(aVoid -> Log.d("Firestore", "Event successfully bookmarked."))
                                    .addOnFailureListener(e -> Log.e("Firestore", "Error bookmarking event: ", e));
                        }
                    } else {
                        bookmarkBtn.setImageResource(R.drawable.unshaded_bookmark2);
                        Toast.makeText(RegistrationEventPage.this, "Event removed from bookmarks!", Toast.LENGTH_SHORT).show();

                        // Remove the event from the shared list
                        for (int i = 0; i < BookmarkPage.bookmarkEvents.size(); i++) {
                            if (BookmarkPage.bookmarkEvents.get(i).getEventTitle().getName().equals(eventName)) {
                                BookmarkPage.bookmarkEvents.remove(i);
                                break;
                            }
                        }

                        // Remove the event from Firestore
                        bookmarkedEventsCollection.document(eventName)
                                .delete()
                                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Event successfully removed from bookmarks."))
                                .addOnFailureListener(e -> Log.e("Firestore", "Error removing bookmark: ", e));
                    }
                }
            }
        });

        // Check if the event is already joined
        isJoined = false; // Reset the status before checking
        for (JoinedEvent joinedEvent : JoinedEventPage.joinedEvents) {
            if (joinedEvent.getEventTitle().getName().equals(eventName)) {
                isJoined = true;
                break;
            }
        }

        // Update joined button state
        if (isJoined) {
            joinedButton.setText("Joined");
            joinedButton.setBackgroundColor(ContextCompat.getColor(RegistrationEventPage.this, R.color.gray));
        } else {
            joinedButton.setText("Join");
            joinedButton.setBackgroundColor(ContextCompat.getColor(RegistrationEventPage.this, R.color.green));
        }

        joinedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isJoined = !isJoined;

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null) {
                    String userId = currentUser.getUid();

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference joinedEventsCollection = db.collection("JoinedEvents");

                    if (isJoined) {
                        joinedButton.setText("Joined");
                        joinedButton.setBackgroundColor(ContextCompat.getColor(RegistrationEventPage.this, R.color.gray));
                        Toast.makeText(RegistrationEventPage.this, "Joining event", Toast.LENGTH_SHORT).show();

                        JoinedEvent newEvent = new JoinedEvent(
                                new Event(eventImageId, eventName),
                                eventDate,
                                eventVenue,
                                eventFacilitator,
                                eventDescription,
                                true
                        );

                        if (!JoinedEventPage.joinedEvents.contains(newEvent)) {
                            JoinedEventPage.joinedEvents.add(newEvent);

                            Map<String, Object> eventData = new HashMap<>();
                            eventData.put("userId", userId);
                            eventData.put("eventImageId", eventImageId);
                            eventData.put("eventName", eventName);
                            eventData.put("eventDate", eventDate);
                            eventData.put("eventVenue", eventVenue);
                            eventData.put("eventFacilitator", eventFacilitator);
                            eventData.put("eventDescription", eventDescription);
                            eventData.put("isJoined", true);

                            joinedEventsCollection.document(eventName)
                                    .set(eventData)
                                    .addOnSuccessListener(aVoid -> Log.d("Firestore", "Event successfully joined."))
                                    .addOnFailureListener(e -> Log.e("Firestore", "Error joining event: ", e));
                        }
                    } else {
                        joinedButton.setText("Join");
                        joinedButton.setBackgroundColor(ContextCompat.getColor(RegistrationEventPage.this, R.color.green));
                        Toast.makeText(RegistrationEventPage.this, "Not joining event", Toast.LENGTH_SHORT).show();

                        // Remove the event from the shared list
                        for (int i = 0; i < JoinedEventPage.joinedEvents.size(); i++) {
                            if (JoinedEventPage.joinedEvents.get(i).getEventTitle().getName().equals(eventName)) {
                                JoinedEventPage.joinedEvents.remove(i);
                                break;
                            }
                        }

                        // Remove the event from Firestore
                        joinedEventsCollection.document(eventName)
                                .delete()
                                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Event successfully removed from joined events."))
                                .addOnFailureListener(e -> Log.e("Firestore", "Error removing joined event: ", e));
                    }
                }
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationEventPage.this, CreatedEventPage.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationEventPage.this, ProfilePage.class);
                startActivity(intent);
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationEventPage.this, BookmarkPage.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationEventPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationEventPage.this, NotificationsPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private void updateEventDisplay() {
        UpcomingEvent event = eventsList.get(currentEventIndex);
        eventNameTextView.setText(event.getEventTitle().getName());
        eventDateTextView.setText(event.getEventDate());
        eventImageView.setImageResource(event.getEventTitle().getImageId());
    }

    private void onSwipeLeft() {
        if (currentEventIndex < eventsList.size() - 1) {
            currentEventIndex++;
            updateEventDisplay();
        } else {
            Toast.makeText(this, "No more events.", Toast.LENGTH_SHORT).show();
        }
    }

    private void onSwipeRight() {
        if (currentEventIndex > 0) {
            currentEventIndex--;
            updateEventDisplay();
        } else {
            Toast.makeText(this, "This is the first event", Toast.LENGTH_SHORT).show();
        }
    }
}
