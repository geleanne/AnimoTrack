package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class CategoryPage  extends AppCompatActivity {
    private ImageButton backArrowButton, profileButton, bookmarkButton, homeButton, eventsButton;
    private RecyclerView recyclerViewEventsOfSpecificCategory;
    private ImageView addEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_page);

        // Access the current logged-in user's full name directly from Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String fullName = user != null ? user.getDisplayName() : "Guest";

        backArrowButton = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        addEventButton = findViewById(R.id.add_event_button);
        eventsButton = findViewById(R.id.eventsButton);
        recyclerViewEventsOfSpecificCategory = findViewById(R.id.recyclerViewEventsOfSpecificCategory);

        String category = getIntent().getStringExtra("category");

        // Get filtered data and update the RecyclerView
        ArrayList<CategorizedEvent> categorizedEvents = DataGenerator.getEventsByCategory(category);
        CategorizedEventAdapter adapter = new CategorizedEventAdapter(this, categorizedEvents, DataGenerator.generateUpcomingEventsData());
        recyclerViewEventsOfSpecificCategory.setAdapter(adapter);
        recyclerViewEventsOfSpecificCategory.setLayoutManager(new LinearLayoutManager(this));

        eventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryPage.this, CreatedEventPage.class);
            intent.putExtra("fullName", fullName);
            startActivity(intent);
        });

        addEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryPage.this, CreateEventActivity.class);
            startActivity(intent);
        });

        backArrowButton.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryPage.this, MainActivity.class);
            startActivity(intent);
        });

        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryPage.this, ProfilePage.class);
            startActivity(intent);
        });

        bookmarkButton.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryPage.this, BookmarkPage.class);
            startActivity(intent);
        });

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryPage.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
