package com.example.note_prob22;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note_prob22.classes.Note;
import com.example.note_prob22.db.SQLiteManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class NoteDetailActivity extends AppCompatActivity
{




    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private EditText titleEditText, descEditText;
    private Button deleteButton;
    private Note selectedNote;
    public static int delet = 0;

    SQLiteManager sqDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initWidgets();
        initDatePicker();
        checkForEditNote();
        sqDB = new SQLiteManager(this);



    }

    private void initWidgets()
    {
        titleEditText = findViewById(R.id.titleEditText);
        descEditText = findViewById(R.id.descriptionEditText);
        deleteButton = findViewById(R.id.deleteNoteButton);
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
    }

    private void checkForEditNote()
    {
        Intent previousIntent = getIntent();

        int passedNoteID = previousIntent.getIntExtra(Note.NOTE_EDIT_EXTRA, -1);
        selectedNote = Note.getNoteForID(passedNoteID);


        if (selectedNote != null)
        {
            titleEditText.setText(selectedNote.getTitle());
            descEditText.setText(selectedNote.getDescription());
            dateButton.setText(selectedNote.getDate());


        }
        else
        {

            deleteButton.setVisibility(View.INVISIBLE);
        }
    }



    public void saveNote(View view)
    {
        if ((titleEditText.getText().toString().trim().length()== 0)  && (descEditText.getText().toString().trim().length() == 0)){
            Toast.makeText(getApplicationContext(), "Нельзя сохранить пустую запись!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
            String title = String.valueOf(titleEditText.getText());
            String desc = String.valueOf(descEditText.getText());
            String date = String.valueOf(dateButton.getText());




            if(selectedNote == null)
            {
                int id = Note.noteArrayList.size();
                Note newNote = new Note(id, title, desc, date);
                Note.noteArrayList.add(newNote);
                sqLiteManager.addNoteToDatabase(newNote);


            }
            else
            {
                selectedNote.setTitle(title);
                selectedNote.setDescription(desc);
                selectedNote.setDate(date);

                sqLiteManager.updateNoteInDB(selectedNote);
            }
            finish();
        }
    }


    public void deleteNote(View view)
    {
        delet = 1;
        selectedNote.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.updateNoteInDB(selectedNote);
        finish();
    }




    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    public void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {

                month = month + 1;

                String Mydate = day + "." + month + "." + year;
;


                Calendar cal = Calendar.getInstance();
                int year1 = cal.get(Calendar.YEAR);
                int month1 = cal.get(Calendar.MONTH);
                int day1 = cal.get(Calendar.DAY_OF_MONTH);
                int month2 = month1+1;
                String tek_data = day1 + "." + month2+ "." +  year1;



                try {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    Date date1 = dateFormat.parse(Mydate);
                    Date date2 = dateFormat.parse(tek_data);


                    long milliseconds = date2.getTime() - date1.getTime();

                    int days = (int) (milliseconds / (24 * 60 * 60 * 1000));

                    if (days <= 0) {
                        String date = makeDateString(day, month, year);
                        dateButton.setText(date);
                    }else{
                        String choisedata = makeDateString(day, month, year);
                        Toast toast = Toast.makeText(getApplicationContext(),
                            "Дату "+ choisedata  +" выбрать нельзя!", Toast.LENGTH_SHORT);
                        toast.show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };




        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);


    }


    private String makeDateString(int day, int month, int year)
    {
        return  day + " " + getMonthFormat(month) + " " + year;
    }


    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "янв.";
        if(month == 2)
            return "фев.";
        if(month == 3)
            return "мар.";
        if(month == 4)
            return "апр.";
        if(month == 5)
            return "май";
        if(month == 6)
            return "июн.";
        if(month == 7)
            return "июл.";
        if(month == 8)
            return "авг.";
        if(month == 9)
            return "сен.";
        if(month == 10)
            return "окт.";
        if(month == 11)
            return "ноя.";
        if(month == 12)
            return "дек.";


        return "янв.";
    }


    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }


}