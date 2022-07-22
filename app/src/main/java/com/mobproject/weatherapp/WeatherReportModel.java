package com.mobproject.weatherapp;

public class WeatherReportModel {

    private String cityID;
    private String dt_txt;
    private String description;
    private Double feels_like;
    private Double temp_min;
    private Double temp_max;

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(Double feels_like) {
        this.feels_like = feels_like;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    public WeatherReportModel() {
    }

    @Override
    public String toString() {
        return "CITY ID: " + cityID + ",\n" +
                "DATE: " + dt_txt + ",\n" +
                "DESCRIPTION: '" + description + "',\n" +
                "FEELS LIKE: " + feels_like + ",\n" +
                "MIN TEMPERATURE: " + temp_min + ",\n" +
                "MAX TEMPERATURE: " + temp_max;
    }
}
