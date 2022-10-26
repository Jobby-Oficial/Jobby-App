package com.example.jobby_oficial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ServiceFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView rvService;
    ArrayList<ServiceClass> arrayList_service;

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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        rvService = view.findViewById(R.id.recyclerView_service);
        rvService.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayList_service = new ArrayList<>();
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
        arrayList_service.add(service7);

        rvService.setAdapter(new ServiceAdapter(arrayList_service));
        return view;
    }
}