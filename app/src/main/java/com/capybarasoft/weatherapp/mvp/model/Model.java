package com.capybarasoft.weatherapp.mvp.model;


import com.capybarasoft.weatherapp.mvp.model.entity.WeatherForecast;

import io.reactivex.Observable;

public interface Model {

    void saveLastWeatherForecastInPrefs(WeatherForecast weatherForecast);

    WeatherForecast getLastSavedWeatherForecast();

    Observable<WeatherForecast> getForecast(String city);

    Observable<WeatherForecast> getForecast(float lat, float lon);
}
