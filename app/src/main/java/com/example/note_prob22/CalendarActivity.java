package com.example.note_prob22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.note_prob22.adapters.NoteAdapterForCalendar;
import com.example.note_prob22.classes.Note;
import com.example.note_prob22.db.SQLiteManager;
import com.example.note_prob22.graph.GraphViewHelper;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {
    SQLiteManager sqLiteManager;

    TextView mostUsedSmile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        RecyclerView recyclerView = findViewById(R.id.rcView);
        sqLiteManager = new SQLiteManager(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NoteAdapterForCalendar adapter = new NoteAdapterForCalendar(Note.noteArrayListWithForCalendar);

        CalendarView calendarView = findViewById(R.id.calendarView);
         mostUsedSmile = findViewById(R.id.mostUsedSmile);



//        if (sqLiteManager.checkLastWeekQuery()){
//            graphView.setVisibility(View.VISIBLE);
//            noData.setVisibility(View.GONE);
//            graphView.setTitle("Cтатистика за неделю \uD83C\uDF25");
//
//            graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(requireContext()));
//            GraphViewHelper.fillGraphViewWithData(graphView, sqLiteManager.get7UniqRecordFromDb());
//
//        }
//        else{
//            graphView.setVisibility(View.GONE);
//            noData.setVisibility(View.VISIBLE);
//
//        }

        mostUsedSmile.setText( sqLiteManager.getMostUsedSmiley());


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                String date = sdf.format(new GregorianCalendar(year, month, dayOfMonth).getTime());


                sqLiteManager.populateNoteListArrayForCalendar(date);
                sqLiteManager.showSmileForDate(date);

                recyclerView.setAdapter(adapter);
               // Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
            }
        });











    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sqLiteManager.checkLastWeekQuery()){

                    showPopupWindow(mostUsedSmile);
                }


            }
        }, 1000);

    }

    private void showPopupWindow(TextView mostUsedSmile) {
        PopupWindow popupWindow = new PopupWindow(this);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_layout, null);
        TextView tv = popupView.findViewById(R.id.smileMost);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7); // Вычитаем 7 дней из текущей даты
        Date oneWeekAgo = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String oneWeekAgoString = dateFormat.format(oneWeekAgo);

        Date currentDate = new Date();
        String dateNow = dateFormat.format(currentDate);
        String srt = "Вы испытали эту эмоцию чаще всего за\n" + oneWeekAgoString + " —\t " + dateNow;
        tv.setText(srt);
        popupWindow.setContentView(popupView);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        final int[] textViewPosition = new int[2];
        mostUsedSmile.getLocationOnScreen(textViewPosition);

        popupWindow.showAtLocation(mostUsedSmile, Gravity.END, 100, -850);



        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }




}