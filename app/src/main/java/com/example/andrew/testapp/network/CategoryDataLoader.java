package com.example.andrew.testapp.network;

import android.util.Log;

import com.example.andrew.testapp.ApiService;
import com.example.andrew.testapp.model.Category;

import java.util.List;

import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Andrew on 28.07.2016.
 */
public class CategoryDataLoader {
    Realm realmDB;
    ApiService service;

    public CategoryDataLoader(){
        realmDB = Realm.getDefaultInstance();
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://www.online-kharkov.com/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                        build();
        service = retrofit.create(ApiService.class);
    }

    public void loadData() {
        loadNextSection();
    }

    private void loadNextSection() {
        service.loadCategory()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->   {
                        Log.d("response", response.results.toString());
                        Log.i("thread", Thread.currentThread().getName());
                        processAndAddData(response.results);
                }, Throwable::printStackTrace);
    }

    private void processAndAddData(final List<Category> category) {
        if (category == null) return;

        realmDB.executeTransactionAsync(trnsaction -> {
                for (Category curentCategory : category) {
                    trnsaction.copyToRealmOrUpdate(curentCategory);
                    Log.i("thread", Thread.currentThread().getName());
                }
        }, error ->   {
                Log.d("err", error.toString());
        });
    }
}
