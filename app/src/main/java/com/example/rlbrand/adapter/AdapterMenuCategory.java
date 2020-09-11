package com.example.rlbrand.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rlbrand.R;
import com.example.rlbrand.model.Category;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AdapterMenuCategory extends RecyclerView.Adapter<AdapterMenuCategory.MyViewHolder> {

    private ArrayList<Category> menuCategories;
    private Context context;

    public AdapterMenuCategory(ArrayList<Category> listMenuCategory, Context c) {
        this.menuCategories = listMenuCategory;
        this.context = c;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View listMenuCategory = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menucategory, parent, false);
        return new MyViewHolder(listMenuCategory);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final Category menuCategory = menuCategories.get( position );
        menuCategory.setSelected(false);
        Uri uri = Uri.parse (menuCategory.getImageCategory());
        Glide.with(context).load(uri).into(holder.imageMenuCategory);

    }

    @Override
    public int getItemCount() {
        return menuCategories.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private ImageView imageMenuCategory, imageChecked;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageMenuCategory = itemView.findViewById(R.id.imageMenuCategory);
            imageChecked = itemView.findViewById(R.id.imageChecked);
        }
    }
}
