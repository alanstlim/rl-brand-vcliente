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
import com.example.rlbrand.activity.MainActivity;
import com.example.rlbrand.adapter.AdapterMenuCategory;
import com.example.rlbrand.helper.Permission;
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

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DressFragment extends Fragment {

    private RecyclerView recyclerMenuDress;
    private ArrayList<Category> dressMenuList = new ArrayList();
    private AdapterMenuCategory adapterMenuCategory;
    private DatabaseReference dbReference;
    private ValueEventListener valueEventListener;
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

    public DressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DressFragment newInstance(String param1, String param2) {
        DressFragment fragment = new DressFragment();
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
        View view = inflater.inflate(R.layout.fragment_dress, container, false);

        recyclerMenuDress   = view.findViewById(R.id.recyclerMenuDress);

        /*
        // Definições do FAB
        final FloatingActionButton fab = view.findViewById(R.id.fabDress);
        fab.setVisibility(View.GONE);

        // Salvar os itens selecionados
        final ShareItem shareItem = new ShareItem(getActivity());

         */

        //Define Layout
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerMenuDress.setLayoutManager(gridLayoutManager);
        recyclerMenuDress.hasFixedSize();

        //Define Adapter
        adapterMenuCategory = new AdapterMenuCategory(dressMenuList, getActivity());
        recyclerMenuDress.setAdapter(adapterMenuCategory);

        recyclerMenuDress.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                recyclerMenuDress, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Category dressItem = dressMenuList.get(position);

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
                Category itemSelected = dressMenuList.get(position);
                ImageView imageChecked = view.findViewById(R.id.imageChecked);

                /*  Verificar se o item está selecionado ou não,
                    além de verificar a posição e o tamanho do shared  */
                if (!itemSelected.isSelected()){
                   imageChecked.setVisibility(View.VISIBLE);
                  // shareItem.saveItem(itemSelected.getImageCategory(),positionItem);
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
                /*
                // Configuração do FAB para compartilhar, liberando apenas se tiver algum item selecionado
                if (shareSize < 0) {
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setClickable(true);
                    fab.setVisibility(View.VISIBLE);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Validar permissões
                           // Permission.validatePermission(MainActivity.permissions, getActivity(), 1);
                        }
                    });
                }
                 */
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
        loadDress();
    }

    @Override
    public void onStop() {
        super.onStop();
        dbReference.removeEventListener(valueEventListener);
    }

    // Metodo para carregar os vestidos
    public void loadDress(){
        dbReference = FirebaseDatabase.getInstance().getReference().child("produtos");
        Query dressSearch = dbReference.orderByChild("category").equalTo("Vestido");

        //Dialog de carregamento
        dialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setMessage("Carregando Catálogo de Vestidos")
                .setCancelable(false)
                .build();
        dialog.show();
        dressMenuList.clear();
        valueEventListener = dressSearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dressDate : dataSnapshot.getChildren()){

                    Category menuDress = dressDate.getValue(Category.class);
                    dressMenuList.add(menuDress);
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

