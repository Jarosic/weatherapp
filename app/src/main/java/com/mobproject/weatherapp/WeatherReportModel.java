package com.mobproject.weatherapp;

public class WeatherReportModel {

    private int id;
    private String main;
    private String description;
    private String icon;

    public WeatherReportModel() {
    }

    public WeatherReportModel(int id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "WeatherReportModel{" +
                "id=" + id +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

    //    {
//        "coord": {
//        "lon": -0.1257,
//                "lat": 51.5085
//    },
//        "weather": [
//        {
//            "id": 803,
//                "main": "Clouds",
//                "description": "broken clouds",
//                "icon": "04d"
//        }
//],
//        "base": "stations",
//            "main": {
//        "temp": 296.9,
//                "feels_like": 296.84,
//                "temp_min": 294.81,
//                "temp_max": 298.76,
//                "pressure": 1021,
//                "humidity": 58
//    },
//        "visibility": 10000,
//            "wind": {
//        "speed": 2.57,
//                "deg": 330
//    },
//        "clouds": {
//        "all": 75
//    },
//        "dt": 1658407841,
//            "sys": {
//        "type": 2,
//                "id": 2075535,
//                "country": "GB",
//                "sunrise": 1658376489,
//                "sunset": 1658433917
//    },
//        "timezone": 3600,
//            "id": 2643743,
//            "name": "London",
//            "cod": 200
//    }
}
