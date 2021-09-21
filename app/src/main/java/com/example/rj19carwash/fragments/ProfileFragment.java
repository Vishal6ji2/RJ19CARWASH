package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_ADDRESS;
import static com.example.rj19carwash.sessions.UserSession.KEY_EMAIL;
import static com.example.rj19carwash.sessions.UserSession.KEY_NAME;
import static com.example.rj19carwash.sessions.UserSession.KEY_PHONE;
import static com.example.rj19carwash.sessions.UserSession.KEY_PROFILE;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rj19carwash.R;
import com.example.rj19carwash.activities.LoginActivity;
import com.example.rj19carwash.databinding.FragmentProfileBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.ProfileResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.utilities.CustomLoading;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "ProfileFragment";
    FragmentProfileBinding profileBinding;

    UserSession userSession;

    GoogleMap mMap;

    CustomLoading customLoading;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        profileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        profileBinding.profileMapview.onCreate(savedInstanceState);

        MapsInitializer.initialize(requireContext());

        userSession = new UserSession(requireContext());
        customLoading = new CustomLoading(requireContext());

        getProfileDetails();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);

        profileBinding.profileEdit.setOnClickListener(view -> {
            // change to edit fragment
            Navigation.findNavController(view).navigate(R.id.editprofile);

        });

        profileBinding.profileChangepwd.setOnClickListener(view -> {
            // change to change password fragment
            Navigation.findNavController(view).navigate(R.id.changepassword);
        });

        profileBinding.profileLogout.setOnClickListener(view -> {
            userSession.logoutCustomerSession();
            startActivity(new Intent(requireContext(), LoginActivity.class));

        });

        return profileBinding.getRoot();
    }

    private void getProfileDetails() {

        customLoading.startLoading(getLayoutInflater());

        RetrofitClient.getInstance().getapi().getProfile("Bearer "+userSession.getKeyToken().get(KEY_TOKEN), userSession.getCustomerData().get(KEY_PHONE))
                .enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {

                        customLoading.dismissLoading();
                        if (response.isSuccessful()){
                            if (response.body() != null) {
                                if (response.body().getResponseCode() == 201) {
                                    ProfileResponse.Data profile = response.body().getData();

                                    try {
                                        int id = profile.getProfile().getId() ;
                                        String name = profile.getProfile().getName();
                                        String email = profile.getProfile().getEmail();
                                        String phone = profile.getProfile().getPhone() ;
                                        String gender = profile.getProfile().getGender();
                                        String address = profile.getProfile().getAddress();
                                        String getProfile = profile.getProfile().getProfile() ;
                                        String status = profile.getProfile().getCustomerStatus() ;

                                        userSession.setKeyCustomerId(id);
                                        userSession.setUserSession(name, email, phone, gender, address, getProfile, status);
                                    }catch (Exception e){
                                        Toast.makeText(getContext(), "Error while fetching data. Try again", Toast.LENGTH_SHORT).show();
                                    }

                                    Log.d("profile",response.body().getData().getProfile().getId().toString());
                                    setCustomerData();
                                }
                            }else {
                                /*toast(requireActivity(), "Something went wrong! try again later");*/
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {

                        customLoading.dismissLoading();
                        Log.d(TAG, t.getMessage());
                        toast(requireActivity(), "Server error! try again later");
                    }
                });
    }

    private void setCustomerData() {

        customLoading.dismissLoading();

        if (userSession.getCustomerData().get(KEY_ADDRESS).isEmpty()){
            profileBinding.profileAddress.setVisibility(View.GONE);
        }else {
            profileBinding.profileAddress.setVisibility(View.VISIBLE);
            profileBinding.profileAddress.setText(userSession.getCustomerData().get(KEY_ADDRESS));
        }
        if (userSession.getCustomerData().get(KEY_EMAIL).isEmpty()){
            profileBinding.profileEmail.setVisibility(View.GONE);
        }else {
            profileBinding.profileEmail.setVisibility(View.VISIBLE);
            profileBinding.profileEmail.setText(userSession.getCustomerData().get(KEY_EMAIL));
        }
        if (userSession.getCustomerData().get(KEY_NAME).isEmpty()){
            profileBinding.profileName.setVisibility(View.GONE);
        }else {
            profileBinding.profileName.setVisibility(View.VISIBLE);
            profileBinding.profileName.setText(userSession.getCustomerData().get(KEY_NAME));
        }
        profileBinding.profilePhone.setText(userSession.getCustomerData().get(KEY_PHONE));

        profileBinding.profileMapview.getMapAsync(this);

       /* if (userSession.getCustomerData().get(KEY_PROFILE) != null && Objects.requireNonNull(userSession.getCustomerData().get(KEY_PROFILE)).equals("")) {
            Picasso.get().load("https://www.rj19carwash.com/" + userSession.getCustomerData().get(KEY_PROFILE)).placeholder(R.drawable.profileicon).into(profileBinding.profileImage);
        }else {
            profileBinding.profileImage.setImageResource(R.drawable.profileicon);
        }*/
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(requireContext());
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
            toast(requireContext(), "Location not found! enter your Complete address");
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
    public void onResume() {
        super.onResume();
        profileBinding.profileMapview.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        profileBinding.profileMapview.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileBinding.profileMapview.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        profileBinding.profileMapview.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        profileBinding.profileMapview.onLowMemory();
    }

}