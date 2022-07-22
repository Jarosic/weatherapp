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
    public static String QUERY_FOR_CITY_WEATHER_BY_NAME = "https://api.openweathermap.org/data/2.5/forecast?q=";
    private final Context context;
    private String cityID;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {

        void onError(String message);

        void onResponse(String cityID);
    }

    public interface ForecastResponse {

        void onError(String message);

        void onResponse(List<WeatherReportModel> weatherReportModel);
    }

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

    public void getCityForecastByID(String cityID, ForecastResponse forecastResponse) {

        String url = QUERY_FOR_CITY_WEATHER_BY_ID + cityID + "&cnt=5&appid=c470906f50ead8554e0027c2bf83deec";
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    forecastResponse.onResponse(getWeatherReportModels(response, cityID));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                forecastResponse.onError(error.getMessage());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getCityForecastByName(String cityName, ForecastResponse forecastResponse) {
        String url = QUERY_FOR_CITY_WEATHER_BY_NAME + cityName + "&cnt=5&appid=c470906f50ead8554e0027c2bf83deec";
        System.out.println(url);
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    forecastResponse.onResponse(getWeatherReportModels(response, cityName));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                forecastResponse.onError(error.getMessage());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    private List<WeatherReportModel> getWeatherReportModels(JSONObject response, String cityNameOrID) throws JSONException {

        JSONArray consolidateWeatherList = response.getJSONArray("list");
        List<WeatherReportModel> weatherReportModels = new ArrayList<>();
        System.out.println("weather list: " + consolidateWeatherList);

        for (int i = 0; i < consolidateWeatherList.length(); i++) {

            JSONArray weather = consolidateWeatherList.getJSONObject(i).getJSONArray("weather");
            JSONObject main = (JSONObject) consolidateWeatherList.getJSONObject(i).getJSONObject("main");
            String date = consolidateWeatherList.getJSONObject(i).getString("dt_txt");
            String description = weather.getJSONObject(0).getString("description");

            WeatherReportModel oneDayWeather = new WeatherReportModel();

            oneDayWeather.setCityNameOrID(cityNameOrID);
            oneDayWeather.setDt_txt(date.substring(0, 10));
            oneDayWeather.setDescription(description);
            oneDayWeather.setFeels_like(main.getDouble("feels_like"));
            oneDayWeather.setTemp_max(main.getDouble("temp_max"));
            oneDayWeather.setTemp_min(main.getDouble("temp_min"));
            weatherReportModels.add(oneDayWeather);
        }
        return weatherReportModels;
    }
}
