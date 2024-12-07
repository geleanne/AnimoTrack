package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.core.EventRegistration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CreatedEventAdapter extends RecyclerView.Adapter<CreatedEventViewHolder> {
    private final Context context;
    private final ArrayList<UpcomingEvent> events;

    public CreatedEventAdapter(Context context, ArrayList<UpcomingEvent> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public CreatedEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.created_event_item, parent, false);
        return new CreatedEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreatedEventViewHolder holder, int position) {
        UpcomingEvent event = events.get(position);
        holder.eventTitle.setText(event.getEventTitle().getName());
        String eventDate = event.getEventDate();

        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = originalFormat.parse(eventDate);
            String formattedDate = targetFormat.format(date);

            holder.eventDateTime.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            holder.eventDateTime.setText(eventDate);
        }

        holder.eventVenue.setText(event.getEventVenue());
        holder.eventImage.setImageResource(event.getEventTitle().getImageId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventRegistration.class);
            intent.putExtra("eventTitle", event.getEventTitle().getName());
            intent.putExtra("eventDate", event.getEventDate());
            intent.putExtra("eventLocation", event.getEventVenue());
            intent.putExtra("eventPoster", event.getEventTitle().getImageId());
            intent.putExtra("event_facilitator", event.getEventFacilitator());
            intent.putExtra("event_description", event.getEventDescription());
            intent.putExtra("event_isBookmarked", event.isBookmarked());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
