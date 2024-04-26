package com.example.note_prob22.graph;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.note_prob22.R;
import com.example.note_prob22.db.SQLiteManager;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;


public class GraphWeekFragment extends Fragment {

    SQLiteManager sqLiteManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph_week, container, false);


        sqLiteManager = new SQLiteManager(requireContext());
        GraphView graphView = view.findViewById(R.id.graphWeek);
        TextView noData = view.findViewById(R.id.noData);



        if (sqLiteManager.checkLastWeekQuery()){
            graphView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
            graphView.setTitle("Cтатистика за неделю \uD83C\uDF25");

            graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(requireContext()));
           // GraphViewHelper.fillGraphViewWithData(graphView, sqLiteManager.get7UniqRecordFromDb());
            GraphViewHelper.fillGraphViewWithData(graphView, sqLiteManager.getDateAndAverageSmileForWeek());

        }
        else{
            graphView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);

        }


        return view;
    }
}