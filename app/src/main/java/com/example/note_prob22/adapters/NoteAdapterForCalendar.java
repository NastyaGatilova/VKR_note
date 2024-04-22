package com.example.note_prob22.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_prob22.R;
import com.example.note_prob22.classes.Note;

import java.util.List;

public class NoteAdapterForCalendar extends RecyclerView.Adapter<NoteAdapterForCalendar.NoteViewHolder> {

    private List<Note> mData;

    public NoteAdapterForCalendar(List<Note> data) {
        mData = data;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_calendar_cell, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.titleTextView.setText(mData.get(position).getTitle());
        holder.dateTextView.setText(mData.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView dateTextView,descrTextView;

        public NoteViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.cellTitle);
            descrTextView = itemView.findViewById(R.id.cellDesc);
            dateTextView = itemView.findViewById(R.id.cellDate);

        }
    }
}



