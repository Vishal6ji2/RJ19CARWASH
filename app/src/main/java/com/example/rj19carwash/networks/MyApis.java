package com.example.rj19carwash.networks;

import com.example.rj19carwash.responses.CategoriesResponse;
import com.example.rj19carwash.responses.LoginResponse;
import com.example.rj19carwash.responses.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MyApis {

    @FormUrlEncoded
    @POST("customer-login")
    Call<LoginResponse> loginResponse(
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("customer-register")
    Call<RegisterResponse> registerResponse(
            @Field("phone") String phone
    );


    @GET("categories")
    Call<CategoriesResponse> getCategories(
            @Header("Authorization") String token
    );
}
