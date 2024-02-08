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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;

public class AccountActivity extends AppCompatActivity {
    SQLiteManager sqLiteManager;
    SQLiteDatabase db;
    Cursor cursor;

    private XYPlot plot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        plot = (XYPlot) findViewById(R.id.plot);
//        Button button = findViewById(R.id.button);
//        final View confettiContainer = findViewById(R.id.confetti_container);







        sqLiteManager = new SQLiteManager(this);
        db = sqLiteManager.getWritableDatabase();
        cursor = db.query("Users", null, null, null, null, null, null);

        TextView login = (TextView) findViewById(R.id.login);
        TextView pass = (TextView) findViewById(R.id.password);



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








        ///график;





        final Number[] domainLabels = {0,1,2,3,4,5,6,7,8,9,10};
        Number[] series1Numbers = {0,1, 4, 2, 8, 4, 16}; //зеленая шкала


        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");

        // создать средства форматирования для рисования серии с помощью LineAndPointRenderer
        // и настраиваем их из xml:
        LineAndPointFormatter series1Format =
                new LineAndPointFormatter(this, R.xml.line_point_formatter_with_labels);


        // просто ради интереса добавьте немного сглаживания к линиям:
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));


        // добавить новую серию в xyplot:
        plot.addSeries(series1, series1Format);


        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(domainLabels[i]);
            }
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });
    }














    public  void  click_del_users(View view){

        androidx.appcompat.app.AlertDialog.Builder a_builder = new androidx.appcompat.app.AlertDialog.Builder(AccountActivity.this);
        a_builder.setMessage("Вы хотите удалить свой аккаунт?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        db.delete(SQLiteManager.USERS,SQLiteManager.USERNAME + " =? ", new String[]{SQLiteManager.USER_REMEMBER});
                        db.delete(SQLiteManager.TABLE_NAME_NOTE, SQLiteManager.USERNAME_USERS + " =? ",new String[]{SQLiteManager.USER_REMEMBER});
                        db.delete(SQLiteManager.TABLE_STORY,SQLiteManager.USERNAME_USERS_STORY + " =? ", new String[]{SQLiteManager.USER_REMEMBER});
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
    public void onDestroy(){
        super.onDestroy();
        db.close();
        cursor.close();
    }
}