package com.example.rj19carwash.activities;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.networks.CheckInternet.isConnected;
import static com.example.rj19carwash.utilities.ViewUtils.phonePattern;
import static com.example.rj19carwash.utilities.ViewUtils.setViewGroupEnabled;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.ActivityForgotPwdBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.ForgotResponse;
import com.example.rj19carwash.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPwdActivity extends AppCompatActivity {

    ActivityForgotPwdBinding forgotPwdBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        forgotPwdBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_pwd);

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

        setViewGroupEnabled(forgotPwdBinding.phoneLayout, false);
        forgotPwdBinding.forgotLoadinglayout.setVisibility(View.VISIBLE);


        RetrofitClient.getInstance().getapi().forgotPassword(forgotPwdBinding.forgotEtPhone.getText().toString())
                .enqueue(new Callback<ForgotResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ForgotResponse> call, @NonNull Response<ForgotResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                if (response.body().getResponseCode() == 201){
                                   toast(ForgotPwdActivity.this, response.body().getMessage());
                                }
                            }else {
                                toast(ForgotPwdActivity.this, "try again later");
                            }
                        }

                        forgotPwdBinding.forgotLoadinglayout.setVisibility(View.GONE);
                        setViewGroupEnabled(forgotPwdBinding.phoneLayout, true);

                    }

                    @Override
                    public void onFailure(@NonNull Call<ForgotResponse> call, @NonNull Throwable t) {
                        forgotPwdBinding.forgotLoadinglayout.setVisibility(View.GONE);
                        setViewGroupEnabled(forgotPwdBinding.phoneLayout, true);

                        Log.d("forgoterror", t.getLocalizedMessage());
                        toast(ForgotPwdActivity.this, "Server error! try again later");
                    }
                });
    }
}