package com.example.andrew.testapp.di.components;

import com.example.andrew.testapp.di.ActivityScope;
import com.example.andrew.testapp.di.modules.MainActivityModule;
import com.example.andrew.testapp.view.MSCCategoryView;
import com.example.andrew.testapp.view.MainActivity;
import com.example.andrew.testapp.view.ViewUserList;

import dagger.Component;

/**
 * Created by Andrew on 06.07.2016.
 */

@ActivityScope
@Component(
        dependencies = ITestAppComponent.class,
        modules = MainActivityModule.class
)
public interface IMainActivityComponent {
    void inject(MainActivity activity);
    void inject(ViewUserList viewUserList);
    void inject(MSCCategoryView mscCategoryView);
}
