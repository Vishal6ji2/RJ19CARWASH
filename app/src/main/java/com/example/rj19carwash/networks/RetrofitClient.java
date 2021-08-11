package com.example.rj19carwash.networks;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import okhttp3.OkHttpClient;
import okhttp3.internal.http.CallServerInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient retrofitClient;

    private static Retrofit retrofit;

    private RetrofitClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//                .callTimeout(10, TimeUnit.SECONDS);
//                .connectTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS);
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
        Gson gson = builder
                .setLenient()
                .create();

        String BASE_URL = "https://www.rj19carwash.com/api/";
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

    }

    @NonNull
    public static synchronized RetrofitClient getInstance(){
        if (retrofitClient == null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    @NonNull
    public MyApis getapi(){
        return retrofit.create(MyApis.class);
    }

}
