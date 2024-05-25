package com.example.note_prob22.graph;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomLabelFormatter extends DefaultLabelFormatter {
    private final DecimalFormat format;

    public CustomLabelFormatter() {
        this.format = new DecimalFormat("0");
    }


    @Override
    public String formatLabel(double value, boolean isValueX) {
        if (isValueX) {

            Date date = new Date((long) value);
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("ru"));
            return dateFormat.format(date);
        } else {

            switch ((int) value) {
                case 0:
                    return "";
                case 1:
                    return "😰";
                case 2:
                    return "🙁";
                case 3:
                    return "😔";
                case 4:
                    return "😐";
                case 5:
                    return "😡";
                case 6:
                    return "🙂";
                case 7:
                    return "😱";
                case 8:
                    return "😃";
                case 9:
                    return "🤩";
                default:
                    return format.format(value);
            }
        }
    }
}
