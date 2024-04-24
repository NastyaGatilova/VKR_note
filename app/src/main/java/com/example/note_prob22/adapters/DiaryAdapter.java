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

//public class DiaryAdapter extends ArrayAdapter<Record>
//{
//    public DiaryAdapter(Context context, List<Record> notes)
//    {
//        super(context, 0, notes);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
//    {
//        Record rec = getItem(position);
//        if(convertView == null)
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.record_cell, parent, false);
//
//        TextView title = convertView.findViewById(R.id.cellTitle2);
//        TextView desc = convertView.findViewById(R.id.cellDesc2);
//        TextView date = convertView.findViewById(R.id.cellDate2);
//        TextView smile = convertView.findViewById(R.id.cellSmile2);
//
//        title.setText(rec.getTitle());
//        desc.setText(rec.getDescription());
//        date.setText(rec.getDate());
//        smile.setText(rec.getSmile());
//
//        return convertView;
//    }
//
//
//}



public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.RecordViewHolder>
{
    private List<Record> mRecords;

    public DiaryAdapter(ArrayList<Record> records)
    {
        mRecords = records;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_cell, parent, false);
        return new RecordViewHolder(view);
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

    @SuppressLint("NotifyDataSetChanged")
    public void clearData() {
        // Clear the list or dataset that the adapter is using to display the data
        mRecords.clear();

        // Notify the adapter that the data set has changed
        notifyDataSetChanged();
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTitleTextView;
        public TextView mDescTextView;
        public TextView mDateTextView, mSmileTextView;

        public RecordViewHolder(View itemView)
        {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.cellTitle2);
            mDescTextView = itemView.findViewById(R.id.cellDesc2);
            mDateTextView = itemView.findViewById(R.id.cellDate2);
            mSmileTextView = itemView.findViewById(R.id.cellSmile2);

        }
    }
}
