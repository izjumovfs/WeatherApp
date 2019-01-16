package com.capybarasoft.weatherapp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DailyForecast {

    @SerializedName("dt")
    private long dt;

    @SerializedName("main")
    private Main main;

    @SerializedName("weather")
    private ArrayList<Weather> weather;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("dt_txt")
    private String dtTxt;

    public long getDt(){
        return dt;
    }

    public Main getMain(){
        return main;
    }

    public ArrayList<Weather> getWeather(){
        return weather;
    }

    public Wind getWind(){
        return wind;
    }

    public String getDate(){
        return dtTxt;
    }


    public class Main {
        @SerializedName("temp")
        private float temp;

        @SerializedName("temp_min")
        private float tempMin;

        @SerializedName("temp_max")
        private float tempMax;

        @SerializedName("pressure")
        private float pressure;

        @SerializedName("humidity")
        private int humidity;

        public float getTemp(){
            return temp;
        }

        public float getTempMin(){
            return tempMin;
        }

        public float getTempMax(){
            return tempMax;
        }

        public float getPressure(){
            return pressure;
        }

        public int getHumidity(){
            return humidity;
        }
    }


    public class Weather{

        @SerializedName("main")
        private String main;

        @SerializedName("desription")
        private String desription;

        public String getMain(){
            return main;
        }

        public String getDesription(){
            return desription;
        }
    }


    public class Wind {

        @SerializedName("speed")
        private float speed;

        @SerializedName("deg")
        private float deg;

        public float getSpeed(){
            return speed;
        }

        public float getDeg() {
            return deg;
        }


    }
}
