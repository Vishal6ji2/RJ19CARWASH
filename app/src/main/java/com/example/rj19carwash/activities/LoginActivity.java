package com.example.rj19carwash.activities;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.networks.CheckInternet.isConnected;
import static com.example.rj19carwash.utilities.ViewUtils.phonePattern;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.ActivityLoginBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.LoginResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.utilities.CustomLoading;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;

    UserSession userSession;

    CustomLoading customLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        customLoading = new CustomLoading(this);

        loginBinding.tvRegister.setPaintFlags(loginBinding.tvRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        loginBinding.tvForgot.setPaintFlags(loginBinding.tvForgot.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        userSession = new UserSession(this);

        loginBinding.btnSendOtp.setOnClickListener(view -> {
            if (isConnected(this)) {
                if (loginBinding.loginEtPhone.getText().toString().isEmpty()){
                    toast(LoginActivity.this, "Please enter Phone number");
                }else if (!((loginBinding.loginEtPhone).getText().toString().matches(phonePattern))) {
                    toast(this, "Phone number is invalid");
                }else if (loginBinding.loginEtPass.getText().toString().isEmpty()){
                    toast(LoginActivity.this, "Please enter password");
                }else {
                    onSendOtpClick(loginBinding.loginEtPhone.getText().toString(), loginBinding.loginEtPass.getText().toString());
                }
            }else {
                toast(this, "Please Check Your Internet Connection");
            }
        });

        loginBinding.tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });

        loginBinding.tvForgot.setOnClickListener(view -> startActivity(new Intent(this, ForgotPwdActivity.class)));

    }

    public void onSendOtpClick(String phone, String password){
/*
        setViewGroupEnabled(loginBinding.phoneLayout, false);
        loginBinding.loginLoadinglayout.setVisibility(View.VISIBLE);
*/

        customLoading.startLoading(getLayoutInflater());

        RetrofitClient.getInstance().getapi().loginResponse(phone, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                customLoading.dismissLoading();

                if (response.isSuccessful() && response.body() != null){

                    if (response.body().getResponseCode() == 201) {

                        toast(LoginActivity.this, response.body().getMessage());

                        userSession.setKeyToken(response.body().getData().getPhone(), response.body().getData().getToken());
                        userSession.setKeyCustomerId(response.body().getData().getId());

                        if (response.body().getData().getAddress() == null && response.body().getData().getName() == null){
                            startActivity(new Intent(LoginActivity.this, CompleteProfileActivity.class));
                            finish();
                        }else {
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }
                    }else {
                        toast(LoginActivity.this, response.body().getMessage());
                    }
                }else {
                    toast(LoginActivity.this,"Wrong Credentials");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {

                customLoading.dismissLoading();

                Log.d("LoginViewModel",t.getMessage());
                toast(LoginActivity.this, "Server error! try again later");
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}