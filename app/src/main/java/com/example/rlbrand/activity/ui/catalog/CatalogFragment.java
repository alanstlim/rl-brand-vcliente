package com.example.rlbrand.activity.ui.catalog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.rlbrand.R;
import com.example.rlbrand.adapter.AdapterCatalog;
import com.example.rlbrand.helper.RecyclerItemClickListener;
import com.example.rlbrand.model.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CatalogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatalogFragment extends Fragment {

    private RecyclerView recyclerCatalog;
    private AdapterCatalog adapterCatalog;
    private ValueEventListener valueEventListenerCatalog;
    private ArrayList<Category> catalogList = new ArrayList<>();
    private ArrayList<Category> subCatalogList = new ArrayList<>();
    private DatabaseReference catalogRef;
    private Dialog dialog;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CatalogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CatalogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CatalogFragment newInstance(String param1, String param2) {
        CatalogFragment fragment = new CatalogFragment();
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
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        recyclerCatalog = view.findViewById(R.id.recyclerCatalog);

        //Configurar Adapter
        adapterCatalog = new AdapterCatalog(catalogList, getActivity());
        recyclerCatalog.setAdapter(adapterCatalog);

        //Define Layout Categoria
        RecyclerView.LayoutManager layoutManagerMain = new LinearLayoutManager(getActivity());
        recyclerCatalog.setLayoutManager(layoutManagerMain);
        recyclerCatalog.setHasFixedSize(true);

        //Referência Firebase
        catalogRef = FirebaseDatabase.getInstance().getReference().child("catalogo");

        //Evento de Click
        recyclerCatalog.addOnItemTouchListener(new RecyclerItemClickListener(getActivity()
                ,recyclerCatalog,new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

               /* Category catalogItem = catalogList.get(position);
                Fragment itemSelectedFragment = new ItemSelectedFragment();

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, itemSelectedFragment)
                        .commit();

                */

            }

            @Override
            public void onLongItemClick(View view, int position) {

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
        loadCatalog();
    }

    @Override
    public void onStop() {
        super.onStop();
        catalogRef.removeEventListener(valueEventListenerCatalog);

    }

    public void loadCatalog() {
        dialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setMessage("Carregando Catálogo Semanal")
                .setCancelable(false)
                .build();
        dialog.show();
        catalogList.clear();
        valueEventListenerCatalog = catalogRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot catalogDate : dataSnapshot.getChildren()) {
                    Category catalog = catalogDate.getValue(Category.class);
                    catalogList.add(catalog);
                    dialog.dismiss();
                }

                adapterCatalog.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}