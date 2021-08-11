package com.example.rj19carwash.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rj19carwash.networks.MyApis;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.CategoriesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesRepository {

    MyApis myApis;

    public CategoriesRepository(){
        myApis = RetrofitClient.getInstance().getapi();
    }

    public LiveData<CategoriesResponse> getCategories(String token){
        MutableLiveData<CategoriesResponse> data = new MutableLiveData<>();

        myApis.getCategories("Bearer "+token).enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesResponse> call, @NonNull Response<CategoriesResponse> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CategoriesResponse> call, @NonNull Throwable t) {

                data.setValue(null);
            }
        });

        return data;
    }
}
