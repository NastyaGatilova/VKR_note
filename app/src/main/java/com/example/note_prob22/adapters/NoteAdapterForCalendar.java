package com.example.note_prob22.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_prob22.R;
import com.example.note_prob22.classes.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        holder.descrTextView.setText(mData.get(position).getDescription());


        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        try {
            Date inputDate = formatter.parse(holder.dateTextView.getText().toString());
            Calendar inputCalendar = Calendar.getInstance();
            inputCalendar.setTime(inputDate);

            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.add(Calendar.DATE, -1); // вычитаем один день из текущей даты

            if (inputCalendar.before(currentCalendar)) {
                holder.noteCalendarCardView.setCardBackgroundColor(Color.parseColor("#E2E2E2"));
                holder.dateTextView.setTextColor(Color.parseColor("#F08080"));
                holder.titleTextView.setTextColor(Color.parseColor("#4C4E4B"));
                holder.descrTextView.setTextColor(Color.parseColor("#939592"));
            }
            else {
                holder.noteCalendarCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.lighGreen));
                holder.dateTextView.setTextColor(Color.parseColor("#009688"));
                holder.dateTextView.setTextColor(Color.parseColor("#FF4CAF50"));
                holder.dateTextView.setTextColor(Color.parseColor("#6D000000"));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView dateTextView,descrTextView;
        public CardView noteCalendarCardView;

        public NoteViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.cellTitle);
            descrTextView = itemView.findViewById(R.id.cellDesc);
            dateTextView = itemView.findViewById(R.id.cellDate);
            noteCalendarCardView = itemView.findViewById(R.id.noteCalendarCardView);

        }
    }
}



