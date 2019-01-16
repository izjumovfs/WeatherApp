package com.capybarasoft.weatherapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.capybarasoft.weatherapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseFragment extends MvpAppCompatFragment {

    protected AlertDialog mProgressDialog;


    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.tv_toolbar_title)
    protected TextView tvTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupToolbar();
        prepareProgressDialog();
    }


    public abstract void onBackPressed();

    private void setupToolbar() {
        if (mToolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            if ((getActivity()) != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

                mToolbar.setNavigationOnClickListener(v -> onBackPressed());
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void prepareProgressDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_dialog);
        mProgressDialog = builder.create();
    }


    protected void showProgressDialog(){
        mProgressDialog.show();
    }

    protected void hideProgressDialog(){
        mProgressDialog.dismiss();
    }
}
