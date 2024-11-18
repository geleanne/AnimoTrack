package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Context;
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
        holder.eventTitle.setText(event.getBookmarkEventTitle().getName());
        holder.eventDate.setText(event.getBookmarkEventDate());
        holder.eventLocation.setText(event.getBookmarkEventVenue());

        holder.imageView.setImageResource(event.getBookmarkEventTitle().getImageId());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
