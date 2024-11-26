package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UpcomingEventAdapter extends RecyclerView.Adapter<UpcomingEventViewHolder> {
    private final Context context;
    private final ArrayList<UpcomingEvent> events;

    public UpcomingEventAdapter(Context context, ArrayList<UpcomingEvent> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public UpcomingEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.upcoming_events_layout, parent, false);
        return new UpcomingEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingEventViewHolder holder, int position) {
        UpcomingEvent event = events.get(position);
        holder.eventTitle.setText(event.getEventTitle().getName());
        holder.eventDate.setText(event.getEventDate());
        holder.eventLocation.setText(event.getEventVenue());


        holder.imageView.setImageResource(event.getEventTitle().getImageId());

        // Set click listener for the entire item view
        holder.itemView.setOnClickListener(v -> {
            // Start the EventsRegistrationPage activity with the event details
            startEventDetailsActivity(context, event);
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void startEventDetailsActivity(Context context, UpcomingEvent event) {
        Intent intent = new Intent(context, RegistrationEventPage.class);
        intent.putExtra("event_name", event.getEventTitle().getName());
        intent.putExtra("event_date", event.getEventDate());
        intent.putExtra("event_venue", event.getEventVenue());
        intent.putExtra("event_image", event.getEventTitle().getImageId());
        intent.putExtra("event_facilitator", event.getEventFacilitator());
        intent.putExtra("event_description", event.getEventDescription());
        intent.putExtra("event_isFavorite", event.isBookmarked());
        context.startActivity(intent);
    }
}
