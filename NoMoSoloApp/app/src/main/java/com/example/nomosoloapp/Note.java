package com.example.nomosoloapp;

import java.util.Date;

public class Note {
    private String id, note;
    private Date date;

    public Note(String id, Date date, String note) {
        this.id = id;
        this.date = date;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
