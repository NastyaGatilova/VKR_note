package com.example.note_prob22;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        DB = new SQLiteManager(this);

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
}