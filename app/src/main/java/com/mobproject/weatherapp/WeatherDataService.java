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

        void onResponse(WeatherReportModel weatherReportModel);
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

    public void getCityForecastByID(String cityID, ForeCastByIDResponse foreCastByIDResponse) {

        String url = QUERY_FOR_CITY_WEATHER_BY_ID + cityID + "&cnt=5&appid=c470906f50ead8554e0027c2bf83deec";
        System.out.println(url);
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray consolidateWeatherList = response
                            .getJSONArray("list")
                            .getJSONObject(0)
                            .getJSONArray("weather");

                    WeatherReportModel firstDay = new WeatherReportModel();

                    JSONObject firstDayFromApi = (JSONObject) consolidateWeatherList.get(0);
                    System.out.println(firstDayFromApi);
                    firstDay.setId(firstDayFromApi.getInt("id"));
                    firstDay.setDescription(firstDayFromApi.getString("description"));
                    firstDay.setMain(firstDayFromApi.getString("main"));
                    firstDay.setIcon(firstDayFromApi.getString("icon"));

                    foreCastByIDResponse.onResponse(firstDay);

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
