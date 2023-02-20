/*
 * Created by Guilherme Cruz
 * Last modified: 25/12/21, 21:31
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

import com.bumptech.glide.Glide;
import com.example.jobby_oficial.Model.Category;
import com.example.jobby_oficial.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder>{
    private Context context;
    List<Category> list_category;
    private OnCategoryListener onCategoryListener;

    public CategoryAdapter(Context context, List<Category> list_category, OnCategoryListener onCategoryListener) {
        this.context = context;
        this.list_category = list_category;
        this.onCategoryListener = onCategoryListener;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_category, parent, false);
        return new CategoryAdapter.viewholder(view, onCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Category category = list_category.get(position);
        holder.tvNameCategory.setText(list_category.get(position).getName());
        //holder.tvDescriptionCategory.setText(list_category.get(position).getName());
        Glide.with(context).load(category.getImage()).into(holder.imgCategory);
    }

    public void getAllCategorys(List<Category> categoryList){
        this.list_category = categoryList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list_category.size();
    }

    static class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgCategory;
        TextView tvNameCategory; //,tvDescriptionCategory
        OnCategoryListener onCategoryListener;

        public viewholder(@NonNull View itemView, OnCategoryListener onCategoryListener) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.image_category);
            tvNameCategory = itemView.findViewById(R.id.tv_name_category);
            //tvDescriptionCategory = itemView.findViewById(R.id.tv_description_category);
            this.onCategoryListener = onCategoryListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCategoryListener.onCategoryClick(getAdapterPosition());
        }
    }

    public interface OnCategoryListener{
        void onCategoryClick(int position);
    }
}
