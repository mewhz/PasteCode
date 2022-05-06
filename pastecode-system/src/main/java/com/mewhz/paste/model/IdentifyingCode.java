package com.mewhz.paste.model;

/**
 * @author mewhz
 */
public class IdentifyingCode {
    private int icId;
    private String identifying;
    private String timeId;

    public IdentifyingCode(){

    }

    public IdentifyingCode(String identifying){
        this.identifying = identifying;
    }

    public IdentifyingCode(String identifying, String timeId) {
        this.identifying = identifying;
        this.timeId = timeId;
    }

    public IdentifyingCode(int icId, String identifying, String timeId) {
        this.icId = icId;
        this.identifying = identifying;
        this.timeId = timeId;
    }

    public int getIcId() {
        return icId;
    }

    public void setIcId(int icId) {
        this.icId = icId;
    }

    public String getIdentifying() {
        return identifying;
    }

    public void setIdentifying(String identifying) {
        this.identifying = identifying;
    }

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    @Override
    public String toString() {
        return "IdentifyingCode{" +
                "icId=" + icId +
                ", identifying='" + identifying + '\'' +
                ", timeId='" + timeId + '\'' +
                '}';
    }
}
