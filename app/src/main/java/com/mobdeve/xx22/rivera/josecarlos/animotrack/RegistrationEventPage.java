package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.BreakIterator;

public class RegistrationEventPage extends AppCompatActivity {

    private UpcomingEvent currentEvent;

    private TextView eventNameTextView;
    private TextView eventDateTextView;
    private TextView eventVenueTextView;
    private TextView eventFacilitatorTextView;
    private TextView eventDescriptionTextView;
    private ImageView eventImageView;
    private boolean isBookmarked = false;

    ImageButton bookmarkBtn;
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
        bookmarkBtn = findViewById(R.id.bookmarkBtn);

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
        int eventImageId = getIntent().getIntExtra("event_image", R.drawable.event1);

        // Set the data to the TextViews
        eventNameTextView.setText(eventName);
        eventDateTextView.setText(eventDate);
        eventVenueTextView.setText(eventVenue);
        eventFacilitatorTextView.setText(eventFacilitator);
        eventDescriptionTextView.setText(eventDescription);
        eventImageView.setImageResource(eventImageId);

        // Check if the event is already bookmarked
        isBookmarked = false; // Reset the status before checking
        for (UpcomingEvent bookmarkedEvent : BookmarkPage.bookmarkEvents) {
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

//        // Set up the initial button state
//        updateBookmarkButton(bookmarkButton, currentEvent);
//
//        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            DataGenerator.toggleBookmark(currentEvent);
//            updateBookmarkButton(bookmarkButton, currentEvent);
//
//                if (currentEvent.isBookmarked()) {
//                    Toast.makeText(RegistrationEventPage.this, "Event added to bookmarks!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(RegistrationEventPage.this, "Event removed from bookmarks!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add the event to the bookmarks list
//                BookmarkEvent bookmarkEvent = new BookmarkEvent(new Event(eventImageId, eventName), eventDate, eventVenue, eventFacilitator, eventDescription, clickedFavorite);
//                BookmarkPage.bookmarkEvents.add(bookmarkEvent);

//                // navigate to the bookmarks page
//                Intent intent = new Intent(EventsRegistrationPage.this, BookmarkPage.class);
//                startActivity(intent);

                isBookmarked = !isBookmarked;

                if (isBookmarked) {
                    bookmarkBtn.setImageResource(R.drawable.shaded_bookmark2);
                    Toast.makeText(RegistrationEventPage.this, "Event added to bookmarks!", Toast.LENGTH_SHORT).show();

                    UpcomingEvent newEvent = new UpcomingEvent(
                            new Event(eventImageId, eventName),
                            eventDate,
                            eventVenue,
                            eventFacilitator,
                            eventDescription,
                            true
                    );

                    if (!BookmarkPage.bookmarkEvents.contains(newEvent)) {
                        BookmarkPage.bookmarkEvents.add(newEvent);
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
                }
            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close current activity and return to the previous one
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationEventPage.this, ProfilePage.class);
                startActivity(intent); // Start the LoginPage activity
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationEventPage.this, BookmarkPage.class);
                startActivity(intent); // Start the BookmarksPage activity
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationEventPage.this, MainActivity.class);
                startActivity(intent); // Start the HomePage activity
            }
        });
    }


//    private void updateBookmarkButton(ImageButton bookmarkButton, UpcomingEvent event) {
//        BreakIterator button = null;
//        if (event.isBookmarked()) {
//            button.setText("Remove Bookmark");  // Update UI to show "unbookmark"
//        } else {
//            button.setText("Bookmark");  // Update UI to show "bookmark"
//        }
//    }

//    public void clickBookmark(Event event)  {
//        if (event.isBookmarked()) {
//            event.setBookmarked(false);
//            BookmarkPage.bookmarkEvents.remove(new BookmarkEvent(event, "date", "venue", "facilitator", "description", false));
//        } else {
//            event.setBookmarked(true);
//            BookmarkPage.bookmarkEvents.add(new BookmarkEvent(event, "date", "venue", "facilitator", "description", true));
//        }
//    }


}
