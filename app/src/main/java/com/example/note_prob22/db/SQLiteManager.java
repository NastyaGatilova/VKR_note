package com.example.note_prob22.db;


import static com.example.note_prob22.AccountActivity.fridayHappyList;
import static com.example.note_prob22.AccountActivity.mondayHappyList;
import static com.example.note_prob22.AccountActivity.saturdayHappyList;
import static com.example.note_prob22.AccountActivity.sundayHappyList;
import static com.example.note_prob22.AccountActivity.thursdayHappyList;
import static com.example.note_prob22.AccountActivity.tuesdayHappyList;
import static com.example.note_prob22.AccountActivity.wednesdayHappyList;
import static com.example.note_prob22.NoteDetailActivity.delet;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.example.note_prob22.classes.Data;
import com.example.note_prob22.classes.Note;
import com.example.note_prob22.classes.Record;
import com.example.note_prob22.graph.PairSmileAndDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "TODO_41";
    private static final int DATABASE_VERSION = 1;

    public static final String USERS = "Users";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    //для запоминания имени пользователя и пароля
    public static String USER_REMEMBER = "";
    public static String PASS_REMEMBER = "";

    public static final String TABLE_NAME_NOTE = "Note";
    public static final String COUNTER_NOTE = "counter";
    public static final String ID_NOTE = "id";
    public static final String TITLE_NODE = "title";
    public static final String DESC_NODE = "descr";
    public static final String DATE_NODE = "date";
    public static final String USERNAME_USERS = "username_users";




    //Таблица информация в дневнике
    public static final String TABLE_NAME_RECORD = "Record";
    public static final String COUNTER_RECORD = "counterRec";
    public static final String ID_RECORD = "id";
    public static final String FEELINGS_RECORD = "feelings";

    public static final String EVENTS_RECORD = "events";
    public static final String DESC_RECORD = "descr";

    public static final String SMILE_RECORD = "smile";
    public static final String DATE_RECORD = "date";

    public static final String TIME_RECORD = "time";
    public static final String USERNAME_USERS_RECORD = "username_users";
//


    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context) {
        if (sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Создание таблицы "Users"
        StringBuilder us;
        us = new StringBuilder()
                .append("CREATE TABLE if not exists ")
                .append(USERS)
                .append("(")
                .append(USERNAME)
                .append(" TEXT PRIMARY KEY, ")
                .append(PASSWORD)
                .append(" TEXT) ");
        sqLiteDatabase.execSQL(us.toString());
        //Создание таблицы "Note"
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE if not exists ")
                .append(TABLE_NAME_NOTE)
                .append("(")
                .append(COUNTER_NOTE)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_NOTE)
                .append(" INT, ")
                .append(TITLE_NODE)
                .append(" TEXT, ")
                .append(DESC_NODE)
                .append(" TEXT, ")
                .append(DATE_NODE)
                .append(" TEXT, ")
                .append(USERNAME_USERS)
                .append(" TEXT) ");

        sqLiteDatabase.execSQL(sql.toString());

        //Создание таблицы "Record"
        StringBuilder sql3;

        sql3 = new StringBuilder()
                .append("CREATE TABLE if not exists ")
                .append(TABLE_NAME_RECORD)
                .append("(")
                .append(COUNTER_RECORD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_RECORD)
                .append(" INT , ")
                .append(FEELINGS_RECORD)
                .append(" TEXT, ")
                .append(EVENTS_RECORD)
                .append(" TEXT, ")
                .append(DESC_RECORD)
                .append(" TEXT, ")
                .append(SMILE_RECORD)
                .append(" INT, ")
                .append(DATE_RECORD)
                .append(" TEXT, ")
                .append(TIME_RECORD)
                .append(" DATETIME, ")
                .append(USERNAME_USERS_RECORD)
                .append(" TEXT) ");

        sqLiteDatabase.execSQL(sql3.toString());


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME_NOTE);
        sqLiteDatabase.execSQL("drop table if exists " + USERS);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME_RECORD);

        onCreate(sqLiteDatabase);
    }

    //авторизация
    public Boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERNAME, username);
        values.put(PASSWORD, password);

        long result = db.insert(USERS, null, values);
        if (result == -1) return false;
        else
            return true;


    }

    public Boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + USERS + " where " + USERNAME + "=?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + USERS + " where " + USERNAME + " =? and " + PASSWORD + "=?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }


