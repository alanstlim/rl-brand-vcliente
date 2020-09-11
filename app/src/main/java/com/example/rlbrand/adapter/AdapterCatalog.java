package com.example.rlbrand.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rlbrand.R;
import com.example.rlbrand.model.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterCatalog extends RecyclerView.Adapter<AdapterCatalog.MyViewHolder>{


    private Context context;
    private ArrayList<Category> catalogList;
    private ArrayList<Category> subList = new ArrayList<>();
    private AdapterSubCategory adapterSubCategory;
    private ValueEventListener valueEventListenerCatalog;
    private DatabaseReference subRef;
    private Query subSearch;

    public AdapterCatalog(ArrayList<Category> listCatalog, Context c) {
        this.catalogList = listCatalog;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_catalog, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final Category catalog = catalogList.get( position );
        holder.textCategory.setText(catalog.getCategory());
        holder.textDescription.setText(catalog.getDescription());
        Uri mainCatalog = Uri.parse (catalog.getImageCategory());
        Glide.with(context).load(mainCatalog).into(holder.imageCategory);


        //Configurando o recycler horinzontal
        subRef = FirebaseDatabase.getInstance().getReference().child("produtos");
        subSearch = subRef.orderByChild("category").equalTo(catalog.getCategory());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.recyclerSub.setLayoutManager(linearLayoutManager);
        holder.recyclerSub.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        adapterSubCategory = new AdapterSubCategory(subList, context);
        holder.recyclerSub.setAdapter(adapterSubCategory);
        loadRecyclerSub();

    }

    @Override
    public int getItemCount() {
        return catalogList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textCategory, textDescription;
        private ImageView imageCategory;
        private RecyclerView recyclerSub;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textCategory        = itemView.findViewById(R.id.textCategory);
            textDescription     = itemView.findViewById(R.id.textItemSubCategory);
            imageCategory       = itemView.findViewById(R.id.imageCategory);
            recyclerSub         = itemView.findViewById(R.id.recyclerSub);
        }
    }

    public void loadRecyclerSub() {
        subList.clear();
        valueEventListenerCatalog = subSearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot subCategoryDate : dataSnapshot.getChildren()) {
                    Category subCategory = subCategoryDate.getValue(Category.class);
                    subList.add(subCategory);
                }

                adapterSubCategory.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
