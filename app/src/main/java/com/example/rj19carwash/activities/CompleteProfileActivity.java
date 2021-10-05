package com.example.rj19carwash.activities;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_ADDRESS;
import static com.example.rj19carwash.sessions.UserSession.KEY_CUSTOMER_ID;
import static com.example.rj19carwash.sessions.UserSession.KEY_EMAIL;
import static com.example.rj19carwash.sessions.UserSession.KEY_NAME;
import static com.example.rj19carwash.sessions.UserSession.KEY_PHONE;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;
import static com.example.rj19carwash.utilities.ViewUtils.emailPattern;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.ActivityCompleteProfileBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.UpdateProfileResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.utilities.CustomLoading;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteProfileActivity extends AppCompatActivity implements OnMapReadyCallback {

    ActivityCompleteProfileBinding completeProfileBinding;
    UserSession userSession;
    
    CustomLoading customLoading;
    GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        completeProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_complete_profile);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        userSession = new UserSession(this);
        customLoading = new CustomLoading(this);

        customLoading.startLoading(getLayoutInflater());
        completeProfileBinding.fillprofileEtPhone.setVisibility(View.GONE);
        setCustomerData();

        completeProfileBinding.fillprofileBtnSave.setOnClickListener(view -> showProfileDialog());

        completeProfileBinding.fillprofileMapview.onCreate(savedInstanceState);
        completeProfileBinding.fillprofileMapview.getMapAsync(this);

        MapsInitializer.initialize(this);
        
    }


    private void showProfileDialog() {

        if (completeProfileBinding.fillprofileEtName.getText().toString().isEmpty()){
            toast(this, "Enter Full Name");

        }else if (!completeProfileBinding.fillprofileEtEmail.getText().toString().matches(emailPattern) && completeProfileBinding.fillprofileEtEmail.getText().toString().isEmpty()) {
            toast(this, "Invalid Email-id");

        }else if (getLocationFromAddress(completeProfileBinding.fillprofileEtAddress.getText().toString()) == null) {
            toast(this, "enter your complete address");

        }else {
            updateProfile();
        }
    }

    private void updateProfile() {
        customLoading.startLoading(getLayoutInflater());

        RetrofitClient.getInstance().getapi().updateProfile("Bearer "+userSession.getKeyToken().get(KEY_TOKEN), userSession.getCustomerId().get(KEY_CUSTOMER_ID), completeProfileBinding.fillprofileEtName.getText().toString(), completeProfileBinding.fillprofileEtEmail.getText().toString(), completeProfileBinding.fillprofileEtAddress.getText().toString())
                .enqueue(new Callback<UpdateProfileResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<UpdateProfileResponse> call, @NonNull Response<UpdateProfileResponse> response) {

                        customLoading.dismissLoading();

                        if (response.isSuccessful()){
                            if (response.body() != null){
                                if (response.body().getResponseCode() == 201){
                                    userSession.setUserSession(response.body().getData().getProfile().getName(), response.body().getData().getProfile().getEmail(), response.body().getData().getProfile().getPhone(), "", response.body().getData().getProfile().getAddress(), "", response.body().getData().getProfile().getCustomerStatus());
                                    toast(CompleteProfileActivity.this, response.body().getMessage());
                                    startActivity(new Intent(CompleteProfileActivity.this, HomeActivity.class));
                                    finish();

                                }else {
                                    toast(CompleteProfileActivity.this, response.body().getMessage());
                                }
                            } else {
                                toast(CompleteProfileActivity.this, "Something went wrong! try again ");
                            }
                        } else {
                            toast(CompleteProfileActivity.this, "Something went wrong! try again ");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UpdateProfileResponse> call, @NonNull Throwable t) {
                        customLoading.dismissLoading();
                        Log.d("updateerror", t.getLocalizedMessage());
                        toast(CompleteProfileActivity.this, "server error! try again later");

                    }
                });

    }

    private void setCustomerData() {
        completeProfileBinding.fillprofileEtPhone.setVisibility(View.GONE);

        customLoading.dismissLoading();
        completeProfileBinding.fillprofileEtAddress.setText(userSession.getCustomerData().get(KEY_ADDRESS));
        completeProfileBinding.fillprofileEtEmail.setText(userSession.getCustomerData().get(KEY_EMAIL));
        completeProfileBinding.fillprofileEtName.setText(userSession.getCustomerData().get(KEY_NAME));
        completeProfileBinding.fillprofileEtPhone.setText(userSession.getCustomerData().get(KEY_PHONE));

        completeProfileBinding.fillprofileEtPhone.setVisibility(View.GONE);

    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            if (address.size() > 0) {
                Address location = address.get(0);
                p1 = new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException ex) {
            toast(this, "Location not found! enter your Complete address");
            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        LatLng latLng = getLocationFromAddress(userSession.getCustomerData().get(KEY_ADDRESS));
        if (latLng != null) {
            mMap.addMarker(new MarkerOptions().position(latLng).title(userSession.getCustomerData().get(KEY_ADDRESS)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        completeProfileBinding.fillprofileMapview.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        completeProfileBinding.fillprofileMapview.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        completeProfileBinding.fillprofileMapview.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        completeProfileBinding.fillprofileMapview.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        completeProfileBinding.fillprofileMapview.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        completeProfileBinding.fillprofileMapview.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        completeProfileBinding.fillprofileMapview.onLowMemory();
    }
}