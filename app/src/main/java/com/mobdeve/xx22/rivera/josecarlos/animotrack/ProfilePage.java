package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfilePage extends AppCompatActivity {
    ImageButton profileButton; // Declare profileButton
    ImageButton bookmarkButton; // Declare bookmarkButton
    ImageButton homeButton; // Declare homeButton
    ImageButton eventsButton; // Declare eventsButton
    private ImageView addEventButton;
    private Button logoutButton;
    TextView fullNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        profileButton = findViewById(R.id.profileButton); // Initialize profileButton
        bookmarkButton = findViewById(R.id.bookmarksButton); // Initialize bookmarkButton
        homeButton = findViewById(R.id.homeButton); // Initialize homeButton
        addEventButton = findViewById(R.id.add_event_button); // Initialize addEventButton
        eventsButton = findViewById(R.id.eventsButton); // Initialize eventsButton
        logoutButton = findViewById(R.id.logoutButton); // Initialize log-out button
        fullNameTextView = findViewById(R.id.fullNameTextView); // Initialize fullNameTextView

        String fullName = getIntent().getStringExtra("fullName");

        if (fullName != null && !fullName.isEmpty()) {
            fullNameTextView.setText(fullName);
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show confirmation dialog
                new AlertDialog.Builder(ProfilePage.this)
                        .setMessage("Are you sure you want to log out?")
                        .setCancelable(false)
                        .setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // If "Log Out" is clicked, redirect to HomepageActivity
                                Intent intent = new Intent(ProfilePage.this, HomepageActivity.class);
                                startActivity(intent); // Start the HomepageActivity
                                finish(); // Close the current activity
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        eventsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, CreatedEvent.class);
            intent.putExtra("fullName", fullName);
            startActivity(intent); // Start the CreatedEvent activity
        });
        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, ProfilePage.class);
            intent.putExtra("fullName", fullName);
            startActivity(intent); // Start the LoginPage activity
        });

        bookmarkButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, BookmarkPage.class);
            intent.putExtra("fullName", fullName);
            startActivity(intent); // Start the BookmarksPage activity
        });

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, MainActivity.class);
            intent.putExtra("fullName", fullName);
            startActivity(intent); // Start the HomePage activity
        });
        addEventButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, CreateEventActivity.class);
            intent.putExtra("fullName", fullName);
            startActivity(intent); // Start the CreateEventActivity activity
        });

    }
}