package com.example.note_prob22;


import static com.example.note_prob22.NoteDetailActivity.delet;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SQLiteManager extends SQLiteOpenHelper
{

    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "TODO_19";
    private static final int DATABASE_VERSION = 1;

    public static final String USERS = "Users";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

//для запоминания имени пользователя и пароля
    public static  String USER_REMEMBER = "";
    public static  String PASS_REMEMBER = "";

    public static final String TABLE_NAME_NOTE = "Note";
    public static final String COUNTER_NOTE = "counter";
    public static final String ID_NOTE = "id";
    public static final String TITLE_NODE = "title";
    public static final String DESC_NODE = "descr";
    public static final String DELETED_NODE = "deleted";
    public static final String DATE_NODE = "date";
    public static final String USERNAME_USERS = "username_users";


    public static final String TABLE_STORY = "Story";
    public static final String COUNTER_STORY = "counter_story";
    public static final String ID_STORY = "id_story";
    public static final String TITLE_STORY = "title_story";
    public static final String DESC_STORY = "desc_story";
    public static final String DELETED_STORY = "deleted_story";
    public static final String DATE_STORY = "date_story";
    public static final String USERNAME_USERS_STORY = "username_users_story";
    public static final String STORY_STATUS = "story_status";//


//Таблица информация в дневнике
    public static final String TABLE_NAME_RECORD = "Record";
    public static final String COUNTER_RECORD = "counterRec";
    public static final String ID_RECORD = "id";
    public static final String TITLE_RECORD = "title";
    public static final String DESC_RECORD = "descr";

    public static final String SMILE_RECORD = "smile";
    public static final String DELETED_RECORD = "deleted";
    public static final String DATE_RECORD = "date";
    public static final String USERNAME_USERS_RECORD = "username_users";
//

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

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
                .append(DELETED_NODE)
                .append(" TEXT, ")
                .append(USERNAME_USERS)
                .append(" TEXT) ");

        sqLiteDatabase.execSQL(sql.toString());
        //Создание таблицы "Story"
        StringBuilder sql2;
        sql2 = new StringBuilder()
                .append("CREATE TABLE if not exists  ")
                .append(TABLE_STORY)
                .append("(")
                .append(COUNTER_STORY)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_STORY)
                .append(" INT, ")
                .append(TITLE_STORY)
                .append(" TEXT, ")
                .append(DESC_STORY)
                .append(" TEXT, ")
                .append(DATE_STORY)
                .append(" TEXT, ")
                .append(STORY_STATUS)//
                .append(" TEXT, ")//
                .append(DELETED_STORY)
                .append(" datetime, ")
                .append(USERNAME_USERS_STORY)
                .append(" TEXT) ");

        sqLiteDatabase.execSQL(sql2.toString());

        //Создание таблицы "Record"
        StringBuilder sql3;
        sql3 = new StringBuilder()
                .append("CREATE TABLE if not exists ")
                .append(TABLE_NAME_RECORD)
                .append("(")
                .append(COUNTER_RECORD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_RECORD)
                .append(" INT, ")
                .append(TITLE_RECORD)
                .append(" TEXT, ")
                .append(DESC_RECORD)
                .append(" TEXT, ")
                .append(SMILE_RECORD)
                .append(" INT, ")
                .append(DATE_RECORD)
                .append(" TEXT, ")
                .append(DELETED_RECORD)
                .append(" TEXT, ")
                .append(USERNAME_USERS_RECORD)
                .append(" TEXT) ");

        sqLiteDatabase.execSQL(sql3.toString());


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {

        sqLiteDatabase.execSQL("drop table if exists " + TABLE_STORY);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME_NOTE);
        sqLiteDatabase.execSQL("drop table if exists " + USERS);
        //
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME_RECORD);
        onCreate(sqLiteDatabase);
    }



