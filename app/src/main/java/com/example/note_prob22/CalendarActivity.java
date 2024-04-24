package com.example.note_prob22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.widget.CalendarView;

import com.example.note_prob22.adapters.NoteAdapterForCalendar;
import com.example.note_prob22.classes.Note;
import com.example.note_prob22.db.SQLiteManager;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {
    SQLiteManager sqLiteManager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        RecyclerView recyclerView = findViewById(R.id.rcView);
        sqLiteManager = new SQLiteManager(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NoteAdapterForCalendar adapter = new NoteAdapterForCalendar(Note.noteArrayListWithForCalendar);

        CalendarView calendarView = findViewById(R.id.calendarView);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                String date = sdf.format(new GregorianCalendar(year, month, dayOfMonth).getTime());


                sqLiteManager.populateNoteListArrayForCalendar(date);
                sqLiteManager.showSmileForDate(date);

                recyclerView.setAdapter(adapter);
               // Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
            }
        });

















    }












}