/*
 * Created by Guilherme Cruz
 * Last modified: 14/01/22, 20:18
 * Copyright (c) 2022.
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
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.viewholder>{
    private Context context;
    List<Service> list_favorite;
    OnFavoriteListener onFavoriteListener;

    public FavoriteAdapter(Context context, List<Service> list_favorite, OnFavoriteListener onFavoriteListener) {
        this.context = context;
        this.list_favorite = list_favorite;
        this.onFavoriteListener = onFavoriteListener;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_favorite, parent, false);
        return new FavoriteAdapter.viewholder(view, onFavoriteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Service favorite = list_favorite.get(position);
        //holder.imgFavorite.setImageResource(list_favorite.get(position).getImageFavorite());
        holder.tvNameFavorite.setText("Service: " + list_favorite.get(position).getName());
        holder.tvCategoryFavorite.setText("Category: " + list_favorite.get(position).getCategory());
        holder.lb_Favorite.setLiked(true);
    }

    public void getAllFavorites(List<Service> favoriteList){
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
                    System.out.println("unLiked");
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
}