//записи



    public void addNoteToDatabase(Note note)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_NOTE, note.getId());
        contentValues.put(TITLE_NODE, note.getTitle());
        contentValues.put(DESC_NODE, note.getDescription());
        contentValues.put(DATE_NODE, note.getDate());
        contentValues.put(DELETED_NODE, getStringFromDate(note.getDeleted()));
        contentValues.put(USERNAME_USERS,USER_REMEMBER);



        sqLiteDatabase.insert(TABLE_NAME_NOTE, null, contentValues);

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date1 = df.format(Calendar.getInstance().getTime());

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(ID_STORY, note.getId());
        contentValues2.put(TITLE_STORY, note.getTitle());
        contentValues2.put(DESC_STORY, note.getDescription());
        contentValues2.put(DATE_STORY, note.getDate());
        contentValues2.put(STORY_STATUS, "Добавлено:");
        contentValues2.put(DELETED_STORY, date1);
        contentValues2.put(USERNAME_USERS_STORY,USER_REMEMBER);


        sqLiteDatabase.insert(TABLE_STORY, null, contentValues2);



    }



    public void populateNoteListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + COUNTER_NOTE + "," +ID_NOTE + "," + TITLE_NODE + ", " + DESC_NODE + ", " +DATE_NODE + ", "
                + DELETED_NODE + "," + USERNAME_USERS + " FROM " + TABLE_NAME_NOTE + " inner join " + USERS + " on " + USERNAME_USERS + " =" + USERNAME
                + " where " + USERNAME_USERS + " =?" , new String[]{USER_REMEMBER}))
        {
            Note.noteArrayList.clear();
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    String desc = result.getString(3);
                   String date = result.getString(4);
                    String stringDeleted = result.getString(5);
                    Date deleted = getDateFromString(stringDeleted);

                    Note note = new Note(id,title,desc,date, deleted);
                    Note.noteArrayList.add(note);
                }

           }
        }
    }







    public void updateNoteInDB(Note note)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_NOTE, note.getId());
        contentValues.put(TITLE_NODE, note.getTitle());
        contentValues.put(DESC_NODE, note.getDescription());
        contentValues.put(DELETED_NODE, getStringFromDate(note.getDeleted()));
        contentValues.put(DATE_NODE, note.getDate());

        sqLiteDatabase.update(TABLE_NAME_NOTE, contentValues, ID_NOTE +" =? and "+  USERNAME_USERS + " =? ", new String[]{String.valueOf(note.getId()), USER_REMEMBER});


        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date1 = df.format(Calendar.getInstance().getTime());

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(ID_STORY, note.getId());
        contentValues2.put(TITLE_STORY, note.getTitle());
        contentValues2.put(DESC_STORY, note.getDescription());
        contentValues2.put(DATE_STORY, note.getDate());
        contentValues2.put(STORY_STATUS, "Обновлено:");
        contentValues2.put(DELETED_STORY, date1);
        contentValues2.put(USERNAME_USERS_STORY,USER_REMEMBER);

        sqLiteDatabase.insert(TABLE_STORY, null, contentValues2);

        if (delet == 1) {
            ContentValues contentValues3 = new ContentValues();
            contentValues3.put(ID_STORY, note.getId());
            contentValues3.put(TITLE_STORY, note.getTitle());
            contentValues3.put(DESC_STORY, note.getDescription());
            contentValues3.put(DATE_STORY, note.getDate());
            contentValues3.put(STORY_STATUS, "Удалено:");
            contentValues3.put(DELETED_STORY, date1);
            contentValues3.put(USERNAME_USERS_STORY,USER_REMEMBER);

            sqLiteDatabase.insert(TABLE_STORY, null, contentValues3);
            delet = 0;

        }


        }


    public void deleteNoteInDB()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME_NOTE, USERNAME_USERS + " =? ",new String[]{USER_REMEMBER});
        sqLiteDatabase.delete(TABLE_STORY,USERNAME_USERS_STORY + " =? ", new String[]{USER_REMEMBER});
    }




    private String getStringFromDate(Date date)
    {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string)
    {
        try
        {
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e)
        {
            return null;
        }
    }

    //авторизация
    public Boolean insertData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERNAME, username);
        values.put(PASSWORD, password);

        long result = db.insert(USERS, null, values);
        if (result == -1) return false;
        else
            return true;


    }

    public Boolean checkusername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + USERS + " where " + USERNAME + "=?", new String[]{username});
        if (cursor.getCount()>0)
            return  true;
        else
            return false;

    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + USERS + " where " + USERNAME + " =? and " + PASSWORD + "=?", new String[]{username, password});
        if (cursor.getCount()>0)
            return  true;
        else
            return false;

    }


