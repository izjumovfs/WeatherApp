package com.capybarasoft.weatherapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.capybarasoft.weatherapp.mvp.model.entity.DailyForecast;
import com.capybarasoft.weatherapp.mvp.model.entity.WeatherForecast;

import java.util.ArrayList;
import java.util.HashMap;

public interface ForecastFragmentView extends MvpView {

    void showProgress();
    void hideProgress();
    void setScreenTitle(String title);

    void setHourlyForecasts(ArrayList<DailyForecast> dailyForecasts);

    void setDailyForecasts(HashMap<String, ArrayList<DailyForecast>> forecastsMap, ArrayList<String> keys);

    void hideSwipeRefreshLayout();

    void failedToReloadForecast(String message);

    void showSearchCityScreen();

    void initLocationListener();

    void failedToRetreiveLocation(String message);

    void displayCurrentForecastData(DailyForecast forecast);
}
