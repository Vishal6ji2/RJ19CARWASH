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
import com.example.rj19carwash.databinding.ActivityRegisterBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.RegisterResponse;
import com.example.rj19carwash.utilities.CustomLoading;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding registerBinding;

    CustomLoading customLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        
        registerBinding.tvSignin.setPaintFlags(registerBinding.tvSignin.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        customLoading = new CustomLoading(this);

        registerBinding.btnRegister.setOnClickListener(view -> {
            if (isConnected(this)) {
                if (registerBinding.registerEtPhone.getText().toString().isEmpty()){
                    toast(RegisterActivity.this, "Please enter Phone number");
                }else if (!((registerBinding.registerEtPhone).getText().toString().matches(phonePattern))) {
                    toast(this, "Phone number is invalid");
                } else {
                    onSendOtpClick(Objects.requireNonNull(Objects.requireNonNull(registerBinding.registerEtPhone).getText()).toString());
                }
            }else {
                toast(this, "Please Check Your Internet Connection");
            }

        });

        registerBinding.tvSignin.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

    }

    public void onSendOtpClick(String phone){

        customLoading.startLoading(getLayoutInflater());

        RetrofitClient.getInstance().getapi().registerResponse(phone).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {

                customLoading.dismissLoading();
                if (response.isSuccessful() ){
                    if (response.body() != null) {

                        if (response.body().getResponseCode() == 201) {
                            toast(RegisterActivity.this, response.body().getMessage());
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();

                        } else {
                            toast(RegisterActivity.this, response.body().getMessage());
                        }
                    }else {
                        toast(RegisterActivity.this, "Something went wrong! try again");
                    }
                }else {
                    toast(RegisterActivity.this, "Something went wrong! try again");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {

                customLoading.dismissLoading();
                Log.d("RegisterViewModel",t.getMessage());
                toast(RegisterActivity.this, "Server error! try again later");
            }
        });

    }

}