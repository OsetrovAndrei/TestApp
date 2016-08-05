package com.example.andrew.testapp.app;

import android.app.Application;
import android.content.Context;

import com.example.andrew.testapp.di.components.DaggerITestAppComponent;
import com.example.andrew.testapp.di.components.ITestAppComponent;
import com.example.andrew.testapp.di.modules.TestAppModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Andrew on 02.07.2016.
 */
public class TestApp extends Application {
    private ITestAppComponent appComponent;

    public static TestApp get(Context context) {
        return (TestApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
        buildGraphAndInject();
    }

    public ITestAppComponent getAppComponent() {
        return appComponent;
    }

    public void buildGraphAndInject() {
        appComponent = DaggerITestAppComponent.builder()
                .testAppModule(new TestAppModule(this))
                .build();
        appComponent.inject(this);
    }
}
