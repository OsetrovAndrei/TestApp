package com.example.andrew.testapp.presenter;

import com.example.andrew.testapp.common.BaseFragmentPresenter;
import com.example.andrew.testapp.view.IViewUserList;

/**
 * Created by Andrew on 06.07.2016.
 */
public interface IUserListPresenter extends BaseFragmentPresenter<IViewUserList> {
    void onResume();
}
