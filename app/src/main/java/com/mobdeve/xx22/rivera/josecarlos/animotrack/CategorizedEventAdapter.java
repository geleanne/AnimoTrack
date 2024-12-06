package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategorizedEventAdapter extends RecyclerView.Adapter<CategorizedEventViewHolder> {
    private Context context;
    private ArrayList<CategorizedEvent> categorizedEvents;
    private final ArrayList<UpcomingEvent> events;

    public CategorizedEventAdapter(Context context, ArrayList<CategorizedEvent> categorizedEvents, ArrayList<UpcomingEvent> events) {
        this.context = context;
        this.categorizedEvents = categorizedEvents;
        this.events = events;
    }

    @NonNull
    @Override
    public CategorizedEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        return new CategorizedEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorizedEventViewHolder holder, int position) {
        CategorizedEvent currentEvent = categorizedEvents.get(position);

        holder.imageView.setImageResource(currentEvent.getEvent().getImageId());
        holder.eventTitle.setText(currentEvent.getEvent().getName());
        holder.eventDate.setText("Category: " + currentEvent.getEvent().getCategory());

        // Set a click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Find the corresponding UpcomingEvent by matching category and name
                UpcomingEvent event = getEventByCategoryAndName(currentEvent.getEvent().getCategory(), currentEvent.getEvent().getName());
                if (event != null) {
                    // Start the event details activity for the correct event
                    UpcomingEventAdapter upcomingEventAdapter = new UpcomingEventAdapter(context, events);
                    upcomingEventAdapter.startEventDetailsActivity(context, event);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categorizedEvents.size();
    }

    // Helper method to find an event by category and name
    private UpcomingEvent getEventByCategoryAndName(String category, String eventName) {
        for (UpcomingEvent event : events) {
            if (event.getEventTitle().getCategory().equals(category) && event.getEventTitle().getName().equals(eventName)) {
                return event;
            }
        }
        return null;
    }
}
