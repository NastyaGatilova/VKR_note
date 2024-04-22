package com.example.note_prob22;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.note_prob22.adapters.NoteAdapter;
import com.example.note_prob22.adapters.NoteAdapterForCalendar;
import com.example.note_prob22.adapters.RecyclerViewItemClickListener;
import com.example.note_prob22.classes.Note;
import com.example.note_prob22.db.SQLiteManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HomeActivity extends AppCompatActivity
{
   // private ListView noteListView;
   RecyclerView recyclerView;
    public FloatingActionButton  exitbtn;

     SQLiteManager dbm;

    NoteAdapter noteAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initWidgets();
        loadFromDBToMemory();
        //setNoteAdapter();

        //setOnClickListener();


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


    private void initWidgets()
    {

       // noteListView = findViewById(R.id.noteListView);
        recyclerView = findViewById(R.id.noteRcView);
        exitbtn = findViewById(R.id.exitbtn);



    }

    private void loadFromDBToMemory()
    {

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateNoteListArray();

    }

//    private void setNoteAdapter()
//    {
//        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(), Note.nonDeletedNotes());
//        noteListView.setAdapter(noteAdapter);
//    }



//    private void setOnClickListener()
//    {
//        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
//            {
//                Note selectedNote = (Note) noteListView.getItemAtPosition(position);
//                Intent editNoteIntent = new Intent(getApplicationContext(), NoteDetailActivity.class);
//                editNoteIntent.putExtra(Note.NOTE_EDIT_EXTRA, selectedNote.getId());
//                startActivity(editNoteIntent);
//            }
//        });
//
//    }





    public void newNote(View view)
    {
        Intent newNoteIntent = new Intent(this, NoteDetailActivity.class);
        startActivity(newNoteIntent);


    }

public  void showCalendar(View view) {
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


    public  void  ActShowStory(View view){

        androidx.appcompat.app.AlertDialog.Builder a_builder = new androidx.appcompat.app.AlertDialog.Builder(HomeActivity.this);
        a_builder.setMessage("Вы хотите просмотреть историю записей?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent newNoteIntent = new Intent(getApplicationContext(), StoryActivity.class);
                        startActivity(newNoteIntent);
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        androidx.appcompat.app.AlertDialog alert = a_builder.create();
        alert.setTitle("История записей");
        alert.show();


    }




//    public  void  delNote(View view){
//
//                AlertDialog.Builder a_builder = new AlertDialog.Builder(HomeActivity.this);
//        a_builder.setMessage("Вы хотите удалить ВСЕ записи?")
//                .setCancelable(false)
//                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(), Note.nonDeletedNotes());
//                        noteListView.setAdapter(noteAdapter);
//
//                        for(int i=0; i< Note.noteArrayList.size();i++){
//                            noteAdapter.remove(Note.noteArrayList.get(i));
//                        }
//                        Note.noteArrayList.clear();
//
//                        noteAdapter.notifyDataSetChanged();
//                        dbm.deleteNoteInDB();
//                    }
//                })
//                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog alert = a_builder.create();
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
                        // Удаление всех записей из адаптера
//                        for(int i = 0; i < noteAdapter.getItemCount(); i++) {
//                            Note note = noteAdapter.getItem(i);
//                            noteAdapter.clearData(note);
//                        }
                        noteAdapter.clearData();
                        // Очистка списка записей
                        Note.noteArrayList.clear();

                        // Уведомление адаптера об изменениях
                        noteAdapter.notifyDataSetChanged();

                        // Удаление записей из базы данных
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





    @Override
    protected void onResume()
    {
        super.onResume();
       // setNoteAdapter();

        //loadFromDBToMemory();
        noteAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbm.close();

    }


}