//    @Override
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NOTE);
//        db.execSQL("DROP TABLE IF EXISTS Users" );
//        onCreate(db);
//    }


    //дневник



    public void addRecordToDatabase(Record rec)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_RECORD, rec.getId());
        contentValues.put(TITLE_RECORD, rec.getTitle());
        contentValues.put(DESC_RECORD, rec.getDescription());
        contentValues.put(SMILE_RECORD, rec.getSmile());
        contentValues.put(DATE_RECORD, rec.getDate());
        contentValues.put(DELETED_RECORD, getStringFromDate(rec.getDeleted()));
        contentValues.put(USERNAME_USERS_RECORD,USER_REMEMBER);



        sqLiteDatabase.insert(TABLE_NAME_RECORD, null, contentValues);



    }



    public void populateRecordListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + COUNTER_RECORD + "," +ID_RECORD + "," + TITLE_RECORD + ", " + DESC_RECORD + ", "+SMILE_RECORD+", " +DATE_RECORD + ", "
                + DELETED_RECORD + "," + USERNAME_USERS_RECORD + " FROM " + TABLE_NAME_RECORD + " inner join " + USERS + " on " + USERNAME_USERS_RECORD + " =" + USERNAME
                + " where " + USERNAME_USERS_RECORD + " =?" , new String[]{USER_REMEMBER}))
        {
            Record.noteDayArrayList.clear();
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    String desc = result.getString(3);
                    String smile = result.getString(4);
                    String date = result.getString(5);
                    String stringDeleted = result.getString(6);
                    Date deleted = getDateFromString(stringDeleted);

                    Record rec = new Record(id,title,desc,smile, date, deleted);
                    Record.noteDayArrayList.add(rec);
                }

            }
        }
    }


    public void recordFromDB()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COUNTER_RECORD + "," +ID_RECORD + "," + TITLE_RECORD + ", " + DESC_RECORD + ", "+SMILE_RECORD+", " +DATE_RECORD + ", "
                + DELETED_RECORD + "," + USERNAME_USERS_RECORD + " FROM " + TABLE_NAME_RECORD + " inner join " + USERS + " on " + USERNAME_USERS_RECORD + " =" + USERNAME
                + " where " + USERNAME_USERS_RECORD + " =?" , new String[]{USER_REMEMBER});
        if (cursor.moveToFirst()) {
            do {
                String data = "";
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    data += cursor.getString(i) + " ";
                }
                Log.d("--Help--", "DB= "+data);
            } while (cursor.moveToNext());
        }
        cursor.close();






    }




    public void updateRecordInDB(Record rec)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_RECORD, rec.getId());
        contentValues.put(TITLE_RECORD, rec.getTitle());
        contentValues.put(DESC_RECORD, rec.getDescription());
        contentValues.put(SMILE_RECORD, rec.getSmile());
        contentValues.put(DELETED_RECORD, getStringFromDate(rec.getDeleted()));
//        contentValues.put(DATE_RECORD, rec.getDate());

        sqLiteDatabase.update(TABLE_NAME_RECORD, contentValues, ID_RECORD +" =? and "+  USERNAME_USERS_RECORD + " =? ", new String[]{String.valueOf(rec.getId()), USER_REMEMBER});


    }

    public void updateSmileInDB(Record rec)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

      contentValues.put(SMILE_RECORD, rec.getSmile());


        sqLiteDatabase.update(TABLE_NAME_RECORD, contentValues, ID_RECORD +" =? and "+  USERNAME_USERS_RECORD + " =? ", new String[]{String.valueOf(rec.getId()), USER_REMEMBER});


    }


    public void deleteRecordFromDB(Record rec) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME_RECORD, ID_RECORD + " =? and " + USERNAME_USERS_RECORD + " =? ", new String[]{String.valueOf(rec.getId()), USER_REMEMBER});
        sqLiteDatabase.close();
    }


    public void deleteRecordInDB()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME_RECORD, USERNAME_USERS_RECORD + " =? ",new String[]{USER_REMEMBER});

    }

   // @SuppressLint("Range")
//    public String getSmileyForRecord(int recordId) {
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        String query = "SELECT smile FROM Record WHERE id = " + recordId;
//        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
//        String smiley = "";
//        if (cursor.moveToFirst()) {
//            smiley = cursor.getString(cursor.getColumnIndex(SMILE_RECORD));
//        }
//        cursor.close();
//        sqLiteDatabase.close();
//        return smiley;
//    }

    @SuppressLint("Range")
    public String checkSmile(int recordId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();


        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + SMILE_RECORD + " FROM " + TABLE_NAME_RECORD + " inner join " + USERS + " on " + USERNAME_USERS_RECORD + " =" + USERNAME
                + " where " + USERNAME_USERS_RECORD + " =?" + " AND "+ ID_RECORD + " = "+ recordId, new String[]{USER_REMEMBER});
        String smiley = "";
        if (cursor.moveToFirst()) {
            smiley = cursor.getString(cursor.getColumnIndex(SMILE_RECORD));
        }
        cursor.close();
        sqLiteDatabase.close();
        return smiley;
    }










}


