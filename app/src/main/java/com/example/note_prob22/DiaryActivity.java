package com.example.note_prob22;

import androidx.appcompat.app.AppCompatActivity;
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


public class DiaryActivity extends AppCompatActivity implements OnItemClickListener
{

    RecyclerView dayInfoListRc;
    DiaryAdapter diaryAdapter;


    SQLiteManager dbm;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        initWidgets();
        loadFromDBToMemory();


        dbm = new SQLiteManager(this);




        dbm.getTimeAndSmileFromDB();
        dbm.getTimeAndSmileForGrafikHours();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        dayInfoListRc.setLayoutManager(layoutManager);

        diaryAdapter = new DiaryAdapter(Record.noteDayArrayList, this);
        dayInfoListRc.setAdapter(diaryAdapter);




    }
    @Override
    public void onItemClick(int position) {
        Record selectedRec = (Record) diaryAdapter.getItem(position);
        Log.d("--Help--", "Position ="+ position);
        Intent editRecordIntent = new Intent(this, DiaryDetailActivity.class);
        editRecordIntent.putExtra(Record.NOTE_EDIT_EXTRA, selectedRec.getId());
        Log.d("--Help--", "selectedRec id ="+ selectedRec.getId());
        editRecordIntent.putExtra("date", selectedRec.getDate());
        startActivity(editRecordIntent);
    }

    private void initWidgets()
    {

        dayInfoListRc = findViewById(R.id.dayInfoListRc);
    }

    private void loadFromDBToMemory()
    {

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateRecordListArray();

    }



