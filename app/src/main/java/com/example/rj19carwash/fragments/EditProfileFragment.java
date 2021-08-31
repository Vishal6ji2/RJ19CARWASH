package com.example.rj19carwash.fragments;

import static android.app.Activity.RESULT_OK;
import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_ADDRESS;
import static com.example.rj19carwash.sessions.UserSession.KEY_CUSTOMER_ID;
import static com.example.rj19carwash.sessions.UserSession.KEY_EMAIL;
import static com.example.rj19carwash.sessions.UserSession.KEY_NAME;
import static com.example.rj19carwash.sessions.UserSession.KEY_PHONE;
import static com.example.rj19carwash.sessions.UserSession.KEY_PROFILE;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;
import static com.example.rj19carwash.utilities.ViewUtils.emailPattern;
import static com.google.android.libraries.places.widget.AutocompleteActivity.RESULT_ERROR;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.FragmentEditProfileBinding;
import com.example.rj19carwash.databinding.ProfileUpdateLayoutBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.UpdateProfileResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment implements OnMapReadyCallback {

    Place place;

    GoogleMap mMap;

    Dialog dialog;

    FragmentEditProfileBinding editProfileBinding;
    ProfileUpdateLayoutBinding updateLayoutBinding;

    UserSession userSession;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        editProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile,container,false);

        userSession = new UserSession(requireContext());

        setCustomerData();

        editProfileBinding.editprofileBtnSave.setOnClickListener(view -> showProfileDialog());

        editProfileBinding.editprofileMapview.onCreate(savedInstanceState);
        editProfileBinding.editprofileMapview.getMapAsync(this);

        MapsInitializer.initialize(requireContext());

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    if (result.getResultCode() == RESULT_OK){

                        if (data != null) {
                            place = Autocomplete.getPlaceFromIntent(data);
                            editProfileBinding.editprofileEtAddress.setText(place.getAddress());

                            mMap.clear();

                            mMap.addMarker(new MarkerOptions().position(Objects.requireNonNull(place.getLatLng())).title(place.getName()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                        }

                    }else if (result.getResultCode() == RESULT_ERROR){
                        Status status;
                        if (data != null) {
                            status = Autocomplete.getStatusFromIntent(data);
                            Log.d("status", status.getStatusMessage());
                        }

                    }
                }
        );

        editProfileBinding.editprofileTvGps.setOnClickListener(view -> {

        });

        if (!Places.isInitialized()){
            Places.initialize(requireContext(), "AIzaSyC3ieLt6-W0prGl727aiPUQR6fBtqpybTY");
        }
        Places.initialize(requireContext(), "AIzaSyC3ieLt6-W0prGl727aiPUQR6fBtqpybTY");
        editProfileBinding.editprofileEtAddress.setFocusable(false);
        editProfileBinding.editprofileEtAddress.setOnClickListener(view -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG, Place.Field.NAME);

            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(requireContext());

            resultLauncher.launch(intent);

        });

        return editProfileBinding.getRoot();
    }


    private void showProfileDialog() {
        dialog = new Dialog(requireContext());
        updateLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.profile_update_layout, requireActivity().findViewById(R.id.profile_update), false);
        dialog.setContentView(updateLayoutBinding.getRoot());

        if (editProfileBinding.editprofileEtName.getText().toString().isEmpty()){
            updateLayoutBinding.profileUpdateIcon.setImageResource(R.drawable.invalidicon);
            updateLayoutBinding.profileUpdateName.setText("Invalid Name");

            dialog.show();

        }else if (!editProfileBinding.editprofileEtEmail.getText().toString().matches(emailPattern) && editProfileBinding.editprofileEtEmail.getText().toString().isEmpty()) {
            updateLayoutBinding.profileUpdateIcon.setImageResource(R.drawable.invalidicon);
            updateLayoutBinding.profileUpdateName.setText("Invalid Email id");

            dialog.show();

        }else if (!editProfileBinding.editprofileEtAddress.getText().toString().isEmpty()) {
            updateLayoutBinding.profileUpdateIcon.setImageResource(R.drawable.invalidicon);
            updateLayoutBinding.profileUpdateName.setText("Invalid Address");

            dialog.show();

        }else {
            updateProfile();
        }
    }

    private void updateProfile() {

        RetrofitClient.getInstance().getapi().updateProfile(userSession.getKeyToken().get(KEY_TOKEN), userSession.getCustomerId().get(KEY_CUSTOMER_ID), editProfileBinding.editprofileEtName.getText().toString(), editProfileBinding.editprofileEtEmail.getText().toString(),editProfileBinding.editprofileEtAddress.getText().toString())
                .enqueue(new Callback<UpdateProfileResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<UpdateProfileResponse> call, @NonNull Response<UpdateProfileResponse> response) {

                        if (response.isSuccessful()){
                            if (response.body() != null){
                                if (response.body().getResponseCode() == 201){
                                    UpdateProfileResponse.Data profile = response.body().getData();

                                    userSession.setUserSession(profile.getName(),profile.getEmail(),profile.getPhone(),"",profile.getAddress(),profile.getProfile(),profile.getCustomerStatus());

                                    updateLayoutBinding.profileUpdateIcon.setImageResource(R.drawable.updateicon);
                                    updateLayoutBinding.profileUpdateName.setText("Update Successfully");

                                    dialog.show();

                                    Navigation.findNavController(requireView()).navigate(R.id.saveprofile);
                                    
                                }
                            }
                            else {
                                toast(requireActivity(), response.body().getMessage());
                                updateLayoutBinding.profileUpdateIcon.setImageResource(R.drawable.invalidicon);
                                updateLayoutBinding.profileUpdateName.setText("Error occurred!");

                                dialog.show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UpdateProfileResponse> call, @NonNull Throwable t) {

                        Log.d("updateerror", t.getLocalizedMessage());
                        toast(requireContext(), "server error! try again later");

                        updateLayoutBinding.profileUpdateIcon.setImageResource(R.drawable.invalidicon);
                        updateLayoutBinding.profileUpdateName.setText("Error Occurred! try again later");

                        dialog.show();

                    }
                });
    }


    private void setCustomerData() {

        editProfileBinding.editprofileEtAddress.setText(userSession.getCustomerData().get(KEY_ADDRESS));
        editProfileBinding.editprofileEtEmail.setText(userSession.getCustomerData().get(KEY_EMAIL));
        editProfileBinding.editprofileEtName.setText(userSession.getCustomerData().get(KEY_NAME));
        editProfileBinding.editprofileEtPhone.setText(String.format("+91 %s", userSession.getCustomerData().get(KEY_PHONE)));


        if (userSession.getCustomerData().get(KEY_PROFILE) != null && Objects.requireNonNull(userSession.getCustomerData().get(KEY_PROFILE)).equals("")) {
            Picasso.get().load("https://www.rj19carwash.com/" + userSession.getCustomerData().get(KEY_PROFILE)).placeholder(R.drawable.profileicon).into(editProfileBinding.editprofileImage);
        }else {
            editProfileBinding.editprofileImage.setImageResource(R.drawable.profileicon);
        }
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

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        LatLng latLng = getLocationFromAddress(userSession.getCustomerData().get(KEY_ADDRESS));
        mMap.addMarker(new MarkerOptions().position(latLng).title(userSession.getCustomerData().get(KEY_ADDRESS)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

    }


    @Override
    public void onStart() {
        super.onStart();
        editProfileBinding.editprofileMapview.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        editProfileBinding.editprofileMapview.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        editProfileBinding.editprofileMapview.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        editProfileBinding.editprofileMapview.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editProfileBinding.editprofileMapview.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        editProfileBinding.editprofileMapview.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        editProfileBinding.editprofileMapview.onLowMemory();
    }
}