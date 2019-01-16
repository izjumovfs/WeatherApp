package com.capybarasoft.weatherapp.di;

import android.content.Context;

import com.capybarasoft.weatherapp.di.modules.ContextModule;
import com.capybarasoft.weatherapp.di.modules.ModelModule;
import com.capybarasoft.weatherapp.di.modules.PresenterModule;
import com.capybarasoft.weatherapp.mvp.model.ModelImpl;
import com.capybarasoft.weatherapp.mvp.presenter.ForecastFragmentPresenter;
import com.capybarasoft.weatherapp.mvp.presenter.MainActivityPresenter;
import com.capybarasoft.weatherapp.mvp.presenter.ChooseCityFragmentPresenter;
import com.capybarasoft.weatherapp.ui.widget.MyWidget;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, ModelModule.class, PresenterModule.class})
public interface AppComponent {

    Context getContext();

    void inject(ModelImpl modelImpl);
    void inject(MainActivityPresenter mainActivityPresenter);
    void inject(ChooseCityFragmentPresenter searchFragmentPresenter);
    void inject(ForecastFragmentPresenter forecastFragmentPresenter);

    void inject (MyWidget widget);
}
