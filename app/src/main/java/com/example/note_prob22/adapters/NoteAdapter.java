package com.example.note_prob22.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.note_prob22.classes.Note;
import com.example.note_prob22.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

//public class NoteAdapter extends ArrayAdapter<Note>
//{
//    public NoteAdapter(Context context, List<Note> notes)
//    {
//        super(context, 0, notes);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
//    {
//        Note note = getItem(position);
//        if(convertView == null)
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_cell, parent, false);
//
//        TextView title = convertView.findViewById(R.id.cellTitle);
//        TextView desc = convertView.findViewById(R.id.cellDesc);
//        TextView date = convertView.findViewById(R.id.cellDate);
//
//        title.setText(note.getTitle());
//        desc.setText(note.getDescription());
//        date.setText(note.getDate());
//
//        return convertView;
//    }
//
//
//}


import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>
{
    private List<Note> mNotes;

    public NoteAdapter(List<Note> notes)
    {
        mNotes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_cell, parent, false);
        return new NoteViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position)
    {
        Note note = mNotes.get(position);
        holder.mTitleTextView.setText(note.getTitle());
        holder.mDescTextView.setText(note.getDescription());
        holder.mDateTextView.setText(note.getDate());



        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        try {
            Date inputDate = formatter.parse(holder.mDateTextView.getText().toString());
            Calendar inputCalendar = Calendar.getInstance();
            inputCalendar.setTime(inputDate);

            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.add(Calendar.DATE, -1); // вычитаем один день из текущей даты

            if (inputCalendar.before(currentCalendar))
            {
                Log.d("Date", "Введенная дата является предыдущей для текущей даты");
                holder.noteCardView.setCardBackgroundColor(Color.parseColor("#E2E2E2"));
                holder.mDateTextView.setTextColor(Color.parseColor("#F08080"));
                holder.mTitleTextView.setTextColor(Color.parseColor("#4C4E4B"));
                holder.mDescTextView.setTextColor(Color.parseColor("#939592"));
            }
                 else {
                holder.noteCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                holder.mDateTextView.setTextColor(Color.parseColor("#009688"));
                holder.mTitleTextView.setTextColor(Color.parseColor("#FF4CAF50"));
                holder.mDescTextView.setTextColor(Color.parseColor("#6D000000"));
                }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }



    @Override
    public int getItemCount()
    {
        return mNotes.size();
    }
    public Note getItem(int position) {
        return mNotes.get(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearData() {
        mNotes.clear();
        notifyDataSetChanged();
    }

    public void removeNote(int position) {
        mNotes.remove(position);
        notifyItemRemoved(position);
    }

    public void sortItemsByDate() {
        Collections.sort(mNotes, new Comparator<Note>() {
            @Override
            public int compare(Note item1, Note item2) {
                return item1.getDate().compareTo(item2.getDate());
            }
        });
        notifyDataSetChanged();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTitleTextView;
        public TextView mDescTextView;
        public TextView mDateTextView;
        public CardView noteCardView;

        public NoteViewHolder(View itemView)
        {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.cellTitle);
            mDescTextView = itemView.findViewById(R.id.cellDesc);
            mDateTextView = itemView.findViewById(R.id.cellDate);
            noteCardView = itemView.findViewById(R.id.noteCardView);
        }
    }
}
