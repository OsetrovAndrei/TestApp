package com.example.andrew.testapp.di.components;

import com.example.andrew.testapp.app.TestApp;
import com.example.andrew.testapp.di.modules.TestAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Andrew on 06.07.2016.
 */

@Singleton
@Component(
        modules = {
                TestAppModule.class
        }
)
public interface ITestAppComponent {
    void inject(TestApp app);
}
