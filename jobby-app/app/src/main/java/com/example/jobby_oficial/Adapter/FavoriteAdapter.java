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
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.viewholder>{
    AlertDialog alertDialog;
    ViewGroup parentView;
    private Context context;
    List<Service> list_service;
    List<Favorite> list_favorite;
    List<ServicesGallery> list_gallery;
    OnFavoriteListener onFavoriteListener;

    public FavoriteAdapter(Context context, List<Service> list_service, List<Favorite> list_favorite, List<ServicesGallery> list_gallery, OnFavoriteListener onFavoriteListener) {
        this.context = context;
        this.list_service = list_service;
        this.list_favorite = list_favorite;
        this.list_gallery = list_gallery;
        this.onFavoriteListener = onFavoriteListener;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_favorite, parent, false);
        parentView = parent;
        return new FavoriteAdapter.viewholder(view, onFavoriteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Service service;
        if (list_service.isEmpty() == false) {
            service = list_service.get(position);
            holder.tvNameFavorite.setText("Service: " + list_service.get(position).getName());
            //holder.tvCategoryFavorite.setText("Category: " + list_service.get(position).getCategory());
            holder.lb_Favorite.setLiked(true);

            if (list_gallery != null) {
                for (ServicesGallery iGallery : list_gallery) {
                    int iGal = list_gallery.get(list_gallery.indexOf(iGallery)).getService_id();
                    int iSev = list_service.get(position).getId();
                    System.out.println("LPG: " + iGal + " | " + iSev);
                    if (iSev == iGal) {
                        String img = iGallery.getImage();
                        String newIMG = img.replace("localhost", "10.0.2.2");
                        System.out.println("Imagemmmmm: " + newIMG);
                        Glide.with(context).load(newIMG).into(holder.imgFavorite);
                    }
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
        return list_favorite.size();
    }

    class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgFavorite;
        TextView tvNameFavorite, tvCategoryFavorite;
        LikeButton lb_Favorite;
        OnFavoriteListener onFavoriteListener;

        public viewholder(@NonNull View itemView, OnFavoriteListener onFavoriteListener) {
            super(itemView);
            imgFavorite = itemView.findViewById(R.id.image_favorite);
            tvNameFavorite = itemView.findViewById(R.id.tv_name_favorite);
            //tvCategoryFavorite = itemView.findViewById(R.id.tv_category_favorite);
            lb_Favorite = itemView.findViewById(R.id.heart_button_favorite);
            this.onFavoriteListener = onFavoriteListener;

            itemView.setOnClickListener(this);

            lb_Favorite.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    System.out.println("liked");
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    System.out.println("unLiked");
                    lb_Favorite.setLiked(true);
                    if (!isConnected(context)) {
                        showInternetDialog();
                    } else {
                        int iAdapterPosition = getAdapterPosition();
                        showFavoriteDialog(iAdapterPosition, lb_Favorite);
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            onFavoriteListener.onFavoriteClick(getAdapterPosition());
        }
    }

    public interface OnFavoriteListener{
        void onFavoriteClick(int position);
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

    public void showFavoriteDialog(int iAdapterPosition, LikeButton lb_Favorite) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        View viewDialog = LayoutInflater.from(parentView.getContext()).inflate(R.layout.dialog_favorite, parentView.findViewById(R.id.favorite_dialog), false);

        viewDialog.findViewById(R.id.btn_remove_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lb_Favorite.setLiked(true);
                int iSev = list_service.get(iAdapterPosition).getId();
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
                lb_Favorite.setLiked(false);
                alertDialog.dismiss();
            }
        });
        viewDialog.findViewById(R.id.tv_cancel_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lb_Favorite.setLiked(true);
                alertDialog.dismiss();
            }
        });
        builder.setView(viewDialog);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }
}
