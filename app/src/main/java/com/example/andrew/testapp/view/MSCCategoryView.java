package com.example.andrew.testapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrew.testapp.R;
import com.example.andrew.testapp.adapter.CategoryAdapter;
import com.example.andrew.testapp.common.BaseFragment;
import com.example.andrew.testapp.di.components.IMainActivityComponent;
import com.example.andrew.testapp.model.Category;
import com.example.andrew.testapp.presenter.MSCCategoryPresenter;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by Andrew on 29.07.2016.
 */
public class MSCCategoryView extends BaseFragment implements IMSCCategoryView {
    @Inject
    MSCCategoryPresenter mscCategoryPresenter;

    @BindView(R.id.msc_category_recycler)
    RecyclerView recyclerView;

    View rootView;
    private static final String KEY_TRANSITION_EFFECT = "transition_effect";
    private int mCurrentTransitionEffect = JazzyHelper.HELIX;
    private JazzyRecyclerViewScrollListener jazzyScrollListener;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_msc_category, container, false);
            ButterKnife.bind(this, rootView);
        }
        if (savedInstanceState != null) {
            try {
                mCurrentTransitionEffect = savedInstanceState.getInt(KEY_TRANSITION_EFFECT, JazzyHelper.HELIX);
                setupJazziness(mCurrentTransitionEffect);
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mscCategoryPresenter.init(this);
        mscCategoryPresenter.onResume();
    }

    private void setupJazziness(int effect) {
        mCurrentTransitionEffect = effect;
        jazzyScrollListener.setTransitionEffect(mCurrentTransitionEffect);
    }

    @Override
    public void setCategoryAdapter(RealmResults<Category> categoryList) {
        categoryAdapter = new CategoryAdapter(categoryList, getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        jazzyScrollListener = new JazzyRecyclerViewScrollListener();
        recyclerView.setOnScrollListener(jazzyScrollListener);
        recyclerView.setAdapter(categoryAdapter);
    }
}
