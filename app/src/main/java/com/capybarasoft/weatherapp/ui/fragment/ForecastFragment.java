package com.capybarasoft.weatherapp.ui.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.capybarasoft.weatherapp.R;
import com.capybarasoft.weatherapp.mvp.model.entity.DailyForecast;
import com.capybarasoft.weatherapp.mvp.presenter.ForecastFragmentPresenter;
import com.capybarasoft.weatherapp.mvp.view.ForecastFragmentView;
import com.capybarasoft.weatherapp.ui.activity.CityChooserActivity;
import com.capybarasoft.weatherapp.ui.adapters.DailyForecastListAdapter;
import com.capybarasoft.weatherapp.ui.adapters.HourlyForecastListAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import util.SnackBarManager;
import util.TemperatureConverter;
import util.TimeConverter;

public class ForecastFragment extends BaseFragment implements ForecastFragmentView, SwipeRefreshLayout.OnRefreshListener {

    @InjectPresenter
    ForecastFragmentPresenter presenter;

    @BindView(R.id.tv_temperature)
    TextView tvTemperature;

    @BindView(R.id.tv_humidity)
    TextView tvHumidity;

    @BindView(R.id.tv_wind)
    TextView tvWind;

    @BindView(R.id.tv_current_date)
    TextView tvCurrentDate;

    @BindView(R.id.rv_days_forecasts)
    RecyclerView rvDailyForecasts;

    @BindView(R.id.rv_hourly_forecasts)
    RecyclerView rvHourlyForecasts;

    @BindView(R.id.srl_refresh_forecast)
    SwipeRefreshLayout srlRefreshForecast;

    private DailyForecastListAdapter dailyForecastListAdapter;

    private HourlyForecastListAdapter hourlyForecastListAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        srlRefreshForecast.setOnRefreshListener(this);
        initRecycler();
        presenter.loadSavedForecast();
    }

    @Override
    public void onBackPressed() {
        getActivity().finish();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                presenter.searchClicked();
            break;
            case R.id.action_location_search:
                presenter.locationClicked();
            break;
        }

        return true;
    }

    private void initRecycler() {
        dailyForecastListAdapter = new DailyForecastListAdapter();
        rvDailyForecasts.setLayoutManager(new LinearLayoutManager(getActivity()));
        dailyForecastListAdapter.setListener(forecasts -> presenter.dailyForecastClicked(forecasts));
        rvDailyForecasts.setAdapter(dailyForecastListAdapter);


        hourlyForecastListAdapter = new HourlyForecastListAdapter();
        rvHourlyForecasts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        hourlyForecastListAdapter.setListener(forecast -> {
            presenter.hourlyForecastClicked(forecast);
        });
        rvHourlyForecasts.setAdapter(hourlyForecastListAdapter);
    }


    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public void setScreenTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setHourlyForecasts(ArrayList<DailyForecast> forecasts) {
        hourlyForecastListAdapter.setItems(forecasts);

        DailyForecast forecast = forecasts.get(0);
        presenter.hourlyForecastClicked(forecast);

    }

    @Override
    public void setDailyForecasts(HashMap<String, ArrayList<DailyForecast>> forecastsMap, ArrayList<String> keys) {
        dailyForecastListAdapter.setItems(forecastsMap, keys);
        rvDailyForecasts.scrollToPosition(0);
    }


    @Override
    public void hideSwipeRefreshLayout() {
        if (srlRefreshForecast.isRefreshing()) {
            srlRefreshForecast.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        presenter.reloadCurrentForecast();
    }


    @Override
    public void failedToReloadForecast(String message) {
        SnackBarManager.show(getActivity(), message, Snackbar.LENGTH_LONG);
    }

    @Override
    public void showSearchCityScreen() {
        Intent intent = new Intent(getActivity(), CityChooserActivity.class);
        startActivity(intent);
    }


    @Override
    public void initLocationListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getActivity());
        client.getLastLocation()
            .addOnCompleteListener(getActivity(), task -> {
               Location location = task.getResult();
                presenter.onLocationReceived(location);
            });
    }

    @Override
    public void failedToRetreiveLocation(String message) {
        SnackBarManager.show(getActivity(), message, Snackbar.LENGTH_LONG);
    }


    @Override
    public void displayCurrentForecastData(DailyForecast forecast){
        String minCelciusTemp = String.valueOf(TemperatureConverter.convertKelvinToCelcius(forecast.getMain().getTempMin()));
        String maxCelciusTemp = String.valueOf(TemperatureConverter.convertKelvinToCelcius(forecast.getMain().getTempMax()));
        String temp = minCelciusTemp + (char) 0x00B0 + " / " + maxCelciusTemp + (char) 0x00B0;
        tvTemperature.setText(temp);
        tvHumidity.setText(forecast.getMain().getHumidity() + " %");
        tvWind.setText(String.valueOf(forecast.getWind().getSpeed()));
        tvCurrentDate.setText(TimeConverter.getDayAndMonthFromString(forecast.getDate()));
    }

}
