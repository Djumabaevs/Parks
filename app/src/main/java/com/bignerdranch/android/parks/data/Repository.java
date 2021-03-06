package com.bignerdranch.android.parks.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bignerdranch.android.parks.controller.AppController;
import com.bignerdranch.android.parks.model.Activities;
import com.bignerdranch.android.parks.model.EntranceFees;
import com.bignerdranch.android.parks.model.Images;
import com.bignerdranch.android.parks.model.OperatingHours;
import com.bignerdranch.android.parks.model.Park;
import com.bignerdranch.android.parks.model.StandardHours;
import com.bignerdranch.android.parks.model.Topics;
import com.bignerdranch.android.parks.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    static List<Park> parkList = new ArrayList<>();

    public static void getParks(final AsyncResponse callback, String code) {
        String url = Util.getParksUrl(code);
        Log.d("Url", "getParks: " + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url , null,
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
                            for (int j = 0; j < activityArray.length(); j++) {
                                Activities activities = new Activities();
                                activities.setId(activityArray.getJSONObject(j).getString("id"));
                                activities.setName(activityArray.getJSONObject(j).getString("name"));

                                activitiesList.add(activities);
                            }

                            park.setActivities(activitiesList);

                            //Topics

                            JSONArray topicArray = jsonObject.getJSONArray("topics");
                            List<Topics> topicsList = new ArrayList<>();
                            for (int j = 0; j < topicArray.length() ; j++) {
                                Topics topics = new Topics();
                                topics.setId(topicArray.getJSONObject(j).getString("id"));
                                topics.setName(topicArray.getJSONObject(j).getString("name"));

                                topicsList.add(topics);
                            }
                            park.setTopics(topicsList);

                            //Operating hours

                            JSONArray opHoursArray = jsonObject.getJSONArray("operatingHours");
                            List<OperatingHours> operatingHoursList = new ArrayList<>();
                            for (int j = 0; j < opHoursArray.length(); j++) {
                                OperatingHours operatingHours = new OperatingHours();
                                operatingHours.setDescription(opHoursArray.getJSONObject(j).getString("description"));
                                StandardHours standardHours = new StandardHours();
                                JSONObject hourObject = opHoursArray.getJSONObject(j).getJSONObject("standardHours");

                                standardHours.setWednesday(hourObject.getString("wednesday"));
                                standardHours.setMonday(hourObject.getString("monday"));
                                standardHours.setThursday(hourObject.getString("thursday"));
                                standardHours.setSunday(hourObject.getString("sunday"));
                                standardHours.setTuesday(hourObject.getString("tuesday"));
                                standardHours.setFriday(hourObject.getString("friday"));
                                standardHours.setSaturday(hourObject.getString("saturday"));

                                operatingHours.setStandardHours(standardHours);

                                operatingHoursList.add(operatingHours);
                            }
                            park.setOperatingHours(operatingHoursList);

                            park.setDirectionsInfo(jsonObject.getString("directionsInfo"));

                            park.setDescription(jsonObject.getString("description"));

                            //Entrance fees

                            JSONArray entranceFeeArray = jsonObject.getJSONArray("entranceFees");
                            List<EntranceFees> entranceFeesList = new ArrayList<>();
                            for (int j = 0; j < entranceFeeArray.length(); j++) {
                                EntranceFees entranceFees = new EntranceFees();
                                entranceFees.setCost(entranceFeeArray.getJSONObject(j).getString("cost"));
                                entranceFees.setDescription(entranceFeeArray.getJSONObject(j).getString("description"));
                                entranceFees.setTitle(entranceFeeArray.getJSONObject(j).getString("title"));

                                entranceFeesList.add(entranceFees);
                            }
                            park.setEntranceFees(entranceFeesList);

                            park.setWeatherInfo(jsonObject.getString("weatherInfo"));


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
