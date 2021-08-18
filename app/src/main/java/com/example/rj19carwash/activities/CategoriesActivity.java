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
import com.example.rj19carwash.adapters.CategoriesAdapter;
import com.example.rj19carwash.databinding.ActivityCategoriesBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.CategoriesResponse;
import com.example.rj19carwash.viewmodels.CategoriesViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {

    CategoriesViewModel categoriesViewModel;
    ActivityCategoriesBinding categoriesBinding;

    ArrayList<CategoriesResponse.Category> arrCategoriesList = new ArrayList<>();
    CategoriesAdapter categoriesAdapter;

    public static String token;
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
                toast(this, "Categories loaded successfully");
            }else {
                toast(this, "Something went wrong");
            }
        }else {
            toast(this, "null Intent");
        }

    }

    public void initViews() {

        categoriesBinding.categoriesRecyclerview.setHasFixedSize(true);
//        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);

        getCategories(token);

    }

    public void getCategories(String token) {

        RetrofitClient.getInstance().getapi().getCategories("Bearer "+token).enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesResponse> call, @NonNull Response<CategoriesResponse> response) {
//                           Log.i("TESTING",response.isSuccessful()+"");
//                Log.i("TESTING",response.body()+"");
                if (response.isSuccessful() && response.body() != null){

                    arrCategoriesList = response.body().getCategories();

                    categoriesAdapter = new CategoriesAdapter(CategoriesActivity.this, arrCategoriesList);
                    categoriesBinding.categoriesRecyclerview.setLayoutManager(new GridLayoutManager(CategoriesActivity.this, 2));
                    categoriesBinding.categoriesRecyclerview.setAdapter(categoriesAdapter);

                }else {
                    // this is showing
                        toast(CategoriesActivity.this, "Server error try again later");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoriesResponse> call, @NonNull Throwable t) {

                arrCategoriesList = null;
                Log.d("failCategories",t.getMessage());
                toast(CategoriesActivity.this, "Server error try again later");
            }
        });
    }

}