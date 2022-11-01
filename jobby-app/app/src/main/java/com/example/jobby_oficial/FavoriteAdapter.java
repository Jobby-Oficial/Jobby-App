package com.example.jobby_oficial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.viewholder>{
    ArrayList<FavoriteClass> arrayList_favorite;
    OnFavoriteListener onFavoriteListener;

    public FavoriteAdapter(ArrayList<FavoriteClass> arrayList_favorite, OnFavoriteListener onFavoriteListener) {
        this.arrayList_favorite = arrayList_favorite;
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
        holder.imgFavorite.setImageResource(arrayList_favorite.get(position).getImageFavorite());
        holder.tvNameFavorite.setText(arrayList_favorite.get(position).getNameFavorite());
        holder.tvCategoryFavorite.setText(arrayList_favorite.get(position).getCategoryFavorite());
    }

    @Override
    public int getItemCount() {
        return arrayList_favorite.size();
    }

    class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgFavorite;
        TextView tvNameFavorite, tvCategoryFavorite;
        OnFavoriteListener onFavoriteListener;

        public viewholder(@NonNull View itemView, OnFavoriteListener onFavoriteListener) {
            super(itemView);
            imgFavorite = itemView.findViewById(R.id.image_favorite);
            tvNameFavorite = itemView.findViewById(R.id.tv_name_favorite);
            tvCategoryFavorite = itemView.findViewById(R.id.tv_category_favorite);
            this.onFavoriteListener = onFavoriteListener;

            itemView.setOnClickListener(this);
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
