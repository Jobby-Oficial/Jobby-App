/*
 * Created by Guilherme Cruz
 * Last modified: 26/12/21, 02:06
 * Copyright (c) 2021.
 * All rights reserved.
 */

package com.example.jobby_oficial.Adapter;

import static com.example.jobby_oficial.View.MainActivity.favoriteViewModel;
import static com.example.jobby_oficial.View.MainActivity.id_User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jobby_oficial.Database.SingletonRoomDatabase;
import com.example.jobby_oficial.Fragment.ServiceFragment;
import com.example.jobby_oficial.Model.Favorite;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.Model.User;
import com.example.jobby_oficial.R;
import com.google.gson.JsonObject;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.viewholder>{
    private Context context;
    List<Service> list_service;
    List<Favorite> list_favorite;
    private OnServiceListener onServiceListener;

    public ServiceAdapter(Context context, List<Service> list_service, List<Favorite> list_favorite, OnServiceListener onServiceListener) {
        this.context = context;
        this.list_service = list_service;
        this.list_favorite = list_favorite;
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
        //Glide.with(context).load(service.getImage()).into(holder.imgService);
        holder.tvNameService.setText("Service: " + list_service.get(position).getName());
        holder.tvCategoryService.setText("Category: " + list_service.get(position).getCategory());

        for (Favorite iFavorite : list_favorite) {
            int iFav = list_favorite.get(list_favorite.indexOf(iFavorite)).getService_id();
            int iSev = list_service.get(position).getId();
            if (iSev == iFav)
                holder.lb_Service.setLiked(true);
        }
    }

    public void getAllServices(List<Service> serviceList){
        this.list_service = serviceList;
        notifyDataSetChanged();
    }

    public void getAllFavorites(List<Favorite> favoriteList){
        this.list_favorite = favoriteList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list_service.size();
    }

    class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgService;
        TextView tvNameService, tvCategoryService;
        LikeButton lb_Service;
        OnServiceListener onServiceListener;

        public viewholder(@NonNull View itemView, OnServiceListener onServiceListener) {
            super(itemView);
            imgService = itemView.findViewById(R.id.image_service);
            tvNameService = itemView.findViewById(R.id.tv_name_service);
            tvCategoryService = itemView.findViewById(R.id.tv_category_service);
            lb_Service = itemView.findViewById(R.id.heart_button_service);
            this.onServiceListener = onServiceListener;

            itemView.setOnClickListener(this);

            lb_Service.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    System.out.println("liked");
                    System.out.println("Teste liked: " + getAdapterPosition());
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("service_id", list_service.get(getAdapterPosition()).getId());
                    jsonObject.addProperty("user_id", id_User);
                    favoriteViewModel.makeApiCallCreateFavorites(jsonObject);
                    notifyDataSetChanged();
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    System.out.println("unLiked");
                    int iSev = list_service.get(getAdapterPosition()).getId();
                    int iUser = Integer.parseInt(id_User);
                    System.out.println("Teste unLiked: " + iSev + " | " + iUser);
                    for (Favorite iFavorite : list_favorite) {
                        if (iUser == list_favorite.get(list_favorite.indexOf(iFavorite)).getUser_id()){
                            System.out.println(iUser + " | " + list_favorite.get(list_favorite.indexOf(iFavorite)).getUser_id());
                            System.out.println(iSev + " | " + list_favorite.get(list_favorite.indexOf(iFavorite)).getService_id());
                            if (iSev == list_favorite.get(list_favorite.indexOf(iFavorite)).getService_id()) {
                                System.out.println(iSev + " | " + list_favorite.get(list_favorite.indexOf(iFavorite)).getService_id());
                                int id = list_favorite.get(list_favorite.indexOf(iFavorite)).getId();
                                System.out.println("Delete: " + id);
                                favoriteViewModel.makeApiCallDeleteFavorites(id);
                                notifyDataSetChanged();
                            }
                        }
                    }
                }
            });
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
