package com.example.rj19carwash.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import com.example.rj19carwash.R;
import com.example.rj19carwash.viewmodels.RegisterViewModel;
import com.example.rj19carwash.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRegisterBinding registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        RegisterViewModel registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerBinding.setRegister(registerViewModel);


        registerBinding.tvSignin.setPaintFlags(registerBinding.tvSignin.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        registerBinding.btnRegister.setOnClickListener(registerViewModel::onClick);

        registerBinding.tvSignin.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

    }

}