package com.mewhz.paste.model;

import java.util.Date;

public class Code {
    private int id;
    private String text;
    private String type;
    private Date date;

    public Code() {

    }

    public Code(String text, String type, Date date) {
        this.text = text;
        this.type = type;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                '}';
    }
}
