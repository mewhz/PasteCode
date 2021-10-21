package com.mewhz.paste.model;

public class Code {
    private int id;
    private String text;
    private String type;


    public Code(){

    }

    public Code(String text, String type) {
        this.text = text;
        this.type = type;
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

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
