package com.mewhz.paste.model;

import java.util.Date;

public class Code {
    private int id;
    private String text;
    private String type;
    private Date date;
    private String ip;
    private String userAgent;
    private String remark;
    public Code() {

    }

    public Code(int id, String text, String type, Date date, String ip, String userAgent, String remark) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.date = date;
        this.ip = ip;
        this.userAgent = userAgent;
        this.remark = remark;
    }

    public Code(String text, String type, Date date, String ip, String userAgent, String remark) {
        this.text = text;
        this.type = type;
        this.date = date;
        this.ip = ip;
        this.userAgent = userAgent;
        this.remark = remark;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", ip='" + ip + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
