package com.example.andrew.testapp.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.andrew.testapp.model.UserModel;
import com.example.andrew.testapp.view.IMainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.subjects.ReplaySubject;

/**
 * Created by Andrew on 01.07.2016.
 */
public class MainPresenter implements IMainPresenter {

    Context context;
    Realm realmDB;
    ReplaySubject<Integer> s;
    private static final String TAG = "MainActivity";
    RealmAsyncTask transaction;

    private IMainActivity view;

    @Inject
    public MainPresenter(IMainActivity view) {
        createReplaySubjectAndSubscribe();
        this.view = view;
        context = view.getContext();
    }

    @Override
    public void initializeDB() {
        realmDB = Realm.getDefaultInstance();
        if(realmDB.isEmpty()){
            fillingDBUserModel();
        } else {
            return;
        }
    }

    private void fillingDBUserModel() {
        List<UserModel> userModelList = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            UserModel user = new UserModel();
            user.setUserName("User" + " " + i);
            user.seteMail(i + "myEMail@" + i + "mail.com");
            user.setPassword(Integer.toString(i));
            userModelList.add(user);
        }
        transaction = realmDB.executeTransactionAsync(transaction -> {
                for (UserModel userModel : userModelList) {
                    transaction.copyToRealmOrUpdate(userModel);
                }
            }, ()-> {
                // Transaction was a success.
            }
        , Throwable::printStackTrace);
    }

    private void cancelAsyncTransaction() {
        if (transaction != null && !transaction.isCancelled()) {
            transaction.cancel();
            transaction = null;
        }
    }

    private void readDBUserModel() {
        Log.d(TAG, "number of user:" + realmDB.where(UserModel.class).count());
        RealmQuery<UserModel> query = realmDB.where(UserModel.class);
        RealmResults<UserModel> userList = query.findAllAsync();
        for (int i = 0; i<userList.size(); i++){
            UserModel user = userList.get(i);
            Log.d(TAG,"Username: " + user.getUserName() + "\n" + "User eMail: " + user.geteMail() + "\n" + "Password: " + user.getPassword() + "\n");
        }
    }

    @Override
    public void onBackPressed() {
        view.popFragmentFromStack();
    }



    private void createReplaySubjectAndSubscribe() {
        s = ReplaySubject.create();
        s.subscribe(v -> toastMessage(v));
    }

    private void toastMessage(Integer i) {
        if (i.equals(2)){
            Toast.makeText(context, Integer.toString(i), Toast.LENGTH_LONG).show();
        }
    }
}