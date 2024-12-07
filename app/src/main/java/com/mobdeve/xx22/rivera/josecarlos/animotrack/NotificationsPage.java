package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class NotificationsPage extends AppCompatActivity {

    private NotificationsAdapter adapter;
    ImageButton backArrow;
    ImageButton profileButton;
    ImageButton bookmarkButton;
    ImageButton homeButton;
    ImageButton eventsButton;
    ImageView addEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_page);

        RecyclerView notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);

        addEventButton = findViewById(R.id.add_event_button);
        backArrow = findViewById(R.id.back_arrow);
        profileButton = findViewById(R.id.profileButton);
        bookmarkButton = findViewById(R.id.bookmarksButton);
        homeButton = findViewById(R.id.homeButton);
        eventsButton = findViewById(R.id.eventsButton);

        ArrayList<NotificationsItem> notificationsList = new ArrayList<>();
        adapter = new NotificationsAdapter(this, notificationsList);

        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);
        notificationsList = new ArrayList<>();
        adapter = new NotificationsAdapter(this, notificationsList);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationsRecyclerView.setAdapter(adapter);

        fetchNotificationsFromFirestore();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsPage.this, CreatedEventPage.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsPage.this, ProfilePage.class);
                startActivity(intent);
            }
        });

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsPage.this, BookmarkPage.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsPage.this, CreateEventActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchNotificationsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            db.collection("notifications")
                    .document(userId)
                    .collection("userNotifications")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        ArrayList<NotificationsItem> notifications = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            String title = document.getString("title");
                            String body = document.getString("body");
                            notifications.add(new NotificationsItem(title, body));
                        }
                        adapter.notifyDataSetChanged();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firestore", "Error fetching notifications", e);
                    });
        }
    }

}