//записи


    public void addNoteToDatabase(String title, String desc, String date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
      //  contentValues.put(ID_NOTE, note.getId());
        contentValues.put(TITLE_NODE, title);
        contentValues.put(DESC_NODE, desc);
        contentValues.put(DATE_NODE, date);
        contentValues.put(USERNAME_USERS, USER_REMEMBER);


        sqLiteDatabase.insert(TABLE_NAME_NOTE, null, contentValues);




    }

    public void showUsers() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT *  FROM " + USERS, null)) {

            if (result.getCount() != 0) {
                while (result.moveToNext()) {

                    String user = result.getString(0);
                    String pass = result.getString(1);



                     Log.d("--Help--", "Db = " + user + " " + pass );

                }

            }
            ;
        }
    }


    public void populateNoteListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + COUNTER_NOTE + "," + ID_NOTE + "," + TITLE_NODE + ", " + DESC_NODE + ", " + DATE_NODE + ", "
               + USERNAME_USERS + " FROM " + TABLE_NAME_NOTE + " inner join " + USERS + " on " + USERNAME_USERS + " =" + USERNAME
                + " where " + USERNAME_USERS + " =?", new String[]{USER_REMEMBER})) {
            Note.noteArrayList.clear();
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String title = result.getString(2);
                    String desc = result.getString(3);
                    String date = result.getString(4);



                    Note note = new Note(id, title, desc, date);
                   // Log.d("--Help--", "Db = " + id + " " + title + " " + desc + " " + date);
                    Note.noteArrayList.add(note);
                }

            }
           ;
        }
    }

    public List<Note> populateNoteList() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<Note> dataList = new ArrayList<>();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + COUNTER_NOTE + "," + ID_NOTE + "," + TITLE_NODE + ", " + DESC_NODE + ", " + DATE_NODE + ", "
                + USERNAME_USERS + " FROM " + TABLE_NAME_NOTE + " inner join " + USERS + " on " + USERNAME_USERS + " =" + USERNAME
                + " where " + USERNAME_USERS + " =?", new String[]{USER_REMEMBER})) {

            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String title = result.getString(2);
                    String desc = result.getString(3);
                    String date = result.getString(4);
                    String stringDeleted = result.getString(5);
                    Date deleted = getDateFromString(stringDeleted);

                    Note note = new Note(id, title, desc, date, deleted);
                    // Log.d("--Help--", "Db = " + id + " " + title + " " + desc + " " + date);
                    dataList.add(note);
                }

            }

        }
        sqLiteDatabase.close();
        return dataList;
    }


    public void populateNoteListArrayForCalendar(String userDate) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + COUNTER_NOTE + "," + ID_NOTE + "," + TITLE_NODE + ", " + DESC_NODE + ", " + DATE_NODE + ", "
                + USERNAME_USERS + " FROM " + TABLE_NAME_NOTE + " inner join " + USERS + " on " + USERNAME_USERS + " =" + USERNAME
                + " where " + USERNAME_USERS + " =?" + " and " + DATE_NODE + " =?", new String[]{USER_REMEMBER, userDate})) {
            Note.noteArrayListWithForCalendar.clear();
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    String desc = result.getString(3);
                    String date = result.getString(4);


                    Note note = new Note(id, title, desc, date);
                    // Log.d("--Help--", "Пользовательская дата= " + userDate + " ; Записи = "+ id + " "+ " "+ title + " ; " +date);
                    Note.noteArrayListWithForCalendar.add(note);
                }

            }
        }
    }


    public void updateNoteInDB(Note note) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_NOTE, note.getId());
        contentValues.put(TITLE_NODE, note.getTitle());
        contentValues.put(DESC_NODE, note.getDescription());
        contentValues.put(DATE_NODE, note.getDate());

        sqLiteDatabase.update(TABLE_NAME_NOTE, contentValues, COUNTER_NOTE + " =? and " + USERNAME_USERS + " =? ", new String[]{String.valueOf(note.getId()), USER_REMEMBER});



    }


    public void deleteNoteInDB() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME_NOTE, USERNAME_USERS + " =? ", new String[]{USER_REMEMBER});
    }


    private String getStringFromDate(Date date) {
        if (date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException | NullPointerException e) {
            return null;
        }
    }

    public void deleteNoteFromDB(Note note) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME_NOTE, COUNTER_NOTE + " =? and " + USERNAME_USERS + " =? ", new String[]{String.valueOf(note.getId()), USER_REMEMBER});
        sqLiteDatabase.close();
    }

