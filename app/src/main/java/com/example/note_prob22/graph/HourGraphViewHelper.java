package com.example.note_prob22.graph;

import android.graphics.Color;
import android.util.Log;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HourGraphViewHelper {

    public static void fillGraphViewWithHour(GraphView graphView, List<PairSmileAndDate> data) {

        int datevalue = -1;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", new Locale("ru"));

        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("ru"));
        String currentTime = timeFormat.format(new Date());

        Collections.sort(data, new Comparator<PairSmileAndDate>() {
            @Override
            public int compare(PairSmileAndDate o1, PairSmileAndDate o2) {
                return o1.getDates().compareTo(o2.getDates());
            }
        });



        for (PairSmileAndDate pair : data) {

            Date date = pair.getDates();
            Double value = pair.getSmile();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (calendar.get(Calendar.MINUTE) >= 30) {
                calendar.add(Calendar.HOUR, 1);
            }
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            String roundedTime = format.format(calendar.getTime());


        //    Log.d("--Help--", "roundedTime ="+roundedTime  + " Полное время = " + date + " Смайл =" + value);


            switch (roundedTime) {
                case "00:00":
                    datevalue = 0;
                    break;
                case "01:00":
                    datevalue =1;
                    break;
                case "02:00":
                    datevalue = 2;
                    break;
                case "03:00":
                    datevalue = 3;
                    break;
                case "04:00":
                    datevalue = 4;
                    break;
                case "05:00":
                    datevalue = 5;
                    break;
                case "06:00":
                    datevalue = 6;
                    break;
                case "07:00":
                    datevalue = 7;
                    break;
                case "08:00":
                    datevalue = 8;
                    break;
                case "09:00":
                    datevalue = 9;
                    break;
                case "10:00":
                    datevalue = 10;
                    break;
                case "11:00":
                    datevalue = 11;
                    break;
                case "12:00":
                    datevalue = 12;
                    break;
                case "13:00":
                    datevalue = 13;
                    break;
                case "14:00":
                    datevalue = 14;
                    break;
                case "15:00":
                    datevalue = 15;
                    break;
                case "16:00":
                    datevalue = 16;
                    break;
                case "17:00":
                    datevalue = 17;
                    break;
                case "18:00":
                    datevalue = 18;
                    break;
                case "19:00":
                    datevalue = 19;
                    break;
                case "20:00":
                    datevalue = 20;
                    break;
                case "21:00":
                    datevalue = 21;
                    break;
                case "22:00":
                    datevalue = 22;
                    break;
                case "23:00":
                    datevalue = 23;
                    break;
                default:
                    datevalue = 0;
                    break;

            }




            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {

                    new DataPoint(datevalue, value)
            });
            graphView.addSeries(series);

            series.setDrawValuesOnTop(true);
            series.setValuesOnTopColor(Color.RED);
            series.setSpacing(50);
        }





        graphView.setTitle("Статистика за " + currentTime + " \uD83D\uDCCA");

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(23);

        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(9);

        graphView.getGridLabelRenderer().setLabelFormatter(new HourCustomLabelFormatter());
        graphView.getGridLabelRenderer().setHumanRounding(false);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(6);
        graphView.getGridLabelRenderer().setNumVerticalLabels(10);

        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScalable(true);





    }
}


