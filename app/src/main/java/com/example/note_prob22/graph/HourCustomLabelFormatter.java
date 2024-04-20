package com.example.note_prob22.graph;

import com.jjoe64.graphview.DefaultLabelFormatter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HourCustomLabelFormatter extends DefaultLabelFormatter {
    private final DecimalFormat format;

    public HourCustomLabelFormatter() {
        this.format = new DecimalFormat("0");
    }

    @Override
    public String formatLabel(double value, boolean isValueX) {
        if (isValueX) {


;
           // return String.valueOf((int) value);
            switch ((int) value) {
                case 0:
                    return "00:00";
                case 1:
                    return "01:00";
                case 2:
                    return "02:00";
                case 3:
                    return "03:00";
                case 4:
                    return "04:00";
                case 5:
                    return "05:00";
                case 6:
                    return "06:00";
                case 7:
                    return "07:00";
                case 8:
                    return "08:00";
                case 9:
                    return "09:00";
                case 10:
                    return "10:00";
                case 11:
                    return "11:00";
                case 12:
                    return "12:00";
                case 13:
                    return "13:00";
                case 14:
                    return "14:00";
                case 15:
                    return "15:00";
                case 16:
                    return "16:00";
                case 17:
                    return "17:00";
                case 18:
                    return "18:00";
                case 19:
                    return "19:00";
                case 20:
                    return "20:00";
                case 21:
                    return "21:00";
                case 22:
                    return "22:00";
                case 23:
                    return "23:00";
                default:
                    return format.format(value);
            }
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