//    @Override
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NOTE);
//        db.execSQL("DROP TABLE IF EXISTS Users" );
//        onCreate(db);
//    }


    //дневник


    public void addRecordToDatabase(String feel, String ev, String des, String smile, String date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", new Locale("ru"));
        String currentTime = timeFormat.format(new Date());



        ContentValues contentValues = new ContentValues();
     //   contentValues.put(ID_RECORD, rec.getId());
        contentValues.put(FEELINGS_RECORD, feel);
        contentValues.put(EVENTS_RECORD, ev);
        contentValues.put(DESC_RECORD, des);
        contentValues.put(SMILE_RECORD, smile);
        contentValues.put(DATE_RECORD, date);
        contentValues.put(TIME_RECORD, currentTime);
    //    contentValues.put(DELETED_RECORD, getStringFromDate(rec.getDeleted()));
        contentValues.put(USERNAME_USERS_RECORD, USER_REMEMBER);


        sqLiteDatabase.insert(TABLE_NAME_RECORD, null, contentValues);


    }

    public void populateRecordListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + COUNTER_RECORD +
                "," + ID_RECORD +
                "," + FEELINGS_RECORD + ", " + EVENTS_RECORD + " , " + DESC_RECORD + ", " + SMILE_RECORD + ", " + DATE_RECORD +
                "," + USERNAME_USERS_RECORD + " FROM " + TABLE_NAME_RECORD + " inner join " + USERS + " on " + USERNAME_USERS_RECORD + " =" + USERNAME
                + " where " + USERNAME_USERS_RECORD + " =?", new String[]{USER_REMEMBER})) {
            Record.noteDayArrayList.clear();
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                 int id = result.getInt(0);
                //    int id = result.getInt(0);
                    String feelings = result.getString(2);
                    String events = result.getString(3);
                    String desc = result.getString(4);
                    String smile = result.getString(5);
                    String date = result.getString(6);
                    String stringDeleted = result.getString(7);
                  //  Date deleted = getDateFromString(stringDeleted);

                    Record rec = new Record(id,feelings, events, desc, smile, date);
                   Log.d("--Help--", "From db = "+ id + " "+ ""+smile  + " "+date);
                    Record.noteDayArrayList.add(rec);
                }

            }
        }
    }

    public List<Record> populateRecordList() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<Record> dataList = new ArrayList<>();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + COUNTER_RECORD + ","
                + ID_RECORD + ","
                + FEELINGS_RECORD + ", " + EVENTS_RECORD + " , " + DESC_RECORD + ", " + SMILE_RECORD + ", " + DATE_RECORD +
                 "," + USERNAME_USERS_RECORD + " FROM " + TABLE_NAME_RECORD + " inner join " + USERS + " on " + USERNAME_USERS_RECORD + " =" + USERNAME
                + " where " + USERNAME_USERS_RECORD + " =?", new String[]{USER_REMEMBER})) {

            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                  int id = result.getInt(0);
                  //  int id = result.getInt(0);
                    String feelings = result.getString(2);
                    String events = result.getString(3);
                    String desc = result.getString(4);
                    String smile = result.getString(5);
                    String date = result.getString(6);
                    String stringDeleted = result.getString(7);
                    //Date deleted = getDateFromString(stringDeleted);

                    Record rec = new Record(id,feelings, events, desc, smile, date);
                    // Log.d("--Help--", "From db = "+ id + " "+ ""+smile  + " "+date);
                    dataList.add(rec);
                }

            }
        }
        sqLiteDatabase.close();
        return dataList;
    }




    public void updateRecordInDB(Record rec) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", new Locale("ru"));
        String currentTime = timeFormat.format(new Date());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       contentValues.put(ID_RECORD, rec.getId());
        contentValues.put(FEELINGS_RECORD, rec.getFeeling());
        contentValues.put(EVENTS_RECORD, rec.getEvents());
        contentValues.put(DESC_RECORD, rec.getDescription());
        contentValues.put(SMILE_RECORD, rec.getSmile());
        contentValues.put(TIME_RECORD, currentTime);
       // contentValues.put(DELETED_RECORD, getStringFromDate(rec.getDeleted()));
        sqLiteDatabase.update(TABLE_NAME_RECORD, contentValues, COUNTER_RECORD + " =? and " + USERNAME_USERS_RECORD + " =? ", new String[]{String.valueOf(rec.getId()), USER_REMEMBER});

    }


    public void deleteRecordFromDB(Record rec) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME_RECORD, COUNTER_RECORD + " =? and " + USERNAME_USERS_RECORD + " =? ", new String[]{String.valueOf(rec.getId()), USER_REMEMBER});

    }

    public int getRecordCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  COUNT(*) FROM " + TABLE_NAME_RECORD + " inner join " + USERS + " on " + USERNAME_USERS_RECORD + " =" + USERNAME
                + " where " + USERNAME_USERS_RECORD + " =?", new String[]{USER_REMEMBER});


        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }


    public void deleteRecordInDB() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME_RECORD, USERNAME_USERS_RECORD + " =? ", new String[]{USER_REMEMBER});

    }


    //вывод всех дат из дневника для графика

    public List<PairSmileAndDate> getDateAndSmileFromTableRecordForGrafik() {
        SQLiteDatabase db = getReadableDatabase();
        List<PairSmileAndDate> pairList = new ArrayList<>();
        double smileNum = 0;

        Cursor cursor = db.rawQuery("SELECT " + COUNTER_RECORD + " ," + SMILE_RECORD + " ," + DATE_RECORD +
                " FROM " + TABLE_NAME_RECORD + " inner join " + USERS + " on " + USERNAME_USERS_RECORD + " =" + USERNAME
                + " where " + USERNAME_USERS_RECORD + " =?" + " ORDER BY " + DATE_RECORD, new String[]{USER_REMEMBER});
        if (cursor.moveToFirst()) {
            do {

                @SuppressLint("Range") String smile = cursor.getString(cursor.getColumnIndex(SMILE_RECORD));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DATE_RECORD));

                switch (smile) {
                    case "\uD83D\uDE30":
                        smileNum = 1;
                        break;
                    case "\uD83D\uDE41":
                        smileNum = 2;
                        break;
                    case "\uD83D\uDE14":
                        smileNum = 3;
                        break;
                    case "\uD83D\uDE10":
                        smileNum = 4;
                        break;
                    case "\uD83D\uDE21":
                        smileNum = 5;
                        break;
                    case "\uD83D\uDE42":
                        smileNum = 6;
                        break;
                    case "\uD83D\uDE31":
                        smileNum = 7;
                        break;
                    case "\uD83D\uDE03":
                        smileNum = 8;
                        break;
                    case "\uD83E\uDD29":
                        smileNum = 9;
                        break;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", new Locale("ru"));
                try {
                    Date d1 = sdf.parse(date);
                    pairList.add(new PairSmileAndDate(d1, smileNum));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            } while (cursor.moveToNext());
        } else {
            //Log.d("--Help--", "Error getDateAndSmileFromTableRecordForGrafik()");
        }
        cursor.close();


        return pairList;
    }

    public List<PairSmileAndDate> getDateAndAverageSmileFromTableRecordForGraphik() {
        SQLiteDatabase db = getReadableDatabase();
        List<PairSmileAndDate> pairList = new ArrayList<>();
        int smileNum = 0;
        List<Data> dataList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT " + COUNTER_RECORD + " ," + SMILE_RECORD + " ," + DATE_RECORD +
                " FROM " + TABLE_NAME_RECORD + " inner join " + USERS + " on " + USERNAME_USERS_RECORD + " =" + USERNAME
                + " where " + USERNAME_USERS_RECORD + " =?" + " ORDER BY " + DATE_RECORD, new String[]{USER_REMEMBER});
        if (cursor.moveToFirst()) {
            do {

                @SuppressLint("Range") String smile = cursor.getString(cursor.getColumnIndex(SMILE_RECORD));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DATE_RECORD));

              //  Log.d("--Help--", "СМайлики с датами "+ " id =" + id + " smile=" + smile + " date= " + date);





                switch (smile) {
                    case "\uD83D\uDE30":
                        smileNum = 1;
                        break;
                    case "\uD83D\uDE41":
                        smileNum = 2;
                        break;
                    case "\uD83D\uDE14":
                        smileNum = 3;
                        break;
                    case "\uD83D\uDE10":
                        smileNum = 4;
                        break;
                    case "\uD83D\uDE21":
                        smileNum = 5;
                        break;
                    case "\uD83D\uDE42":
                        smileNum = 6;
                        break;
                    case "\uD83D\uDE31":
                        smileNum = 7;
                        break;
                    case "\uD83D\uDE03":
                        smileNum = 8;
                        break;
                    case "\uD83E\uDD29":
                        smileNum = 9;
                        break;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", new Locale("ru"));
                Date d1 = null;
                try {
                    d1 = sdf.parse(date);
                   // pairList.add(new PairSmileAndDate(d1, smileNum));

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Data data = new Data(smileNum, d1);
                dataList.add(data);
            } while (cursor.moveToNext());
        } else {
            //Log.d("--Help--", "Error getDateAndSmileFromTableRecordForGrafik()");
        }
        cursor.close();


        // Отсортировать данные по дате
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dataList.sort(Comparator.comparing(d -> d.date));
        }

        // Рассчитать среднее значение номеров для каждой даты
        Map<Date, List<Integer>> dateToNumbersMap = new HashMap<>();
        for (Data data : dataList) {
            if (dateToNumbersMap.containsKey(data.date)) {
                dateToNumbersMap.get(data.date).add(data.number);
            } else {
                List<Integer> numbers = new ArrayList<>();
                numbers.add(data.number);
                dateToNumbersMap.put(data.date, numbers);
            }
        }

        for (Map.Entry<Date, List<Integer>> entry : dateToNumbersMap.entrySet()) {
            Date date = entry.getKey();
            List<Integer> numbers = entry.getValue();

            // Рассчет среднего значения
            double average = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                average = numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
            }

           // Log.d("--Help--", "Среднее = Date: " + date + ", Average: " + average);
            pairList.add(new PairSmileAndDate(date, average));
        }

        return pairList;
    }
    public boolean checkLastMonthQuery() {
        Calendar calendar = Calendar.getInstance();
        SQLiteDatabase db = getReadableDatabase();
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String lastMonthDate = sdf.format(calendar.getTime());

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME_RECORD +
                        " WHERE " + DATE_RECORD + " >= ?" + " and " + USERNAME_USERS_RECORD + " =?",
                new String[]{lastMonthDate, USER_REMEMBER});

        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            // Log.d("--Help--", "Error executing query");
            return false;
        }

    }


    public List<PairSmileAndDate> getDateAndAverageSmileForMounth() {
        SQLiteDatabase db = getReadableDatabase();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -31);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", new Locale("ru"));
        String lastMonthDate = sdf.format(calendar.getTime());
        List<PairSmileAndDate> pairList = new ArrayList<>();
        int smileNum = 0;
        List<Data> dataList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT " + COUNTER_RECORD + " ," + SMILE_RECORD + " ," + DATE_RECORD +
                " FROM " + TABLE_NAME_RECORD + " inner join " + USERS + " on " + USERNAME_USERS_RECORD + " =" + USERNAME
                + " where " + USERNAME_USERS_RECORD + " =?" + " ORDER BY " + DATE_RECORD, new String[]{USER_REMEMBER});
        if (cursor.moveToFirst()) {
            do {
             //   @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(ID_RECORD));
                @SuppressLint("Range") String smile = cursor.getString(cursor.getColumnIndex(SMILE_RECORD));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DATE_RECORD));

                switch (smile) {
                    case "\uD83D\uDE30":
                        smileNum = 1;
                        break;
                    case "\uD83D\uDE41":
                        smileNum = 2;
                        break;
                    case "\uD83D\uDE14":
                        smileNum = 3;
                        break;
                    case "\uD83D\uDE10":
                        smileNum = 4;
                        break;
                    case "\uD83D\uDE21":
                        smileNum = 5;
                        break;
                    case "\uD83D\uDE42":
                        smileNum = 6;
                        break;
                    case "\uD83D\uDE31":
                        smileNum = 7;
                        break;
                    case "\uD83D\uDE03":
                        smileNum = 8;
                        break;
                    case "\uD83E\uDD29":
                        smileNum = 9;
                        break;
                }

                Date d1 = null;
                Date lastD = null;
                try {
                    d1 = sdf.parse(date);
                    lastD =sdf.parse(lastMonthDate);
                    // pairList.add(new PairSmileAndDate(d1, smileNum));

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                if (d1.after(lastD) ){
                    Data data = new Data(smileNum, d1);
                    dataList.add(data);
                }

            } while (cursor.moveToNext());
        } else {
            //Log.d("--Help--", "Error getDateAndSmileFromTableRecordForGrafik()");
        }
        cursor.close();


        // Отсортировать данные по дате
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dataList.sort(Comparator.comparing(d -> d.date));
        }

        // Рассчитать среднее значение номеров для каждой даты
        Map<Date, List<Integer>> dateToNumbersMap = new HashMap<>();
        for (Data data : dataList) {
            if (dateToNumbersMap.containsKey(data.date)) {
                dateToNumbersMap.get(data.date).add(data.number);
            } else {
                List<Integer> numbers = new ArrayList<>();
                numbers.add(data.number);
                dateToNumbersMap.put(data.date, numbers);
            }
        }

        for (Map.Entry<Date, List<Integer>> entry : dateToNumbersMap.entrySet()) {
            Date date = entry.getKey();
            List<Integer> numbers = entry.getValue();

            // Рассчет среднего значения
            double average = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                average = numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
            }

            //  Log.d("--Help--", "Среднее = Date: " + date + ", Average: " + average);
            pairList.add(new PairSmileAndDate(date, average));
        }

        return pairList;
    }




    public boolean checkLastWeekQuery() {
        Calendar calendar = Calendar.getInstance();
        SQLiteDatabase db = getReadableDatabase();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String lastWeekDate = sdf.format(calendar.getTime());

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME_RECORD +
                        " WHERE " + DATE_RECORD + " >= ?" + " and " + USERNAME_USERS_RECORD + " =?",
                new String[]{lastWeekDate, USER_REMEMBER});

        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            //Log.d("--Help--", "Error executing query week");
            return false;
        }

    }

    public List<PairSmileAndDate> getDateAndAverageSmileForWeek() {
        SQLiteDatabase db = getReadableDatabase();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -8);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String lastWeekDate = sdf.format(calendar.getTime());
        List<PairSmileAndDate> pairList = new ArrayList<>();
        int smileNum = 0;
        List<Data> dataList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT " + COUNTER_RECORD + " ," + SMILE_RECORD + " ," + DATE_RECORD +
                " FROM " + TABLE_NAME_RECORD + " inner join " + USERS + " on " + USERNAME_USERS_RECORD + " =" + USERNAME
                + " where " + USERNAME_USERS_RECORD + " =?" + " ORDER BY " + DATE_RECORD, new String[]{USER_REMEMBER});
        if (cursor.moveToFirst()) {
            do {
                //@SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(ID_RECORD));
                @SuppressLint("Range") String smile = cursor.getString(cursor.getColumnIndex(SMILE_RECORD));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DATE_RECORD));

                switch (smile) {
                    case "\uD83D\uDE30":
                        smileNum = 1;
                        break;
                    case "\uD83D\uDE41":
                        smileNum = 2;
                        break;
                    case "\uD83D\uDE14":
                        smileNum = 3;
                        break;
                    case "\uD83D\uDE10":
                        smileNum = 4;
                        break;
                    case "\uD83D\uDE21":
                        smileNum = 5;
                        break;
                    case "\uD83D\uDE42":
                        smileNum = 6;
                        break;
                    case "\uD83D\uDE31":
                        smileNum = 7;
                        break;
                    case "\uD83D\uDE03":
                        smileNum = 8;
                        break;
                    case "\uD83E\uDD29":
                        smileNum = 9;
                        break;
                }

                Date d1 = null;
                Date lastD = null;
                try {
                    d1 = sdf.parse(date);
                    lastD = sdf.parse(lastWeekDate);
                    // pairList.add(new PairSmileAndDate(d1, smileNum));

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (d1.after(lastD) ){
                    Data data = new Data(smileNum, d1);
                    dataList.add(data);
                }

            } while (cursor.moveToNext());
        } else {
            //Log.d("--Help--", "Error getDateAndSmileFromTableRecordForGrafik()");
        }
        cursor.close();


        // Отсортировать данные по дате
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dataList.sort(Comparator.comparing(d -> d.date));
        }

        // Рассчитать среднее значение номеров для каждой даты
        Map<Date, List<Integer>> dateToNumbersMap = new HashMap<>();
        for (Data data : dataList) {
            if (dateToNumbersMap.containsKey(data.date)) {
                dateToNumbersMap.get(data.date).add(data.number);
            } else {
                List<Integer> numbers = new ArrayList<>();
                numbers.add(data.number);
                dateToNumbersMap.put(data.date, numbers);
            }
        }

        for (Map.Entry<Date, List<Integer>> entry : dateToNumbersMap.entrySet()) {
            Date date = entry.getKey();
            List<Integer> numbers = entry.getValue();

            // Рассчет среднего значения
            double average = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                average = numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
            }

            Log.d("--Help--", "Среднее = Date: " + date + ", Average: " + average);
            pairList.add(new PairSmileAndDate(date, average));
        }

        return pairList;
    }






    public boolean isTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME_RECORD + " where " + USERNAME_USERS_RECORD + " =?", new String[]{USER_REMEMBER});

        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count == 0) {
                //    Log.d("--Help--", "Table is empty");
                return true;
            } else if (count == 1) {
                //   Log.d("--Help--", "1 record");
                return true;
            } else {
                return false;
            }
        } else {
            // Log.d("--Help--", "Error executing query");
            return false;
        }

    }


    public List<PairSmileAndDate> get30UniqRecordFromDb() {
        double smileNum = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        List<PairSmileAndDate> pairList = new ArrayList<>();
        String query = "SELECT " + COUNTER_RECORD + ", " + SMILE_RECORD + "," + DATE_RECORD + " FROM " + TABLE_NAME_RECORD + " where " + USERNAME_USERS_RECORD + " =?" + " GROUP BY " + DATE_RECORD + " ORDER BY " + COUNTER_RECORD + " DESC LIMIT 30";
        Cursor cursor = db.rawQuery(query, new String[]{USER_REMEMBER});

        if (cursor.moveToFirst()) {
            do {
             //   @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(ID_RECORD));
                @SuppressLint("Range") String smile = cursor.getString(cursor.getColumnIndex(SMILE_RECORD));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DATE_RECORD));

                switch (smile) {
                    case "\uD83D\uDE30":
                        smileNum = 1;
                        break;
                    case "\uD83D\uDE41":
                        smileNum = 2;
                        break;
                    case "\uD83D\uDE14":
                        smileNum = 3;
                        break;
                    case "\uD83D\uDE10":
                        smileNum = 4;
                        break;
                    case "\uD83D\uDE21":
                        smileNum = 5;
                        break;
                    case "\uD83D\uDE42":
                        smileNum = 6;
                        break;
                    case "\uD83D\uDE31":
                        smileNum = 7;
                        break;
                    case "\uD83D\uDE03":
                        smileNum = 8;
                        break;
                    case "\uD83E\uDD29":
                        smileNum = 9;
                        break;
                }

                try {
                    Date d1 = sdf.parse(date);
                    pairList.add(new PairSmileAndDate(d1, smileNum));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } while (cursor.moveToNext());
        } else {
            Log.d("--Help--", "Error get30UniqRecordFromDb");
        }
