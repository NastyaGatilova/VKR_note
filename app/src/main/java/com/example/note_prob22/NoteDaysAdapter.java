package com.example.note_prob22;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NoteDaysAdapter extends ArrayAdapter<Record>
{
    public NoteDaysAdapter(Context context, List<Record> notes)
    {
        super(context, 0, notes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Record rec = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dayifo_cell, parent, false);

        TextView title = convertView.findViewById(R.id.cellTitle2);
        TextView desc = convertView.findViewById(R.id.cellDesc2);
        TextView date = convertView.findViewById(R.id.cellDate2);
        TextView smile = convertView.findViewById(R.id.cellSmile2);

        title.setText(rec.getTitle());
        desc.setText(rec.getDescription());
        date.setText(rec.getDate());
        smile.setText(rec.getSmile());

        return convertView;
    }


}
