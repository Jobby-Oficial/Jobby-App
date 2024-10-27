/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobby_oficial.Model.Avaliation;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.R;

import java.util.List;

public class AvaliationAdapter extends RecyclerView.Adapter<AvaliationAdapter.viewholder>{
    private Context context;
    List<Avaliation> list_avaliation;
    List<Service> list_service;
    AvaliationAdapter.OnAvaliationListener onAvaliationListener;

    public AvaliationAdapter(Context context, List<Avaliation> list_avaliation, List<Service> list_service, AvaliationAdapter.OnAvaliationListener onAvaliationListener) {
        this.context = context;
        this.list_avaliation = list_avaliation;
        this.list_service = list_service;
        this.onAvaliationListener = onAvaliationListener;
    }

    @NonNull
    @Override
    public AvaliationAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_avaliation, parent, false);
        return new AvaliationAdapter.viewholder(view, onAvaliationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AvaliationAdapter.viewholder holder, int position) {
        //Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        Avaliation avaliation = list_avaliation.get(position);
        holder.tvRatingAvaliation.setText(String.valueOf(list_avaliation.get(position).getAvaliation()));
        for (Service iService : list_service) {
            int iUser = list_service.get(list_service.indexOf(iService)).getId();
            int iServ = list_avaliation.get(position).getService_id();
            System.out.println(iUser + " | " + iServ);
            if (iUser == iServ)
                holder.tvServiceAvaliation.setText(list_service.get(list_service.indexOf(iService)).getName());
        }
        //holder.itemView.startAnimation(animation);
    }

    public void getAllAvaliations(List<Avaliation> avaliationList){
        this.list_avaliation = avaliationList;
        notifyDataSetChanged();
    }

    public void getAllServices(List<Service> serviceList){
        this.list_service = serviceList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list_avaliation.size();
    }

    class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvRatingAvaliation, tvServiceAvaliation;
        AvaliationAdapter.OnAvaliationListener onAvaliationListener;

        public viewholder(@NonNull View itemView, AvaliationAdapter.OnAvaliationListener onAvaliationListener) {
            super(itemView);
            tvRatingAvaliation = itemView.findViewById(R.id.tv_rating_avaliation);
            tvServiceAvaliation = itemView.findViewById(R.id.tv_service_avaliation);
            this.onAvaliationListener = onAvaliationListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onAvaliationListener.onAvaliationClick(getAdapterPosition());
        }
    }

    public interface OnAvaliationListener{
        void onAvaliationClick(int position);
    }
}
