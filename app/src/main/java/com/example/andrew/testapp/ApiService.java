package com.example.andrew.testapp;

import com.example.andrew.testapp.model.Category;
import com.example.andrew.testapp.model.CategoryResponse;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Andrew on 13.06.2016.
 */

public interface ApiService {

    @GET("get_categories_list/?lang=en&city=-1/")
    Observable<CategoryResponse<List<Category>>> loadCategory();
}

