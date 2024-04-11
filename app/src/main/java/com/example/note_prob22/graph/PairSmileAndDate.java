package com.example.note_prob22.graph;

import java.util.Date;

public class PairSmileAndDate {
    private Integer smile;
    private Date dates;

    public PairSmileAndDate(Date dates, Integer smile) {
        this.dates = dates;
        this.smile = smile;

    }

    public Integer getSmile() {
        return smile;
    }

    public Date getDates() {
        return dates;
    }
}
