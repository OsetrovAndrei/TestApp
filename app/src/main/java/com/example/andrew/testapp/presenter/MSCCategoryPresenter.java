package com.example.andrew.testapp.presenter;

import android.util.Log;

import com.example.andrew.testapp.network.Model;
import com.example.andrew.testapp.view.IMSCCategoryView;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Andrew on 29.07.2016.
 */
public class MSCCategoryPresenter implements IMSCCategoryPresenter {
    private IMSCCategoryView view;

    @Inject
    public MSCCategoryPresenter(){

    }

    @Override
    public void init(IMSCCategoryView view) {
        this.view = view;
    }

    private void setCategoryDataToAdapter(){
        Subscription listDataSubscription = Model.getInstance().getSelectedNewsFeed()
                .subscribe(categories ->  {
                        Log.d("categories", categories.toString());
                        view.setCategoryAdapter(categories);
                });
    }

    @Override
    public void onResume() {
        setCategoryDataToAdapter();
    }
}
