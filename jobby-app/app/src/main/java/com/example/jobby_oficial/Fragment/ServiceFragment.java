/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Fragment;

import static com.example.jobby_oficial.View.MainActivity.id_User;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.example.jobby_oficial.Adapter.ServiceAdapter;
import com.example.jobby_oficial.Model.Avaliation;
import com.example.jobby_oficial.Model.Favorite;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.Model.ServicesGallery;
import com.example.jobby_oficial.Model.Username;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.View.MainActivity;
import com.example.jobby_oficial.View.ServiceDetailActivity;
import com.example.jobby_oficial.ViewModel.AvaliationViewModel;
import com.example.jobby_oficial.ViewModel.FavoriteViewModel;
import com.example.jobby_oficial.ViewModel.ServiceViewModel;
import com.example.jobby_oficial.ViewModel.ServicesGalleryViewModel;
import com.example.jobby_oficial.ViewModel.UsersViewModel;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class ServiceFragment extends Fragment implements ServiceAdapter.OnServiceListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    AlphaInAnimationAdapter alphaInAnimationAdapter;
    private ServiceViewModel serviceViewModel;
    private FavoriteViewModel favoriteViewModel;
    private UsersViewModel usersViewModel;
    private AvaliationViewModel avaliationViewModel;
    private ServicesGalleryViewModel servicesGalleryViewModel;
    RecyclerView rvService;
    ServiceAdapter adapter;
    List<Service> list_service;
    List<Favorite> list_favorite;
    List<Username> list_username;
    List<Avaliation> list_avaliation;
    List<ServicesGallery> list_gallery;
    String nameCategory;

    public ServiceFragment() {
        // Required empty public constructor
    }

    public static ServiceFragment newInstance(String param1, String param2) {
        ServiceFragment fragment = new ServiceFragment();
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
            mParam1 = getArguments().getString("pCategoryName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null)
            nameCategory = bundle.getString("nameCategory");
        System.out.println("Service: " + nameCategory);
        rvService = view.findViewById(R.id.recyclerView_service);
        rvService.setLayoutManager(new LinearLayoutManager(getContext()));
        rvService.setHasFixedSize(true);
        list_service = new ArrayList<>();
        list_favorite = new ArrayList<>();
        list_gallery = new ArrayList<>();

        serviceViewModel = new ViewModelProvider(this).get(ServiceViewModel.class);
        serviceViewModel.getAllServices().observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> serviceList) {
                list_service.clear();
                if (nameCategory != null) {
                    for (Service i : serviceList) {
                        if (serviceList.get(serviceList.indexOf(i)).getCategory().equals(nameCategory))
                            list_service.add(serviceList.get(serviceList.indexOf(i)));
                    }
                    if (list_service.size() == 0) {
                        ((MainActivity) getActivity()).LockScrollview(true);
                        NotFoundFragment fragment = new NotFoundFragment();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).detach(fragment).attach(fragment).commit();

                    }
                    else
                        ((MainActivity)getActivity()).LockScrollview(false);
                    adapter.getAllServices(list_service);
                }
                else{
                    list_service = serviceList;
                    adapter.getAllServices(list_service);
                }
                adapter.notifyDataSetChanged();
                System.out.println("Lista: " + list_service);
            }
        });
        serviceViewModel.makeApiCallServices();

        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        favoriteViewModel.getAllFavorites().observe(getViewLifecycleOwner(), new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favoriteList) {
                list_favorite = favoriteList;
                adapter.getAllFavorites(list_favorite);
                //Animations
                alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
                alphaInAnimationAdapter.setDuration(1000);//[1 Sec]
                alphaInAnimationAdapter.setInterpolator(new AccelerateDecelerateInterpolator());
                alphaInAnimationAdapter.setFirstOnly(false);
                rvService.setAdapter(alphaInAnimationAdapter);
                //rvService.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
                System.out.println("Lista NOVAAAAAAAAAAA: " + list_favorite);
            }
        });

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        usersViewModel.getAllUsernames().observe(getViewLifecycleOwner(), new Observer<List<Username>>() {
            @Override
            public void onChanged(List<Username> usernameList) {
                list_username = usernameList;
                System.out.println("Lista Username/Service: " + list_username);
            }
        });
        usersViewModel.makeApiCallUsernames();

        avaliationViewModel = new ViewModelProvider(this).get(AvaliationViewModel.class);
        avaliationViewModel.getAllAvaliations().observe(getViewLifecycleOwner(), new Observer<List<Avaliation>>() {
            @Override
            public void onChanged(List<Avaliation> avaliationList) {
                list_avaliation = avaliationList;
                System.out.println("Lista Avaliation/Service: " + list_avaliation);
            }
        });
        JsonObject joAvaliation = new JsonObject();
        joAvaliation.addProperty("user_id", id_User);
        avaliationViewModel.makeApiCallAvaliations(joAvaliation);

        servicesGalleryViewModel = new ViewModelProvider(this).get(ServicesGalleryViewModel.class);
        servicesGalleryViewModel.getAllServicesGallerys().observe(getViewLifecycleOwner(), new Observer<List<ServicesGallery>>() {
            @Override
            public void onChanged(List<ServicesGallery> galleryList) {
                list_gallery = galleryList;
                adapter.getAllServicesGallery(list_gallery);
                System.out.println("Lista Services Gallery/Service: " + list_gallery);
            }
        });
        servicesGalleryViewModel.makeApiCallServicesGallerys();

        adapter = new ServiceAdapter(getContext(), list_service, list_favorite, list_gallery,this);
        //rvService.setAdapter(adapter);

        //Animations
        alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
        alphaInAnimationAdapter.setDuration(1000);//[1 Sec]
        alphaInAnimationAdapter.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
        rvService.setAdapter(alphaInAnimationAdapter);

        return view;
    }

    @Override
    public void onServiceClick(int position) {
        list_service.get(position);
        //Toast.makeText(getContext(),"Service Position: " + position,Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getContext(), ServiceDetailActivity.class);
        int service_id = list_service.get(position).getId();
        String category = list_service.get(position).getCategory();
        String name = list_service.get(position).getName();
        String description = list_service.get(position).getDescription();
        String price = list_service.get(position).getPrice();
        int profissional_id = list_service.get(position).getUser_id();
        String profissional = "";
        double rating = 0.0;

        for (Username iUsername : list_username) {
            int iUser = list_username.get(list_username.indexOf(iUsername)).getId();
            if (iUser == profissional_id)
                profissional = list_username.get(list_username.indexOf(iUsername)).getUsername();
        }

        if (id_User != null) {
            for (Avaliation iAvaliation : list_avaliation) {
                int iUser = list_avaliation.get(list_avaliation.indexOf(iAvaliation)).getUser_id();
                if (iUser == Integer.parseInt(id_User)) {
                    int iServ = list_avaliation.get(list_avaliation.indexOf(iAvaliation)).getService_id();
                    if (iServ == service_id)
                        rating = list_avaliation.get(list_avaliation.indexOf(iAvaliation)).getAvaliation();
                }
            }
        }

        //Put Extra
        intent.putExtra("Service_id", service_id);
        intent.putExtra("Category", category);
        intent.putExtra("Name", name);
        intent.putExtra("Description", description);
        intent.putExtra("Price", price);
        intent.putExtra("Profissional_id", profissional_id);
        intent.putExtra("Profissional", profissional);
        intent.putExtra("Rating", rating);
        intent.putExtra("Gallery", (Serializable) list_gallery);

        startActivity(intent);
    }
}