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
        holder.eventTitle.setText(event.getEventTitle().getName()); // Correct access for title
        String eventDate = event.getEventDate(); // Assuming this is a string in format "yyyy-MM-dd HH:mm"

        // If eventDate is a string in full datetime format, use SimpleDateFormat to extract just the date
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()); // Adjust format if needed
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); // Only the date

            Date date = originalFormat.parse(eventDate); // Parse the original date-time string
            String formattedDate = targetFormat.format(date); // Format to show only the date

            holder.eventDateTime.setText(formattedDate); // Set the formatted date in the TextView
        } catch (ParseException e) {
            e.printStackTrace();
            holder.eventDateTime.setText(eventDate); // Fallback if date parsing fails
        }

        holder.eventVenue.setText(event.getEventVenue());
        holder.eventImage.setImageResource(event.getEventTitle().getImageId());  // Use correct method for image ID

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventRegistration.class);
            intent.putExtra("eventTitle", event.getEventTitle().getName());  // Correct access
            intent.putExtra("eventDate", event.getEventDate());
            intent.putExtra("eventLocation", event.getEventVenue());
            intent.putExtra("eventPoster", event.getEventTitle().getImageId());  // Correct access
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
