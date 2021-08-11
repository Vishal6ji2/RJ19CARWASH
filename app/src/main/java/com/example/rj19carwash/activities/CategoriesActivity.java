package com.example.rj19carwash.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.rj19carwash.R;
import com.example.rj19carwash.adapters.CategoriesAdapter;
import com.example.rj19carwash.databinding.ActivityCategoriesBinding;
import com.example.rj19carwash.repositories.CategoriesRepository;
import com.example.rj19carwash.responses.CategoriesResponse;
import com.example.rj19carwash.viewmodels.CategoriesViewModel;

import java.util.ArrayList;
import java.util.Collection;

public class CategoriesActivity extends AppCompatActivity {

    CategoriesViewModel categoriesViewModel;
    ActivityCategoriesBinding categoriesBinding;

    ArrayList<CategoriesResponse> arrCategoriesList = new ArrayList<>();
    CategoriesAdapter categoriesAdapter;

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoriesBinding = DataBindingUtil.setContentView(this, R.layout.activity_categories);

        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);

        initViews();

    }

    public void initViews(){
        categoriesBinding.categoriesRecyclerview.setHasFixedSize(true);
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        categoriesAdapter = new CategoriesAdapter(this, arrCategoriesList);
        categoriesBinding.categoriesRecyclerview.setLayoutManager(new GridLayoutManager(this,2));
        categoriesBinding.categoriesRecyclerview.setAdapter(categoriesAdapter);
        getCategories(token);
    }

    public void getCategories(String token){
        categoriesViewModel.getCategories(token).observe(this, categoriesResponse -> {
            if (categoriesResponse != null){
                arrCategoriesList.addAll((Collection<? extends CategoriesResponse>) categoriesResponse);
            }
        });
    }
}