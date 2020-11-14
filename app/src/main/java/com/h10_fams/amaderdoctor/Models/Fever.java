package com.h10_fams.amaderdoctor.Models;

public class Fever {
    private String temperature, suger, cough;

    public Fever() {
        temperature = "98.4";
        suger = "No";
        cough = "No";
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getSuger() {
        return suger;
    }

    public void setSuger(String suger) {
        this.suger = suger;
    }

    public String getCough() {
        return cough;
    }

    public void setCough(String cough) {
        this.cough = cough;
    }
}
