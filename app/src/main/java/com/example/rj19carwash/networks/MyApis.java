package com.example.rj19carwash.networks;

import com.example.rj19carwash.responses.CategoriesResponse;
import com.example.rj19carwash.responses.ChangePasswordResponse;
import com.example.rj19carwash.responses.ForgotResponse;
import com.example.rj19carwash.responses.LoginResponse;
import com.example.rj19carwash.responses.OrderNowResponse;
import com.example.rj19carwash.responses.OrderStatusResponse;
import com.example.rj19carwash.responses.OrdersResponse;
import com.example.rj19carwash.responses.ProfileResponse;
import com.example.rj19carwash.responses.RegisterResponse;
import com.example.rj19carwash.responses.ServicesResponse;
import com.example.rj19carwash.responses.SlotsResponse;
import com.example.rj19carwash.responses.SubCategoriesResponse;
import com.example.rj19carwash.responses.TransactionResponse;
import com.example.rj19carwash.responses.UpdateProfileResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("transaction")
    Call<TransactionResponse> getTransactions(
            @Header("Authorization") String token,
            @Path("id") int customer_id
    );


    @FormUrlEncoded
    @POST("order-now")
    Call<OrderNowResponse> bookOrderNow(
            @Header("Authorization") String token,
            @Field("service_id") int service_id,
            @Field("employee_id") int employee_id,
            @Field("slot") String slot,
            @Field("price") String price,
            @Field("customer_id") int customer_id
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
    @POST("booking-slot")
    Call<SlotsResponse> getSlots(
            @Header("Authorization") String token,
            @Field("service_id") int service_id,
            @Field("employee_id") int employee_id,
            @Field("customer_id") int customer_id
    );

    @FormUrlEncoded
    @POST("customer-change-password")
    Call<ChangePasswordResponse> changePassword(
            @Header("Authorization") String token,
            @Field("phone") String phone,
            @Field("password") String password
    );

    @GET("orders/{customer_id}")
    Call<OrdersResponse> getOrders(
            @Header("Authorization") String token,
            @Path("customer_id") int customer_id

    );

    @FormUrlEncoded
    @POST("order-confirm")
    Call<OrderStatusResponse> setOrderStatus(
            @Header("Authorization") String token,
            @Field("order_id") int order_id,
            @Field("status") String status
    );

}
