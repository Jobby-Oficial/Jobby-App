package com.example.jobby_oficial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoryFragment extends Fragment implements CategoryAdapter.OnCategoryListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView rvCategory;
    ArrayList<CategoryClass> arrayList_category;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        rvCategory = view.findViewById(R.id.recyclerView_category);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayList_category = new ArrayList<>();
        CategoryClass category1 = new CategoryClass(R.drawable.ic_topic,"Categoria 1"," Descrisão 1");
        CategoryClass category2 = new CategoryClass(R.drawable.ic_topic,"Categoria 2"," Descrisão 2");
        CategoryClass category3 = new CategoryClass(R.drawable.ic_topic,"Categoria 3"," Descrisão 3");
        CategoryClass category4 = new CategoryClass(R.drawable.ic_topic,"Categoria 4"," Descrisão 4");
        CategoryClass category5 = new CategoryClass(R.drawable.ic_topic,"Categoria 5"," Descrisão 5");
        CategoryClass category6 = new CategoryClass(R.drawable.ic_topic,"Categoria 6"," Descrisão 6");
        CategoryClass category7 = new CategoryClass(R.drawable.ic_topic,"Categoria 7"," Descrisão 7");
        arrayList_category.add(category1);
        arrayList_category.add(category2);
        arrayList_category.add(category3);
        arrayList_category.add(category4);
        arrayList_category.add(category5);
        arrayList_category.add(category6);
        arrayList_category.add(category7);

        rvCategory.setAdapter(new CategoryAdapter(arrayList_category,this));
        return view;
    }

    @Override
    public void onCategoryClick(int position) {
        arrayList_category.get(position);
        Toast.makeText(getContext(),"Category Position: " + position,Toast.LENGTH_SHORT).show();
    }
}