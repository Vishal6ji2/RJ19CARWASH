package com.example.rj19carwash.activities;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_ADDRESS;
import static com.example.rj19carwash.sessions.UserSession.KEY_EMAIL;
import static com.example.rj19carwash.sessions.UserSession.KEY_NAME;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.ActivityHomeBinding;
import com.example.rj19carwash.sessions.UserSession;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding homeBinding;

    UserSession userSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        userSession = new UserSession(this);
//        homeBinding.homeBnv.setBackground(null);
//        homeBinding.homeBnv.getMenu().getItem(2).setEnabled(false);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navhostfragment);
        NavController navController;


        if (navHostFragment != null) {

            navController = navHostFragment.getNavController();

            NavigationUI.setupWithNavController(homeBinding.homeBnv, navController);
        }
    }
}