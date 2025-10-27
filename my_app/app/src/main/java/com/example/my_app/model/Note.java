package com.example.my_app.model;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private String title;
    private String content;
    private Date dateTime;

    public Note(String title, String content, Date dateTime) {
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDateTime() {
        return dateTime;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return title + "\n" + content + "\n" + dateTime.toString();
    }
}
