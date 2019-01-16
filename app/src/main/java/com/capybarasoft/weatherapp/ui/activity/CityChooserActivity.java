package com.capybarasoft.weatherapp.ui.activity;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.capybarasoft.weatherapp.ui.fragment.ChooseCityFragment;

import util.FragmentTransactionManager;

public class CityChooserActivity extends MvpAppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FragmentTransactionManager.displayFragment(
                getSupportFragmentManager(),
                new ChooseCityFragment(),
                android.R.id.content,
                false
        );
    }
}
