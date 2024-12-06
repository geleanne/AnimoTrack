package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class BookmarkPage extends AppCompatActivity {

    public static ArrayList<BookmarkEvent> bookmarkEvents = new ArrayList<>();
    private RecyclerView recyclerViewBookmarkEvents;
    private BookmarkEventAdapter adapter;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    ImageButton backArrow;
    ImageButton profileButton; // Declare profileButton
    ImageButton bookmarkButton; // Declare bookmarkButton
    ImageButton eventsButton;
    ImageButton homeButton; // Declare homeButton
    ImageView addEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_events);

        recyclerViewBookmarkEvents = findViewById(R.id.recycler_view_bookmarked_events);
        recyclerViewBookmarkEvents.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter
        adapter = new BookmarkEventAdapter(this, bookmarkEvents);
        recyclerViewBookmarkEvents.setAdapter(adapter);

        // Fetch bookmarked events from Firestore
        fetchBookmarkedEvents();

        backArrow = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        eventsButton = findViewById(R.id.eventsButton);
        addEventButton = findViewById(R.id.add_event_button);

        backArrow.setOnClickListener(v -> finish());

        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookmarkPage.this, ProfilePage.class);
            startActivity(intent);
        });

        eventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookmarkPage.this, CreatedEventPage.class);
            startActivity(intent);
        });

        bookmarkButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookmarkPage.this, BookmarkPage.class);
            startActivity(intent);
        });

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookmarkPage.this, MainActivity.class);
            startActivity(intent);
        });

        addEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookmarkPage.this, CreateEventActivity.class);
            startActivity(intent);
        });
    }

    private void fetchBookmarkedEvents() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("BookmarkedEvents")
                    .whereEqualTo("userId", userId)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        bookmarkEvents.clear(); // Clear old data
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            String eventName = doc.getString("eventName");
                            if (eventName != null) {
                                // Fetch collegeDept from AnimoTrackEvents collection
                                db.collection("AnimoTrackEvents")
                                        .document(eventName)
                                        .get()
                                        .addOnSuccessListener(eventDoc -> {
                                            String collegeDept = eventDoc.getString("collegeDept");
                                            BookmarkEvent event = new BookmarkEvent(
                                                    new Event(
                                                            doc.getLong("eventImageId").intValue(),
                                                            eventName,
                                                            doc.getString("category")
                                                    ),
                                                    doc.getString("eventDate"),
                                                    doc.getString("eventVenue"),
                                                    doc.getString("eventFacilitator"),
                                                    doc.getString("eventDescription"),
                                                    collegeDept != null ? collegeDept : "Unknown College",
                                                    doc.getBoolean("isBookmarked")
                                            );
                                            bookmarkEvents.add(event);
                                            adapter.notifyDataSetChanged(); // Notify adapter of new data
                                        })
                                        .addOnFailureListener(e -> Log.e("Firestore", "Error fetching collegeDept", e));
                            }
                        }
                    })
                    .addOnFailureListener(e -> Log.e("Firestore", "Error loading bookmarks", e));
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged(); // Refresh the list
        }
    }
}

