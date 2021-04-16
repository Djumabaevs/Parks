package com.bignerdranch.android.parks.data;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bignerdranch.android.parks.controller.AppController;
import com.bignerdranch.android.parks.model.Activities;
import com.bignerdranch.android.parks.model.Images;
import com.bignerdranch.android.parks.model.Park;
import com.bignerdranch.android.parks.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    static List<Park> parkList = new ArrayList<>();

    public static void getParks(final AsyncResponse callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Util.PARKS_URL, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Park park = new Park();
                            park.setId(jsonObject.getString("id"));
                            park.setFullName(jsonObject.getString("fullName"));
                            park.setLatitude(jsonObject.getString("latitude"));
                            park.setLongitude(jsonObject.getString("longitude"));
                            park.setParkCode(jsonObject.getString("parkCode"));
                            park.setStates(jsonObject.getString("states"));

                            JSONArray imageList = jsonObject.getJSONArray("images");
                            List<Images> list = new ArrayList<>();
                            for (int j = 0; j < imageList.length(); j++) {
                                Images images = new Images();
                                images.setCredit(imageList.getJSONObject(j).getString("credit"));
                                images.setTitle(imageList.getJSONObject(j).getString("title"));
                                images.setUrl(imageList.getJSONObject(j).getString("url"));

                                list.add(images);
                            }
                            park.setImages(list);

                            park.setWeatherInfo(jsonObject.getString("weatherInfo"));
                            park.setName(jsonObject.getString("name"));
                            park.setDesignation(jsonObject.getString("designation"));

                            //Setup activities
                            JSONArray activityArray = jsonObject.getJSONArray("activities");
                            List<Activities> activitiesList = new ArrayList<>();
                            for (int j = 0; j < activityArray.length();j++) {
                                Activities activities = new Activities();
                                activities.setId(activityArray.getJSONObject(j).getString("id"));
                                activities.setName(activityArray.getJSONObject(j).getString("name"));

                                activitiesList.add(activities);
                            }

                            park.setActivities(activitiesList);


                            parkList.add(park);
                        }
                        if(callback != null) {
                            callback.processPark(parkList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, Throwable::printStackTrace);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
