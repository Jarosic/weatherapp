package com.mobproject.weatherapp;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    public static String QUERY_FOR_CITY_ID = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String QUERY_FOR_CITY_WEATHER_BY_ID = "https://api.openweathermap.org/data/2.5/forecast?id=";
    public static String QUERY_FOR_CITY_WEATHER_BY_NAME = "https://api.openweathermap.org/data/2.5/forecast?name=";
    private final Context context;
    private String cityID;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {

        void onError(String message);

        void onResponse(String cityID);
    }

    public interface ForeCastByIDResponse {

        void onError(String message);

        void onResponse(List<WeatherReportModel> weatherReportModel);
    }

    //    "list": [
//    {
//        "dt": 1658491200,
//            "main": {
//        "temp": 303.93,
//                "feels_like": 302.75,
//                "temp_min": 303.93,
//                "temp_max": 306.26,
//                "pressure": 1016,
//                "sea_level": 1016,
//                "grnd_level": 987,
//                "humidity": 31,
//                "temp_kf": -2.33
//    },
//        "weather": [
//        {
//            "id": 800,
//                "main": "Clear",
//                "description": "clear sky",
//                "icon": "01d"
//        }
//],
//        "clouds": {
//        "all": 0
//    },
//        "wind": {
//        "speed": 4.21,
//                "deg": 355,
//                "gust": 5.72
//    },
//        "visibility": 10000,
//            "pop": 0,
//            "sys": {
//        "pod": "d"
//    },
//        "dt_txt": "2022-07-22 12:00:00"
//    },
    // "https://api.openweathermap.org/data/2.5/forecast?id=707471&APPID=c470906f50ead8554e0027c2bf83deec"
    public void getCityID(String cityName, VolleyResponseListener volleyResponseListener) {
        String url = QUERY_FOR_CITY_ID + cityName + "&APPID=c470906f50ead8554e0027c2bf83deec";

        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                cityID = "";

                try {
                    cityID = response.get("id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListener.onResponse(cityID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError(error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getCityForecastByID(String cityID, ForeCastByIDResponse foreCastByIDResponse) {

        String url = QUERY_FOR_CITY_WEATHER_BY_ID + cityID + "&cnt=5&appid=c470906f50ead8554e0027c2bf83deec";
        System.out.println(url);
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray consolidateWeatherList = response.getJSONArray("list");
                    List<WeatherReportModel> weatherReportModels = new ArrayList<>();
                    System.out.println("weather list: " + consolidateWeatherList);

                    for (int i = 0; i < consolidateWeatherList.length(); i++) {

                        JSONArray weather = consolidateWeatherList.getJSONObject(i).getJSONArray("weather");
                        JSONObject main = (JSONObject) consolidateWeatherList.getJSONObject(i).getJSONObject("main");
                        String date = consolidateWeatherList.getJSONObject(i).getString("dt_txt");

                        System.out.println("DATE: " + date);

                        WeatherReportModel oneDayWeather = new WeatherReportModel();
                        String description = weather.getJSONObject(0).getString("description");

                        oneDayWeather.setCityID(cityID);
                        oneDayWeather.setDt_txt(date.substring(0, 10));
                        oneDayWeather.setDescription(description);
                        oneDayWeather.setFeels_like(main.getDouble("feels_like"));
                        oneDayWeather.setTemp_max(main.getDouble("temp_max"));
                        oneDayWeather.setTemp_min(main.getDouble("temp_min"));
                        weatherReportModels.add(oneDayWeather);
                    }
                    foreCastByIDResponse.onResponse(weatherReportModels);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                foreCastByIDResponse.onError(error.getMessage());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);

        List<WeatherReportModel> report = new ArrayList<>();
    }
//
//    public List<WeatherReportModel>  getCityForecastByName(String cityName) {
//
//    }
}
