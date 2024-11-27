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

        // Check if the user is admin (with credentials: admin, 123)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (email.equals("admin") && password.equals("123")) { // admin credentials condition
                // Fetch admin data from the 'admins' document
                db.collection("admins").document("CarlosAndAnge") // Assuming this is how admins are stored
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                String adminFullName = documentSnapshot.getString("name");
                                if (adminFullName != null && !adminFullName.isEmpty()) {
                                    fullNameTextView.setText(adminFullName); // Set admin full name to TextView
                                }
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(ProfilePage.this, "Error fetching admin data", Toast.LENGTH_SHORT).show();
                        });
            } else {
                // Regular user data fetching from 'AnimoTrackUsers' collection
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
            startActivity(intent); // Start the ProfilePage activity
        });

        bookmarkButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, BookmarkPage.class);
            startActivity(intent); // Start the BookmarksPage activity
        });

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, MainActivity.class);
            startActivity(intent); // Start the MainActivity activity
        });

        addEventButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, CreateEventActivity.class);
            startActivity(intent); // Start the CreateEventActivity activity
        });
    }
}
