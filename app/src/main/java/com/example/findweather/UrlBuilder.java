package com.example.findweather;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlBuilder {

    private static final String TAG = "builderUrl";

    public URL builderUrl(String city) {

        String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
        String PARAM_CITY = "q";
        String PARAM_APPID = "appid";
        String appid_value = "b72ed42a05f9f49f9798a53d2e8b3422";
        String PARAM_UNITS = "units";
        String units_value = "metric";
        String PARAM_LANG = "lang";
        String lang_value = "ru";

        Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_CITY, city)
                .appendQueryParameter(PARAM_UNITS, units_value)
                .appendQueryParameter(PARAM_LANG, lang_value)
                .appendQueryParameter(PARAM_APPID, appid_value)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "builderUrl: " + url);
        return url;
    }
}