//        for (PairSmileAndDate pair : pairList) {
//            Log.d("--Help--", "Uni 30 dates: " + pair.getDates() + ", Smile: " + pair.getSmile());
//        }
        cursor.close();
        return pairList;
    }









    public List<PairSmileAndDate> get7UniqRecordFromDb() {
        double smileNum = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        List<PairSmileAndDate> pairList = new ArrayList<>();
        String query = "SELECT " + COUNTER_RECORD + ", " + SMILE_RECORD + "," + DATE_RECORD + " FROM " + TABLE_NAME_RECORD + " where " + USERNAME_USERS_RECORD + " =?" + " GROUP BY " + DATE_RECORD + " ORDER BY " + DATE_RECORD + " DESC LIMIT 7";
        Cursor cursor = db.rawQuery(query, new String[]{USER_REMEMBER});

        if (cursor.moveToFirst()) {
            do {
                //  @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(ID_RECORD));
                @SuppressLint("Range") String smile = cursor.getString(cursor.getColumnIndex(SMILE_RECORD));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DATE_RECORD));


                switch (smile) {
                    case "\uD83D\uDE30":
                        smileNum = 1;
                        break;
                    case "\uD83D\uDE41":
                        smileNum = 2;
                        break;
                    case "\uD83D\uDE14":
                        smileNum = 3;
                        break;
                    case "\uD83D\uDE10":
                        smileNum = 4;
                        break;
                    case "\uD83D\uDE21":
                        smileNum = 5;
                        break;
                    case "\uD83D\uDE42":
                        smileNum = 6;
                        break;
                    case "\uD83D\uDE31":
                        smileNum = 7;
                        break;
                    case "\uD83D\uDE03":
                        smileNum = 8;
                        break;
                    case "\uD83E\uDD29":
                        smileNum = 9;
                        break;
                }

                try {
                    Date d1 = sdf.parse(date);
                    pairList.add(new PairSmileAndDate(d1, smileNum));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } while (cursor.moveToNext());
        } else {
            Log.d("--Help--", "Error get7UniqRecordFromDb");
        }
