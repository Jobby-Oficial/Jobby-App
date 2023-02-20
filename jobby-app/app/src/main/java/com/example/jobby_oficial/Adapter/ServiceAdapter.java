/*
 * Created by Guilherme Cruz
 * Last modified: 26/12/21, 02:06
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.viewholder>{
    private Context context;
    List<Service> list_service;
    private OnServiceListener onServiceListener;

    public ServiceAdapter(Context context, List<Service> list_service, OnServiceListener onServiceListener) {
        this.context = context;
        this.list_service = list_service;
        this.onServiceListener = onServiceListener;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_service, parent, false);
        return new ServiceAdapter.viewholder(view, onServiceListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Service service = list_service.get(position);
        //holder.imgService.setImageResource(arrayList_service.get(position).getImageService());
        holder.tvNameService.setText("Service: " + list_service.get(position).getName());
        holder.tvCategoryService.setText("Category: " + list_service.get(position).getCategory());
    }

    public void getAllServices(List<Service> serviceList){
        this.list_service = serviceList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list_service.size();
    }

    class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgService;
        TextView tvNameService, tvCategoryService;
        OnServiceListener onServiceListener;

        public viewholder(@NonNull View itemView, OnServiceListener onServiceListener) {
            super(itemView);
            imgService = itemView.findViewById(R.id.image_service);
            tvNameService = itemView.findViewById(R.id.tv_name_service);
            tvCategoryService = itemView.findViewById(R.id.tv_category_service);
            this.onServiceListener = onServiceListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onServiceListener.onServiceClick(getAdapterPosition());
        }
    }

    public interface OnServiceListener{
        void onServiceClick(int position);
    }
}
