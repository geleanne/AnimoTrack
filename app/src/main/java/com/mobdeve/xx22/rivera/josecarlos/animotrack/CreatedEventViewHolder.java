package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CreatedEventViewHolder extends RecyclerView.ViewHolder {
    public ImageView eventImage;
    public TextView eventTitle, eventDateTime, eventVenue;

    public CreatedEventViewHolder(View itemView) {
        super(itemView);
        eventImage = itemView.findViewById(R.id.event_image);
        eventTitle = itemView.findViewById(R.id.event_title);
        eventDateTime = itemView.findViewById(R.id.event_date_time);
        eventVenue = itemView.findViewById(R.id.event_venue);
    }
}
