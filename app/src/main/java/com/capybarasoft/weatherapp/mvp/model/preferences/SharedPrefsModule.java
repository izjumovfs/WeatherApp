package com.capybarasoft.weatherapp.mvp.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;

public class SharedPrefsModule {

    private static final String PREFERENCES_NAME = "WEATHER_APP_PREFERENCES";

    private static final String LAST_WEATHER_FORECAST = "LAST_WEATHER_FORECAST";

    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mEditor;

    @Inject
    public SharedPrefsModule(@NonNull Context context){
        mSharedPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPrefs.edit();
    }


    public void saveLastWeatherForecast(String weatherForecast) {
        mEditor.putString(LAST_WEATHER_FORECAST, weatherForecast);
        mEditor.apply();
    }

    public String getLastWeatherForecast() {
        return mSharedPrefs.getString(LAST_WEATHER_FORECAST, null);
    }

}
