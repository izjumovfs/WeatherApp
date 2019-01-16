package com.capybarasoft.weatherapp.mvp.view;

import com.arellomobile.mvp.MvpView;

public interface ChooseCityFragmentView extends MvpView {

    void showProgress();
    void hideProgress();
    void failedToFindCity(String error);
    void setScreenTitle(int title);
    void openForecastScreen();
}
