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

public class GraphMounthFragment extends Fragment {

    SQLiteManager sqLiteManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph_mounth, container, false);


        sqLiteManager = new SQLiteManager(requireContext());
        GraphView graphView = view.findViewById(R.id.graphMounth);
        TextView tv = view.findViewById(R.id.noData);

      //  sqLiteManager.get30UniqRecordFromDb();
       if (sqLiteManager.checkLastMonthQuery()){
           graphView.setVisibility(View.VISIBLE);
           tv.setVisibility(View.GONE);
           graphView.setTitle("Статистика за месяц \uD83C\uDF24");
           graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(requireContext()));
           GraphViewHelper.fillGraphViewWithData(graphView, sqLiteManager.get30UniqRecordFromDb());
       }else {
           graphView.setVisibility(View.GONE);
           tv.setVisibility(View.VISIBLE);
       }
        return view;
    }
}