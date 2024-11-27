package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class CategoryPage  extends AppCompatActivity {
    private ImageButton backArrowButton, profileButton, bookmarkButton, homeButton;
    private RecyclerView recyclerViewEventsOfSpecificCategory;
    private ImageView addEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_page);

        // Access the current logged-in user's full name directly from Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String fullName = user != null ? user.getDisplayName() : "Guest"; // Default if null

        backArrowButton = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        addEventButton = findViewById(R.id.add_event_button);
        recyclerViewEventsOfSpecificCategory = findViewById(R.id.recyclerViewEventsOfSpecificCategory);

        // Retrieve category from intent
        String category = getIntent().getStringExtra("category");

        // Get filtered data and set up RecyclerView
        ArrayList<CategorizedEvent> categorizedEvents = DataGenerator.getEventsByCategory(category);
        CategorizedEventAdapter adapter = new CategorizedEventAdapter(this, categorizedEvents, DataGenerator.generateUpcomingEventsData());
        recyclerViewEventsOfSpecificCategory.setAdapter(adapter);
        recyclerViewEventsOfSpecificCategory.setLayoutManager(new LinearLayoutManager(this));

        // Set OnClickListener for the buttons
        addEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryPage.this, CreateEventActivity.class);
            startActivity(intent); // Start the CreateEventActivity activity
        });

        backArrowButton.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryPage.this, MainActivity.class);
            startActivity(intent); // Start the HomePage activity
        });

        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryPage.this, ProfilePage.class);
            startActivity(intent); // Start the ProfilePage activity
        });

        bookmarkButton.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryPage.this, BookmarkPage.class);
            startActivity(intent); // Start the BookmarksPage activity
        });

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryPage.this, MainActivity.class);
            startActivity(intent); // Start the HomePage activity
        });
    }
}
