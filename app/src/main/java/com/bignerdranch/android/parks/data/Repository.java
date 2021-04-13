package com.bignerdranch.android.parks.data;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bignerdranch.android.parks.controller.AppController;
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
