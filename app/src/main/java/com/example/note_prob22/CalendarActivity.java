package com.example.note_prob22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;


public class CalendarActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Выводим выбранную дату в формате dd-MM-yyyy
                String date = String.format("%02d-%02d-%d", dayOfMonth, month+1, year);
                Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
            }
        });
    }






}