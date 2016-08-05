package com.example.andrew.testapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrew.testapp.R;
import com.example.andrew.testapp.model.UserModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Andrew on 07.07.2016.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private RealmResults<UserModel> userList;
    Realm realmDB = Realm.getDefaultInstance();


    public UserListAdapter(RealmResults<UserModel> userList){
        this.userList = userList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_user_list_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserModel user = userList.get(position);
        holder.name.setText(user.getUserName());
        holder.eMail.setText(user.geteMail());
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        TextView name;

        @BindView(R.id.textView2)
        TextView eMail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    // Insert a new item to the RecyclerView
    public void insert(UserModel data) {
        realmDB.executeTransactionAsync(transaction -> {
                transaction.copyToRealmOrUpdate(data);
        }, error ->   {
            Log.d("err", error.toString());
        });
    }

    // Remove a RecyclerView item containing the Data object
    public void remove(int index) {
        realmDB.executeTransactionAsync(realm ->{
            userList.deleteFromRealm(index);
            Log.i("thread", Thread.currentThread().getName());}, ()-> {
                // Transaction was a success.
        }, Throwable::printStackTrace);
        notifyItemRemoved(index);
    }
}
