package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.FirebaseInstallations;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText eToken;

    RecyclerView recyclerViewUpcomingEvents;
    ImageView addEventButton;
    ImageButton notificationsButton;
    ImageButton profileButton;
    ImageButton bookmarkButton;
    ImageButton homeButton;
    ImageButton eventsButton;
    ImageButton upcomingEventsExtButton;
    ImageButton orgsIconImageButton;
    ImageButton acadsIconImageButton;
    ImageButton seminarIconImageButton;
    ImageButton sportsIconImageButton;
    ImageButton culturalIconImageButton;
    TextView greetNameTextView;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize FirebaseAuth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Call saveEventsToFirestore to save events to Firestore when the app starts
        DataGenerator dataGenerator = new DataGenerator();
        dataGenerator.saveEventsToFirestore();

        recyclerViewUpcomingEvents = findViewById(R.id.recyclerViewUpcomingEvents);

        notificationsButton = findViewById(R.id.notificationsButton);
        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        eventsButton = findViewById(R.id.eventsButton);
        upcomingEventsExtButton = findViewById(R.id.upcomingEventsExtButton);
        orgsIconImageButton = findViewById(R.id.orgsIconImageButton);
        acadsIconImageButton = findViewById(R.id.acadsIconImageButton);
        seminarIconImageButton = findViewById(R.id.seminarIconImageButton);
        sportsIconImageButton = findViewById(R.id.sportsIconImageButton);
        culturalIconImageButton = findViewById(R.id.culturalIconImageButton);
        addEventButton = findViewById(R.id.add_event_button);
        greetNameTextView = findViewById(R.id.greetNameTextView);
//        eToken = findViewById(R.id.eToken);

//        FirebaseMessaging.getInstance().subscribeToTopic("events")
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Log.d("FCM", "Subscribed to events topic.");
//                    } else {
//                        Log.e("FCM", "Failed to subscribe.");
//                    }
//                });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        System.out.println(token);
                        Toast.makeText(MainActivity.this, "Your device registration token is" + token, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "FCM Token: " + token); // Make sure the token is logged

//                        eToken.setText(token);

                    }
                });



        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            String installationId = task.getResult();
                            Log.d("Installations", "Installation ID: " + installationId);

                            // Get the current user's UID
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String uid = user.getUid();

                                // Save the Installation ID to Firestore under the user's document
                                db.collection("AnimoTrackUsers").document(uid)
                                        .update("installation_id", installationId)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    // Show a toast confirming that the Installation ID was saved
                                                    Toast.makeText(MainActivity.this, "Installation ID saved successfully!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    // Handle any errors
                                                    Toast.makeText(MainActivity.this, "Failed to save Installation ID", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }

                        } else {
                            Log.e("Installations", "Unable to get Installation ID");
                        }
                    }
                });

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();

            // Get the user data from Firestore using the user's UID
            db.collection("AnimoTrackUsers").document(uid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // Get the user's full name from Firestore
                                    String fullName = document.getString("name");
                                    if (fullName != null && !fullName.isEmpty()) {
                                        greetNameTextView.setText("Hello, " + fullName + "!");
                                    }
                                } else {
                                    // Document doesn't exist
                                    Log.d("MainActivity", "No such document");
                                }
                            } else {
                                Log.d("MainActivity", "Get failed with ", task.getException());
                            }
                        }
                    });
        }

        // Proceed with setting up your RecyclerView and other buttons
        ArrayList<UpcomingEvent> events = DataGenerator.generateUpcomingEventsData();
        int maxEventsToShow = 5;
        if (events.size() > maxEventsToShow) {
            events = new ArrayList<>(events.subList(0, maxEventsToShow));
        }

        UpcomingEventAdapter upcomingEventAdapter = new UpcomingEventAdapter(this, events);
        recyclerViewUpcomingEvents.setAdapter(upcomingEventAdapter);
        recyclerViewUpcomingEvents.setLayoutManager(new LinearLayoutManager(this));


        upcomingEventsExtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpcomingEventExtPage.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for the buttons
        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreatedEventPage.class);
                startActivity(intent); // Start the CreatedEvent activity
            }
        });

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivity(intent); // Start the CreateEventActivity
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfilePage.class);
                startActivity(intent); // Start the LoginPage activity
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookmarkPage.class);
                startActivity(intent); // Start the BookmarksPage activity
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent); // Start the HomePage activity
            }
        });

        // --------------------------------- //
        // Set OnClickListener for the categories
        orgsIconImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryPage.class);
                intent.putExtra("category", "Organizations");
                startActivity(intent);
            }
        });

        acadsIconImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryPage.class);
                intent.putExtra("category", "Academics");
                startActivity(intent);
            }
        });

        seminarIconImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryPage.class);
                intent.putExtra("category", "Seminars");
                startActivity(intent);
            }
        });

        sportsIconImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryPage.class);
                intent.putExtra("category", "Sports");
                startActivity(intent);
            }
        });

        culturalIconImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryPage.class);
                intent.putExtra("category", "Cultural");
                startActivity(intent);
            }
        });

        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Debugging Toast to verify button click
                Toast.makeText(MainActivity.this, "Notifications Button Clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, NotificationsPage.class);
                startActivity(intent);
            }
        });

    }

    // Method to update the notification icon
    public void updateNotificationIcon(boolean hasNewNotification) {
        if (hasNewNotification) {
            notificationsButton.setImageResource(R.drawable.notif_icon_badge);
        } else {
            notificationsButton.setImageResource(R.drawable.notif_icon); // Default icon
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reset the notification icon
        updateNotificationIcon(false);
    }
}

