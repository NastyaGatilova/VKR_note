package com.example.note_prob22;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button signin;
    SQLiteManager  DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username= findViewById(R.id.username1);
        password= findViewById(R.id.password1);
        signin = findViewById(R.id.signin1);
        DB = new SQLiteManager(this);

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
}