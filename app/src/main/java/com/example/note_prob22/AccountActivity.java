package com.example.note_prob22;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AccountActivity extends AppCompatActivity {
    SQLiteManager sqLiteManager;
    SQLiteDatabase db;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        Button button = findViewById(R.id.button);
        final View confettiContainer = findViewById(R.id.confetti_container);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                CommonConfetti.rainingConfetti((ViewGroup) confettiContainer, new int[] {Color.RED, Color.GREEN, Color.BLUE})
                        .infinite();





            }
        });





        sqLiteManager = new SQLiteManager(this);
        db = sqLiteManager.getWritableDatabase();
        cursor = db.query("Users", null, null, null, null, null, null);

        TextView login = (TextView) findViewById(R.id.login);
        TextView pass = (TextView) findViewById(R.id.password);
        login.append(SQLiteManager.USER_REMEMBER);
        pass.append(SQLiteManager.PASS_REMEMBER);

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