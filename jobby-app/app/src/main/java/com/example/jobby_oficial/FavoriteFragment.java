/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:32
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements FavoriteAdapter.OnFavoriteListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView rvFavorite;
    ArrayList<FavoriteClass> arrayList_favorite;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rvFavorite = view.findViewById(R.id.recyclerView_favorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayList_favorite = new ArrayList<>();
        FavoriteClass favorite1 = new FavoriteClass(R.drawable.ic_topic,"Nome do serviço 1","Categoria 1");
        FavoriteClass favorite2 = new FavoriteClass(R.drawable.ic_topic,"Nome do serviço 2","Categoria 2");
        FavoriteClass favorite3 = new FavoriteClass(R.drawable.ic_topic,"Nome do serviço 3","Categoria 3");
        FavoriteClass favorite4 = new FavoriteClass(R.drawable.ic_topic,"Nome do serviço 4","Categoria 4");
        FavoriteClass favorite5 = new FavoriteClass(R.drawable.ic_topic,"Nome do serviço 5","Categoria 5");
        FavoriteClass favorite6 = new FavoriteClass(R.drawable.ic_topic,"Nome do serviço 6","Categoria 6");
        FavoriteClass favorite7 = new FavoriteClass(R.drawable.ic_topic,"Nome do serviço 7","Categoria 7");
        arrayList_favorite.add(favorite1);
        arrayList_favorite.add(favorite2);
        arrayList_favorite.add(favorite3);
        arrayList_favorite.add(favorite4);
        arrayList_favorite.add(favorite5);
        arrayList_favorite.add(favorite6);
        arrayList_favorite.add(favorite7);

        rvFavorite.setAdapter(new FavoriteAdapter(arrayList_favorite, this));
        return view;
    }

    @Override
    public void onFavoriteClick(int position) {
        arrayList_favorite.get(position);
        Toast.makeText(getContext(),"Favorite Position: " + position,Toast.LENGTH_SHORT).show();
    }
}