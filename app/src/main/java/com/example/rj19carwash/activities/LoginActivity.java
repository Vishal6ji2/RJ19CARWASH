package com.example.rj19carwash.activities;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.networks.CheckInternet.isConnected;
import static com.example.rj19carwash.utilities.ViewUtils.phonePattern;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.ActivityLoginBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.LoginResponse;
import com.example.rj19carwash.sessions.ThemeSession;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.utilities.CustomLoading;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;

    UserSession userSession;

    CustomLoading customLoading;

    ThemeSession themeSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        customLoading = new CustomLoading(this);

        userSession = new UserSession(this);
        themeSession = new ThemeSession(this);

        if (themeSession.checkTheme()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            themeSession.setTheme(true);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            themeSession.setTheme(false);
        }


        loginBinding.btnSendOtp.setOnClickListener(view -> {
            if (isConnected(this)) {
                if (loginBinding.loginEtPhone.getText().toString().isEmpty()){
                    toast(LoginActivity.this, "Please enter Phone number");
                }else if (!((loginBinding.loginEtPhone).getText().toString().matches(phonePattern))) {
                    toast(this, "Phone number is invalid");
                }else {
                    onSendOtpClick(loginBinding.loginEtPhone.getText().toString());
                }
            }else {
                toast(this, "Please Check Your Internet Connection");
            }
        });

    }

    public void onSendOtpClick(String phone){

        customLoading.startLoading(getLayoutInflater());

        RetrofitClient.getInstance().getapi().loginResponse(phone).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                customLoading.dismissLoading();

                if (response.isSuccessful() && response.body() != null){

                    if (response.body().getResponseCode() == 201) {

                        toast(LoginActivity.this, response.body().getMessage());

                        userSession.logoutCustomerSession();

                        userSession.setKeyCustomerId(response.body().getData().getId());
                        userSession.setKeyToken(response.body().getData().getPhone(), response.body().getData().getToken());

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