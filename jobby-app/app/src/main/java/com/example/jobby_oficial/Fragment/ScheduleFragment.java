/*
 * Created by Guilherme Cruz
 * Last modified: 19/01/22, 19:44
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Fragment;

import static com.example.jobby_oficial.View.MainActivity.id_User;

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

import com.example.jobby_oficial.Adapter.ScheduleAdapter;
import com.example.jobby_oficial.Model.Schedule;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.Model.Username;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.View.MainActivity;
import com.example.jobby_oficial.ViewModel.ScheduleViewModel;
import com.example.jobby_oficial.ViewModel.ServiceViewModel;
import com.example.jobby_oficial.ViewModel.UsersViewModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class ScheduleFragment extends Fragment implements ScheduleAdapter.OnScheduleListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    AlphaInAnimationAdapter alphaInAnimationAdapter;
    private ScheduleViewModel scheduleViewModel;
    private UsersViewModel usersViewModel;
    private ServiceViewModel serviceViewModel;
    RecyclerView rvSchedule;
    ScheduleAdapter adapter;
    List<Schedule> list_schedule;
    List<Username> list_username;
    List<Service> list_service;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
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
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        rvSchedule = view.findViewById(R.id.recyclerView_schedule);
        rvSchedule.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSchedule.setHasFixedSize(true);
        list_schedule = new ArrayList<>();

        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        scheduleViewModel.getAllSchedules().observe(getViewLifecycleOwner(), new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> scheduleList) {
                list_schedule = scheduleList;
                if (list_schedule.size() == 0) {
                    ((MainActivity) getActivity()).LockScrollview(true);
                    NotFoundFragment fragment = new NotFoundFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).detach(fragment).attach(fragment).commit();

                }
                else
                    ((MainActivity)getActivity()).LockScrollview(false);
                adapter.getAllSchedules(list_schedule);
                adapter.notifyDataSetChanged();
                System.out.println("Lista Schedules: " + list_schedule);
            }
        });
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("client_id", id_User);
        scheduleViewModel.makeApiCallSchedules(jsonObject);

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

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        usersViewModel.getAllUsernames().observe(getViewLifecycleOwner(), new Observer<List<Username>>() {
            @Override
            public void onChanged(List<Username> usernameList) {
                list_username = usernameList;
                adapter.getAllUsernames(list_username);
                adapter.notifyDataSetChanged();
                System.out.println("Lista Username: " + list_username);
            }
        });
        usersViewModel.makeApiCallUsernames();

        adapter = new ScheduleAdapter(getContext(), list_schedule, list_username, list_service, this);
        //rvSchedule.setAdapter(adapter);

        //Animations
        alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
        alphaInAnimationAdapter.setDuration(1000);//[1 Sec]
        alphaInAnimationAdapter.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
        rvSchedule.setAdapter(alphaInAnimationAdapter);

        return view;
    }

    @Override
    public void onScheduleClick(int position) {
        list_schedule.get(position);
        //Toast.makeText(getContext(),"Schedule Position: " + position,Toast.LENGTH_SHORT).show();
    }
}