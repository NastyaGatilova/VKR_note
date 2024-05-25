package com.example.note_prob22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.note_prob22.adapters.DiaryAdapter;
import com.example.note_prob22.adapters.NoteAdapter;
import com.example.note_prob22.adapters.OnItemClickListener;
import com.example.note_prob22.adapters.RecyclerViewItemClickListener;
import com.example.note_prob22.classes.Note;
import com.example.note_prob22.classes.Record;
import com.example.note_prob22.db.SQLiteManager;
import com.example.note_prob22.viewmodel.RecordViewModel;

import java.util.ArrayList;
import java.util.List;


public class DiaryActivity extends AppCompatActivity implements OnItemClickListener {

    RecyclerView dayInfoListRc;
    DiaryAdapter diaryAdapter;

    public RecordViewModel recViewModel;
    SQLiteManager dbm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        recViewModel = new ViewModelProvider(this).get(RecordViewModel.class);
        dayInfoListRc = findViewById(R.id.dayInfoListRc);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        dayInfoListRc.setLayoutManager(layoutManager);

        diaryAdapter = new DiaryAdapter( Record.noteDayArrayList,this);
        dayInfoListRc.setAdapter(diaryAdapter);


        loadFromDBToMemory();




        dbm = new SQLiteManager(this);


        dbm.getTimeAndSmileFromDB();
        dbm.getTimeAndSmileForGrafikHours();



    }

    @Override
    public void onItemClick(int position) {
        Record selectedRec = (Record) diaryAdapter.getItem(position);

        Intent editRecordIntent = new Intent(this, DiaryDetailActivity.class);
        editRecordIntent.putExtra(Record.NOTE_EDIT_EXTRA, selectedRec.getId());

        Log.d("--Help--", " Id записи из DiaryActivity =" + selectedRec.getId());
        editRecordIntent.putExtra("date", selectedRec.getDate());
        startActivity(editRecordIntent);
    }



    private void loadFromDBToMemory() {

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateRecordListArray();

    }




    public void newInfo(View view) {
        Intent newNoteIntent = new Intent(this, DiaryDetailActivity.class);
        startActivity(newNoteIntent);

    }




    @Override
    protected void onStop() {
        super.onStop();

        dbm.getDateAndSmileFromTableRecordForGrafik();
        diaryAdapter.notifyDataSetChanged();


    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        diaryAdapter.updateAdapter(dbm.populateRecordList());
        diaryAdapter.notifyDataSetChanged();




    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbm.close();

    }


}