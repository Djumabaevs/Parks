package com.bignerdranch.android.parks.util;

public class Util {
    public static final String PARKS_URL = "https://developer.nps.gov/api/v1/parks?stateCode=AZ&api_key=iMu0z8ZUxhZa5bc5fQw1LNtNbtXZdOwgov01a4Qd";

    public static String getParksUrl(String stateCode) {
        return "https://developer.nps.gov/api/v1/parks?stateCode="+stateCode+"&api_key=iMu0z8ZUxhZa5bc5fQw1LNtNbtXZdOwgov01a4Qd";
    }
}
