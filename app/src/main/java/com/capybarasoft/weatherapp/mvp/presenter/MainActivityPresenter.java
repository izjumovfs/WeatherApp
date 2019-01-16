package com.capybarasoft.weatherapp.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.capybarasoft.weatherapp.application.WeatherApplication;
import com.capybarasoft.weatherapp.mvp.model.Model;
import com.capybarasoft.weatherapp.mvp.model.entity.WeatherForecast;
import com.capybarasoft.weatherapp.mvp.view.MainActivityView;

import javax.inject.Inject;


@InjectViewState
public class MainActivityPresenter extends BasePresenter<MainActivityView> {

    @Inject
    Model mModel;

    public MainActivityPresenter() {
        WeatherApplication.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }


    public void checkWeatherForecast(){
        if (mModel.getLastSavedWeatherForecast() != null){
            getViewState().openForecastScreen();
        }
        else{
            getViewState().openCityChooserScreen();
        }
    }

}
