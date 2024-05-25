package com.example.note_prob22;


import static com.example.note_prob22.AccountActivity.fridayHappyList;
import static com.example.note_prob22.AccountActivity.mondayHappyList;
import static com.example.note_prob22.AccountActivity.saturdayHappyList;
import static com.example.note_prob22.AccountActivity.sundayHappyList;
import static com.example.note_prob22.AccountActivity.thursdayHappyList;
import static com.example.note_prob22.AccountActivity.tuesdayHappyList;
import static com.example.note_prob22.AccountActivity.wednesdayHappyList;
import static com.example.note_prob22.SmilesActivity.selectSmilePicture;
import static com.example.note_prob22.SmilesActivity.smileyEvents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_prob22.classes.Record;
import com.example.note_prob22.classes.SmileyEvent;
import com.example.note_prob22.db.SQLiteManager;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DiaryDetailActivity extends DiaryActivity {
    public static int delet = 0;


    private Record selectedDayNote;

    private EditText yourFeelingsEditText, yourdescDayEditText, yourEventsEditText;
    private Button deleteButton2, smileBtn;
    private TextView cellSmile2;


    SQLiteManager sqDB;

    SharedPreferences sharedPreferences;


    private TextView dateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_diary_detail);
        initWg();


        ////
        sharedPreferences = this.getSharedPreferences("Note", Context.MODE_PRIVATE);

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

//        if (cellSmile2.getText() == "🤩") {
//            long timestamp = System.currentTimeMillis();
//           // String smileyType = "happiness"; // или любой другой тип смайлика
//            SmileyEvent event = new SmileyEvent(timestamp);
//            smileyEvents.add(event);
//        }


    }


    private void chechEmptyRecordOrNot() {
        Intent previousIntent = getIntent();

        int passedNoteID = previousIntent.getIntExtra(Record.NOTE_EDIT_EXTRA, -1);

        selectedDayNote = Record.getNoteDayForID(passedNoteID);


        if (selectedDayNote != null) {
            // Log.d("--Help--", " Id Не НОВОЙ записи из DiaryDetailActivity =" + selectedDayNote.getId());
            //Log.d("--Help--", " Текст НЕ НОВОЙ записи из DiaryDetailActivity =" + selectedDayNote.getFeeling());
            yourFeelingsEditText.setText(selectedDayNote.getFeeling());
            yourEventsEditText.setText(selectedDayNote.getEvents());
            yourdescDayEditText.setText(selectedDayNote.getDescription());


            if ((selectSmilePicture != "+")) smileBtn.setText(selectSmilePicture);

            else smileBtn.setText(selectedDayNote.getSmile());

        } else {
            smileBtn.setText(selectSmilePicture);
            dateNowForTextView();
            deleteButton2.setVisibility(View.INVISIBLE);


        }


        selectSmilePicture = "+";


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("titleEditText2", yourFeelingsEditText.getText().toString());
        outState.putString("descEditText2", yourdescDayEditText.getText().toString());
        outState.putString("eventsEditText2", yourEventsEditText.getText().toString());
    }

    private void initWg() {
        yourFeelingsEditText = findViewById(R.id.yourFeeling);
        yourdescDayEditText = findViewById(R.id.yourDescrDay);
        yourEventsEditText = findViewById(R.id.yourEvents);
        deleteButton2 = findViewById(R.id.deleteButton2);
        dateTV = findViewById(R.id.dateTV);
        smileBtn = findViewById(R.id.smileBtn);

        cellSmile2 = dayInfoListRc.findViewById(R.id.cellSmile2);

    }

    private void dateNowForTextView() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("ru"));
        String date = dateFormat.format(currentDate);
        dateTV.setText(date);
    }


    @SuppressLint("NotifyDataSetChanged")
    public void saveRecord(View view) {

        if ((yourFeelingsEditText.getText().toString().trim().length() == 0) && (yourdescDayEditText.getText().toString().trim().length() == 0) && (yourEventsEditText.getText().toString().trim().length() == 0) && (smileBtn.getText() == "+")) {
            Toast.makeText(getApplicationContext(), "Нельзя сохранить пустую запись!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
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
            } else smile = "";


            SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", new Locale("ru"));
            String currentTime = timeFormat.format(new Date());


            ///
            if (smile.equals("🤩")) {
                ///для сохранения текущего дня недели
                LocalDate today = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    today = LocalDate.now();
                    DayOfWeek dayOfWeek = today.getDayOfWeek();
                    int dayOfWeekNumber = dayOfWeek.getValue();


                    Gson gson = new Gson();

                    switch (dayOfWeekNumber) {
                        case 1: {
                            mondayHappyList.add(currentTime);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String json = gson.toJson(mondayHappyList);
                            editor.putString("mondayHappyList", json);
                            editor.apply();
                            break;
                        }
                        case 2: {
                            tuesdayHappyList.add(currentTime);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String json = gson.toJson(tuesdayHappyList);
                            editor.putString("tuesdayHappyList", json);
                            editor.apply();
                            break;
                        }
                        case 3: {
                            wednesdayHappyList.add(currentTime);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String json = gson.toJson(wednesdayHappyList);
                            editor.putString("wednesdayHappyList", json);
                            editor.apply();
                            break;
                        }
                        case 4: {
                            thursdayHappyList.add(currentTime);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String json = gson.toJson(thursdayHappyList);
                            editor.putString("thursdayHappyList", json);
                            editor.apply();
                            break;
                        }
                        case 5: {
                            fridayHappyList.add(currentTime);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String json = gson.toJson(fridayHappyList);
                            editor.putString("fridayHappyList", json);
                            editor.apply();
                            break;
                        }
                        case 6: {
                            saturdayHappyList.add(currentTime);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String json = gson.toJson(saturdayHappyList);
                            editor.putString("saturdayHappyList", json);
                            editor.apply();
                            break;
                        }
                        case 7: {
                            sundayHappyList.add(currentTime);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String json = gson.toJson(sundayHappyList);
                            editor.putString("sundayHappyList", json);
                            editor.apply();
                            break;
                        }
                    }


                }
            }


            if (selectedDayNote == null) {
                int id = 0;
                if (sqLiteManager.populateRecordList().size() == 0) {
                    id = 0;
                }
                else {
                     id = sqLiteManager.populateRecordList().size();
                }

                Log.d("--Help--", "ID =" + id);
                Record newRec = new Record(id, feelings, events, desc, smile, date);
                diaryAdapter.addItem(newRec);
              // Record.noteDayArrayList.add(newRec);
               // recViewModel.addRecord(newRec);
                sqLiteManager.addRecordToDatabase(newRec);




            } else {

                selectedDayNote.setFeeling(feelings);
                selectedDayNote.setEvents(events);
                selectedDayNote.setDescription(desc);
                selectedDayNote.setSmile(smile);


                sqLiteManager.updateRecordInDB(selectedDayNote);

            }

            finish();
        }

    }

