package com.example.note_prob22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DiaryActivity extends AppCompatActivity
{
    private ListView dayInfoList;


    SQLiteManager dbm;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
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
        DiaryAdapter noteDaysAdapterAdapter = new DiaryAdapter(getApplicationContext(), Record.nonDeletedDayNotes());
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
                Intent editNoteIntent = new Intent(getApplicationContext(), DiaryDetailActivity.class);
                editNoteIntent.putExtra(Record.NOTE_EDIT_EXTRA, selectedRec.getId());
                editNoteIntent.putExtra("date", selectedRec.getDate());
//                Log.d("--Help--", "selectedRec.getId="+selectedRec.getId());
                startActivity(editNoteIntent);
            }
        });

    }

//ее удалить потом
//    private void addRecImitation(){
//        Record newRec = new Record(4, "вмаа", "sshhs", "😰", "05 апр. 2024" );
//        Record newRec2 = new Record(5, "вмаа", "sshhs", "😃", "06 апр. 2024" );
//        Record newRec3 = new Record(6, "вмаа", "sshhs", "🙂", "07 апр. 2024" );
//        Record newRec4 = new Record(7, "вмаа", "sshhs", "🙂", "08 апр. 2024" );
//        Record newRec5 = new Record(8, "вмаа", "sshhs", "😃", "09 апр. 2024" );
//
//        Record.noteDayArrayList.add(newRec);
//        Record.noteDayArrayList.add(newRec2);
//        Record.noteDayArrayList.add(newRec3);
//        Record.noteDayArrayList.add(newRec4);
//        Record.noteDayArrayList.add(newRec5);
//
//        dbm.addRecordToDatabase(newRec);
//        dbm.addRecordToDatabase(newRec2);
//        dbm.addRecordToDatabase(newRec3);
//        dbm.addRecordToDatabase(newRec4);
//        dbm.addRecordToDatabase(newRec5);
//
//
//    }


    public void newInfo(View view)
    {
        Intent newNoteIntent = new Intent(this, DiaryDetailActivity.class);
        startActivity(newNoteIntent);

    }



//график должен    в OnStope


    @Override
    protected void onStop() {
        super.onStop();

            dbm.dateAndSmileFromTableRecordForGrafik();

//        String inputDate = "02 февр. 2024";
//        String outputFormat = "dd.MM.yyyy";
//        SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM. yyyy", new Locale("ru")); // Укажите соответствующий язык
//        SimpleDateFormat outputFormat2 = new SimpleDateFormat(outputFormat);
//
//        try {
//            Date date = inputFormat.parse(inputDate);
//            String outputDate = outputFormat2.format(date);
//            Log.d("--Help--", outputDate);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            Log.d("--Help--", "error");
//
//        }

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