//        for (PairSmileAndDate pair : pairList) {
//            Log.d("--Help--", "Uni 7 dates: " + pair.getDates() + ", Smile: " + pair.getSmile());
//        }
        cursor.close();
        return pairList;
    }


    // вывести самые частые смайлики

    public String getMostUsedSmiley() {

        SQLiteDatabase db = sqLiteManager.getReadableDatabase();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7); // Вычитаем 7 дней из текущей даты
        Date oneWeekAgo = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String oneWeekAgoString = dateFormat.format(oneWeekAgo);

        Date currentDate = new Date();
        String dateNow = dateFormat.format(currentDate);

        String query = "SELECT " + COUNTER_RECORD + ", " + SMILE_RECORD + " FROM " + TABLE_NAME_RECORD + " where " + USERNAME_USERS_RECORD + " =?" + " and " + DATE_RECORD + " >=?";
        Cursor cursor = db.rawQuery(query, new String[]{USER_REMEMBER, oneWeekAgoString});


        HashMap<String, Integer> smileyCountMap = new HashMap<>();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String smiley = cursor.getString(cursor.getColumnIndex(SMILE_RECORD));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    smileyCountMap.put(smiley, smileyCountMap.getOrDefault(smiley, 0) + 1);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        String mostUsedSmiley = "";
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : smileyCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostUsedSmiley = entry.getKey();
            }
        }
       // String resultString = mostUsedSmiley +  " самая частая эмоция за "+oneWeekAgoString + " — " + dateNow;
      //  Log.d("--Help--", "Самый частый смайлик = " + " Дата1=" + oneWeekAgoString + " Дата2=" + dateNow + " " + mostUsedSmiley);
        return mostUsedSmiley;
    }

    @SuppressLint("SuspiciousIndentation")
    public void getTimeAndSmileFromDB() {

        SQLiteDatabase db = getReadableDatabase();


        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("ru"));
        String date = dateFormat.format(currentDate);

        Cursor cursor = db.rawQuery("SELECT " + COUNTER_RECORD + "," + SMILE_RECORD + ", " + TIME_RECORD + ", "
                + USERNAME_USERS_RECORD + " FROM " + TABLE_NAME_RECORD + " INNER JOIN " + USERS + " ON " + USERNAME_USERS_RECORD + " = " + USERNAME
                + " WHERE " + USERNAME_USERS_RECORD + " = ? AND  " + DATE_RECORD + " = ?", new String[]{USER_REMEMBER, date});

        if (cursor.moveToFirst()) {
            do {
                String data = "";
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    data += cursor.getString(i) + " ";
                }
                //  Log.d("--Help--", "DB= " + data);
            } while (cursor.moveToNext());
        } else // Log.d("--Help--", "Error  getTimeAndSmileFromDB()");
            cursor.close();


    }

    public List<PairSmileAndDate> getTimeAndSmileForGrafikHours() {
        SQLiteDatabase db = getReadableDatabase();
        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("ru"));
        String date = dateFormat.format(currentDate);

        List<PairSmileAndDate> pairList = new ArrayList<>();
        double smileNum = 0;

        Cursor cursor = db.rawQuery("SELECT " + COUNTER_RECORD + "," + SMILE_RECORD + ", " + TIME_RECORD + ", "
                + USERNAME_USERS_RECORD + " FROM " + TABLE_NAME_RECORD + " INNER JOIN " + USERS + " ON " + USERNAME_USERS_RECORD + " = " + USERNAME
                + " WHERE " + USERNAME_USERS_RECORD + " = ? AND  " + DATE_RECORD + " = ?", new String[]{USER_REMEMBER, date});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String smile = cursor.getString(cursor.getColumnIndex(SMILE_RECORD));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(TIME_RECORD));

                switch (smile) {
                    case "\uD83D\uDE30":
                        smileNum = 1;
                        break;
                    case "\uD83D\uDE41":
                        smileNum = 2;
                        break;
                    case "\uD83D\uDE14":
                        smileNum = 3;
                        break;
                    case "\uD83D\uDE10":
                        smileNum = 4;
                        break;
                    case "\uD83D\uDE21":
                        smileNum = 5;
                        break;
                    case "\uD83D\uDE42":
                        smileNum = 6;
                        break;
                    case "\uD83D\uDE31":
                        smileNum = 7;
                        break;
                    case "\uD83D\uDE03":
                        smileNum = 8;
                        break;
                    case "\uD83E\uDD29":
                        smileNum = 9;
                        break;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm", new Locale("ru"));
                try {
                    Date d1 = sdf.parse(time);
                    pairList.add(new PairSmileAndDate(d1, smileNum));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            } while (cursor.moveToNext());
        } else {
            // Log.d("--Help--", "Error getDateAndSmileFromTableRecordForGrafik()");
        }

