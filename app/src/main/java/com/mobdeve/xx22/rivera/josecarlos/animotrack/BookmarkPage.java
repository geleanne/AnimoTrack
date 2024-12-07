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
import java.util.Objects;

public class BookmarkPage extends AppCompatActivity {

    public static ArrayList<BookmarkEvent> bookmarkEvents = new ArrayList<>();
    private BookmarkEventAdapter adapter;

    ImageButton backArrow;
    ImageButton profileButton;
    ImageButton bookmarkButton;
    ImageButton eventsButton;
    ImageButton homeButton;
    ImageView notificationsButton;
    ImageView addEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_events);

        RecyclerView recyclerViewBookmarkEvents = findViewById(R.id.recycler_view_bookmarked_events);
        recyclerViewBookmarkEvents.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BookmarkEventAdapter(this, bookmarkEvents);
        recyclerViewBookmarkEvents.setAdapter(adapter);

        fetchBookmarkedEvents();

        backArrow = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        eventsButton = findViewById(R.id.eventsButton);
        addEventButton = findViewById(R.id.add_event_button);
        notificationsButton = findViewById(R.id.notificationsButton);

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

        notificationsButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookmarkPage.this, NotificationsPage.class);
            startActivity(intent);
        });
    }

    private void fetchBookmarkedEvents() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("BookmarkedEvents")
                    .whereEqualTo("userId", userId)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        bookmarkEvents.clear();
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            String eventName = doc.getString("eventName");
                            if (eventName != null) {
                                db.collection("AnimoTrackEvents")
                                        .document(eventName)
                                        .get()
                                        .addOnSuccessListener(eventDoc -> {
                                            String collegeDept = eventDoc.getString("collegeDept");
                                            BookmarkEvent event = new BookmarkEvent(
                                                    new Event(
                                                            Objects.requireNonNull(doc.getLong("eventImageId")).intValue(),
                                                            eventName,
                                                            doc.getString("category")
                                                    ),
                                                    doc.getString("eventDate"),
                                                    doc.getString("eventVenue"),
                                                    doc.getString("eventFacilitator"),
                                                    doc.getString("eventDescription"),
                                                    collegeDept != null ? collegeDept : "Unknown College",
                                                    Boolean.TRUE.equals(doc.getBoolean("isBookmarked"))
                                            );
                                            bookmarkEvents.add(event);
                                            adapter.notifyDataSetChanged();
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
            adapter.notifyDataSetChanged();
        }
    }
}

