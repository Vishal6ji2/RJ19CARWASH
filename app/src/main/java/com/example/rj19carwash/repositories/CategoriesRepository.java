package com.example.rj19carwash.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rj19carwash.networks.MyApis;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.CategoriesResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesRepository {

    MyApis myApis;

    public CategoriesRepository(){
        myApis = RetrofitClient.getInstance().getapi();
    }

    public LiveData<ArrayList<CategoriesResponse>> getCategories(String token){
        MutableLiveData<ArrayList<CategoriesResponse>> data = new MutableLiveData<>();

        myApis.getCategories("Bearer "+token).enqueue(new Callback<ArrayList<CategoriesResponse>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<CategoriesResponse>> call, @NonNull Response<ArrayList<CategoriesResponse>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<CategoriesResponse>> call, @NonNull Throwable t) {

                data.setValue(null);
            }
        });
        return data;
    }
}
