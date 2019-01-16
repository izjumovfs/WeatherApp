package com.capybarasoft.weatherapp.ui.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.capybarasoft.weatherapp.R;
import com.capybarasoft.weatherapp.mvp.model.entity.DailyForecast;


import java.util.ArrayList;

import util.TemperatureConverter;
import util.TimeConverter;


public class DailyForecastListViewHolder extends RecyclerView.ViewHolder {

	private TextView tvDay;
	private TextView tvTemperature;

	private DailyForecast dailyForecast;
	private ArrayList<DailyForecast> dailyForecasts;

	private DailyForecastListAdapter.OnItemClickListener clickListener;

	public DailyForecastListViewHolder(View itemView, DailyForecastListAdapter.OnItemClickListener listener) {
		super(itemView);
		clickListener = listener;
		findViews();
		setListeners();
	}

	private void findViews() {
		tvDay = itemView.findViewById(R.id.tv_day);
		tvTemperature = itemView.findViewById(R.id.tv_temperature);
	}

	private void setListeners() {
		itemView.setOnClickListener(v -> {
			if (DailyForecastListViewHolder.this.getAdapterPosition() != -1) {
				clickListener.onItemClick(dailyForecasts);
			}
		});
	}

	public void onBind(@NonNull DailyForecast dailyForecast, ArrayList<DailyForecast> forecasts) {
		this.dailyForecast = dailyForecast;
		this.dailyForecasts = forecasts;
		tvDay.setText(TimeConverter.getDayOfWeekFromTimeString(dailyForecast.getDate()));
		String minCelciusTemp = String.valueOf(TemperatureConverter.convertKelvinToCelcius(this.dailyForecast.getMain().getTempMin()));
		String maxCelciusTemp = String.valueOf(TemperatureConverter.convertKelvinToCelcius(this.dailyForecast.getMain().getTempMax()));
		String temp = minCelciusTemp + (char) 0x00B0 + " / " + maxCelciusTemp + (char) 0x00B0;
		tvTemperature.setText(temp);
	}

}
