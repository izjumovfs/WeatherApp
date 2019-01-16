package com.capybarasoft.weatherapp.di.modules;


import com.capybarasoft.weatherapp.mvp.model.Model;
import com.capybarasoft.weatherapp.mvp.model.ModelImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    Model provideDataRepository(ModelImpl model) {
        return model;
    }
}
