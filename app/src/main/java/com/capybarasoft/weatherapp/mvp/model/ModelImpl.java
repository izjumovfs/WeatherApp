package com.capybarasoft.weatherapp.mvp.model;

import com.capybarasoft.weatherapp.application.WeatherApplication;
import com.capybarasoft.weatherapp.mvp.model.api.ApiInterface;
import com.capybarasoft.weatherapp.mvp.model.entity.WeatherForecast;
import com.capybarasoft.weatherapp.mvp.model.preferences.SharedPrefsModule;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import util.Constants;

public class ModelImpl implements Model {

    private ApiInterface mApiInterface;

    @Inject
    SharedPrefsModule mPrefModule;

    @Inject
    public ModelImpl(ApiInterface apiInterface) {
        mApiInterface = apiInterface;
        WeatherApplication.getAppComponent().inject(this);
    }

    @Override
    public void saveLastWeatherForecastInPrefs(WeatherForecast forecast) {
        Gson gson = new Gson();
        String json = gson.toJson(forecast);
        mPrefModule.saveLastWeatherForecast(json);
    }


    @Override
    public WeatherForecast getLastSavedWeatherForecast(){
        WeatherForecast weatherForecast = null;
        Gson gson = new Gson();
        String forecastInjson = mPrefModule.getLastWeatherForecast();
        try {
            weatherForecast = gson.fromJson(forecastInjson, WeatherForecast.class);
        } catch (JsonSyntaxException exc) {
            exc.printStackTrace();
        }
        return weatherForecast;
    }

    @Override
    public Observable<WeatherForecast> getForecast(String city){
        return mApiInterface.getForecast(city, Constants.Keys.OPEN_WEATHER_MAP_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<WeatherForecast> getForecast(float lat, float lon){
        return mApiInterface.getForecast(lat, lon, Constants.Keys.OPEN_WEATHER_MAP_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
