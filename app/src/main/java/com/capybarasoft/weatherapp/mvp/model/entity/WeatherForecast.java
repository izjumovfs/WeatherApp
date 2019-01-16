package com.capybarasoft.weatherapp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherForecast {

    @SerializedName("cod")
    private String cod;

    @SerializedName("list")
    private ArrayList<DailyForecast> dailyForecasts;

    @SerializedName("city")
    private City city;

    public String getCod(){
        return cod;
    }

    public ArrayList<DailyForecast> getDailyForecasts(){
        return dailyForecasts;
    }

    public City getCity(){
        return city;
    }

    public class City{

        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        public int getId(){
            return id;
        }

        public String getName(){
            return name;
        }

    }

}
