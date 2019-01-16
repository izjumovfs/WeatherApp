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


public class HourlyForecastListViewHolder extends RecyclerView.ViewHolder {

	private TextView tvTime;
	private TextView tvTemperature;

	private DailyForecast dailyForecast;

	private HourlyForecastListAdapter.OnItemClickListener clickListener;

	public HourlyForecastListViewHolder(View itemView, HourlyForecastListAdapter.OnItemClickListener listener) {
		super(itemView);
		clickListener = listener;
		findViews();
		setListeners();
	}

	private void findViews() {
		tvTime = itemView.findViewById(R.id.tv_time);
		tvTemperature = itemView.findViewById(R.id.tv_temperature);
	}

	private void setListeners() {
		itemView.setOnClickListener(v -> {
			if (HourlyForecastListViewHolder.this.getAdapterPosition() != -1) {
				clickListener.onItemClick(dailyForecast);
			}
		});
	}

	public void onBind(@NonNull DailyForecast dailyForecast) {
		this.dailyForecast = dailyForecast;
		tvTime.setText(TimeConverter.getHours(dailyForecast.getDate()));
		String minCelciusTemp = String.valueOf(TemperatureConverter.convertKelvinToCelcius(this.dailyForecast.getMain().getTempMin()));
		String maxCelciusTemp = String.valueOf(TemperatureConverter.convertKelvinToCelcius(this.dailyForecast.getMain().getTempMax()));
		String temp = minCelciusTemp + (char) 0x00B0 + " / " + maxCelciusTemp + (char) 0x00B0;
		tvTemperature.setText(temp);
	}

}
