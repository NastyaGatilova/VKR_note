package com.example.note_prob22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note_prob22.db.SQLiteManager;

public class MainActivity extends AppCompatActivity {

    EditText username, password, repassword;
    Button signin, signup;
    SQLiteManager DB;
    public static String USER_REG = "";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("Note", Context.MODE_PRIVATE);


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        DB = new SQLiteManager(this);

        DB.showUsers();

       checkIsUserLoggedIn();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) )
                    Toast.makeText(MainActivity.this, "Все поля обязательные для заполнения", Toast.LENGTH_LONG).show();

            else if (pass.length() >= 5) {
                    if (pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if (checkuser == false){
                            Boolean insert = DB.insertData(user,pass);
                            if (insert == true ){


                                SQLiteManager.USER_REMEMBER = user;
                                SQLiteManager.PASS_REMEMBER = pass;



                                Toast.makeText(MainActivity.this, "Вы зарегистрировались успешно", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                                 rememberUserLoggedIn(user,pass);
                                finish();


                            }else {
                                Toast.makeText(MainActivity.this, "Регистрация НЕ прошла", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Такой пользователь уже существует", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "Пароль не совпадает", Toast.LENGTH_LONG).show();
                    }
                }else
                    Toast.makeText(MainActivity.this, "Пароль должен содержать больше 5 символов!", Toast.LENGTH_LONG).show();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class );
                startActivity(intent);
                finish();
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