//                for (PairSmileAndDate pair : pairList) {
//            Log.d("--Help--", "Time: " + pair.getDates() + ", Smile: " + pair.getSmile());
//        }
        cursor.close();


        return pairList;
    }

    public String showSmileForDate(String userDate) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + COUNTER_RECORD + "," +
              //  ID_RECORD + "," +
                DATE_RECORD + ", " + SMILE_RECORD + ", "
                + USERNAME_USERS_RECORD + " FROM " + TABLE_NAME_RECORD + " inner join " + USERS + " on " + USERNAME_USERS_RECORD + " =" + USERNAME
                + " where " + USERNAME_USERS + " =?" + " and " + DATE_RECORD + " =?" + " ORDER BY " + SMILE_RECORD + " DESC LIMIT 1", new String[]{USER_REMEMBER, userDate})) {

            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String date = result.getString(2);
                    String smile = result.getString(3);


                    Log.d("--Help--", "Для подсказки Smile= " + userDate + " ; Записи = " + id + " " + " " + date + " ; " + smile);
                    return smile;
                }

            }
        }
        return "";
    }

    public void populateSmilesListArrayForCalendar(String userDate) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + COUNTER_RECORD + "," +
               ID_RECORD + "," +
                SMILE_RECORD + ", " + DATE_RECORD + ", "
                + USERNAME_USERS_RECORD + " FROM " + TABLE_NAME_RECORD + " inner join " + USERS + " on " + USERNAME_USERS_RECORD + " =" + USERNAME
                + " where " + USERNAME_USERS_RECORD + " =?" + " and " + DATE_RECORD + " =?", new String[]{USER_REMEMBER, userDate})) {

            Record.recordArrayListWithForCalendar.clear();

            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String smile = result.getString(2);
                    String date = result.getString(3);


                    Record rec = new Record( id, "", "", "", smile, date);
                    // Log.d("--Help--", "Пользовательская дата= " + userDate + " ; Записи = "+ id + " "+ " "+ title + " ; " +date);
                    Record.recordArrayListWithForCalendar.add(rec);
                }

            }
        }
    }

