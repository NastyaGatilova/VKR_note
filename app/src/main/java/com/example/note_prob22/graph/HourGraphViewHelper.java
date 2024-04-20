package com.example.note_prob22.graph;

import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HourGraphViewHelper {

    public static void fillGraphViewWithHour(GraphView graphView, List<PairSmileAndDate> data) {
        Collections.sort(data, new Comparator<PairSmileAndDate>() {
            @Override
            public int compare(PairSmileAndDate o1, PairSmileAndDate o2) {
                return o1.getDates().compareTo(o2.getDates());
            }
        });

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>();

        for (PairSmileAndDate pair : data) {
            Date date = pair.getDates();
            Integer value = pair.getSmile();

            DataPoint dataPoint = new DataPoint(date.getTime(), value);

            series.appendData(dataPoint, true, data.size());

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
