package com.example.note_prob22;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.note_prob22.classes.Record;
import com.example.note_prob22.classes.SmileyEvent;
import com.example.note_prob22.db.SQLiteManager;
import com.github.jinatonic.confetti.CommonConfetti;

import java.util.ArrayList;
import java.util.List;



public class SmilesActivity extends AppCompatActivity {

    public static int selectSmile = 0;
    public static String selectSmilePicture = "+";

    public static List<SmileyEvent> smileyEvents = new ArrayList<>();
    private int currentSmile =0;
    private String currentSmilePicture = "+";

    private Record selectedSmileRecord;
    SQLiteManager sqDB;
    private Button btn1_alarming, btn2_verysad, btn3_sad, btn4_neutral, btn5_angry, btn6_happy, btn7_surprised, btn8_varyhappy, btn9_besthappy, btn_ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smiles);
        initwg();





        sqDB = new SQLiteManager(this);

    }



    private void initwg() {
        btn1_alarming = findViewById(R.id.btn1_alarming);
        btn2_verysad = findViewById(R.id.btn2_verysad);
        btn3_sad = findViewById(R.id.btn3_sad);
        btn4_neutral = findViewById(R.id.btn4_neutral);
        btn5_angry = findViewById(R.id.btn5_angry);
        btn6_happy = findViewById(R.id.btn6_happy);
        btn7_surprised = findViewById(R.id.btn7_surprised);
        btn8_varyhappy = findViewById(R.id.btn8_varyhappy);
        btn9_besthappy = findViewById(R.id.btn9_besthappy);



        btn_ok = findViewById(R.id.btn_ok);

    }

    public void getBtn1_alarming(View view) {

        btn1_alarming.setEnabled(false);

        btn2_verysad.setEnabled(true);
        btn3_sad.setEnabled(true);
        btn4_neutral.setEnabled(true);
        btn5_angry.setEnabled(true);
        btn6_happy.setEnabled(true);
        btn7_surprised.setEnabled(true);
        btn8_varyhappy.setEnabled(true);
        btn9_besthappy.setEnabled(true);


        currentSmile = 1;

        currentSmilePicture = btn1_alarming.getText().toString();
//        Log.d("--Help--", ""+currentSmile+ "" + currentSmilePicture);

    }


    public void getBtn2_verysad(View view) {

        btn2_verysad.setEnabled(false);

        btn1_alarming.setEnabled(true);
        btn3_sad.setEnabled(true);
        btn4_neutral.setEnabled(true);
        btn5_angry.setEnabled(true);
        btn6_happy.setEnabled(true);
        btn7_surprised.setEnabled(true);
        btn8_varyhappy.setEnabled(true);
        btn9_besthappy.setEnabled(true);


        currentSmile = 2;
//        Log.d("--Help--", ""+currentSmile);
        currentSmilePicture = btn2_verysad.getText().toString();

    }


    public void getBtn3_sad(View view) {
        btn3_sad.setEnabled(false);
        btn2_verysad.setEnabled(true);
        btn1_alarming.setEnabled(true);
        btn4_neutral.setEnabled(true);
        btn5_angry.setEnabled(true);
        btn6_happy.setEnabled(true);
        btn7_surprised.setEnabled(true);
        btn8_varyhappy.setEnabled(true);
        btn9_besthappy.setEnabled(true);


        currentSmile = 3;
//        Log.d("--Help--", ""+currentSmile);
        currentSmilePicture = btn3_sad.getText().toString();
    }


    public void getBtn4_neutral(View view) {

        btn1_alarming.setEnabled(true);
        btn2_verysad.setEnabled(true);
        btn3_sad.setEnabled(true);
        btn4_neutral.setEnabled(false);
        btn5_angry.setEnabled(true);
        btn6_happy.setEnabled(true);
        btn7_surprised.setEnabled(true);
        btn8_varyhappy.setEnabled(true);
        btn9_besthappy.setEnabled(true);


        currentSmile = 4;
//        Log.d("--Help--", ""+currentSmile);
        currentSmilePicture = btn4_neutral.getText().toString();
    }

    public void getBtn5_angry(View view) {

        btn1_alarming.setEnabled(true);
        btn2_verysad.setEnabled(true);
        btn3_sad.setEnabled(true);
        btn4_neutral.setEnabled(true);
        btn5_angry.setEnabled(false);
        btn6_happy.setEnabled(true);
        btn7_surprised.setEnabled(true);
        btn8_varyhappy.setEnabled(true);
        btn9_besthappy.setEnabled(true);


        currentSmile = 5;
//        Log.d("--Help--", ""+currentSmile);
        currentSmilePicture = btn5_angry.getText().toString();
    }

    public void getbtn6_happy(View view) {

        btn1_alarming.setEnabled(true);
        btn2_verysad.setEnabled(true);
        btn3_sad.setEnabled(true);
        btn4_neutral.setEnabled(true);
        btn5_angry.setEnabled(true);
        btn6_happy.setEnabled(false);
        btn7_surprised.setEnabled(true);
        btn8_varyhappy.setEnabled(true);
        btn9_besthappy.setEnabled(true);


        currentSmile = 6;
//        Log.d("--Help--", ""+currentSmile);
        currentSmilePicture = btn6_happy.getText().toString();
    }
    public void getbtn7_surprised(View view) {

        btn1_alarming.setEnabled(true);
        btn2_verysad.setEnabled(true);
        btn3_sad.setEnabled(true);
        btn4_neutral.setEnabled(true);
        btn5_angry.setEnabled(true);
        btn6_happy.setEnabled(true);
        btn7_surprised.setEnabled(false);
        btn8_varyhappy.setEnabled(true);
        btn9_besthappy.setEnabled(true);


        currentSmile = 7;
//        Log.d("--Help--", ""+currentSmile);
        currentSmilePicture = btn7_surprised.getText().toString();
    }

    public void getbtn8_varyhappy(View view) {

        btn1_alarming.setEnabled(true);
        btn2_verysad.setEnabled(true);
        btn3_sad.setEnabled(true);
        btn4_neutral.setEnabled(true);
        btn5_angry.setEnabled(true);
        btn6_happy.setEnabled(true);
        btn7_surprised.setEnabled(true);
        btn8_varyhappy.setEnabled(false);
        btn9_besthappy.setEnabled(true);


        currentSmile = 8;
//        Log.d("--Help--", ""+currentSmile);
        currentSmilePicture = btn8_varyhappy.getText().toString();
    }
    public void getbtn9_besthappy(View view) {

        btn1_alarming.setEnabled(true);
        btn2_verysad.setEnabled(true);
        btn3_sad.setEnabled(true);
        btn4_neutral.setEnabled(true);
        btn5_angry.setEnabled(true);
        btn6_happy.setEnabled(true);
        btn7_surprised.setEnabled(true);
        btn8_varyhappy.setEnabled(true);
        btn9_besthappy.setEnabled(false);




        final View confettiContainer = findViewById(R.id.confetti_container);
        CommonConfetti.rainingConfetti((ViewGroup) confettiContainer, new int[] {Color.RED, Color.GREEN, Color.BLUE})
                .infinite();

        currentSmile = 9;
//        Log.d("--Help--", ""+currentSmile);
        currentSmilePicture = btn9_besthappy.getText().toString();




    }

    public void btnOk(View view) {

        selectSmile = currentSmile;
        selectSmilePicture = currentSmilePicture;






        finish();


    }



}