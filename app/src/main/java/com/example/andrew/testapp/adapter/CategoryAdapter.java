package com.example.andrew.testapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andrew.testapp.R;
import com.example.andrew.testapp.model.Category;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Andrew on 29.07.2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private RealmResults<Category> categoryList;
    private Context context;


    public CategoryAdapter(RealmResults<Category> categoryList, Context context){
        this.categoryList = categoryList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_msc_category_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.name.setText(category.getName());
        try {
            Uri uri = Uri.parse(category.getImage());
            Picasso.with(context).load(uri).placeholder(R.drawable.placeholder).into(holder.image);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.category_name);
            image = (ImageView) itemView.findViewById(R.id.category_image);
            itemView.setOnLongClickListener(onLongClickListener);
        }

        View.OnLongClickListener onLongClickListener = view ->  {
            remove(this.getAdapterPosition());
            return true;
        };
    }

    public void remove(int index) {
        Realm realmDB = Realm.getDefaultInstance();
        realmDB.executeTransactionAsync(realm ->{
            RealmResults<Category> catList = realm.where(Category.class).findAll();
            catList.deleteFromRealm(index);
            Log.i("thread", Thread.currentThread().getName());}, ()-> {
            Log.i("succes", "sucsess");
            // Transaction was a success.
        }, Throwable::printStackTrace);
        notifyItemRemoved(index);
    }
}

