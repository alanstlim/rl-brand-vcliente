package com.example.rlbrand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rlbrand.R;
import com.example.rlbrand.model.Category;

import java.util.List;

public class AdapterFilter extends RecyclerView.Adapter<AdapterFilter.MyViewHolder> {

    private List<Category> filterCategoryList;
    private Context context;

    public AdapterFilter (List<Category> listFilterCategory, Context c) {
        this.filterCategoryList = listFilterCategory;
        this.context = c;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View listFilterSubCategory = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_filter, parent, false);
        return new MyViewHolder(listFilterSubCategory);



    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Category filterCategory = filterCategoryList.get( position );
        holder.category = filterCategory.getCategory();
        holder.subCategory = filterCategory.getSubCategory();
        holder.checkFilter.setText(filterCategory.getSubCategory());

    }

    @Override
    public int getItemCount() {
        return filterCategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private String category, subCategory;
        private CheckBox checkFilter;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkFilter = itemView.findViewById(R.id.checkFilter);
        }
    }
}



/*
//Evento de Click Filtro
        recyclerMenuFilter.addOnItemTouchListener(new RecyclerItemClickListener(getActivity()
                ,recyclerMenuFilter,new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Category currentFilter = filterMenuList.get(position);
                dbReference = FirebaseDatabase.getInstance().getReference().child("produtos");
                Query dressSearch = dbReference.orderByChild("subCategory").equalTo(currentFilter.getSubCategory());
                dressMenuList.clear();
                valueEventListener = dressSearch.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dressDate : dataSnapshot.getChildren()){

                            Category menuDress = dressDate.getValue(Category.class);
                            dressMenuList.add(menuDress);

                        }

                        adapterMenuCategory.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));
 */