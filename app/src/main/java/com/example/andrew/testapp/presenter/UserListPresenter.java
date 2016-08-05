package com.example.andrew.testapp.presenter;

import android.util.Log;

import com.example.andrew.testapp.model.UserModel;
import com.example.andrew.testapp.view.IViewUserList;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Andrew on 06.07.2016.
 */
public class UserListPresenter implements IUserListPresenter {

    private IViewUserList view;
    Realm realmDB;

    @Inject
    public UserListPresenter(){
    }


    @Override
    public void init(IViewUserList view) {
        this.view = view;
    }

    private rx.Observable<RealmResults<UserModel>> getUserList() {
        realmDB = Realm.getDefaultInstance();
        return realmDB.where(UserModel.class).findAllAsync().asObservable();
    }

    @Override
    public void onResume() {
        getUserList()
                .subscribe(users ->  {
                    Log.d("users", users.toString());
                    view.setUserRecyclerViewAdapter(users);
                }, Throwable::printStackTrace);
    }
}
