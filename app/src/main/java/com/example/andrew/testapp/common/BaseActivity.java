package com.example.andrew.testapp.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.andrew.testapp.app.TestApp;
import com.example.andrew.testapp.di.components.ITestAppComponent;

/**
 * Created by Andrew on 06.07.2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(TestApp.get(this).getAppComponent());
    }

    protected abstract void setupComponent(ITestAppComponent appComponent);
}