//ее удалить потом
    private void addRecImitation(){




        Record newRec0 = new Record(0, "вмаа", "sshhs", "sshhs","😃", "20 мар. 2024" );
       Record newRec1 = new Record(1, "вмаа", "sshhs", "sshhs","🙂", "27 мар. 2024"  );
        Record newRec2 = new Record(2, "вмаа", "sshhs","sshhs", "🙂", "28 мар. 2024"  );
        Record newRec3 = new Record(3, "вмаа", "sshhs","sshhs", "😃", "29 мар. 2024"  );
        Record newRec4 = new Record(4, "вмаа", "sshhs", "sshhs","😃", "30 мар. 2024"  );
//
        Record newRec5 = new Record(5, "вмаа", "sshhs","sshhs", "😰", "31 мар. 2024"  );
      Record newRec6 = new Record(6, "вмаа", "sshhs","sshhs", "🙂", "01 апр. 2024"  );
        Record newRec7 = new Record(7, "вмаа", "sshhs","sshhs", "😰", "02 апр. 2024" );
        Record newRec8 = new Record(8, "вмаа", "sshhs", "sshhs","🙂", "03 апр. 2024"  );
        Record newRec9 = new Record(9, "вмаа", "sshhs", "sshhs","😰", "04 апр. 2024"  );
        Record newRec10 = new Record(10, "вмаа", "sshhs","sshhs", "😰", "05 апр. 2024"  );
        Record newRec11 = new Record(11, "вмаа", "sshhs", "sshhs","😰", "06 апр. 2024"  );
        Record newRec12 = new Record(12, "вмаа", "sshhs", "sshhs","😰", "07 апр. 2024" );

        Record newRec13 = new Record(13, "вмаа", "sshhs","sshhs", "😰", "08 апр. 2024"  );
        Record newRec14 = new Record(14, "вмаа", "sshhs", "sshhs","🙂", "09 апр. 2024"  );
        Record newRec15 = new Record(15, "вмаа", "sshhs","sshhs", "😰", "10 апр. 2024"  );
        Record newRec16 = new Record(15, "вмаа", "sshhs", "sshhs","🙂", "11 апр. 2024"  );
        Record newRec17 = new Record(17, "вмаа", "sshhs", "sshhs","😰", "12 апр. 2024" );
        Record newRec18 = new Record(18, "вмаа", "sshhs", "sshhs","😰", "13 апр. 2024"  );
        Record newRec19 = new Record(19, "вмаа", "sshhs","sshhs", "😰", "14 апр. 2024"  );
        Record newRec20 = new Record(20, "вмаа", "sshhs","sshhs", "😰", "15 апр. 2024"  );
        Record newRec21 = new Record(21, "вмаа", "sshhs", "sshhs","😰", "16 апр. 2024" );
        Record newRec22 = new Record(22, "вмаа", "sshhs","sshhs", "😰", "17 апр. 2024"  );
        Record newRec23 = new Record(23, "вмаа", "sshhs", "sshhs","🤩", "18 апр. 2024"  );
        Record newRec24 = new Record(24, "вмаа", "sshhs", "sshhs","😡", "18 апр. 2024"  );
        Record newRec25 = new Record(25, "вмаа", "sshhs", "sshhs","🙂", "18 апр. 2024" );

        Record newRec26 = new Record(13, "вмаа", "sshhs","sshhs", "😰", "19 апр. 2024"  );
        Record newRec27 = new Record(14, "вмаа", "sshhs", "sshhs","🙂", "21 апр. 2024"  );
        Record newRec28 = new Record(15, "вмаа", "sshhs","sshhs", "😰", "22 апр. 2024"  );
        Record newRec29 = new Record(15, "вмаа", "sshhs", "sshhs","🙂", "23 апр. 2024"  );
        Record newRec30 = new Record(17, "вмаа", "sshhs", "sshhs","😰", "24 апр. 2024" );
        Record newRec31 = new Record(18, "вмаа", "sshhs", "sshhs","😰", "25 апр. 2024"  );
        Record newRec32 = new Record(19, "вмаа", "sshhs","sshhs", "😰", "26 апр. 2024"  );


        Record.noteDayArrayList.add(newRec0);
        Record.noteDayArrayList.add(newRec1);
        Record.noteDayArrayList.add(newRec2);
        Record.noteDayArrayList.add(newRec3);
        Record.noteDayArrayList.add(newRec4);
        Record.noteDayArrayList.add(newRec5);
        Record.noteDayArrayList.add(newRec6);
        Record.noteDayArrayList.add(newRec7);
      Record.noteDayArrayList.add(newRec8);
       Record.noteDayArrayList.add(newRec9);

        Record.noteDayArrayList.add(newRec10);
        Record.noteDayArrayList.add(newRec11);
        Record.noteDayArrayList.add(newRec12);
       Record.noteDayArrayList.add(newRec13);
       Record.noteDayArrayList.add(newRec14);
        Record.noteDayArrayList.add(newRec15);
        Record.noteDayArrayList.add(newRec16);

        Record.noteDayArrayList.add(newRec17);
        Record.noteDayArrayList.add(newRec18);
       Record.noteDayArrayList.add(newRec19);
        Record.noteDayArrayList.add(newRec20);
        Record.noteDayArrayList.add(newRec21);
        Record.noteDayArrayList.add(newRec22);
        Record.noteDayArrayList.add(newRec23);

        Record.noteDayArrayList.add(newRec24);
        Record.noteDayArrayList.add(newRec25);
        Record.noteDayArrayList.add(newRec26);
        Record.noteDayArrayList.add(newRec27);
        Record.noteDayArrayList.add(newRec28);
        Record.noteDayArrayList.add(newRec29);
        Record.noteDayArrayList.add(newRec30);
        Record.noteDayArrayList.add(newRec31);
        Record.noteDayArrayList.add(newRec32);

        dbm.addRecordToDatabase(newRec0);
        dbm.addRecordToDatabase(newRec1);
        dbm.addRecordToDatabase(newRec2);
       dbm.addRecordToDatabase(newRec3);
        dbm.addRecordToDatabase(newRec4);
        dbm.addRecordToDatabase(newRec5);
       dbm.addRecordToDatabase(newRec6);
        dbm.addRecordToDatabase(newRec7);
       dbm.addRecordToDatabase(newRec8);
       dbm.addRecordToDatabase(newRec9);
       dbm.addRecordToDatabase(newRec10);
       dbm.addRecordToDatabase(newRec11);
        dbm.addRecordToDatabase(newRec12);
        dbm.addRecordToDatabase(newRec13);
        dbm.addRecordToDatabase(newRec14);
        dbm.addRecordToDatabase(newRec15);
        dbm.addRecordToDatabase(newRec16);

       dbm.addRecordToDatabase(newRec17);
       dbm.addRecordToDatabase(newRec18);
        dbm.addRecordToDatabase(newRec19);
        dbm.addRecordToDatabase(newRec20);
        dbm.addRecordToDatabase(newRec21);
        dbm.addRecordToDatabase(newRec22);
        dbm.addRecordToDatabase(newRec23);
        dbm.addRecordToDatabase(newRec24);
        dbm.addRecordToDatabase(newRec25);
        dbm.addRecordToDatabase(newRec26);
        dbm.addRecordToDatabase(newRec27);
        dbm.addRecordToDatabase(newRec28);
        dbm.addRecordToDatabase(newRec29);
        dbm.addRecordToDatabase(newRec30);
        dbm.addRecordToDatabase(newRec31);
        dbm.addRecordToDatabase(newRec32);


    }


    public void newInfo(View view)
    {
        //Intent newNoteIntent = new Intent(this, DiaryDetailActivity.class);
       // startActivity(newNoteIntent);

       // dbm.deleteRecordInDB();
       addRecImitation();
    }



//график должен    в OnStope


    @Override
    protected void onStop() {
        super.onStop();

            dbm.getDateAndSmileFromTableRecordForGrafik();
            diaryAdapter.notifyDataSetChanged();


    }





    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume()
    {
        super.onResume();
//        setNoteDaysAdapter();
        diaryAdapter.notifyDataSetChanged();



    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbm.close();

    }


}