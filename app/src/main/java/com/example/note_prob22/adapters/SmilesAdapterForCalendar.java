package com.example.note_prob22.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_prob22.R;
import com.example.note_prob22.classes.Record;

import java.util.List;

public class SmilesAdapterForCalendar extends RecyclerView.Adapter<SmilesAdapterForCalendar.SmilesViewHolder> {

    private List<Record> mData;

    public SmilesAdapterForCalendar(List<Record> data) {
        mData = data;
    }

    @NonNull
    @Override
    public SmilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.smiles_cell, parent, false);
        return new SmilesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmilesViewHolder holder, int position) {
        holder.smileTextView.setText(mData.get(position).getSmile());





    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class SmilesViewHolder extends RecyclerView.ViewHolder {

        public TextView smileTextView;


        public SmilesViewHolder(View itemView) {
            super(itemView);
           smileTextView = itemView.findViewById(R.id.cellSmileCalendar);

        }
    }
}