///корявая
//    public void deleteRecord(View view) {
////        selectedDayNote.setDeleted(new Date());
////        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
////
////        sqLiteManager.deleteRecordFromDB(selectedDayNote);
////        diaryAdapter.removeItem(selectedDayNote.getId());
////        diaryAdapter.notifyItemRemoved(selectedDayNote.getId());
//
//        int position = Record.getPositionForID(selectedDayNote.getId());
//        if (position != -1) {
////            selectedDayNote.setDeleted(new Date());
////
////
////
////            for (int i = position; i < Record.noteDayArrayList.size(); i++) {
////                Record rec = Record.noteDayArrayList.get(i);
////                rec.setId(i);
////            }
////            diaryAdapter.removeItem(selectedDayNote.getId());
////            //  diaryAdapter.removeItem(position);
////
////
////
////
////           // diaryAdapter.notifyItemRangeChanged(position, diaryAdapter.getItemCount());
////            SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
////            sqLiteManager.deleteRecordFromDB(selectedDayNote);
//
//
//
//
//            selectedDayNote.setDeleted(new Date());
//            diaryAdapter.removeItem(position);
//            // обновляем значения ID для последующих элементов в списке
////            for (int i = position; i < Record.noteDayArrayList.size(); i++) {
////                Record rec = Record.noteDayArrayList.get(i);
////                rec.setId(i);
////            }
//            //
//            // diaryAdapter.notifyItemRangeChanged(position, diaryAdapter.getItemCount());
//            SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
//            sqLiteManager.deleteRecordFromDB(selectedDayNote);
//            // обновляем список из базы данных
//
////            List<Record> records = sqLiteManager.populateRecordList();
////            Record.noteDayArrayList.clear();
////            Record.noteDayArrayList.addAll(records);
////            for (Record rec : records) {
////                if (!rec.isDeleted()) {
////                    Record.noteDayArrayList.add(rec);
////                }
////            }
//            // diaryAdapter.notifyDataSetChanged();
//
//
////            diaryAdapter.notifyDataSetChanged();
//
//        }
//
//
//        finish();
//    }


    public void deleteRecord(View view) {


//        int position = Record.getPositionForID(selectedDayNote.getId());
//        if (position != -1) {
//
//            selectedDayNote.setDeleted(new Date());
//            diaryAdapter.removeItem(position);
//
//            SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
//            sqLiteManager.deleteRecordFromDB(selectedDayNote);
//
//
//        }
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.deleteRecordFromDB(selectedDayNote);
        diaryAdapter.deleteItem(selectedDayNote);

        diaryAdapter.updateAdapter(sqLiteManager.populateRecordList());


        finish();
    }
    public void chooseSmile(View view) {
        Intent intent = new Intent(this, SmilesActivity.class);
        startActivity(intent);
    }
}



