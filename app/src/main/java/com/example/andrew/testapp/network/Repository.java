package com.example.andrew.testapp.network;

import android.support.annotation.UiThread;

import com.example.andrew.testapp.model.Category;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Andrew on 28.07.2016.
 */
public class Repository implements Closeable {

    private static final long MINIMUM_NETWORK_WAIT_SEC = 120; // Minimum 2 minutes between each network request

    private final Realm realm;
    private final CategoryDataLoader dataLoader;
    private Map<String, Long> lastNetworkRequest = new HashMap<>();

    @UiThread
    public Repository() {
        realm = Realm.getDefaultInstance();
        dataLoader = new CategoryDataLoader();
    }


    /**
     * Loads the news feed as well as all future updates.
     */
    @UiThread
    public Observable<RealmResults<Category>> loadNewsFeed(boolean forceReload) {
        // Start loading data from the network if needed
        // It will put all data into Realm
        if (forceReload || timeSinceLastNetworkRequest() > MINIMUM_NETWORK_WAIT_SEC) {
            dataLoader.loadData();
            lastNetworkRequest.put("request",System.currentTimeMillis());
        }

        // Return the data in Realm. The query result will be automatically updated when the network requests
        // save data in Realm
        return realm.where(Category.class).findAllAsync().asObservable();
    }

    private long timeSinceLastNetworkRequest() {
        Long lastRequest = lastNetworkRequest.get("request");
        if (lastRequest != null) {
            return TimeUnit.SECONDS.convert(System.currentTimeMillis() - lastRequest, TimeUnit.MILLISECONDS);
        } else {
            return Long.MAX_VALUE;
        }
    }


    /**
     * Returns story details
     */
    @UiThread
    public Observable<Category> loadCategory() {
        return realm.where(Category.class).findFirstAsync()
                .<Category>asObservable()
                .filter(new Func1<Category, Boolean>() {
                    @Override
                    public Boolean call(Category category) {
                        return category.isLoaded();
                    }
                });
    }

    /**
     * Closes all underlying resources used by the Repository.
     */
    @UiThread
    public void close() {
        realm.close();
    }

}
