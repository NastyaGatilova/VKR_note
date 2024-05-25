package com.example.note_prob22;

import static com.example.note_prob22.SmilesActivity.smileyEvents;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.note_prob22.classes.Note;
import com.example.note_prob22.classes.SmileyEvent;
import com.example.note_prob22.db.SQLiteManager;
import com.example.note_prob22.graph.GraphViewPagerAdapter;
import com.example.note_prob22.graph.HourGraphViewHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {
    public static ArrayList<String> mondayHappyList = new ArrayList<>();
    public static ArrayList<String> tuesdayHappyList = new ArrayList<>();
    public static ArrayList<String> wednesdayHappyList = new ArrayList<>();
    public static ArrayList<String> thursdayHappyList = new ArrayList<>();
    public static ArrayList<String> fridayHappyList = new ArrayList<>();
    public static ArrayList<String> saturdayHappyList = new ArrayList<>();
    public static ArrayList<String> sundayHappyList = new ArrayList<>();
    SQLiteManager sqLiteManager;
    SQLiteDatabase db;
    Cursor cursor;

    TextView happyHour;
    SharedPreferences sharedPreferences;

    private String averageHappyTimeInDay = "";

    private String averageHappyTimeMonday = "";
    private String averageHappyTimeTuesday = "";
    private String averageHappyTimeWednesday = "";
    private String averageHappyTimeThursday = "";
    private String averageHappyTimeFriday= "";
    private String averageHappyTimeSaturday= "";
    private String averageHappyTimeSunday = "";





    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sharedPreferences = this.getSharedPreferences("Note", Context.MODE_PRIVATE);

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


        LocalDate today = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            today = LocalDate.now();
            DayOfWeek dayOfWeek = today.getDayOfWeek();
            int dayOfWeekNumber = dayOfWeek.getValue();
            searchAverageHappytimeDay(dayOfWeekNumber);
        }


    }



    private void searchAverageHappytimeDay(int day){
        SharedPreferences sharedPreferences = getSharedPreferences("Note", Context.MODE_PRIVATE);

        switch (day){
            case 1:{
                String jsonRecords = sharedPreferences.getString("mondayHappyList", null);
                if (jsonRecords != null) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                    ArrayList<String> sunday = gson.fromJson(jsonRecords, listType);

                    averageHappyTimeMonday = setAverageTime(sunday);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("averageHappyTimeMonday", averageHappyTimeMonday);
                    editor.apply();
//                    for (int i = 0; i < sunday.size(); i++) {
//                        Log.d("--Help--", "mondayHappyList список = " + i + " " +sunday.get(i));
//                    }
                } else {
                    Log.d("--Help--", "Ошибка Проверки списка  " + day  );
                }
                break;
            }
            case 2:{
                String jsonRecords = sharedPreferences.getString("tuesdayHappyList", null);
                if (jsonRecords != null) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                    ArrayList<String> sunday = gson.fromJson(jsonRecords, listType);

                    averageHappyTimeTuesday = setAverageTime(sunday);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("averageHappyTimeTuesday", averageHappyTimeTuesday);
                    editor.apply();
//                    for (int i = 0; i < sunday.size(); i++) {
//                        Log.d("--Help--", "tuesdayHappyList список = " + i + " " +sunday.get(i));
//                    }
                } else {
                    Log.d("--Help--", "Ошибка Проверки списка  " + day  );
                }
                break;
            }
            case 3:{
                String jsonRecords = sharedPreferences.getString("wednesdayHappyList", null);
                if (jsonRecords != null) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                    ArrayList<String> sunday = gson.fromJson(jsonRecords, listType);

                    averageHappyTimeWednesday = setAverageTime(sunday);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("averageHappyTimeWednesday", averageHappyTimeWednesday);
                    editor.apply();
//                    for (int i = 0; i < sunday.size(); i++) {
//                        Log.d("--Help--", "wednesdayHappyList список = " + i + " " +sunday.get(i));
//                    }
                } else {
                    Log.d("--Help--", "Ошибка Проверки списка  " + day  );
                }
                break;
            }
            case 4:{
                String jsonRecords = sharedPreferences.getString("thursdayHappyList", null);
                if (jsonRecords != null) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                    ArrayList<String> sunday = gson.fromJson(jsonRecords, listType);

                    averageHappyTimeThursday = setAverageTime(sunday);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("averageHappyTimeThursday", averageHappyTimeThursday);
                    editor.apply();
//                    for (int i = 0; i < sunday.size(); i++) {
//                        Log.d("--Help--", "thursdayHappyList список = " + i + " " +sunday.get(i));
//                    }
                } else {
                    Log.d("--Help--", "Ошибка Проверки списка  " + day  );
                }
                break;
            }
            case 5:{
                String jsonRecords = sharedPreferences.getString("fridayHappyList", null);
                if (jsonRecords != null) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                    ArrayList<String> sunday = gson.fromJson(jsonRecords, listType);

                    averageHappyTimeFriday = setAverageTime(sunday);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("averageHappyTimeFriday", averageHappyTimeFriday);
                    editor.apply();
//                    for (int i = 0; i < sunday.size(); i++) {
//                        Log.d("--Help--", "fridayHappyList список = " + i + " " +sunday.get(i));
//                    }
                } else {
                    Log.d("--Help--", "Ошибка Проверки списка  " + day  );
                }
                break;
            }
            case 6:{
                String jsonRecords = sharedPreferences.getString("saturdayHappyList", null);
                if (jsonRecords != null) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                    ArrayList<String> sunday = gson.fromJson(jsonRecords, listType);

                    averageHappyTimeSaturday= setAverageTime(sunday);
//                    for (int i = 0; i < sunday.size(); i++) {
//                        Log.d("--Help--", "saturdayHappyList список = " + i + " " +sunday.get(i));
//                    }

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("averageHappyTimeSaturday", averageHappyTimeSaturday);
                    editor.apply();
                    Log.w("--Help--", "averageHappyTimeSaturday  = " + averageHappyTimeSaturday);
                } else {
                    Log.d("--Help--", "Ошибка Проверки списка  " + day  );
                }
                //убрать
                String mon = sharedPreferences.getString("averageHappyTimeMonday", null);
                String tue = sharedPreferences.getString("averageHappyTimeTuesday", null);
                String wen = sharedPreferences.getString("averageHappyTimeWednesday", null);
                String th = sharedPreferences.getString("averageHappyTimeThursday", null);
                String fr = sharedPreferences.getString("averageHappyTimeFriday", null);
                String sat = sharedPreferences.getString("averageHappyTimeSaturday", null);
                String sun = sharedPreferences.getString("averageHappyTimeSunday", null);
                ArrayList<String> happylist = new ArrayList<>();
                if (mon != null) happylist.add(mon);
                if (tue != null) happylist.add(tue);
                if (wen != null) happylist.add(wen);
                if (th != null) happylist.add(th);
                if (fr != null) happylist.add(fr);
                if (sat != null) happylist.add(sat);
                if (sun != null) happylist.add(sun);

                String happy = setAverageTime(happylist);
                Log.w("--Help--", "HappyHour=" +happy);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault());
                try {
                    Date date = dateFormat.parse(happy);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24-часовой формат
                    int minute = calendar.get(Calendar.MINUTE);

                    happyHour.setText("Ваш счастливый час - "+ hour +":" + minute);

                } catch (ParseException e) {
                    e.printStackTrace();
                }



                break;
            }
            case 7:{
                String jsonRecords = sharedPreferences.getString("sundayHappyList", null);
                if (jsonRecords != null) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                    ArrayList<String> sunday = gson.fromJson(jsonRecords, listType);

                    averageHappyTimeSunday = setAverageTime(sunday);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("averageHappyTimeSunday", averageHappyTimeSunday);
                    editor.apply();

                } else {
                    Log.d("--Help--", "Ошибка Проверки списка  " + day  );
                }

                String mon = sharedPreferences.getString("averageHappyTimeMonday", null);
                String tue = sharedPreferences.getString("averageHappyTimeTuesday", null);
                String wen = sharedPreferences.getString("averageHappyTimeWednesday", null);
                String th = sharedPreferences.getString("averageHappyTimeThursday", null);
                String fr = sharedPreferences.getString("averageHappyTimeFriday", null);
                String sat = sharedPreferences.getString("averageHappyTimeSaturday", null);
                String sun = sharedPreferences.getString("averageHappyTimeSunday", null);
                ArrayList<String> happylist = new ArrayList<>();
                if (mon != null) happylist.add(mon);
                if (tue != null) happylist.add(tue);
                if (wen != null) happylist.add(wen);
                if (th != null) happylist.add(th);
                if (fr != null) happylist.add(fr);
                if (sat != null) happylist.add(sat);
                if (sun != null) happylist.add(sun);

                String happy = setAverageTime(happylist);
                Log.w("--Help--", "HappyHour=" +happy);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault());
                try {
                    Date date = dateFormat.parse(happy);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24-часовой формат
                    int minute = calendar.get(Calendar.MINUTE);

                    happyHour.setText("Ваш счастливый час - "+ hour +":" + minute);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            }
            default: Log.d("--Help--", "Ошибка"  );
        }



    }

    private long calculateAverageTime(ArrayList<String> list) {
        long[] timeArray = convertTimeListToLongArray(list);
        long sum = 0;
        for (int i = 0; i < timeArray.length; i++) {
            sum += timeArray[i];
        }
        return sum / timeArray.length;
    }

    private String setAverageTime(ArrayList<String> list) {
        long averageTimeInMillis = calculateAverageTime(list);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
        Date date = new Date(averageTimeInMillis);
        averageHappyTimeInDay = dateFormat.format(date);
        Log.w("--Help--", "Cр время = "+averageHappyTimeInDay );
        return averageHappyTimeInDay;
    }

    private long[] convertTimeListToLongArray(ArrayList<String> listDay) {
        long[] timeArray = new long[listDay.size()];
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
        for (int i = 0; i < listDay.size(); i++) {
            try {
                Date date = dateFormat.parse(listDay.get(i));
                timeArray[i] = date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return timeArray;
    }




    public void click_del_users(View view) {

        androidx.appcompat.app.AlertDialog.Builder a_builder = new androidx.appcompat.app.AlertDialog.Builder(AccountActivity.this);
        a_builder.setMessage("Хотите выйти из аккаунта?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        userLoggedOut();
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
    public void userLoggedOut() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("isLoggedIn");
        editor.remove("UserRemember");
        editor.remove("PassRemember");
        SQLiteManager.USER_REMEMBER = "";
        SQLiteManager.PASS_REMEMBER = "";
        editor.apply();
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