package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationsPage extends AppCompatActivity {

    private RecyclerView notificationsRecyclerView;
    private NotificationsAdapter adapter;
    private ArrayList<NotificationsItem> notificationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_page);

        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);

        // Initialize the notifications list.
        notificationsList = new ArrayList<>();
        // Example: Add sample notifications
        notificationsList.add(new NotificationsItem("Event Updated", "The venue for Event A has changed."));
        notificationsList.add(new NotificationsItem("New Event Added", "Event B has been added to the calendar."));

        adapter = new NotificationsAdapter(this, notificationsList);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationsRecyclerView.setAdapter(adapter);
    }
}
