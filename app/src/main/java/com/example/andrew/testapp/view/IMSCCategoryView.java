package com.example.andrew.testapp.view;

import com.example.andrew.testapp.model.Category;

import io.realm.RealmResults;

/**
 * Created by Andrew on 29.07.2016.
 */
public interface IMSCCategoryView {
    void setCategoryAdapter(RealmResults<Category> categoryList);
}
