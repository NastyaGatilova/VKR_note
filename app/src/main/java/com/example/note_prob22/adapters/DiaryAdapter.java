package com.example.note_prob22.adapters;
import android.annotation.SuppressLint;
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


public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.RecordViewHolder>
{
    private OnItemClickListener listener;
    private List<Record> mRecords;

    public DiaryAdapter(ArrayList<Record> records, OnItemClickListener listener)
    {

        mRecords = records;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_cell, parent, false);
        return new RecordViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position)
    {
        Record rec = mRecords.get(position);
        holder.mTitleTextView.setText(rec.getFeeling());
        holder.mDescTextView.setText(rec.getDescription());
        holder.mDateTextView.setText(rec.getDate());
        holder.mSmileTextView.setText(rec.getSmile());



    }

    @Override
    public int getItemCount()
    {
        return mRecords.size();
    }
    public Record getItem(int position) {
        return mRecords.get(position);
    }
    public void removeItem(int position) {
//        mRecords.remove(position);
//        notifyItemRemoved(position);
        mRecords.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    public static class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private OnItemClickListener listener;
        public TextView mTitleTextView;
        public TextView mDescTextView;
        public TextView mDateTextView, mSmileTextView;

        public RecordViewHolder(View itemView, OnItemClickListener listener)
        {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.cellTitle2);
            mDescTextView = itemView.findViewById(R.id.cellDesc2);
            mDateTextView = itemView.findViewById(R.id.cellDate2);
            mSmileTextView = itemView.findViewById(R.id.cellSmile2);

            this.listener = listener;
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition());
        }
    }
}
