package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarkEventViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView eventTitle;
    TextView eventDate;
    TextView eventCollege;
    TextView eventLocation;

    public BookmarkEventViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.eventPoster);
        eventTitle = itemView.findViewById(R.id.eventTitle);
        eventDate = itemView.findViewById(R.id.eventDate);
        eventCollege = itemView.findViewById(R.id.eventCollege);
        eventLocation = itemView.findViewById(R.id.eventLocation);
    }
}