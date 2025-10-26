package com.example.my_app.model;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private String title;
    private String content;
    private Date time;

    public Note(String title, String content, Date time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getTime() {
        return time;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return title + "\n" + content + "\n" + time.toString();
    }
}
