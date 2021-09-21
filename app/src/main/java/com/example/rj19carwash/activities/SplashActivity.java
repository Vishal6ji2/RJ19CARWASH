package com.example.rj19carwash.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.rj19carwash.R;
import com.example.rj19carwash.sessions.UserSession;

public class SplashActivity extends AppCompatActivity {

    UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userSession = new UserSession(this);

        new Handler().postDelayed(() -> {
            if (userSession.checkLogin()){
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            }else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();
        },3000);

    }
}