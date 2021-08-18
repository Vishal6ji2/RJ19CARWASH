package com.example.rj19carwash.activities;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.utilities.ViewUtils.phonePattern;
import static com.example.rj19carwash.utilities.ViewUtils.setViewGroupEnabled;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.ActivityRegisterBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.RegisterResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding registerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        
        registerBinding.tvSignin.setPaintFlags(registerBinding.tvSignin.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);


        registerBinding.btnRegister.setOnClickListener(view -> {
            if (!((registerBinding.registerEtPhone).getText().toString().matches(phonePattern))){
                toast(this, "Phone number is invalid");
            }else {
                onSendOtpClick(Objects.requireNonNull(Objects.requireNonNull(registerBinding.registerEtPhone).getText()).toString());
            }

        });

        registerBinding.tvSignin.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

    }

    static boolean checkAllSameDigits(String toString) {
        int count = toString.length();
        boolean check = false;
        for (int i = 1; i<count; i++){
            check = toString.charAt(i) == toString.charAt(0);
        }
        return check;
    }

    public void onSendOtpClick(String phone){

        setViewGroupEnabled(Objects.requireNonNull(registerBinding.registerPhoneLayout), false);
        registerBinding.regLoadinglayout.setVisibility(View.VISIBLE);

        RetrofitClient.getInstance().getapi().registerResponse(phone).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null){

                    if (response.body().getResponseCode() == 201) {
                        toast(RegisterActivity.this, response.body().getMessage());
                        registerBinding.regLoadinglayout.setVisibility(View.GONE);
                        setViewGroupEnabled(registerBinding.registerPhoneLayout, true);
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();

                    }else {
                        registerBinding.regLoadinglayout.setVisibility(View.GONE);
                        setViewGroupEnabled(registerBinding.registerPhoneLayout, true);
                        toast(RegisterActivity.this, response.body().getMessage());
                    }

                }else {
                    registerBinding.regLoadinglayout.setVisibility(View.GONE);
                    setViewGroupEnabled(registerBinding.registerPhoneLayout, true);
                    toast(RegisterActivity.this, "Wrong Credentials");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {

                registerBinding.regLoadinglayout.setVisibility(View.GONE);
                setViewGroupEnabled(registerBinding.registerPhoneLayout, true);
                Log.d("LoginViewModel",t.getMessage());
                toast(RegisterActivity.this, "Server error! try again later");
            }
        });

    }

}