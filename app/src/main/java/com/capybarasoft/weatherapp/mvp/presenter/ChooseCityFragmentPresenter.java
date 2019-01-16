package com.capybarasoft.weatherapp.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.capybarasoft.weatherapp.R;
import com.capybarasoft.weatherapp.application.WeatherApplication;
import com.capybarasoft.weatherapp.mvp.model.Model;
import com.capybarasoft.weatherapp.mvp.model.api.util.APIUtil;
import com.capybarasoft.weatherapp.mvp.model.entity.WeatherForecast;
import com.capybarasoft.weatherapp.mvp.view.ChooseCityFragmentView;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


@InjectViewState
public class ChooseCityFragmentPresenter extends BasePresenter<ChooseCityFragmentView> {

    @Inject
    Model mModel;

    public ChooseCityFragmentPresenter() {
        WeatherApplication.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setScreenTitle(R.string.choose_your_city);
    }

    public void searchQuerySubmited(String query){
        getViewState().showProgress();
        unsubscribeOnDestroy(mModel.getForecast(query)
            .subscribe(forecast -> {
                getViewState().hideProgress();
                if (forecast.getCod().equals(APIUtil.CODE_OK)){
                    mModel.saveLastWeatherForecastInPrefs(forecast);
                    WeatherApplication.getApplication().updateWidget();
                    getViewState().openForecastScreen();
                }
                else {
                    getViewState().failedToFindCity(WeatherApplication.getApplication().getString(R.string.city_not_found));
                }
            },
            throwable -> {
                throwable.printStackTrace();
                getViewState().hideProgress();
                getViewState().failedToFindCity(WeatherApplication.getApplication().getString(R.string.city_not_found));
            }));
    }
}
