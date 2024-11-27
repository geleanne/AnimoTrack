package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BookmarkPage extends AppCompatActivity {

    public static ArrayList<UpcomingEvent> bookmarkEvents = new ArrayList<>();
    private RecyclerView recyclerViewBookmarkEvents;
    private UpcomingEventAdapter adapter;

    ImageButton backArrow;
    ImageButton profileButton; // Declare profileButton
    ImageButton bookmarkButton; // Declare bookmarkButton
    ImageButton homeButton; // Declare homeButton
    ImageView addEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark);

        String fullName = getIntent().getStringExtra("fullName");

        recyclerViewBookmarkEvents = findViewById(R.id.recycler_view_bookmarked_events);
        recyclerViewBookmarkEvents.setLayoutManager(new LinearLayoutManager(this));

        // Pass the bookmarked events to the adapter
        adapter = new UpcomingEventAdapter(this, bookmarkEvents);
        recyclerViewBookmarkEvents.setAdapter(adapter);

        backArrow = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton); // Initialize profileButton
        bookmarkButton = findViewById(R.id.bookmarksButton); // Initialize bookmarkButton
        homeButton = findViewById(R.id.homeButton); // Initialize homeButton
        addEventButton = findViewById(R.id.add_event_button); // Initialize addEventButton

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkPage.this, BookmarkPage.class);
                intent.putExtra("fullName", fullName);
                finish(); // Close current activity and return to the previous one
            }
        });

        // Set OnClickListener for the profileButton
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkPage.this, ProfilePage.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the LoginPage activity
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookmarkPage.this, BookmarkPage.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the BookmarksPage activity
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkPage.this, MainActivity.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the HomePage activity
            }
        });

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkPage.this, CreateEventActivity.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent); // Start the CreateEventActivity activity
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged(); // Refresh the list
        }
    }

}
