package com.example.andrew.testapp.view;

import android.content.Context;

import com.example.andrew.testapp.model.UserModel;

import io.realm.RealmResults;

/**
 * Created by Andrew on 06.07.2016.
 */
public interface IViewUserList {
    Context getUserContext();
    void setUserRecyclerViewAdapter(RealmResults<UserModel> userList);

}
