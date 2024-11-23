package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CategoryPage  extends AppCompatActivity {
    private ImageButton backArrowButton, profileButton, bookmarkButton, homeButton;
    private RecyclerView recyclerViewEventsOfSpecificCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_page);

        backArrowButton = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton); // Initialize profileButton
        bookmarkButton = findViewById(R.id.bookmarksButton); // Initialize bookmarkButton
        homeButton = findViewById(R.id.homeButton); // Initialize homeButton
        recyclerViewEventsOfSpecificCategory = findViewById(R.id.recyclerViewEventsOfSpecificCategory);

        // Retrieve category from intent
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        // Get filtered data and set up RecyclerView
        ArrayList<CategorizedEvent> categorizedEvents = DataGenerator.getEventsByCategory(category);
        CategorizedEventAdapter adapter = new CategorizedEventAdapter(this, categorizedEvents, DataGenerator.generateUpcomingEventsData());
        recyclerViewEventsOfSpecificCategory.setAdapter(adapter);
        recyclerViewEventsOfSpecificCategory.setLayoutManager(new LinearLayoutManager(this));

        // Set OnClickListener for the buttons
        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryPage.this, MainActivity.class);
                startActivity(intent); // Start the HomePage activity
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryPage.this, ProfilePage.class);
                startActivity(intent); // Start the LoginPage activity
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryPage.this, BookmarkPage.class);
                startActivity(intent); // Start the BookmarksPage activity
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryPage.this, MainActivity.class);
                startActivity(intent); // Start the HomePage activity
            }
        });
    }
}
