package com.example.rj19carwash.networks;

import com.example.rj19carwash.responses.CategoriesResponse;
import com.example.rj19carwash.responses.ChangePasswordResponse;
import com.example.rj19carwash.responses.ForgotResponse;
import com.example.rj19carwash.responses.LoginResponse;
import com.example.rj19carwash.responses.ProfileResponse;
import com.example.rj19carwash.responses.RegisterResponse;
import com.example.rj19carwash.responses.ServicesResponse;
import com.example.rj19carwash.responses.SlotsResponse;
import com.example.rj19carwash.responses.SubCategoriesResponse;
import com.example.rj19carwash.responses.UpdateProfileResponse;

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

    @GET("profile/{phone}")
    Call<ProfileResponse> getProfile(
            @Header("Authorization") String token,
            @Path("phone") String phone

    );

    @GET("viewservice")
    Call<ServicesResponse> getServices(
            @Header("Authorization") String token
    );


    @FormUrlEncoded
    @POST("updateprofile")
    Call<UpdateProfileResponse> updateProfile(
            @Header("Authorization") String token,
            @Field("id") int id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("address") String address
    );

    @FormUrlEncoded
    @POST("customer-forgot-password")
    Call<ForgotResponse> forgotPassword(
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST
    Call<SlotsResponse> getSlots(
            @Header("Authorization") String token,
            @Field("service_id") String service_id,
            @Field("employee_id") String employee_id
    );

    @FormUrlEncoded
    @POST
    Call<ChangePasswordResponse> changePassword(
            @Header("Authorization") String token,
            @Field("phone") String phone,
            @Field("password") String password
    );
}
