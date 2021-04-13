package com.bignerdranch.android.parks.data;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bignerdranch.android.parks.model.Park;
import com.bignerdranch.android.parks.util.Util;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    static List<Park> parkList = new ArrayList<>();

    public static void getParks(final AsyncResponse callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Util.PARKS_URL, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, Throwable::printStackTrace);
    }
}
