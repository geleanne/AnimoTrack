package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;

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
    ImageView eventsJoinedButton;
    ImageView eventsBookmarkedButton;
    ImageButton profileButton;
    ImageButton bookmarkButton;
    ImageButton homeButton;
    ImageButton eventsButton;
    ImageView notificationsButton;
    ImageView addEventButton;
    Button logoutButton;
    Button deleteAccButton;
    TextView fullNameTextView;
    TextView numberJoinedEvents;
    TextView numberBookmarkedEvents;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        eventsJoinedButton = findViewById(R.id.eventsJoinedCountView);
        eventsBookmarkedButton = findViewById(R.id.eventsBookmarkedCountView);
        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        addEventButton = findViewById(R.id.add_event_button);
        eventsButton = findViewById(R.id.eventsButton);
        deleteAccButton = findViewById(R.id.deleteAccButton);
        logoutButton = findViewById(R.id.logoutButton);
        fullNameTextView = findViewById(R.id.fullNameTextView);
        numberJoinedEvents = findViewById(R.id.numberJoinedEvents);
        numberBookmarkedEvents = findViewById(R.id.numberBookmarkedEvents);
        notificationsButton = findViewById(R.id.notificationsButton);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
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

        // if the user has bookmarked events, display and update the numberBookmarkedEvents TextView accordingly
        if (!BookmarkPage.bookmarkEvents.isEmpty()) {
            numberBookmarkedEvents.setText(String.valueOf(BookmarkPage.bookmarkEvents.size()));
        } else {
            numberBookmarkedEvents.setText("0");
        }

        // if the user has joined events, display and update the numberJoinedEvents TextView accordingly
        if (!JoinedEventPage.joinedEvents.isEmpty()) {
            numberJoinedEvents.setText(String.valueOf(JoinedEventPage.joinedEvents.size()));
        } else {
            numberJoinedEvents.setText("0");
        }

        eventsJoinedButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePage.this, JoinedEventPage.class);
            startActivity(intent); // Start the JoinedEvent activity
        });

        eventsBookmarkedButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePage.this, BookmarkPage.class);
            startActivity(intent); // Start the Bookmark activity
        });

        deleteAccButton.setOnClickListener(v -> {
            if (currentUser != null) {
                // Show confirmation dialog
                new AlertDialog.Builder(ProfilePage.this)
                        .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                        .setCancelable(false)
                        .setPositiveButton("Delete Account", (dialog, id) -> {
                            db.collection("AnimoTrackUsers").document(currentUser.getUid())
                                    .delete()
                                    .addOnSuccessListener(aVoid -> {
                                        currentUser.delete()
                                                .addOnSuccessListener(aVoid1 -> {
                                                    Toast.makeText(ProfilePage.this, "Account deleted successfully.", Toast.LENGTH_SHORT).show();
                                                    // Redirect to a landing or login page
                                                    Intent intent = new Intent(ProfilePage.this, LoginPage.class);
                                                    startActivity(intent);
                                                    finish();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(ProfilePage.this, "Error deleting account. Please try again.", Toast.LENGTH_SHORT).show();
                                                });
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(ProfilePage.this, "Error deleting user data. Please try again.", Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            } else {
                Toast.makeText(ProfilePage.this, "No user is logged in.", Toast.LENGTH_SHORT).show();
            }
        });

        logoutButton.setOnClickListener(v -> {
            new AlertDialog.Builder(ProfilePage.this)
                    .setMessage("Are you sure you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Log Out", (dialog, id) -> {
                        // Sign out the user from Firebase
                        FirebaseAuth.getInstance().signOut();

                        // Clear locally stored data
                        BookmarkPage.bookmarkEvents.clear(); // Clear bookmarked events
                        JoinedEventPage.joinedEvents.clear(); // Clear joined events

                        // Redirect to the login page
                        Intent intent = new Intent(ProfilePage.this, LoginPage.class);
                        startActivity(intent);
                        finish(); // Close the current activity
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });


        eventsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, CreatedEventPage.class);
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

        notificationsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, NotificationsPage.class);
            startActivity(intent); // Start the NotificationsPage activity
        });
    }
}
