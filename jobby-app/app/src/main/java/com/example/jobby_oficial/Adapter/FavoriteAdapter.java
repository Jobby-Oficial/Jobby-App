/*
 * Created by Guilherme Cruz
 * Last modified: 14/01/22, 20:18
 * Copyright (c) 2022.
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobby_oficial.Model.Favorite;
import com.example.jobby_oficial.Model.Service;
import com.example.jobby_oficial.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.viewholder>{
    AlertDialog alertDialog;
    //View viewDialog;
    ViewGroup parentView;
    private Context context;
    List<Service> list_service;
    List<Favorite> list_favorite;
    OnFavoriteListener onFavoriteListener;

    public FavoriteAdapter(Context context, List<Service> list_service, List<Favorite> list_favorite, OnFavoriteListener onFavoriteListener) {
        this.context = context;
        this.list_service = list_service;
        this.list_favorite = list_favorite;
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
            //holder.imgFavorite.setImageResource(list_favorite.get(position).getImageFavorite());
            holder.tvNameFavorite.setText("Service: " + list_service.get(position).getName());
            holder.tvCategoryFavorite.setText("Category: " + list_service.get(position).getCategory());
            holder.lb_Favorite.setLiked(true);
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
            tvCategoryFavorite = itemView.findViewById(R.id.tv_category_favorite);
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
                    lb_Favorite.setLiked(true);
                    int iAdapterPosition = getAdapterPosition();
                    showFavoriteDialog(iAdapterPosition, lb_Favorite);
                    System.out.println("unLiked");
                    //lb_Favorite.setLiked(bFav);
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
