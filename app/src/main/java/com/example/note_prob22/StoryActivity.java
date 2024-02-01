package com.example.note_prob22;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;


public class StoryActivity extends AppCompatActivity {
 SQLiteManager sql;
 SQLiteDatabase db;
 Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        TextView tv = (TextView) findViewById(R.id.tv_);

        sql = new SQLiteManager(this);
        db = sql.getWritableDatabase();

        cursor = db.rawQuery("SELECT " + SQLiteManager.COUNTER_STORY + "," +SQLiteManager.ID_STORY + "," + SQLiteManager.TITLE_STORY + ", " +
        SQLiteManager.DESC_STORY + ", " +SQLiteManager.DATE_STORY + ", " + SQLiteManager.STORY_STATUS + ", "
                + SQLiteManager.DELETED_STORY + "," + SQLiteManager.USERNAME_USERS_STORY + " FROM " + SQLiteManager.TABLE_STORY + " inner join "
                + SQLiteManager.USERS + " on " +
                SQLiteManager.USERNAME_USERS_STORY + " =" + SQLiteManager.USERNAME
                + " where " + SQLiteManager.USERNAME_USERS_STORY + " =?" + " order by " + SQLiteManager.ID_STORY, new String[]{SQLiteManager.USER_REMEMBER});

        tv.setText("");

        while (cursor.moveToNext())
        {
            int id = cursor.getInt(1);
            String title = cursor.getString(2);
            String desc = cursor.getString(3);
            String date = cursor.getString(4);
            String status = cursor.getString(5);
            String stringDeleted = cursor.getString(6);

            tv.append(" ID:" + id + "\n Название: " + title + "\n Описание: " + desc + "\n Дата пользователя: " + date + "\n "+ status + "\n "+ stringDeleted
                    +"\n" + "                                                                                           \uD83D\uDCC3\uD83D\uDD8A" + "\n");

        }
        cursor.close();
    }



}