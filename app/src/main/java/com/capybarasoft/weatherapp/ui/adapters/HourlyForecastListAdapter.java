package com.capybarasoft.weatherapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capybarasoft.weatherapp.R;
import com.capybarasoft.weatherapp.mvp.model.entity.DailyForecast;

import java.util.ArrayList;
import java.util.HashMap;


public class HourlyForecastListAdapter extends RecyclerView.Adapter  {

	private ArrayList<DailyForecast> mForecasts;

	private OnItemClickListener listener;

	public void setListener(OnItemClickListener listener) {
		this.listener = listener;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		final View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.hourly_forecast_item, parent, false);
		return new HourlyForecastListViewHolder(view, listener);
	}


	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		((HourlyForecastListViewHolder) holder).onBind(mForecasts.get(position));
	}

	@Override
	public int getItemCount() {
		return mForecasts.size();
	}

	public void setItems(ArrayList<DailyForecast> forecasts) {
		this.mForecasts = forecasts;
		notifyDataSetChanged();
	}

	public interface OnItemClickListener {
		void onItemClick(DailyForecast forecast);
	}
}
