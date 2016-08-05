package com.example.andrew.testapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrew.testapp.R;
import com.example.andrew.testapp.model.UserModel;
import com.example.andrew.testapp.adapter.UserListAdapter;
import com.example.andrew.testapp.common.BaseFragment;
import com.example.andrew.testapp.di.components.IMainActivityComponent;
import com.example.andrew.testapp.presenter.UserListPresenter;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

/**
 * Created by Andrew on 06.07.2016.
 */
public class ViewUserList extends BaseFragment implements IViewUserList{

    @Inject
    UserListPresenter userListPresenter;

    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;


    private static final String KEY_TRANSITION_EFFECT = "transition_effect";
    View rootView;
    private RecyclerView recyclerView;
    UserListAdapter userListAdapter;
    private int mCurrentTransitionEffect = JazzyHelper.HELIX;
    private JazzyRecyclerViewScrollListener jazzyScrollListener;

    @OnClick(R.id.fab_add)
    public void onClick(View view) {
        UserModel user = new UserModel();
        user.setUserName("AddUser" + userListAdapter.getItemCount());
        user.setPassword("User");
        user.seteMail("MyMail");
        int i = userListAdapter.getItemCount();
        userListAdapter.insert(user);
    }


    @Override
    public void onResume() {
        super.onResume();
        userListPresenter.init(this);
        userListPresenter.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_user_list, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.view);
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
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public Context getUserContext() {
        return getActivity().getBaseContext();
    }

    private void setSwipeDeleteItemListener(RecyclerView recyclerView){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                userListAdapter.remove(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void setUserRecyclerViewAdapter(RealmResults<UserModel> userList) {
        if (userListAdapter == null){
            userListAdapter = new UserListAdapter(userList);
            LinearLayoutManager llm = new LinearLayoutManager(getUserContext());
            recyclerView.setLayoutManager(llm);
            jazzyScrollListener = new JazzyRecyclerViewScrollListener();
            recyclerView.setOnScrollListener(jazzyScrollListener);
            recyclerView.setAdapter(userListAdapter);
            setSwipeDeleteItemListener(recyclerView);
        } else {
            userListAdapter.notifyItemInserted(userListAdapter.getItemCount() + 1);
        }

    }

    private void setupJazziness(int effect) {
        mCurrentTransitionEffect = effect;
        jazzyScrollListener.setTransitionEffect(mCurrentTransitionEffect);
    }
}