//вывод в графике событий соответвующей точке
public ArrayList<Record> populateRecordEventsListArrayForCalendar(String userDate) {


    SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

    ArrayList<Record> dataList = new ArrayList<>();
    try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + COUNTER_RECORD + ","
           + ID_RECORD + ","
            + FEELINGS_RECORD + ", " + EVENTS_RECORD + " , " + DESC_RECORD + ", " + SMILE_RECORD + ", " + DATE_RECORD + ", " +
              USERNAME_USERS_RECORD + " FROM " + TABLE_NAME_RECORD + " inner join " + USERS + " on " + USERNAME_USERS_RECORD + " = " + USERNAME
            + " where " + USERNAME_USERS_RECORD + " =? " + " and " + DATE_RECORD + " =?", new String[]{USER_REMEMBER,userDate })) {

        if (result.getCount() != 0) {
            while (result.moveToNext()) {
                int id = result.getInt(1);
                String feelings = result.getString(2);
                String events = result.getString(3);
                String desc = result.getString(4);
                String smile = result.getString(5);
                String date = result.getString(6);



                Record rec = new Record(id,feelings, events, desc, smile, date);
                Log.d("--Help--", "Точка = userDate ="+ userDate + id + " "+ ""+smile  + " "+date);
                dataList.add(rec);
            }

        }
    }



    return dataList;
}


    public void deleteLastRecord() {
        String query = "DELETE FROM " + TABLE_NAME_RECORD + " WHERE " + COUNTER_RECORD + " = (SELECT MAX(" + COUNTER_RECORD + ") FROM " + TABLE_NAME_RECORD + ")";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }
}


