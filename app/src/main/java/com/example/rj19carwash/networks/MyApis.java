package com.example.rj19carwash.networks;

import com.example.rj19carwash.responses.CategoriesResponse;
import com.example.rj19carwash.responses.LoginResponse;
import com.example.rj19carwash.responses.RegisterResponse;
import com.example.rj19carwash.responses.SubCategoriesResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyApis {

    @FormUrlEncoded
    @POST("customer-login")
    Call<LoginResponse> loginResponse(
            @Field("phone") String phone,
            @Field("password") String password
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

    @GET("categories/{id}")
    Call<SubCategoriesResponse> getSubCategories(
            @Header("Authorization") String token,
            @Path("id") int cat_id

    );
}
