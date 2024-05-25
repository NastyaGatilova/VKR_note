package com.example.note_prob22.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_prob22.R;
import com.example.note_prob22.classes.Record;

import java.util.ArrayList;
import java.util.List;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder>
{
    private List<Record> mRecords;

    public EventsAdapter(ArrayList<Record> records)
    {

        mRecords = records;
    }


    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_cell, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position)
    {
        Record rec = mRecords.get(position);
        holder.feelings.setText(rec.getFeeling());
        holder.events.setText(rec.getEvents());
        holder.desc.setText(rec.getDescription());
        holder.date.setText(rec.getDate());
        holder.smile.setText(rec.getSmile());



    }

    @Override
    public int getItemCount()
    {
        return mRecords.size();
    }
    public Record getItem(int position) {
        return mRecords.get(position);
    }


    public static class EventsViewHolder extends RecyclerView.ViewHolder
    {

        public TextView feelings,events,desc, smile, date ;

        public EventsViewHolder(View itemView)
        {
            super(itemView);
            feelings = itemView.findViewById(R.id.feelingsTv);
            events = itemView.findViewById(R.id.eventsTv);
            desc = itemView.findViewById(R.id.descTv);
            smile = itemView.findViewById(R.id.smileTv);
            date = itemView.findViewById(R.id.dateTv);


        }

    }
}
