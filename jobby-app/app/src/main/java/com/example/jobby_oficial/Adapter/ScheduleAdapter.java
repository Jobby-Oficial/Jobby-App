/*
 * Created by Guilherme Cruz
 * Last modified: 19/01/22, 18:08
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Adapter;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobby_oficial.Model.Favorite;
import com.example.jobby_oficial.Model.Schedule;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.Model.Username;
import com.example.jobby_oficial.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.viewholder>{
    private Context context;
    List<Schedule> list_schedule;
    List<Username> list_username;
    List<Service> list_service;
    ScheduleAdapter.OnScheduleListener onScheduleListener;

    public ScheduleAdapter(Context context, List<Schedule> list_schedule, List<Username> list_username, List<Service> list_service, ScheduleAdapter.OnScheduleListener onScheduleListener) {
        this.context = context;
        this.list_schedule = list_schedule;
        this.list_username = list_username;
        this.list_service = list_service;
        this.onScheduleListener = onScheduleListener;
    }

    @NonNull
    @Override
    public ScheduleAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_schedule, parent, false);
        return new ScheduleAdapter.viewholder(view, onScheduleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.viewholder holder, int position) {
        String sTime = list_schedule.get(position).getService_time();
        StringBuffer buffer = new StringBuffer(sTime);
        buffer.deleteCharAt(5).deleteCharAt(5).deleteCharAt(5);
        SimpleDateFormat formatoOrigem = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        try {
            data = formatoOrigem.parse(list_schedule.get(position).getService_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatoDestino = new SimpleDateFormat("dd-MM-yyyy");
        String sDate = formatoDestino.format(data);
        Schedule schedule = list_schedule.get(position);
        for (Service iService : list_service) {
            int iUser = list_service.get(list_service.indexOf(iService)).getId();
            int iServ = list_schedule.get(position).getService_id();
            System.out.println(iUser + " | " + iServ);
            if (iUser == iServ)
                holder.tvServiceSchedule.setText("Service: " + list_service.get(list_service.indexOf(iService)).getName());
        }
        for (Username iUsername : list_username) {
            int iUser = list_username.get(list_username.indexOf(iUsername)).getId();
            int iProf = list_schedule.get(position).getProfessional_id();
            System.out.println(iUser + " | " + iProf);
            if (iUser == iProf)
                holder.tvProfessionalSchedule.setText("Profissional: " + list_username.get(list_username.indexOf(iUsername)).getUsername());
        }
        holder.tvDateSchedule.setText(sDate + " " + buffer.toString());
        holder.tvStatusSchedule.setText(String.valueOf(list_schedule.get(position).getJob_status_id()));
        holder.tvPriceSchedule.setText(String.valueOf(list_schedule.get(position).getPrice()) + "€");
    }

    public void getAllSchedule(List<Schedule> scheduleList){
        this.list_schedule = scheduleList;
        notifyDataSetChanged();
    }

    public void getAllUsername(List<Username> usernameList){
        this.list_username = usernameList;
        notifyDataSetChanged();
    }

    public void getAllService(List<Service> serviceList){
        this.list_service = serviceList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list_schedule.size();
    }

    class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvServiceSchedule, tvProfessionalSchedule, tvDateSchedule, tvStatusSchedule, tvPriceSchedule;
        ScheduleAdapter.OnScheduleListener onScheduleListener;

        public viewholder(@NonNull View itemView, ScheduleAdapter.OnScheduleListener onScheduleListener) {
            super(itemView);
            tvServiceSchedule = itemView.findViewById(R.id.tv_service_schedule);
            tvProfessionalSchedule = itemView.findViewById(R.id.tv_professional_schedule);
            tvDateSchedule = itemView.findViewById(R.id.tv_date_schedule);
            tvStatusSchedule = itemView.findViewById(R.id.tv_status_schedule);
            tvPriceSchedule = itemView.findViewById(R.id.tv_price_schedule);
            this.onScheduleListener = onScheduleListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onScheduleListener.onScheduleClick(getAdapterPosition());
        }
    }

    public interface OnScheduleListener{
        void onScheduleClick(int position);
    }
}
