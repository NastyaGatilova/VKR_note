package com.example.note_prob22.graph;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.note_prob22.R;
import com.example.note_prob22.SQLiteManager;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;


public class GraphYearFragment extends Fragment {

    SQLiteManager sqLiteManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph_year, container, false);


        sqLiteManager = new SQLiteManager(requireContext());
        GraphView graphView = view.findViewById(R.id.graphYear);
        TextView noData = view.findViewById(R.id.noDataYear);


      //  graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(requireContext()));
        //GraphViewHelper.fillGraphViewWithData(graphView, sqLiteManager.dateAndSmileFromTableRecordForGrafik());

        if (sqLiteManager.isTableEmpty()){

            graphView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
        else{

            graphView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);

            graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(requireContext()));
            GraphViewHelper.fillGraphViewWithData(graphView, sqLiteManager.getDateAndSmileFromTableRecordForGrafik());
        }





        return view;
    }
}