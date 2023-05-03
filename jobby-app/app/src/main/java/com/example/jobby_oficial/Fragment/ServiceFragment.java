/*
 * Created by Guilherme Cruz
 * Last modified: 30/12/21, 01:58
 * Copyright (c) 2021.
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

import com.example.jobby_oficial.Adapter.ServiceAdapter;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.View.MainActivity;
import com.example.jobby_oficial.ViewModel.ServiceViewModel;

import java.util.ArrayList;
import java.util.List;

public class ServiceFragment extends Fragment implements ServiceAdapter.OnServiceListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ServiceViewModel serviceViewModel;
    RecyclerView rvService;
    ServiceAdapter adapter;
    List<Service> list_service;
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

        adapter = new ServiceAdapter(getContext(), list_service,this);
        rvService.setAdapter(adapter);

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

        return view;

        /*arrayList_service = new ArrayList<>();
        ServiceClass service1 = new ServiceClass(R.drawable.ic_topic,"Nome do serviço 1","Categoria 1");
        ServiceClass service2 = new ServiceClass(R.drawable.ic_topic,"Nome do serviço 2","Categoria 2");
        ServiceClass service3 = new ServiceClass(R.drawable.ic_topic,"Nome do serviço 3","Categoria 3");
        ServiceClass service4 = new ServiceClass(R.drawable.ic_topic,"Nome do serviço 4","Categoria 4");
        ServiceClass service5 = new ServiceClass(R.drawable.ic_topic,"Nome do serviço 5","Categoria 5");
        ServiceClass service6 = new ServiceClass(R.drawable.ic_topic,"Nome do serviço 6","Categoria 6");
        ServiceClass service7 = new ServiceClass(R.drawable.ic_topic,"Nome do serviço 7","Categoria 7");
        arrayList_service.add(service1);
        arrayList_service.add(service2);
        arrayList_service.add(service3);
        arrayList_service.add(service4);
        arrayList_service.add(service5);
        arrayList_service.add(service6);
        arrayList_service.add(service7);*/
    }

    @Override
    public void onServiceClick(int position) {
        list_service.get(position);
        Toast.makeText(getContext(),"Service Position: " + position,Toast.LENGTH_SHORT).show();
    }
}