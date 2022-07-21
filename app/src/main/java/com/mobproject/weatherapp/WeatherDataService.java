package com.mobproject.weatherapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataService {

    public static String QUERY_FOR_CITY_ID = "https://api.openweathermap.org/data/2.5/weather?q=";
    private final Context context;
    private String cityID;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{

        void onError(String message);

        void onResponse(String cityID);
    }

    public void getCityID(String cityName, VolleyResponseListener volleyResponseListener) {

        String url = QUERY_FOR_CITY_ID + cityName + "&APPID=c470906f50ead8554e0027c2bf83deec";

        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                cityID = "";

                try {
                    System.out.println("RESP: " + response);
                    cityID = response
                            .getJSONArray("weather")
                            .getJSONObject(0)
                            .get("id").toString();

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

//    public List<WeatherReportModel>  getCityForecastByID(String cityID) {
//
//    }
//
//    public List<WeatherReportModel>  getCityForecastByName(String cityName) {
//
//    }
}
