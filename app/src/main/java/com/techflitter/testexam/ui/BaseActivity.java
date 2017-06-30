package com.techflitter.testexam.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * Created by TechFlitter on 6/30/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    protected void init() {
    }

    protected void initView() {
    }

    protected abstract int getContentViewId();
}
