package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilePage extends AppCompatActivity {
    ImageButton profileButton; // Declare profileButton
    ImageButton bookmarkButton; // Declare bookmarkButton
    ImageButton homeButton; // Declare homeButton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        profileButton = findViewById(R.id.profileButton); // Initialize profileButton
        bookmarkButton = findViewById(R.id.bookmarksButton); // Initialize bookmarkButton
        homeButton = findViewById(R.id.homeButton); // Initialize homeButton

        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, ProfilePage.class);
            startActivity(intent); // Start the LoginPage activity
        });

        bookmarkButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, BookmarkPage.class);
            startActivity(intent); // Start the BookmarksPage activity
        });

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, MainActivity.class);
            startActivity(intent); // Start the HomePage activity
        });
    }
}