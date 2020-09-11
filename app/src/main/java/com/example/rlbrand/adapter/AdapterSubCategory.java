package com.example.rlbrand.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rlbrand.R;
import com.example.rlbrand.helper.ShareItem;
import com.example.rlbrand.model.Category;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AdapterSubCategory extends RecyclerView.Adapter<AdapterSubCategory.MyViewHolder> {

    private ArrayList<Category> subCategoryList;
    private Context context;

    public AdapterSubCategory(ArrayList<Category> listSubCategory, Context c) {
        this.subCategoryList = listSubCategory;
        this.context = c;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewSubCategory = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_subcategory, parent, false);
        return new MyViewHolder(viewSubCategory);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Category subCategory = subCategoryList.get( position );
        Uri imageCatalog = Uri.parse (subCategory.getImageCategory());
        Glide.with(context).load(imageCatalog).into(holder.imageCatalog);

    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageCatalog;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCatalog = itemView.findViewById(R.id.imageCatalog);
        }
    }
}
