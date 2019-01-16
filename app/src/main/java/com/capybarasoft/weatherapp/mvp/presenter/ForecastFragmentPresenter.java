package com.capybarasoft.weatherapp.mvp.presenter;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.capybarasoft.weatherapp.R;
import com.capybarasoft.weatherapp.application.WeatherApplication;
import com.capybarasoft.weatherapp.mvp.model.Model;
import com.capybarasoft.weatherapp.mvp.model.api.util.APIUtil;
import com.capybarasoft.weatherapp.mvp.model.entity.DailyForecast;
import com.capybarasoft.weatherapp.mvp.model.entity.WeatherForecast;
import com.capybarasoft.weatherapp.mvp.view.ChooseCityFragmentView;
import com.capybarasoft.weatherapp.mvp.view.ForecastFragmentView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.inject.Inject;

import util.TimeConverter;


@InjectViewState
public class ForecastFragmentPresenter extends BasePresenter<ForecastFragmentView> {

    @Inject
    public Model mModel;
    @Inject
    public Context mContext;

    private WeatherForecast mCurrentForecast;
    private LinkedHashMap<String, ArrayList<DailyForecast>> mForecastsMap;
    private ArrayList<String> mKeysList;


    public ForecastFragmentPresenter() {
        WeatherApplication.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }

    public void loadSavedForecast(){
        mCurrentForecast = mModel.getLastSavedWeatherForecast();
        mForecastsMap = parseForecastByDays(mCurrentForecast);

        getViewState().setScreenTitle(mCurrentForecast.getCity().getName());
        getViewState().setDailyForecasts(mForecastsMap, mKeysList);
        getViewState().setHourlyForecasts(mForecastsMap.get(mKeysList.get(0)));
//        for (DailyForecast dailyForecast : mCurrentForecast.getDailyForecasts()){
//            if (TimeConverter.equalsCurrentDate(dailyForecast)){
//                getViewState().setScreenTitle(mCurrentForecast.getCity().getName());
//                getViewState().onWeatherForecastLoaded(dailyForecast);
//                getViewState().setDailyForecasts(mCurrentForecast.getDailyForecasts());
//                break;
//            }
//        }

    }

    public void dailyForecastClicked(ArrayList<DailyForecast> forecasts){
        getViewState().setHourlyForecasts(forecasts);
    }

    public void hourlyForecastClicked(DailyForecast dailyForecast){
        getViewState().displayCurrentForecastData(dailyForecast);
    }

    public void reloadCurrentForecast(){
        unsubscribeOnDestroy(mModel.getForecast(mCurrentForecast.getCity().getName())
            .subscribe(forecast -> {
                getViewState().hideSwipeRefreshLayout();
                if (forecast.getCod().equals(APIUtil.CODE_OK)){
                    mModel.saveLastWeatherForecastInPrefs(forecast);
                    WeatherApplication.getApplication().updateWidget();
                    loadSavedForecast();
                }
                else {
                    getViewState().failedToReloadForecast(WeatherApplication.getApplication().getString(R.string.failed_to_refresh_forecast));
                }
            },
            throwable -> {
                throwable.printStackTrace();
                getViewState().hideSwipeRefreshLayout();
                getViewState().failedToReloadForecast(WeatherApplication.getApplication().getString(R.string.failed_to_refresh_forecast));
        }));
    }

    public void searchClicked(){
        getViewState().showSearchCityScreen();
    }


    public void locationClicked(){
        getViewState().initLocationListener();
    }


    public void onLocationReceived(Location location){
        if (location != null){
            getViewState().showProgress();
            unsubscribeOnDestroy(mModel.getForecast((float)location.getLatitude(), (float)location.getLongitude())
                    .subscribe(forecast -> {
                                getViewState().hideProgress();
                                if (forecast.getCod().equals(APIUtil.CODE_OK)){
                                    mModel.saveLastWeatherForecastInPrefs(forecast);
                                    WeatherApplication.getApplication().updateWidget();
                                    loadSavedForecast();
                                }
                                else {
                                    getViewState().failedToReloadForecast(WeatherApplication.getApplication().getString(R.string.failed_to_refresh_forecast));
                                }
                            },
                            throwable -> {
                                throwable.printStackTrace();
                                getViewState().hideProgress();
                                getViewState().failedToReloadForecast(WeatherApplication.getApplication().getString(R.string.failed_to_refresh_forecast));
                            }));
        }
        else {
            getViewState().failedToRetreiveLocation(WeatherApplication.getApplication().getString(R.string.please_turn_on_gps));
        }
    }


    private LinkedHashMap<String, ArrayList<DailyForecast>> parseForecastByDays(WeatherForecast weatherForecast){

        LinkedHashMap<String, ArrayList<DailyForecast>> forecastsMap = new LinkedHashMap<>();
        mKeysList = new ArrayList<>();
        for (DailyForecast forecast : weatherForecast.getDailyForecasts()){
            String dateWithoutTime = TimeConverter.getDateWithoutTime(forecast.getDate());

            ArrayList<DailyForecast> forecasts = forecastsMap.get(dateWithoutTime);
            if(forecasts == null) {
                forecasts= new ArrayList<DailyForecast>();
                forecasts.add(forecast);
                forecastsMap.put(dateWithoutTime, forecasts);
            } else {
                forecasts.add(forecast);
            }
            if (!mKeysList.contains(dateWithoutTime)){
                mKeysList.add(dateWithoutTime);
            }
        }
        return forecastsMap;

    }

}
