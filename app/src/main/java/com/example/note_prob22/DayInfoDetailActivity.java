package com.example.note_prob22;


import static com.example.note_prob22.SmilesActivity.selectSmilePicture;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.TooltipCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DayInfoDetailActivity extends DaysActivity {
    public static int delet = 0;
    private Record selectedDayNote;
    private Button saveButton2;
    private EditText titleEditText2, descEditText2;
    private Button deleteButton2,smileBtn;

    SQLiteManager sqDB;

    private boolean flagUpdate=false;

    private String dayBeforeYesterdayStr = "";

    private String dayYesterdayStr = "";

    private TextView dateTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_day_info_detail);
        initWg();

        if (savedInstanceState != null) {
            String editTextValue = savedInstanceState.getString("titleEditText2");
            titleEditText2.setText(editTextValue);
            String descEditText2 = savedInstanceState.getString("descEditText2");
            titleEditText2.setText(descEditText2);
          //  String smile = savedInstanceState.getString("smileBtn");
         //   smileBtn.setText(smile);
        }
        sqDB = new SQLiteManager(this);


        //chechEmptyRecordOrNot();


       // checkForEditNote();

      //  dateNowForTextView();
    }


    @Override
    protected void onResume() {
        super.onResume();

        Intent previousIntent = getIntent();

        int passedNoteID = previousIntent.getIntExtra(Record.NOTE_EDIT_EXTRA, -1);
        String date = previousIntent.getStringExtra("date");
        dateTV.setText(date);
//        Log.d("--Help--", "checkSmile=" + sqDB.checkSmile(passedNoteID));





        chechEmptyRecordOrNot();









//        Intent previousIntent = getIntent();
//
//        int passedNoteID = previousIntent.getIntExtra(Record.NOTE_EDIT_EXTRA, -1);
//
//        if ( sqDB.checkSmile(passedNoteID) == "") smileBtn.setText("+");
//        else
//            smileBtn.setText(sqDB.checkSmile(passedNoteID));
//
//
//      Log.d("--Help--", "ID=" + passedNoteID + "smile =" +  sqDB.checkSmile(passedNoteID));

    }

    private void chechEmptyRecordOrNot()
    {
        Intent previousIntent = getIntent();

        int passedNoteID = previousIntent.getIntExtra(Record.NOTE_EDIT_EXTRA, -1);

        selectedDayNote = Record.getNoteDayForID(passedNoteID);


        if (selectedDayNote != null)
        {

            Log.d("--Help--", "Процесс обновления записи");
//            Log.d("--Help--", "Cмайлик " + sqDB.checkSmile(passedNoteID)+  " для записи id="+ passedNoteID);


           // smileBtn.setText(sqDB.checkSmile(passedNoteID));
            titleEditText2.setText(selectedDayNote.getTitle());
            descEditText2.setText(selectedDayNote.getDescription());

            Log.d("--Help--", "selectsmile!!!!!="+selectSmilePicture);
            if (selectSmilePicture != "+") smileBtn.setText(selectSmilePicture);
            else smileBtn.setText(selectedDayNote.getSmile());








        }
        else
        {
            Log.d("--Help--", "Запись новая");
            Log.d("--Help--", "selectSmile="+selectSmilePicture);
            smileBtn.setText(selectSmilePicture);
            dateNowForTextView();
            deleteButton2.setVisibility(View.INVISIBLE);


        }





    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("titleEditText2", titleEditText2.getText().toString());
        outState.putString("descEditText2", descEditText2.getText().toString());
       // outState.putString("smileBtn", smileBtn.getText().toString());
    }

    private void initWg(){
    titleEditText2 = findViewById(R.id.titleEditText2);
    descEditText2 = findViewById(R.id.descriptionEditText2);
    deleteButton2 = findViewById(R.id.deleteButton2);
    dateTV = findViewById(R.id.dateTV);
    smileBtn = findViewById(R.id.smileBtn);

}

    private void dateNowForTextView(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("ru"));
        String date = dateFormat.format(currentDate);
        dateTV.setText(date);
}



//    private void checkForEditNote()
//    {
//        Intent previousIntent = getIntent();
//
//        int passedNoteID = previousIntent.getIntExtra(Record.NOTE_EDIT_EXTRA, -1);
//        selectedDayNote = Record.getNoteDayForID(passedNoteID);
//
//       // smileBtn.setText(selectSmilePicture);
//
//
//        if (selectedDayNote != null)
//        {
////            titleEditText2.setText(selectedDayNote.getTitle());
////            descEditText2.setText(selectedDayNote.getDescription());
////           smileBtn.setText(selectedDayNote.getSmile());
//
//
//
//        }
//        else
//        {
//           // if (selectSmilePicture == "") smileBtn.setText("+");
////            deleteButton2.setVisibility(View.INVISIBLE);
//
//
//
//           // smileBtn.setText("+");
//
//        }
//    }




    public void saveRecord(View view)
    {

       Date currentDate = new Date();

        // задаем формат вывода даты
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("ru"));
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String title = String.valueOf(titleEditText2.getText());
        String desc = String.valueOf(descEditText2.getText());


        String date = dateFormat.format(currentDate);


  //     String smile = String.valueOf(selectSmilePicture);;
//        String smile = sqDB.checkSmile(passedNoteID)\
        String smile = "";
        if ((smileBtn.getText()) != "+")
        smile = String.valueOf(smileBtn.getText());
        else smile="";

        if(selectedDayNote == null)
        {
            int id = Record.noteDayArrayList.size();
            Record newRec = new Record(id, title, desc, smile, date );
            Record.noteDayArrayList.add(newRec);
            sqLiteManager.addRecordToDatabase(newRec);

        }
        else
        {

            selectedDayNote.setTitle(title);
            selectedDayNote.setDescription(desc);
            selectedDayNote.setSmile(smile);
          //  sqLiteManager.updateSmileInDB(selectedDayNote);


            sqLiteManager.updateRecordInDB(selectedDayNote);
        }
        selectSmilePicture="+";
        finish();
    }



    public void deleteRecord(View view)
    {
        delet = 1;
        selectedDayNote.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        // sqLiteManager.updateRecordInDB(selectedDayNote);
        sqLiteManager.deleteRecordFromDB(selectedDayNote);
        finish();
    }



    public void chooseSmile(View view)
    {

        Intent previousIntent = getIntent();


        Intent intent = new Intent(this, SmilesActivity.class);
        startActivity(intent);



    }
}



