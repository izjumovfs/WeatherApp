package com.capybarasoft.weatherapp.mvp.model.api;

import com.capybarasoft.weatherapp.mvp.model.api.util.APIUtil;
import com.capybarasoft.weatherapp.mvp.model.entity.WeatherForecast;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET(APIUtil.FORECAST)
    @Headers("Content-Type: application/json")
    Observable<WeatherForecast> getForecast(@Query("q") String city, @Query("appid") String appId);


    @GET(APIUtil.FORECAST)
    @Headers("Content-Type: application/json")
    Observable<WeatherForecast> getForecast(@Query("lat")float lat, @Query("lon") float lon, @Query("appid") String appId);
}
