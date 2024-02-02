package com.example.note_prob22;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;


public class DaysActivity extends AppCompatActivity
{
    private ListView dayInfoList;


    SQLiteManager dbm;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);
        initWidgets();
        loadFromDBToMemory();
        setNoteDaysAdapter();
        setOnClickListener();




        dbm = new SQLiteManager(this);

        dbm.recordFromDB();



    }


    private void initWidgets()
    {

     dayInfoList = findViewById(R.id.dayInfoList);

    }

    private void loadFromDBToMemory()
    {

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateRecordListArray();

    }


    public void setNoteDaysAdapter()
    {
        NoteDaysAdapter noteDaysAdapterAdapter = new NoteDaysAdapter(getApplicationContext(), Record.nonDeletedDayNotes());
        dayInfoList.setAdapter(noteDaysAdapterAdapter);


    }


    private void setOnClickListener()
    {
        dayInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Record selectedRec = (Record) dayInfoList.getItemAtPosition(position);
                Intent editNoteIntent = new Intent(getApplicationContext(), DayInfoDetailActivity.class);
                editNoteIntent.putExtra(Record.NOTE_EDIT_EXTRA, selectedRec.getId());
                editNoteIntent.putExtra("date", selectedRec.getDate());
//                Log.d("--Help--", "selectedRec.getId="+selectedRec.getId());
                startActivity(editNoteIntent);
            }
        });

    }




    public void newInfo(View view)
    {
        Intent newNoteIntent = new Intent(this, DayInfoDetailActivity.class);
        startActivity(newNoteIntent);

    }















    @Override
    protected void onResume()
    {
        super.onResume();
        setNoteDaysAdapter();

//        NoteDaysAdapter adapter = (NoteDaysAdapter) dayInfoList.getAdapter();
//        int count = adapter.getCount();
//        for (int i = 0; i < count; i++) {
//            Record data = (Record) adapter.getItem(i);
//            Log.d("--Help--", "Записи: Title: " + data.getTitle() + ", Description: " + data.getDescription());
//        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbm.close();

    }


}