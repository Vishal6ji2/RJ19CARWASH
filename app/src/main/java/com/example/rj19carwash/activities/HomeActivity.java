package com.example.rj19carwash.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.ActivityHomeBinding;
import com.example.rj19carwash.sessions.ThemeSession;
import com.example.rj19carwash.sessions.UserSession;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding homeBinding;

    UserSession userSession;

    boolean isNightMode = false;

    ThemeSession themeSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        userSession = new UserSession(this);
        themeSession = new ThemeSession(this);
        homeBinding.homeBnv.setBackground(null);
        homeBinding.homeBnv.getMenu().getItem(2).setEnabled(false);

        if (themeSession.checkTheme()){
            homeBinding.homeFabmode.setImageResource(R.drawable.lighticon);
        }else {
            homeBinding.homeFabmode.setImageResource(R.drawable.darkicon);
        }

        homeBinding.homeFabmode.setOnClickListener(view -> {

            if (themeSession.checkTheme()){

                homeBinding.homeFabmode.setImageResource(R.drawable.lighticon);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                isNightMode = false;
                themeSession.setTheme(false);
            }else {
                homeBinding.homeFabmode.setImageResource(R.drawable.darkicon);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                isNightMode = true;
                themeSession.setTheme(true);
            }

        });

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navhostfragment);
        NavController navController;


        if (navHostFragment != null) {

            navController = navHostFragment.getNavController();

            NavigationUI.setupWithNavController(homeBinding.homeBnv, navController);
        }
    }
}