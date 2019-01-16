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
import java.util.List;



public class DailyForecastListAdapter extends RecyclerView.Adapter  {

	private HashMap<String, ArrayList<DailyForecast>> forecastsMap = new HashMap<>();
	private ArrayList<String> mKeys;

	private OnItemClickListener listener;

	public void setListener(OnItemClickListener listener) {
		this.listener = listener;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		final View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.daily_forecast_item, parent, false);
		return new DailyForecastListViewHolder(view, listener);
	}


	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		ArrayList<DailyForecast> forecasts = forecastsMap.get(mKeys.get(position));
		((DailyForecastListViewHolder) holder).onBind(forecasts.get(0), forecasts);
	}

	@Override
	public int getItemCount() {
		return forecastsMap.size();
	}

	public void setItems(HashMap<String, ArrayList<DailyForecast>> forecasts, ArrayList<String> keys) {
		this.forecastsMap = forecasts;
		this.mKeys = keys;
	}

	public interface OnItemClickListener {
		void onItemClick(ArrayList<DailyForecast> forecasts);
	}
}
