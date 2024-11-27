package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilePage extends AppCompatActivity {
    ImageButton profileButton;
    ImageButton bookmarkButton;
    ImageButton homeButton;
    ImageButton eventsButton;
    private ImageView addEventButton;
    private Button logoutButton;
    TextView fullNameTextView;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        addEventButton = findViewById(R.id.add_event_button);
        eventsButton = findViewById(R.id.eventsButton);
        logoutButton = findViewById(R.id.logoutButton);
        fullNameTextView = findViewById(R.id.fullNameTextView);

        // Get the current user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Fetch user data from Firestore using the user's UID
            db.collection("AnimoTrackUsers").document(currentUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String fullName = documentSnapshot.getString("name");
                            if (fullName != null && !fullName.isEmpty()) {
                                fullNameTextView.setText(fullName); // Set the full name to TextView
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ProfilePage.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                    });
        }

        logoutButton.setOnClickListener(v -> {
            new AlertDialog.Builder(ProfilePage.this)
                    .setMessage("Are you sure you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Log Out", (dialog, id) -> {
                        Intent intent = new Intent(ProfilePage.this, HomepageActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
        eventsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, CreatedEvent.class);
            startActivity(intent); // Start the CreatedEvent activity
        });
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
        addEventButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, CreateEventActivity.class);
            startActivity(intent); // Start the CreateEventActivity activity
        });

    }
}