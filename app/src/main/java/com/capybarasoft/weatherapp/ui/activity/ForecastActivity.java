package com.capybarasoft.weatherapp.ui.activity;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.capybarasoft.weatherapp.ui.fragment.ChooseCityFragment;
import com.capybarasoft.weatherapp.ui.fragment.ForecastFragment;

import util.FragmentTransactionManager;

public class ForecastActivity extends MvpAppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FragmentTransactionManager.displayFragment(
                getSupportFragmentManager(),
                new ForecastFragment(),
                android.R.id.content,
                false
        );
    }
}
