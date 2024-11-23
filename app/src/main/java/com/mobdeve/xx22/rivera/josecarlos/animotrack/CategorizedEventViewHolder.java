package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategorizedEventViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView eventTitle, eventDate, eventLocation;
    View itemView;


    public CategorizedEventViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView; // Store the root view for click handling

        imageView = itemView.findViewById(R.id.eventPoster);
        eventTitle = itemView.findViewById(R.id.eventTitle);
        eventDate = itemView.findViewById(R.id.eventDate);
        eventLocation = itemView.findViewById(R.id.eventLocation);
    }
}