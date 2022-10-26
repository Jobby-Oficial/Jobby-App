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

    public ServiceAdapter(ArrayList<ServiceClass> arrayList_service) {
        this.arrayList_service = arrayList_service;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_service, parent, false);
        return new ServiceAdapter.viewholder(view);
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

    class viewholder extends RecyclerView.ViewHolder {
        ImageView imgService;
        TextView tvNameService, tvCategoryService;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            imgService = itemView.findViewById(R.id.image_service);
            tvNameService = itemView.findViewById(R.id.tv_name_service);
            tvCategoryService = itemView.findViewById(R.id.tv_category_service);
        }
    }
}
