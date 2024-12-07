package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class JoinedEventAdapter extends RecyclerView.Adapter<JoinedEventViewHolder> {
    private final Context context;
    private final ArrayList<JoinedEvent> events;

    public JoinedEventAdapter(Context context, ArrayList<JoinedEvent> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public JoinedEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.joined_events_layout, parent, false);
        return new JoinedEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinedEventViewHolder holder, int position) {
        JoinedEvent event = events.get(position);

        holder.eventTitle.setText(event.getEventTitle().getName());
        holder.eventDate.setText(event.getEventDate());
        holder.eventLocation.setText(event.getEventVenue());

        holder.imageView.setImageResource(event.getEventTitle().getImageId());

        holder.itemView.setOnClickListener(v -> {
            startEventDetailsActivity(context, event);
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void startEventDetailsActivity(Context context, JoinedEvent event) {
        Intent intent = new Intent(context, RegistrationEventPage.class);
        intent.putExtra("event_name", event.getEventTitle().getName());
        intent.putExtra("event_date", event.getEventDate());
        intent.putExtra("event_venue", event.getEventVenue());
        intent.putExtra("event_image", event.getEventTitle().getImageId());
        intent.putExtra("event_facilitator", event.getEventFacilitator());
        intent.putExtra("event_description", event.getEventDescription());
        intent.putExtra("event_isJoined", event.isJoined());
        context.startActivity(intent);
    }
}
