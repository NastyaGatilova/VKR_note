package com.example.note_prob22;


import static com.example.note_prob22.SmilesActivity.selectSmilePicture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_prob22.classes.Record;
import com.example.note_prob22.db.SQLiteManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiaryDetailActivity extends DiaryActivity {
    public static int delet = 0;
    private Record selectedDayNote;
    private Button saveButton2;
    private EditText yourFeelingsEditText, yourdescDayEditText, yourEventsEditText;
    private Button deleteButton2,smileBtn;

    SQLiteManager sqDB;

    private boolean saveBtnFlag=false;



    private TextView dateTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_diary_detail);
        initWg();

        if (savedInstanceState != null) {
            String editTextValue = savedInstanceState.getString("titleEditText2");
            yourFeelingsEditText.setText(editTextValue);
            String descEditText2 = savedInstanceState.getString("descEditText2");
            yourdescDayEditText.setText(descEditText2);
            String eventsEditText2 = savedInstanceState.getString("eventsEditText2");
            yourEventsEditText.setText(eventsEditText2);
        }
        sqDB = new SQLiteManager(this);

    }


    @Override
    protected void onResume() {
        super.onResume();

        Intent previousIntent = getIntent();
        String date = previousIntent.getStringExtra("date");
        dateTV.setText(date);
        chechEmptyRecordOrNot();

    }

    private void chechEmptyRecordOrNot()
    {
        Intent previousIntent = getIntent();

        int passedNoteID = previousIntent.getIntExtra(Record.NOTE_EDIT_EXTRA, -1);

        selectedDayNote = Record.getNoteDayForID(passedNoteID);


        if (selectedDayNote != null)
        {

            yourFeelingsEditText.setText(selectedDayNote.getFeeling());
            yourEventsEditText.setText(selectedDayNote.getEvents());
            yourdescDayEditText.setText(selectedDayNote.getDescription());


            if ((selectSmilePicture != "+")) smileBtn.setText(selectSmilePicture);

            else  smileBtn.setText(selectedDayNote.getSmile());

        }
        else
        {
            smileBtn.setText(selectSmilePicture);
            dateNowForTextView();
            deleteButton2.setVisibility(View.INVISIBLE);


        }


        selectSmilePicture="+";


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("titleEditText2", yourFeelingsEditText.getText().toString());
        outState.putString("descEditText2", yourdescDayEditText.getText().toString());
        outState.putString("eventsEditText2", yourEventsEditText.getText().toString());
    }

    private void initWg(){
    yourFeelingsEditText = findViewById(R.id.yourFeeling);
    yourdescDayEditText = findViewById(R.id.yourDescrDay);
    yourEventsEditText = findViewById(R.id.yourEvents);
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






    public void saveRecord(View view)  {

        if ((yourFeelingsEditText.getText().toString().trim().length()== 0)  && (yourdescDayEditText.getText().toString().trim().length() == 0)  && (yourEventsEditText.getText().toString().trim().length() == 0) && (smileBtn.getText()=="+")){
            Toast.makeText(getApplicationContext(), "Нельзя сохранить пустую запись!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            saveBtnFlag = true;

            Date currentDate = new Date();

            // задаем формат вывода даты
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("ru"));


            SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
            String feelings = String.valueOf(yourFeelingsEditText.getText());
            String events = String.valueOf(yourEventsEditText.getText());
            String desc = String.valueOf(yourdescDayEditText.getText());






            String date = dateFormat.format(currentDate);





            String smile = "";
            if ((smileBtn.getText()) != "+") {
                smile = String.valueOf(smileBtn.getText());
            }
            else smile="";



            if(selectedDayNote == null)
            {
                int id = Record.noteDayArrayList.size();
                Record newRec = new Record(id, feelings, events, desc, smile, date);
                Record.noteDayArrayList.add(newRec);
                sqLiteManager.addRecordToDatabase(newRec);

            }
            else
            {

                selectedDayNote.setFeeling(feelings);
                selectedDayNote.setEvents(events);
                selectedDayNote.setDescription(desc);
                selectedDayNote.setSmile(smile);




                sqLiteManager.updateRecordInDB(selectedDayNote);
            }

            finish();
        }

    }



    public void deleteRecord(View view)
    {
        delet = 1;
        selectedDayNote.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.deleteRecordFromDB(selectedDayNote);
        finish();
    }



    public void chooseSmile(View view)
    {
        Intent intent = new Intent(this, SmilesActivity.class);
        startActivity(intent);
    }
}



