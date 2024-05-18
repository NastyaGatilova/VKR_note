package com.example.note_prob22;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.note_prob22.adapters.NoteAdapter;
import com.example.note_prob22.adapters.NoteAdapterForCalendar;
import com.example.note_prob22.adapters.RecyclerViewItemClickListener;
import com.example.note_prob22.classes.Note;
import com.example.note_prob22.db.SQLiteManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


public class HomeActivity extends AppCompatActivity {
    // private ListView noteListView;
    RecyclerView recyclerView;
    public FloatingActionButton exitbtn;
    CardView cardView;
    SQLiteManager dbm;

    NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initWidgets();
        loadFromDBToMemory();

        dbm = new SQLiteManager(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        noteAdapter = new NoteAdapter(Note.noteArrayList);
        recyclerView.setAdapter(noteAdapter);



        // dbm.deleteRecordInDB();



        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this, recyclerView, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Note selectedNote = (Note) noteAdapter.getItem(position);
                Intent editNoteIntent = new Intent(getApplicationContext(), NoteDetailActivity.class);
                editNoteIntent.putExtra(Note.NOTE_EDIT_EXTRA, selectedNote.getId());
                startActivity(editNoteIntent);
            }
        }));


    }


    private void initWidgets() {
        recyclerView = findViewById(R.id.noteRcView);
        exitbtn = findViewById(R.id.exitbtn);
        cardView = recyclerView.findViewById(R.id.noteCardView);


    }

    private void loadFromDBToMemory() {

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateNoteListArray();

    }


    public void newNote(View view) {
        Intent newNoteIntent = new Intent(this, NoteDetailActivity.class);
        startActivity(newNoteIntent);


    }




    public void showCalendar(View view) {
        Intent newNoteIntent = new Intent(this, CalendarActivity.class);
        startActivity(newNoteIntent);
    }

    public void exitAcc(View view) {
        Intent newNoteIntent = new Intent(this, AccountActivity.class);
        startActivity(newNoteIntent);

    }

    public void showDaysActivity(View view) {
        Intent newNoteIntent = new Intent(this, DiaryActivity.class);
        startActivity(newNoteIntent);

    }


//    public void ActShowStory(View view) {
//
//        androidx.appcompat.app.AlertDialog.Builder a_builder = new androidx.appcompat.app.AlertDialog.Builder(HomeActivity.this);
//        a_builder.setMessage("Вы хотите просмотреть историю записей?")
//                .setCancelable(false)
//                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent newNoteIntent = new Intent(getApplicationContext(), StoryActivity.class);
//                        startActivity(newNoteIntent);
//                    }
//                })
//                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//        androidx.appcompat.app.AlertDialog alert = a_builder.create();
//        alert.setTitle("История записей");
//        alert.show();
//
//
//    }


    public void delNote(View view) {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(HomeActivity.this);
        a_builder.setMessage("Вы хотите удалить ВСЕ записи?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    public void onClick(DialogInterface dialog, int which) {

                        noteAdapter.clearData();
                        // Очистка списка записей
                        Note.noteArrayList.clear();
                        noteAdapter.notifyDataSetChanged();
                        dbm.deleteNoteInDB();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = a_builder.create();
        alert.show();
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        // setNoteAdapter();

        //loadFromDBToMemory();
        noteAdapter.sortItemsByDate();
        noteAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbm.close();

    }


}