/*
 * Created by Guilherme Cruz
 * Last modified: 27/01/22, 20:20
 * Copyright (c) 2022.
 * All rights reserved.
 */

package com.example.jobby_oficial.Adapter;

import static com.example.jobby_oficial.View.MainActivity.favoriteViewModel;
import static com.example.jobby_oficial.View.MainActivity.id_User;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jobby_oficial.Model.Favorite;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.Model.ServicesGallery;
import com.example.jobby_oficial.R;
import com.example.jobby_oficial.View.AuthenticationMenu;
import com.example.jobby_oficial.View.MainActivity;
import com.google.gson.JsonObject;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.viewholder>{
    AlertDialog alertDialog;
    ViewGroup parentView;
    private Context context;
    List<Service> list_service;
    List<Favorite> list_favorite;
    List<ServicesGallery> list_gallery;
    private OnServiceListener onServiceListener;

    public ServiceAdapter(Context context, List<Service> list_service, List<Favorite> list_favorite, List<ServicesGallery> list_gallery, OnServiceListener onServiceListener) {
        this.context = context;
        this.list_service = list_service;
        this.list_favorite = list_favorite;
        this.list_gallery = list_gallery;
        this.onServiceListener = onServiceListener;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_service, parent, false);
        parentView = parent;
        return new ServiceAdapter.viewholder(view, onServiceListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Service service = list_service.get(position);
        holder.tvNameService.setText("Service: " + list_service.get(position).getName());
        //holder.tvCategoryService.setText("Category: " + list_service.get(position).getCategory());

        if (id_User != null) {
            for (Favorite iFavorite : list_favorite) {
                int iFav = list_favorite.get(list_favorite.indexOf(iFavorite)).getService_id();
                int iSev = list_service.get(position).getId();
                if (iSev == iFav)
                    holder.lb_Service.setLiked(true);
            }
        }

        if (list_service.isEmpty() == false) {
            for (ServicesGallery iGallery : list_gallery) {
                int iGal = list_gallery.get(list_gallery.indexOf(iGallery)).getService_id();
                int iSev = list_service.get(position).getId();
                System.out.println("LPG: " + iGal + " | " + iSev);
                if (iSev == iGal) {
                    String img = iGallery.getImage();
                    String newIMG = img.replace("localhost", "10.0.2.2");
                    System.out.println("Imagemmmmm: " + newIMG);
                    Glide.with(context).load(newIMG).into(holder.imgService);
                }
            }
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

    public void getAllServicesGallery(List<ServicesGallery> galleryList){
        this.list_gallery = galleryList;
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
            //tvCategoryService = itemView.findViewById(R.id.tv_category_service);
            lb_Service = itemView.findViewById(R.id.heart_button_service);
            this.onServiceListener = onServiceListener;

            itemView.setOnClickListener(this);

            lb_Service.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    System.out.println("liked");
                    if (id_User != null) {
                        if (!isConnected(context)) {
                            lb_Service.setLiked(false);
                            showInternetDialog();
                        } else {
                            System.out.println("Teste liked: " + getAdapterPosition());
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("service_id", list_service.get(getAdapterPosition()).getId());
                            jsonObject.addProperty("user_id", id_User);
                            favoriteViewModel.makeApiCallCreateFavorites(jsonObject);
                            notifyDataSetChanged();
                        }

                    }
                    else {
                        lb_Service.setLiked(false);
                        showSessionDialog();
                    }
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    System.out.println("unLiked");
                    if (!isConnected(context)) {
                        lb_Service.setLiked(true);
                        showInternetDialog();
                    } else {
                        int iSev = list_service.get(getAdapterPosition()).getId();
                        int iUser = Integer.parseInt(id_User);
                        System.out.println("Teste unLiked: " + iSev + " | " + iUser);
                        for (Favorite iFavorite : list_favorite) {
                            if (iUser == list_favorite.get(list_favorite.indexOf(iFavorite)).getUser_id()) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        View view = LayoutInflater.from(parentView.getContext()).inflate(R.layout.dialog_internet, parentView.findViewById(R.id.internet_dialog));

        view.findViewById(R.id.btn_connect_internet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
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

    private void showSessionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        View view = LayoutInflater.from(parentView.getContext()).inflate(R.layout.dialog_session, parentView.findViewById(R.id.session_dialog));

        view.findViewById(R.id.btn_sign_in_session).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AuthenticationMenu.class);
                context.startActivity(intent);
            }
        });
        view.findViewById(R.id.tv_cancel_session).setOnClickListener(new View.OnClickListener() {
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
