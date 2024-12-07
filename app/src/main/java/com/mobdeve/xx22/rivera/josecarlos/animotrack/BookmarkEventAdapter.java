package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BookmarkEventAdapter extends RecyclerView.Adapter<BookmarkEventViewHolder> {
    private final Context context;
    private final ArrayList<BookmarkEvent> events;

    public BookmarkEventAdapter(Context context, ArrayList<BookmarkEvent> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public BookmarkEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bookmark_events_layout, parent, false);
        return new BookmarkEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkEventViewHolder holder, int position) {
        BookmarkEvent event = events.get(position);

        holder.eventTitle.setText(event.getEventTitle().getName());
        holder.eventDate.setText(event.getEventDate());
        holder.eventCollege.setText(event.getCollegeDept());
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

    public void startEventDetailsActivity(Context context, BookmarkEvent event) {
        Intent intent = new Intent(context, RegistrationEventPage.class);
        intent.putExtra("event_name", event.getEventTitle().getName());
        intent.putExtra("event_date", event.getEventDate());
        intent.putExtra("event_venue", event.getEventVenue());
        intent.putExtra("event_image", event.getEventTitle().getImageId());
        intent.putExtra("event_facilitator", event.getEventFacilitator());
        intent.putExtra("event_description", event.getEventDescription());
        intent.putExtra("event_isBookmarked", event.isBookmarked());
        context.startActivity(intent);
    }
}
