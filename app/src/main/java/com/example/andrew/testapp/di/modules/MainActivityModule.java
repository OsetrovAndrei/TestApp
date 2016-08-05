package com.example.andrew.testapp.di.modules;

import com.example.andrew.testapp.presenter.MSCCategoryPresenter;
import com.example.andrew.testapp.presenter.MainPresenter;
import com.example.andrew.testapp.presenter.UserListPresenter;
import com.example.andrew.testapp.view.IMainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andrew on 06.07.2016.
 */
@Module
public class MainActivityModule {

    IMainActivity view;

    public MainActivityModule(IMainActivity view) {
        this.view = view;
    }

    @Provides
    public IMainActivity provideView() {
        return view;
    }

    @Provides
    public MainPresenter provideMainActivityPresenterImpl (IMainActivity view){
        return  new MainPresenter(view);
    }

    @Provides
    public UserListPresenter provideUserListPresenter(){
        return new UserListPresenter();
    }

    @Provides
    public MSCCategoryPresenter provideMSCCategoryPresenter(){
        return new MSCCategoryPresenter();
    }

}
