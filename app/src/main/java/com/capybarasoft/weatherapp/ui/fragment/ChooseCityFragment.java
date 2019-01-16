package com.capybarasoft.weatherapp.ui.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.capybarasoft.weatherapp.R;
import com.capybarasoft.weatherapp.mvp.presenter.ChooseCityFragmentPresenter;
import com.capybarasoft.weatherapp.mvp.view.ChooseCityFragmentView;
import com.capybarasoft.weatherapp.ui.activity.ForecastActivity;

import util.SnackBarManager;

public class ChooseCityFragment extends BaseFragment implements ChooseCityFragmentView, SearchView.OnQueryTextListener {

    @InjectPresenter
    ChooseCityFragmentPresenter presenter;

    private SearchView searchView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_city, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        getActivity().finish();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.choose_city_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);

        if (search == null) return;
        setUpSearchView(search);
    }

    @Override
    public void setScreenTitle(int title) {
        tvTitle.setText(title);
    }

    private void setUpSearchView(MenuItem search) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) search.getActionView();
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
        searchView.setQueryHint(getString(R.string.search));
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        presenter.searchQuerySubmited(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }


    @Override
    public void showProgress(){
        showProgressDialog();
    }

    @Override
    public void hideProgress(){
        hideProgressDialog();
    }

    @Override
    public void failedToFindCity(String error){
        SnackBarManager.show(getActivity(), error, Snackbar.LENGTH_LONG);
    }

    @Override
    public void openForecastScreen(){
        Intent intent = new Intent(getActivity(), ForecastActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
