package com.example.jobby_oficial;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jobby_oficial.Model.Category;
import com.example.jobby_oficial.Network.APIServiceCategory;
import com.example.jobby_oficial.Network.CategoryRetroInstance;
import com.example.jobby_oficial.Repository.CategoryRepository;
import com.example.jobby_oficial.ViewModel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment implements CategoryAdapter.OnCategoryListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private CategoryViewModel categoryViewModel;
    RecyclerView rvCategory;
    CategoryAdapter adapter;
    List<Category> list_category;

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
        rvCategory.setHasFixedSize(true);
        list_category = new ArrayList<>();

        adapter = new CategoryAdapter(getContext(), list_category,this);
        rvCategory.setAdapter(adapter);
        //rvCategory.setAdapter(new CategoryAdapter(getContext(), list_category,this));

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategorys().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categoryList) {
                list_category = categoryList;
                adapter.getAllCategorys(categoryList);
                adapter.notifyDataSetChanged();
            }
        });
        categoryViewModel.makeApiCall();

        return view;

        /*CategoryClass category1 = new CategoryClass(R.drawable.ic_topic,"Categoria 1"," Descrisão 1");
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
        arrayList_category.add(category7);*/
    }

    @Override
    public void onCategoryClick(int position) {
        list_category.get(position);
        Toast.makeText(getContext(),"Category Position: " + position,Toast.LENGTH_SHORT).show();
    }
}