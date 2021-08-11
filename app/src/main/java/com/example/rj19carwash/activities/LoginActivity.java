package com.example.rj19carwash.activities;

import static com.example.rj19carwash.Views.toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.example.rj19carwash.R;
import com.example.rj19carwash.responses.LoginResponse;
import com.example.rj19carwash.utilities.Resource;
import com.example.rj19carwash.viewmodels.LoginViewModel;
import com.example.rj19carwash.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;
    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginBinding.tvRegister.setPaintFlags(loginBinding.tvRegister.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        initObservers();

    }

    public void initObservers(){
        loginViewModel.getSendOtpDetails().observe(this, loginResponse -> {
           switch (loginResponse.status){
               case SUCCESS:

                   loginBinding.phoneLayout.setVisibility(View.GONE);
                   loginBinding.otpLayout.setVisibility(View.VISIBLE);
                   loginBinding.blackbg.setVisibility(View.GONE);
                   break;
               case ERROR:
//                   if (Resource.error("otp failed", null).status.equals(""))
                   loginBinding.blackbg.setVisibility(View.GONE);
                   toast(this, loginResponse.message);
                   break;
               case LOADING:
                   loginBinding.blackbg.setVisibility(View.VISIBLE);
                   break;
           }
        });
    }
}