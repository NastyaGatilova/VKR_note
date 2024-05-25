package com.example.note_prob22.graph;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_prob22.R;
import com.example.note_prob22.adapters.EventsAdapter;
import com.example.note_prob22.classes.Record;
import com.example.note_prob22.db.SQLiteManager;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class GraphViewHelper {


    @SuppressLint("ClickableViewAccessibility")
    public static void fillGraphViewWithData(GraphView graphView, List<PairSmileAndDate> data, Context context) {

        SQLiteManager sqLiteManager = new SQLiteManager(context);
        Collections.sort(data, new Comparator<PairSmileAndDate>() {
            @Override
            public int compare(PairSmileAndDate o1, PairSmileAndDate o2) {
                return o1.getDates().compareTo(o2.getDates());
            }
        });

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();


        for (PairSmileAndDate pair : data) {
            Date date = pair.getDates();
            Double value = pair.getSmile();

            DataPoint dataPoint = new DataPoint(date.getTime(), value);

            series.appendData(dataPoint, true, data.size());
            series.setDrawDataPoints(true);
            series.setDrawBackground(true);
            series.setBackgroundColor(Color.parseColor("#C188E9F5"));
        }

        graphView.removeAllSeries();
        graphView.addSeries(series);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(data.get(0).getDates().getTime());
        graphView.getViewport().setMaxX(data.get(data.size()-1).getDates().getTime());

        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(9);

        graphView.getGridLabelRenderer().setLabelFormatter(new CustomLabelFormatter());
        graphView.getGridLabelRenderer().setHumanRounding(false);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(2);
        graphView.getGridLabelRenderer().setNumVerticalLabels(10);

        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScalable(true);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {

                Date date = new Date((long) dataPoint.getX());

                DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("ru"));
                String formattedDate = dateFormat.format(date);

                sqLiteManager.populateRecordEventsListArrayForCalendar(formattedDate);

                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup_events, null);

                TextView popupText = popupView.findViewById(R.id.popup_text);
                popupText.setText("Дата: " + formattedDate);


//                ListView popupList = popupView.findViewById(R.id.popup_list);
//
//                List<String> listData = sqLiteManager.populateRecordEventsListArrayForCalendar(formattedDate);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listData);
//                popupList.setAdapter(adapter);

                RecyclerView popup_list = popupView.findViewById(R.id.popup_list);
                ArrayList<Record> listData = sqLiteManager.populateRecordEventsListArrayForCalendar(formattedDate);
                EventsAdapter eventsAdapter = new EventsAdapter(listData);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                popup_list.setLayoutManager(layoutManager);
                popup_list.setAdapter(eventsAdapter);




                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                boolean focusable = true; // lets taps outside the popup window
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


                popupWindow.showAtLocation(graphView, Gravity.CENTER, 0, 0);

                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });


            }
        });
    }



}