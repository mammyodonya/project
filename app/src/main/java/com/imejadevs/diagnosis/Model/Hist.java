package com.imejadevs.diagnosis.Model;

public class Hist {
    String disease,level,date,time,email,recommendation;

    public Hist() {
    }

    public Hist(String disease, String level, String date, String time, String email, String recommendation) {
        this.disease = disease;
        this.level = level;
        this.date = date;
        this.time = time;
        this.email = email;
        this.recommendation = recommendation;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
