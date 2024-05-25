package com.example.note_prob22.classes;

import java.util.ArrayList;
import java.util.Date;

public class Record
{

    public static ArrayList<Record> noteDayArrayList = new ArrayList<Record>();
    public static ArrayList<Record> recordArrayListWithForCalendar = new ArrayList<>();
    public static ArrayList<Record> eventsArrayList = new ArrayList<>();
    public static String NOTE_EDIT_EXTRA =  "recordEdit";

    private int id;
    private String feeling;
    private String events;
    private String description;
    private Date deleted;
    private String date;

    private String smile;


    public Record (int id,String feeling, String events, String description,   String smile, String date, Date deleted)
    {
        this.id = id;
        this.feeling = feeling;
        this.events = events;
        this.description = description;
        this.smile = smile;
        this.date = date;
        this.deleted = deleted;


    }

//    public Record(String feeling, String events, String description, String smile, String date)
//    {
//
//        this.feeling = feeling;
//        this.events = events;
//        this.description = description;
//        this.smile=smile;
//        this.date = date;
//        deleted = null;
//
//    }


    public Record(int id, String feeling, String events, String description, String smile, String date)
    {
        this.id = id;
        this.feeling = feeling;
        this.events = events;
        this.description = description;
        this.smile=smile;
        this.date = date;
        deleted = null;

    }

    public Record(int id,String events, String date)
    {
      this.id = id;
        this.events = events;
        this.date = date;


    }

    public boolean isDeleted() {
        return deleted != null;
    }



    public static Record getNoteDayForID(int passedNoteID)
    {
        for (Record rec : noteDayArrayList)
        {
            if(rec.getId() == passedNoteID)
                return rec;
        }

        return null;
    }





    public static int getPositionForID(int id) {
        for (int i = 0; i < noteDayArrayList.size(); i++) {
            if (noteDayArrayList.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFeeling()
    {
        return feeling;
    }

    public void setFeeling(String feeling)
    {
        this.feeling = feeling;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

//    public Date getDeleted()
//    {
//        return deleted;
//    }
//
//    public void setDeleted(Date deleted)
//    {
//        this.deleted = deleted;
//    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }


    public String getSmile()
    {
        return smile;
    }

    public void setSmile(String smile)
    {
        this.smile = smile;
    }

    public  String getEvents(){return events; }
    public void setEvents(String events){ this.events = events;}

}
