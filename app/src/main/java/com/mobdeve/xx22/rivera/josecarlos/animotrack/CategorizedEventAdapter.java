package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategorizedEventAdapter extends RecyclerView.Adapter<CategorizedEventViewHolder> {
    private Context context;
    private ArrayList<CategorizedEvent> categorizedEvents;

    public CategorizedEventAdapter(Context context, ArrayList<CategorizedEvent> categorizedEvents) {
        this.context = context;
        this.categorizedEvents = categorizedEvents;
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
    }

    @Override
    public int getItemCount() {
        return categorizedEvents.size();
    }
}
