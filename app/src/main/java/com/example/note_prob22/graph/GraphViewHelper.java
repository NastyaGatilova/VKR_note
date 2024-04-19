package com.example.note_prob22.graph;

import android.graphics.Color;
import android.util.Pair;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

//public class GraphViewHelper {
//
//    public static void fillGraphViewWithData(GraphView graphView, List<PairSmileAndDate> data) {
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
//
//
//        for (PairSmileAndDate pair : data) {
//            Date date = pair.getDates();
//            Integer value = pair.getSmile();
//
//            DataPoint dataPoint = new DataPoint(date.getTime(), value);
//
//
//            series.appendData(dataPoint, true, data.size());
//            series.setDrawDataPoints(true);
//            series.setDrawBackground(true);
//            series.setBackgroundColor(Color.parseColor("#C188E9F5"));
//        }
//
//        graphView.removeAllSeries();
//        graphView.addSeries(series);
//        graphView.getViewport().setXAxisBoundsManual(true);
//        graphView.getViewport().setMinX(data.get(1).getDates().getTime());
//        graphView.getViewport().setMaxX(data.get(data.size()-1).getDates().getTime());
//
//        graphView.getViewport().setYAxisBoundsManual(true);
//        graphView.getViewport().setMinY(0);
//        graphView.getViewport().setMaxY(9);
//
//
////       graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
//        graphView.getGridLabelRenderer().setLabelFormatter(new CustomLabelFormatter());
//        graphView.getGridLabelRenderer().setHumanRounding(false);
//        graphView.getGridLabelRenderer().setNumHorizontalLabels(2); // only 4 because of the space
//        graphView.getGridLabelRenderer().setNumVerticalLabels(10); // only 4 because of the space
//
//        graphView.getViewport().setScrollable(true);
//        graphView.getViewport().setScalable(true) ;
//
//
//    }
//}

public class GraphViewHelper {

    public static void fillGraphViewWithData(GraphView graphView, List<PairSmileAndDate> data) {
        Collections.sort(data, new Comparator<PairSmileAndDate>() {
            @Override
            public int compare(PairSmileAndDate o1, PairSmileAndDate o2) {
                return o1.getDates().compareTo(o2.getDates());
            }
        });

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        for (PairSmileAndDate pair : data) {
            Date date = pair.getDates();
            Integer value = pair.getSmile();

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
    }
}