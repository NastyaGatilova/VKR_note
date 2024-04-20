package com.example.note_prob22;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.note_prob22.graph.GraphViewPagerAdapter;
import com.example.note_prob22.graph.HourCustomLabelFormatter;
import com.example.note_prob22.graph.HourGraphViewHelper;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AccountActivity extends AppCompatActivity {
    SQLiteManager sqLiteManager;
    SQLiteDatabase db;
    Cursor cursor;
    Button btnChoosePeriod;

    //  private LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[0]);

    //    private LineGraphSeries<DataPoint> series2;
    //private XYPlot plot;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        // PanZoom.attach(plot, PanZoom.Pan.HORIZONTAL, PanZoom.Zoom.STRETCH_HORIZONTAL);
//        Button button = findViewById(R.id.button);
//        final View confettiContainer = findViewById(R.id.confetti_container);

        TextView login = findViewById(R.id.login);
        TextView pass = findViewById(R.id.password);

        GraphView graphView = findViewById(R.id.graph);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);



        sqLiteManager = new SQLiteManager(this);
        db = sqLiteManager.getWritableDatabase();
        cursor = db.query("Users", null, null, null, null, null, null);
        //  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");

        GraphViewPagerAdapter graphViewPagerAdapter = new GraphViewPagerAdapter(this);
        viewPager2.setAdapter(graphViewPagerAdapter);

       // viewPager2.setUserInputEnabled(false);


        if (sqLiteManager.isTableEmpty()){

            graphView.setVisibility(View.GONE);

        }
        else{

            graphView.setVisibility(View.VISIBLE);


          graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter((this)));
          HourGraphViewHelper.fillGraphViewWithHour(graphView, sqLiteManager.getTimeAndSmileForGrafikHours());



        }




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


//        PopupMenu popupMenu2 = new PopupMenu(this,btnChoosePeriod);
//        popupMenu2.getMenuInflater().inflate(R.menu.popup_menu, popupMenu2.getMenu());
//        popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.week:
//                       // graphView.removeAllSeries();
//                       graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getBaseContext()));
//                        GraphViewHelper.fillGraphViewWithData(graphView,  sqLiteManager.dateAndSmileFromTableRecordForGrafikLastWeek());
//                        return true;
//                    case R.id.month:
////                        graphView.removeAllSeries();
//                        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getBaseContext()));
//                       GraphViewHelper.fillGraphViewWithData(graphView,  sqLiteManager.dateAndSmileFromTableRecordForGrafikLastMounth());
//                        return true;
//                    case R.id.year:
////                        graphView.removeAllSeries();
//                       GraphViewHelper.fillGraphViewWithData(graphView,  sqLiteManager.dateAndSmileFromTableRecordForGrafik());
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//        });
//
//
//
//        btnChoosePeriod.setOnClickListener(v -> popupMenu2.show());




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


    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
        cursor.close();
    }
}