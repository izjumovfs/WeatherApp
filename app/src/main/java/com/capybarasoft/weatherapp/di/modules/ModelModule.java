package com.capybarasoft.weatherapp.di.modules;


import android.content.Context;

import com.capybarasoft.weatherapp.mvp.model.api.ApiInterface;
import com.capybarasoft.weatherapp.mvp.model.api.ApiModule;
import com.capybarasoft.weatherapp.mvp.model.api.util.APIUtil;
import com.capybarasoft.weatherapp.mvp.model.preferences.SharedPrefsModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    @Provides
    ApiInterface provideApiInterface() {
        return ApiModule.getApiInterface(APIUtil.BASE_URL);
    }

    @Provides
    @Singleton
    SharedPrefsModule provideSharedPrefsModule(Context context) {
        return new SharedPrefsModule(context);
    }
}
