package com.example.note_prob22;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note_prob22.db.SQLiteManager;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button signin;
    SQLiteManager DB;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = this.getSharedPreferences("Note", Context.MODE_PRIVATE);
        username= findViewById(R.id.username1);
        password= findViewById(R.id.password1);
        signin = findViewById(R.id.signin1);
        DB = new SQLiteManager(this);

        checkIsUserLoggedIn();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass))
                    Toast.makeText(LoginActivity.this, "Все поля обязательные для заполнения", Toast.LENGTH_LONG).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user,pass);
                    if (checkuserpass == true){


                        SQLiteManager.USER_REMEMBER = user;
                        SQLiteManager.PASS_REMEMBER = pass;

                        Toast.makeText(LoginActivity.this, "Успешно", Toast.LENGTH_LONG).show();
                        rememberUserLoggedIn(user, pass);
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);

                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "Не успешно", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
    public void rememberUserLoggedIn(String userRemember, String passRemember) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("UserRemember", userRemember);
        editor.putString("PassRemember", passRemember);
        editor.apply();
    }

    public void checkIsUserLoggedIn() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            SQLiteManager.USER_REMEMBER = sharedPreferences.getString("UserRemember", "DefaultValue");
            SQLiteManager.PASS_REMEMBER = sharedPreferences.getString("PassRemember", "DefaultValue");
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}