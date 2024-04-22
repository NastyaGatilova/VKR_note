package com.example.note_prob22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.note_prob22.adapters.NoteAdapter;
import com.example.note_prob22.adapters.NoteAdapterForCalendar;
import com.example.note_prob22.classes.Note;
import com.example.note_prob22.db.SQLiteManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {
    SQLiteManager sqLiteManager;
    SQLiteDatabase db;
    private ArrayList<String> dataList = new ArrayList<>();
    ListView listView;
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
              // sqLiteManager.getNoteWithUserDate(date);
                sqLiteManager.populateNoteListArrayForCalendar(date);

                recyclerView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
            }
        });
    }


//private void getDataForThisDate(String date){
//    //List<Note> dataList =
//            sqLiteManager.getNoteWithUserDate(date);
//   // updateList(dataList);
//}
//
//    private void updateList(List<Note> dataList) {
//        // обновить адаптер списка
//        ArrayAdapter<Note> adapter = new ArrayAdapter<>(this, R.layout.note_cell,  R.id.textView, dataList);
//        listView.setAdapter(adapter);
//    }

    private void setNoteAdapter()
    {
       // NoteAdapterForCalendar adapter = new NoteAdapterForCalendar(this, Note.nonDeletedNotes());
        //listView.setAdapter(adapter);
    }

    private void loadFromDBToMemory(String date)
    {

        sqLiteManager.populateNoteListArrayForCalendar(date);


    }


}