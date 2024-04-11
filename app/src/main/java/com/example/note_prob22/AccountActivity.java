package com.example.note_prob22;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.net.Uri;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiView;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class AccountActivity extends AppCompatActivity {
    SQLiteManager sqLiteManager;
    SQLiteDatabase db;
    Cursor cursor;
    Button insertButton;
    EditText x;
    EditText y;
    GraphView graphView;
    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[0]);

    //    private LineGraphSeries<DataPoint> series2;
    //private XYPlot plot;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        // PanZoom.attach(plot, PanZoom.Pan.HORIZONTAL, PanZoom.Zoom.STRETCH_HORIZONTAL);
//        Button button = findViewById(R.id.button);
//        final View confettiContainer = findViewById(R.id.confetti_container);


        sqLiteManager = new SQLiteManager(this);
        db = sqLiteManager.getWritableDatabase();
        cursor = db.query("Users", null, null, null, null, null, null);
        //  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", new Locale("ru"));
        TextView login = findViewById(R.id.login);
        TextView pass = findViewById(R.id.password);
     //   GraphView graph = findViewById(R.id.graph);
     //   Button btnAddDate = findViewById(R.id.btnAddDate);
        insertButton = findViewById(R.id.btnAddDate);

        // showGraph( graph ,sdf);
        graphView = findViewById(R.id.graph);
        exqInsert();
        graphView.addSeries(series);



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


        String dateStringNew = "24 февр. 2024";


//        btnAddDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                try {
//                    Date dateNew = sdf.parse(dateStringNew);
//                    addDataPoint(graph, dateNew);
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        });






    }

    private void exqInsert() {
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int xVal =9;
                int yVal = 2;

                sqLiteManager.insertDataGraph(xVal, yVal);
                series.resetData(getDataPoint());



            }
        });
    }

//    private DataPoint[] getDataPoint() {
//        String[] columns= {"xValues", "yValues"};
//        Cursor cursor1 = db.query("myTable",columns,null,null,null,null,null);
//        DataPoint[] dp = new DataPoint[cursor1.getCount()];
//        for (int i=0;i<cursor1.getCount();i++){
//            cursor.moveToNext();
//            dp[i]= new DataPoint(cursor1.getInt(0), cursor1.getInt(1));
//        }
//        return dp;
//    }

    private DataPoint[] getDataPoint() {
        String[] columns = {"xValues", "yValues"};
        Cursor cursor1 = db.query("myTable", columns, null, null, null, null, null);
        DataPoint[] dp = new DataPoint[cursor1.getCount()];

        if (cursor1.moveToFirst()) {
            int xIndex = cursor1.getColumnIndex("xValues");
            int yIndex = cursor1.getColumnIndex("yValues");

            for (int i = 0; i < cursor1.getCount(); i++) {
                dp[i] = new DataPoint(cursor1.getInt(xIndex), cursor1.getInt(yIndex));
                cursor1.moveToNext();
            }
        }

        cursor1.close(); // Не забудьте закрыть курсор после использования

        return dp;
    }



//    private void showGraph(GraphView graph, SimpleDateFormat sdf){
////        String dateString1 = "02.02.2024";
////        String dateString2 = "03.02.2024";
////        String dateString3 = "08.02.2024";
////        String dateString4 = "18.02.2024";
////        String dateString5 = "19.02.2024";
////        String dateString6 = "21.02.2024";
////        String dateString7 = "22.02.2024";
//
//        String dateString1 = "02 февр. 2024";
//        String dateString2 = "03 февр. 2024";
//        String dateString3 = "08 февр. 2024";
//        String dateString4 = "18 февр. 2024";
//        String dateString5 = "19 февр. 2024";
//        String dateString6 = "21 февр. 2024";
//        String dateString7 = "22 февр. 2024";
//
//
//        try {
//            Date d1 = sdf.parse(dateString1);
//            Date d2 = sdf.parse(dateString2);
//            Date d3 = sdf.parse(dateString3);
//            Date d4 = sdf.parse(dateString4);
//            Date d5 = sdf.parse(dateString5);
//            Date d6 = sdf.parse(dateString6);
//            Date d7 = sdf.parse(dateString7);
//
//
////            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
////                    new DataPoint(d1, 5),
////                    new DataPoint(d2, 9),
////                    new DataPoint(d3, 3),
////                    new DataPoint(d4, 8),
////                    new DataPoint(d5, 8),
////                    new DataPoint(d6, 8),
////                    new DataPoint(d7, 8),
////                    //  new DataPoint(dateNow, 8)
////
////            });
//            series2 = new LineGraphSeries<>(new DataPoint[]{
//                    new DataPoint(d1, 5),
//                    new DataPoint(d2, 9),
//                    new DataPoint(d3, 3),
//                    new DataPoint(d4, 8),
//                    new DataPoint(d5, 8),
//                    new DataPoint(d6, 8),
//                    new DataPoint(d7, 8),
//                    //  new DataPoint(dateNow, 8)
//
//            });
//
//            // graph.addSeries(series);
//            graph.addSeries(series2);
//            graph.getViewport().setXAxisBoundsManual(true);
//            graph.getViewport().setMinX(d1.getTime());
//            graph.getViewport().setMaxX(d7.getTime());
//
//            graph.getViewport().setYAxisBoundsManual(true);
//            graph.getViewport().setMinY(1);
//            graph.getViewport().setMaxY(9);
//
//            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
//            graph.getGridLabelRenderer().setHumanRounding(false);
//            graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
//
//
//            graph.getViewport().setScrollable(true);
//
//
//
//            graph.getViewport().setScalable(true) ;
//
//// styling
////            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
////                @Override
////                public int get(DataPoint data) {
////                    return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
////                }
////            });
////
////            series.setSpacing(10);
////
//// draw values on top
////            series.setDrawValuesOnTop(true);
////           series.setValuesOnTopColor(Color.RED);
//
//
//
//
//
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void addDataPoint(GraphView graph, Date date){
//      series2.appendData(new DataPoint(date, 8), true, 100);
//     //   graph.getViewport().setMinX(d1.getTime()-100);
//
//        graph.getViewport().setMaxX(date.getTime()+100);
//
//
//    }


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