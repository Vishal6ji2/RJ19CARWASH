package com.example.rj19carwash.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
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
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoriesBinding = DataBindingUtil.setContentView(this, R.layout.activity_categories);

        intent = getIntent();
        if (intent != null) {
            if (intent.getStringExtra("token") != null) {
                token = intent.getStringExtra("token");
                initViews();
            }
        }

    }

    public void initViews() {

        categoriesBinding.categoriesRecyclerview.setHasFixedSize(true);
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);

        categoriesAdapter = new CategoriesAdapter(this, arrCategoriesList);
        categoriesBinding.categoriesRecyclerview.setAdapter(categoriesAdapter);
        getCategories(token);

    }

    public void getCategories(String token) {
        LiveData<ArrayList<CategoriesResponse>> categoriesResponseLiveData = categoriesViewModel.getCategories(token);

        categoriesViewModel.getCategories(token).observe(this, categoriesResponses -> {
            if (categoriesResponses != null){
//                if (categoriesResponses)
            }
        });
    }

}