

package com.capybarasoft.weatherapp.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.capybarasoft.weatherapp.R;
import com.capybarasoft.weatherapp.application.WeatherApplication;
import com.capybarasoft.weatherapp.mvp.model.Model;
import com.capybarasoft.weatherapp.mvp.model.entity.DailyForecast;
import com.capybarasoft.weatherapp.mvp.model.entity.WeatherForecast;
import com.capybarasoft.weatherapp.ui.activity.MainActivity;

import javax.inject.Inject;

import util.TemperatureConverter;
import util.TimeConverter;


public class MyWidget extends AppWidgetProvider {

    static final String ACTION_ON_CLICK = "widget.ON_ITEM_CLICK";


    @Inject
    public Model mModel;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        WeatherApplication.getAppComponent().inject(this);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        WeatherApplication.getAppComponent().inject(this);
        for (int i : appWidgetIds) {
            updateWidget(context, appWidgetManager, i);
        }
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }


    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        WeatherForecast mCurrentForecast = mModel.getLastSavedWeatherForecast();
        for (DailyForecast dailyForecast : mCurrentForecast.getDailyForecasts()){
            if (TimeConverter.equalsCurrentDate(dailyForecast)){
                String minCelciusTemp = String.valueOf(TemperatureConverter.convertKelvinToCelcius(dailyForecast.getMain().getTempMin()));
                String maxCelciusTemp = String.valueOf(TemperatureConverter.convertKelvinToCelcius(dailyForecast.getMain().getTempMax()));
                String temp = minCelciusTemp + (char) 0x00B0 + " / " + maxCelciusTemp + (char) 0x00B0;
                rv.setTextViewText(R.id.tv_temperature, temp);
                rv.setTextViewText(R.id.tv_humidity, dailyForecast.getMain().getHumidity() + " %");
                rv.setTextViewText(R.id.tv_wind, String.valueOf(dailyForecast.getWind().getSpeed()));
                rv.setTextViewText(R.id.tv_current_date, TimeConverter.getDayAndMonthFromString(dailyForecast.getDate()));
                rv.setTextViewText(R.id.tv_city, mCurrentForecast.getCity().getName());
                break;
            }
        }
        setClickIntent(rv);
        appWidgetManager.updateAppWidget(widgetId, rv);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(ACTION_ON_CLICK)) {
            Intent mainIntent = new Intent(context, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(mainIntent);
        }
    }


    private void setClickIntent(RemoteViews rv){
        Intent listClickIntent = new Intent(WeatherApplication.getApplication(), MyWidget.class);
        listClickIntent.setAction(ACTION_ON_CLICK);
        PendingIntent listClickPIntent = PendingIntent.getBroadcast(WeatherApplication.getApplication(), 0, listClickIntent, 0);
        rv.setOnClickPendingIntent(R.id.widget_container, listClickPIntent);
    }

}
