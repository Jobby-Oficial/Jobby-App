/*
 * Created by Guilherme Cruz
 * Last modified: 04/12/21, 12:32
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.viewholder>{
    ArrayList<ServiceClass> arrayList_service;
    private OnServiceListener onServiceListener;

    public ServiceAdapter(ArrayList<ServiceClass> arrayList_service, OnServiceListener onServiceListener) {
        this.arrayList_service = arrayList_service;
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
        holder.imgService.setImageResource(arrayList_service.get(position).getImageService());
        holder.tvNameService.setText(arrayList_service.get(position).getNameService());
        holder.tvCategoryService.setText(arrayList_service.get(position).getCategoryService());
    }

    @Override
    public int getItemCount() {
        return arrayList_service.size();
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
