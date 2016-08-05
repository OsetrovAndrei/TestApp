package com.example.andrew.testapp.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.andrew.testapp.model.Category;

import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Andrew on 28.07.2016.
 */
public class Model {

    private static Model instance = null;
    private final Repository repository;

    // This could be replaced by Dependency Injection for easier testing
    public static synchronized Model getInstance() {
        if (instance == null) {
            Repository repository = new Repository();
            instance = new Model(repository);
        }
        return instance;
    }

    private Model(Repository repository) {
        this.repository = repository;
    }

    /**
     * Returns the news feed for the currently selected category.
     */
    public Observable<RealmResults<Category>> getSelectedNewsFeed() {
        return repository.loadNewsFeed(false);
    }

    /**
     * Forces a reload of the newsfeed
     */
    public void reloadNewsFeed() {
        repository.loadNewsFeed(true);
    }


    /**
     * Returns the story with the given Id
     */
    public Observable<Category> getCategory(@NonNull final String storyId) {
        // Repository is only responsible for loading the data
        // Any validation is done by the model
        // See http://blog.danlew.net/2015/12/08/error-handling-in-rxjava/
        if (TextUtils.isEmpty(storyId)) {
            throw new IllegalArgumentException("Invalid storyId: " + storyId);
        }
        return repository.loadCategory()
                .filter(new Func1<Category, Boolean>() {
                    @Override
                    public Boolean call(Category category) {
                        return category.isValid();
                    }
                });
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        repository.close();
    }
}

