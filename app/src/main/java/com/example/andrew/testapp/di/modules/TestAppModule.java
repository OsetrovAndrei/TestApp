package com.example.andrew.testapp.di.modules;

import android.app.Application;

import com.example.andrew.testapp.app.TestApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andrew on 06.07.2016.
 */
@Module
public class TestAppModule {
    private final TestApp app;

    public TestAppModule(TestApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }
}
