package com.example.note_prob22;

import java.util.ArrayList;
import java.util.Date;

public class Note
{
    public static ArrayList<Note> noteArrayList = new ArrayList<>();
  //  public static ArrayList<Note> noteDayArrayList = new ArrayList<>();
    public static String NOTE_EDIT_EXTRA =  "noteEdit";

    private int id;
    private String title;
    private String description;
    private Date deleted;
    private String date;


    public Note(int id, String title, String description,  String date, Date deleted)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.deleted = deleted;

    }


    public Note(int id, String title, String description, String date)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        deleted = null;


    }


    public static Note getNoteForID(int passedNoteID)
    {
        for (Note note : noteArrayList)
        {
            if(note.getId() == passedNoteID)
                return note;
        }

        return null;
    }

//    public static Note getNoteDayForID(int passedNoteID)
//    {
//        for (Note note : noteDayArrayList)
//        {
//            if(note.getId() == passedNoteID)
//                return note;
//        }
//
//        return null;
//    }

    public static ArrayList<Note> nonDeletedNotes()
    {
        ArrayList<Note> nonDeleted = new ArrayList<>();
        for(Note note : noteArrayList)
        {
            if(note.getDeleted() == null)
                nonDeleted.add(note);
        }

        return nonDeleted;
    }

//    public static ArrayList<Note> nonDeletedDayNotes()
//    {
//        ArrayList<Note> nonDeleted = new ArrayList<>();
//        for(Note note : noteDayArrayList)
//        {
//            if(note.getDeleted() == null)
//                nonDeleted.add(note);
//        }
//
//        return nonDeleted;
//    }



    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Date deleted)
    {
        this.deleted = deleted;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }


}
