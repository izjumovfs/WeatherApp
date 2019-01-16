package com.capybarasoft.weatherapp.application;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.capybarasoft.weatherapp.di.AppComponent;
import com.capybarasoft.weatherapp.di.DaggerAppComponent;
import com.capybarasoft.weatherapp.di.modules.ContextModule;
import com.capybarasoft.weatherapp.ui.widget.MyWidget;
import com.facebook.stetho.Stetho;

public class WeatherApplication extends MultiDexApplication {

    private static AppComponent mAppComponent;
    private static WeatherApplication mInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        buildAppComponent();
        Stetho.initializeWithDefaults(this);

    }

    private void buildAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
            .contextModule(new ContextModule(this))
            .build();
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static WeatherApplication getApplication() {
        return mInstance;
    }


    public void updateWidget(){
        int widgetIDs[] = AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(this, MyWidget.class));
        Intent intent = new Intent(this, MyWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIDs);
        sendBroadcast(intent);
    }

}
