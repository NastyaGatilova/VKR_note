package com.example.note_prob22.graph;

import java.util.Date;

public class PairSmileAndDate {
    private Double smile;
    private Date dates;

    public PairSmileAndDate(Date dates, Double smile) {
        this.dates = dates;
        this.smile = smile;

    }

    public Double getSmile() {
        return smile;
    }

    public Date getDates() {
        return dates;
    }
}
