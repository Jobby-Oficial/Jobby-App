/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Fragment;

import static com.example.jobby_oficial.View.MainActivity.id_User;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.jobby_oficial.Adapter.AvaliationAdapter;
import com.example.jobby_oficial.Model.Avaliation;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.View.MainActivity;
import com.example.jobby_oficial.ViewModel.AvaliationViewModel;
import com.example.jobby_oficial.ViewModel.ServiceViewModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class AvaliationFragment extends Fragment implements AvaliationAdapter.OnAvaliationListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    AlphaInAnimationAdapter alphaInAnimationAdapter;
    private ServiceViewModel serviceViewModel;
    private AvaliationViewModel avaliationViewModel;
    AlertDialog alertDialog;
    RecyclerView rvAvaliation;
    AvaliationAdapter adapter;
    List<Service> list_service;
    List<Avaliation> list_avaliation;

    public AvaliationFragment() {
        // Required empty public constructor
    }

    public static AvaliationFragment newInstance(String param1, String param2) {
        AvaliationFragment fragment = new AvaliationFragment();
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
        View view = inflater.inflate(R.layout.fragment_avaliation, container, false);
        rvAvaliation = view.findViewById(R.id.recyclerView_avaliation);
        rvAvaliation.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAvaliation.setHasFixedSize(true);
        list_service = new ArrayList<>();
        list_avaliation = new ArrayList<>();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
                if (!isConnected(getActivity())) {
                    showInternetDialog();
                    adapter.notifyDataSetChanged();
                } else {
                    int iPosition = viewHolder.getAdapterPosition();
                    showAvaliationRemoveDialog(iPosition);
                }
            }
        }).attachToRecyclerView(rvAvaliation);

        avaliationViewModel = new ViewModelProvider(this).get(AvaliationViewModel.class);
        avaliationViewModel.getAllAvaliations().observe(getViewLifecycleOwner(), new Observer<List<Avaliation>>() {
            @Override
            public void onChanged(List<Avaliation> avaliationList) {
                list_avaliation = avaliationList;
                if (list_avaliation.size() == 0) {
                    ((MainActivity) getActivity()).LockScrollview(true);
                    NotFoundFragment fragment = new NotFoundFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).detach(fragment).attach(fragment).commit();

                } else
                    ((MainActivity) getActivity()).LockScrollview(false);
                adapter.getAllAvaliations(list_avaliation);
                adapter.notifyDataSetChanged();
                System.out.println("Lista Avaliations: " + list_avaliation);
            }
        });
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", id_User);
        avaliationViewModel.makeApiCallAvaliations(jsonObject);

        serviceViewModel = new ViewModelProvider(this).get(ServiceViewModel.class);
        serviceViewModel.getAllServices().observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> serviceList) {
                list_service = serviceList;
                adapter.getAllServices(list_service);
                adapter.notifyDataSetChanged();
                System.out.println("Lista Service: " + list_service);
            }
        });
        serviceViewModel.makeApiCallServices();

        adapter = new AvaliationAdapter(getContext(), list_avaliation, list_service, this);
        //rvAvaliation.setAdapter(adapter);

        //Animations
        alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
        alphaInAnimationAdapter.setDuration(1000);//[1 Sec]
        alphaInAnimationAdapter.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
        rvAvaliation.setAdapter(alphaInAnimationAdapter);

        return view;
    }

    private void showAvaliationRemoveDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_avaliation_remove, getActivity().findViewById(R.id.avaliation_remove_dialog));

        view.findViewById(R.id.btn_remove_avaliation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avaliationViewModel.makeApiCallDeleteAvaliations(list_avaliation.get(position).getId());
                adapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel_avaliation_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    @Override
    public void onAvaliationClick(int position) {
        list_avaliation.get(position);
        //Toast.makeText(getContext(),"Avaliation Position: " + position,Toast.LENGTH_SHORT).show();
    }

    private boolean isConnected(Context connected) {
        ConnectivityManager connectivityManager = (ConnectivityManager) connected.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())){
            return true;
        }
        else {
            return false;
        }
    }

    private void showInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_internet, getActivity().findViewById(R.id.internet_dialog));

        view.findViewById(R.id.btn_connect_internet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel_internet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }
}