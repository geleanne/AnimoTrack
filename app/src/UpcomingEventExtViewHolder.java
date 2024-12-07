package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UpcomingEventExtViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView eventTitle, eventDate, eventLocation, eventCollege;

    public UpcomingEventExtViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.eventPoster);
        eventTitle = itemView.findViewById(R.id.eventTitle);
        eventDate = itemView.findViewById(R.id.eventDate);
        eventCollege = itemView.findViewById(R.id.collegeDept);
        eventLocation = itemView.findViewById(R.id.eventLocation);
    }
}
