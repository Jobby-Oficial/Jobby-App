package com.example.jobby_oficial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder>{
    ArrayList<CategoryClass> arrayList_category;
    private OnCategoryListener onCategoryListener;

    public CategoryAdapter(ArrayList<CategoryClass> arrayList_category, OnCategoryListener onCategoryListener) {
        this.arrayList_category = arrayList_category;
        this.onCategoryListener = onCategoryListener;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_category, parent, false);
        return new viewholder(view, onCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.imgCategory.setImageResource(arrayList_category.get(position).getImageCategory());
        holder.tvNameCategory.setText(arrayList_category.get(position).getNameCategory());
        holder.tvDescriptionCategory.setText(arrayList_category.get(position).getDescriptionCategory());
    }

    @Override
    public int getItemCount() {
        return arrayList_category.size();
    }

    static class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgCategory;
        TextView tvNameCategory, tvDescriptionCategory;
        OnCategoryListener onCategoryListener;

        public viewholder(@NonNull View itemView, OnCategoryListener onCategoryListener) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.image_category);
            tvNameCategory = itemView.findViewById(R.id.tv_name_category);
            tvDescriptionCategory = itemView.findViewById(R.id.tv_description_category);
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
