/*
 * Created by Guilherme Cruz
 * Last modified: 16/01/22, 18:25
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jobby_oficial.Adapter.FavoriteAdapter;
import com.example.jobby_oficial.Model.Favorite;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.View.MainActivity;
import com.example.jobby_oficial.ViewModel.FavoriteViewModel;
import com.example.jobby_oficial.ViewModel.ServiceViewModel;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteAdapter.OnFavoriteListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private FavoriteViewModel favoriteViewModel;
    private ServiceViewModel serviceViewModel;
    RecyclerView rvFavorite;
    FavoriteAdapter adapter;
    List<Favorite> list_favorite;
    List<Service> list_service;

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
        rvFavorite.setHasFixedSize(true);
        list_favorite = new ArrayList<>();
        list_service = new ArrayList<>();

        adapter = new FavoriteAdapter(getContext(), list_service,this);
        rvFavorite.setAdapter(adapter);

        /*SessionManager sessionManager = new SessionManager(getContext());
        HashMap<String, String> userDetails = sessionManager.getUserDetailFromSession();
        id_User = userDetails.get(sessionManager.KEY_ID);*/

        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        favoriteViewModel.getAllFavorites().observe(getViewLifecycleOwner(), new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favoriteList) {
                list_favorite = favoriteList;
                System.out.println("Lista Favoriros: " + list_favorite);
                if (list_favorite.size() == 0) {
                    ((MainActivity) getActivity()).LockScrollview(true);
                    NotFoundFragment fragment = new NotFoundFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).detach(fragment).attach(fragment).commit();

                } else
                    ((MainActivity) getActivity()).LockScrollview(false);
            }
        });
        /*JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", id_User);
        favoriteViewModel.makeApiCallFavorites(jsonObject);*/

        serviceViewModel = new ViewModelProvider(this).get(ServiceViewModel.class);
        serviceViewModel.getAllServices().observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> serviceList) {
                list_service.clear();
                for (Service iService : serviceList) {
                    for (Favorite iFavorite : list_favorite) {
                        int iSev = serviceList.get(serviceList.indexOf(iService)).getId();
                        int iFav = list_favorite.get(list_favorite.indexOf(iFavorite)).getService_id();
                        if (iSev == iFav)
                            list_service.add(serviceList.get(serviceList.indexOf(iService)));
                    }
                }
                adapter.getAllFavorites(list_service);
                adapter.notifyDataSetChanged();
                System.out.println("Lista Service/Favorite: " + list_service);
            }
        });
        serviceViewModel.makeApiCallServices();

        /*lb_Service = viewF.findViewById(R.id.heart_button_favorite);
        lb_Service.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(getContext(), "liked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(getContext(), "unLiked", Toast.LENGTH_SHORT).show();
            }
        });*/

        return view;

        /*arrayList_favorite = new ArrayList<>();
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
        arrayList_favorite.add(favorite7);*/
    }

    @Override
    public void onFavoriteClick(int position) {
        list_favorite.get(position);
        Toast.makeText(getContext(),"Favorite Position: " + position,Toast.LENGTH_SHORT).show();
    }
}