package com.example.rj19carwash.activities;


import static com.example.rj19carwash.Views.toast;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.rj19carwash.R;
import com.example.rj19carwash.adapters.SubCategoriesAdapter;
import com.example.rj19carwash.databinding.ActivitySubCategoriesBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.SubCategoriesResponse;
import static com.example.rj19carwash.activities.CategoriesActivity.token;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubCategoriesActivity extends AppCompatActivity {

    ActivitySubCategoriesBinding categoriesBinding;

    ArrayList<SubCategoriesResponse.Subcategory> arrSubCategoriesList = new ArrayList<>();
    SubCategoriesAdapter subCategoriesAdapter;

    int cat_id;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoriesBinding = DataBindingUtil.setContentView(this, R.layout.activity_sub_categories);

        intent = getIntent();
        if (intent != null) {
            if (intent.getIntExtra("cat_id", 1) !=0){

                cat_id = intent.getIntExtra("cat_id", 1);
                initViews();
                toast(this, "SubCategories loaded successfully");
            }else {
//                it is showing this toast
                toast(this, "Something went wrong");
            }
        }else {
            toast(this, "null Intent");
        }

    }

    public void initViews() {

        categoriesBinding.subcategoriesRecyclerview.setHasFixedSize(true);
//        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);

        if (token != null && cat_id != 0) {
            getSubCategories(token, cat_id);
        }else {
            toast(this, token+" "+cat_id);
        }

    }

    public void getSubCategories(String token, int cat_id) {

        RetrofitClient.getInstance().getapi().getSubCategories("Bearer "+token, cat_id).enqueue(new Callback<SubCategoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubCategoriesResponse> call, @NonNull Response<SubCategoriesResponse> response) {
                
                if (response.isSuccessful() && response.body() != null){
                    arrSubCategoriesList = response.body().getSubcategories();
                    subCategoriesAdapter = new SubCategoriesAdapter(SubCategoriesActivity.this, arrSubCategoriesList);
                    categoriesBinding.subcategoriesRecyclerview.setLayoutManager(new GridLayoutManager(SubCategoriesActivity.this, 2));
                    categoriesBinding.subcategoriesRecyclerview.setAdapter(subCategoriesAdapter);

                }else {
                    arrSubCategoriesList = null;
                    toast(SubCategoriesActivity.this, "Server error! try again later");
                }
            }

            @Override
            public void onFailure(@NonNull Call<SubCategoriesResponse> call, @NonNull Throwable t) {

                arrSubCategoriesList = null;
                Log.d("suberror", t.getMessage());
                toast(SubCategoriesActivity.this, "Something went wrong! try again later");
            }
        });
    }

}
