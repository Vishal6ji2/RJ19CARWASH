package com.example.rj19carwash.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.rj19carwash.R;
import com.example.rj19carwash.sessions.ThemeSession;
import com.example.rj19carwash.sessions.UserSession;

public class SplashActivity extends AppCompatActivity {

    UserSession userSession;

//    ThemeSession themeSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        userSession = new UserSession(this);
//        themeSession = new ThemeSession(this);


        new Handler().postDelayed(() -> {
          /*  if (userSession.checkLogin()){
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            }else {
          */      startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//            }
            finish();
        },3000);

    }
}