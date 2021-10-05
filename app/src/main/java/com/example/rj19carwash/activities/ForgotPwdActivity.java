package com.example.rj19carwash.activities;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.networks.CheckInternet.isConnected;
import static com.example.rj19carwash.utilities.ViewUtils.phonePattern;
import static com.example.rj19carwash.utilities.ViewUtils.setViewGroupEnabled;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.ActivityForgotPwdBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.ForgotResponse;
import com.example.rj19carwash.responses.LoginResponse;
import com.example.rj19carwash.utilities.CustomLoading;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPwdActivity extends AppCompatActivity {

    ActivityForgotPwdBinding forgotPwdBinding;

    CustomLoading customLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forgotPwdBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_pwd);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        customLoading = new CustomLoading(this);

        forgotPwdBinding.btnForgot.setOnClickListener(view -> {
            if (isConnected(this)) {
                if (!forgotPwdBinding.forgotEtPhone.getText().toString().matches(phonePattern)) {
                    toast(this, "Invalid Mobile No.");
                } else {
                    forgotPassword();
                }
            }else {
                toast(this, "Please Check Your Internet Connection");
            }
        });

    }

    private void forgotPassword() {

        customLoading.startLoading(getLayoutInflater());
/*
        RetrofitClient.getInstance().getapi().forgotPassword(forgotPwdBinding.forgotEtPhone.getText().toString())
                .enqueue(new Callback<ForgotResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ForgotResponse> call, @NonNull Response<ForgotResponse> response) {
                        customLoading.dismissLoading();
                        if (response.isSuccessful() && response.body() != null){
                                if (response.body().getResponseCode() == 201){
                                   toast(ForgotPwdActivity.this, response.body().getMessage());
                                   startActivity(new Intent(ForgotPwdActivity.this, LoginActivity.class));
                                   finish();
                                }else {
                                    toast(ForgotPwdActivity.this, response.body().getMessage());
                                }
                        }else {
                            toast(ForgotPwdActivity.this, "Something went wrong! try again");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ForgotResponse> call, @NonNull Throwable t) {

                        customLoading.dismissLoading();
                        Log.d("forgoterror", t.getLocalizedMessage());
                        toast(ForgotPwdActivity.this, "Server error! try again later");
                    }
                });*/
    }
}