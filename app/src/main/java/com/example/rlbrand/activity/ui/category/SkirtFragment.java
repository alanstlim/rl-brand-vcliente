package com.example.rlbrand.activity.ui.category;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.rlbrand.R;
import com.example.rlbrand.activity.ItemSelectedActivity;
import com.example.rlbrand.adapter.AdapterMenuCategory;
import com.example.rlbrand.helper.RecyclerItemClickListener;
import com.example.rlbrand.helper.ShareItem;
import com.example.rlbrand.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkirtFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkirtFragment extends Fragment {

    private RecyclerView recyclerMenuSkirt;
    private ArrayList<Category> skirtMenuList = new ArrayList<>();
    private List<Category> filterMenuList = new ArrayList<>();
    private AdapterMenuCategory adapterMenuCategory;
    private ValueEventListener valueEventListener;
    private DatabaseReference dbReference;
    private Dialog dialog;
    private int shareSize = -1;
    private int positionItem;
    private String imageItem;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SkirtFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SkirtFragment newInstance(String param1, String param2) {
        SkirtFragment fragment = new SkirtFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skirt, container, false);

        recyclerMenuSkirt   = view.findViewById(R.id.recyclerMenuSkirt);

        //Define Layout Categoria
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerMenuSkirt.setLayoutManager(layoutManager);
        recyclerMenuSkirt.setHasFixedSize(true);

        //Define Adapter
        adapterMenuCategory = new AdapterMenuCategory(skirtMenuList, getActivity());
        recyclerMenuSkirt.setAdapter(adapterMenuCategory);

        recyclerMenuSkirt.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                recyclerMenuSkirt, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Category dressItem = skirtMenuList.get(position);

                Intent itemSelected = new Intent(getActivity(), ItemSelectedActivity.class);
                itemSelected.putExtra("category", dressItem.getCategory());
                itemSelected.putExtra("subCategory", dressItem.getSubCategory());
                itemSelected.putExtra("urlImage", dressItem.getImageCategory());
                //itemSelected.putExtra("size", dressItem.getItemSize());
                //itemSelected.putExtra("price", dressItem.getItemPrice());
                startActivity(itemSelected);



            }

            @Override
            public void onLongItemClick(View view, int position) {
                Category itemSelected = skirtMenuList.get(position);
                ImageView imageChecked = view.findViewById(R.id.imageChecked);

                /*  Verificar se o item está selecionado ou não,
                    além de verificar a posição e o tamanho do shared  */
                if (!itemSelected.isSelected()){
                    imageChecked.setVisibility(View.VISIBLE);
                    //shareItem.saveItem(itemSelected.getImageCategory(),positionItem);
                    itemSelected.setSelected(true);
                    positionItem += 1;
                    shareSize += 1;

                } else {
                    imageChecked.setVisibility(View.GONE);
                    itemSelected.setSelected(false);
                    //shareItem.deleteItem(positionItem);
                    positionItem -= 1;
                    shareSize -= 1;
                }
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LoadMenuSkirt();
    }

    @Override
    public void onStop() {
        super.onStop();
        dbReference.removeEventListener(valueEventListener);
    }

    public void LoadMenuSkirt() {
        dbReference = FirebaseDatabase.getInstance().getReference().child("produtos");
        Query productSearch = dbReference.orderByChild("category").equalTo("Saia");

        //Dialog de carregamento
        dialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setMessage("Carregando Catálogo de Saias")
                .setCancelable(false)
                .build();
        dialog.show();

        skirtMenuList.clear();

        valueEventListener = productSearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot skirtDate : dataSnapshot.getChildren()) {
                    Category menuSkirt = skirtDate.getValue(Category.class);
                    skirtMenuList.add(menuSkirt);
                    dialog.dismiss();
                }

                adapterMenuCategory.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}