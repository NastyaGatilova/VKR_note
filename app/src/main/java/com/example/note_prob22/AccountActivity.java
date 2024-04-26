package com.example.note_prob22;

import static com.example.note_prob22.SmilesActivity.smileyEvents;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.note_prob22.classes.SmileyEvent;
import com.example.note_prob22.db.SQLiteManager;
import com.example.note_prob22.graph.GraphViewPagerAdapter;
import com.example.note_prob22.graph.HourGraphViewHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {
    SQLiteManager sqLiteManager;
    SQLiteDatabase db;
    Cursor cursor;

    TextView happyHour;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        TextView login = findViewById(R.id.login);
        TextView pass = findViewById(R.id.password);

        GraphView graphView = findViewById(R.id.graph);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);

        happyHour = findViewById(R.id.happyHour);



        sqLiteManager = new SQLiteManager(this);
        db = sqLiteManager.getWritableDatabase();
        cursor = db.query("Users", null, null, null, null, null, null);
        //  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");

        GraphViewPagerAdapter graphViewPagerAdapter = new GraphViewPagerAdapter(this);
        viewPager2.setAdapter(graphViewPagerAdapter);

       // viewPager2.setUserInputEnabled(false);






        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter((this)));
        HourGraphViewHelper.fillGraphViewWithHour(graphView, sqLiteManager.getTimeAndSmileForGrafikHours());


        findHappiestHour();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                builder.setTitle("Логин");
                builder.setMessage(SQLiteManager.USER_REMEMBER);
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                builder.setTitle("Пароль");
                builder.setMessage(SQLiteManager.PASS_REMEMBER);
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        });


    }



    public void click_del_users(View view) {

        androidx.appcompat.app.AlertDialog.Builder a_builder = new androidx.appcompat.app.AlertDialog.Builder(AccountActivity.this);
        a_builder.setMessage("Вы хотите удалить свой аккаунт?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        db.delete(SQLiteManager.USERS, SQLiteManager.USERNAME + " =? ", new String[]{SQLiteManager.USER_REMEMBER});
                        db.delete(SQLiteManager.TABLE_NAME_NOTE, SQLiteManager.USERNAME_USERS + " =? ", new String[]{SQLiteManager.USER_REMEMBER});
                        db.delete(SQLiteManager.TABLE_STORY, SQLiteManager.USERNAME_USERS_STORY + " =? ", new String[]{SQLiteManager.USER_REMEMBER});
                        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        androidx.appcompat.app.AlertDialog alert = a_builder.create();
        alert.setTitle("\uD83D\uDE22⛔");
        alert.show();


    }


    private void findHappiestHour() {
        Map<Integer, Integer> hourCountMap = countSmileyEventsPerHour(smileyEvents);

        int maxCount = 0;
        int happiestHour = -1;
        for (Map.Entry<Integer, Integer> entry : hourCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                happiestHour = entry.getKey();
            }
        }

        if (happiestHour != -1) {
            // Log.d("--Help--", "The happiest hour is: " + happiestHour);
            happyHour.setText("\uD83E\uDD29 Ваш счастливый час —\t "+ happiestHour + "ч.");
        }
    }

    private Map<Integer, Integer> countSmileyEventsPerHour(List<SmileyEvent> smileyEvents) {
        Map<Integer, Integer> hourCountMap = new HashMap<>();
        for (int i = 0; i < 24; i++) {
            hourCountMap.put(i, 0);
        }

        Calendar calendar = Calendar.getInstance();
        for (SmileyEvent event : smileyEvents) {
            calendar.setTimeInMillis(event.getTimestamp());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            hourCountMap.put(hour, hourCountMap.get(hour) + 1);
        }

        return hourCountMap;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
        cursor.close();
    }
}