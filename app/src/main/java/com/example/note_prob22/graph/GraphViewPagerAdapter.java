package com.example.note_prob22.graph;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class GraphViewPagerAdapter extends FragmentStateAdapter {
    public GraphViewPagerAdapter(FragmentActivity fm) {
        super(fm);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new GraphYearFragment();
            case 1:
              return new GraphMounthFragment();
            case 2:
                return new GraphWeekFragment();
            default:
                return new GraphYearFragment();
        }
    }
}
