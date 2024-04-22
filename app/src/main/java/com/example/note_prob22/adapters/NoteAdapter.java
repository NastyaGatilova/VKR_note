package com.example.note_prob22.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.note_prob22.classes.Note;
import com.example.note_prob22.R;

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

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position)
    {
        Note note = mNotes.get(position);
        holder.mTitleTextView.setText(note.getTitle());
        holder.mDescTextView.setText(note.getDescription());
        holder.mDateTextView.setText(note.getDate());
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
        // Clear the list or dataset that the adapter is using to display the data
        mNotes.clear();

        // Notify the adapter that the data set has changed
        notifyDataSetChanged();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTitleTextView;
        public TextView mDescTextView;
        public TextView mDateTextView;

        public NoteViewHolder(View itemView)
        {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.cellTitle);
            mDescTextView = itemView.findViewById(R.id.cellDesc);
            mDateTextView = itemView.findViewById(R.id.cellDate);
        }
    }
}
