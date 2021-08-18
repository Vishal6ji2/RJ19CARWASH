package com.example.rj19carwash.activities;


import static com.example.rj19carwash.utilities.ViewUtils.emailPattern;
import static com.example.rj19carwash.utilities.ViewUtils.phonePattern;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.ActivityEditProfileBinding;
import com.example.rj19carwash.databinding.ProfileUpdateLayoutBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity implements OnMapReadyCallback {

    ActivityEditProfileBinding editProfileBinding;

    ProfileUpdateLayoutBinding updateLayoutBinding;

    Place place;

    GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);

        editProfileBinding.editprofileBtnSave.setOnClickListener(view -> showProfileDialog());

        editProfileBinding.editprofileMapview.onCreate(savedInstanceState);
        editProfileBinding.editprofileMapview.getMapAsync(this);

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();

                        if (data != null) {
                            place = Autocomplete.getPlaceFromIntent(data);
                            editProfileBinding.editprofileEtAddress.setText(place.getAddress());

                            mMap.clear();

                            mMap.addMarker(new MarkerOptions().position(Objects.requireNonNull(place.getLatLng())).title(place.getName()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                        }

                    }
                }
        );

        if (!Places.isInitialized()){
            Places.initialize(this, "AIzaSyC3ieLt6-W0prGl727aiPUQR6fBtqpybTY");
        }
        Places.initialize(this, "AIzaSyC3ieLt6-W0prGl727aiPUQR6fBtqpybTY");
        editProfileBinding.editprofileEtAddress.setFocusable(false);
        editProfileBinding.editprofileEtAddress.setOnClickListener(view -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG, Place.Field.NAME);

            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(EditProfileActivity.this);

            resultLauncher.launch(intent);

        });

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        LatLng latLng = new LatLng(-33.852, 151.211);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Car Wash Office"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private void showProfileDialog() {
        Dialog dialog = new Dialog(this);
        updateLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.profile_update_layout, findViewById(R.id.profile_update), false);
        dialog.setContentView(updateLayoutBinding.getRoot());

        if (!editProfileBinding.editprofileEtEmail.getText().toString().matches(emailPattern) && editProfileBinding.editprofileEtEmail.getText().toString().isEmpty()) {
            updateLayoutBinding.profileUpdateIcon.setImageResource(R.drawable.invalidicon);
            updateLayoutBinding.profileUpdateName.setText("Invalid Email id");

        }else if (!editProfileBinding.editprofileEtPhone.getText().toString().matches(phonePattern) && editProfileBinding.editprofileEtPhone.getText().toString().isEmpty()){
            updateLayoutBinding.profileUpdateIcon.setImageResource(R.drawable.invalidicon);
            updateLayoutBinding.profileUpdateName.setText("Invalid Phone");

        }else {
            updateLayoutBinding.profileUpdateIcon.setImageResource(R.drawable.updateicon);
            updateLayoutBinding.profileUpdateName.setText("Profile Updated");

        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        editProfileBinding.editprofileMapview.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        editProfileBinding.editprofileMapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        editProfileBinding.editprofileMapview.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        editProfileBinding.editprofileMapview.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editProfileBinding.editprofileMapview.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        editProfileBinding.editprofileMapview.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        editProfileBinding.editprofileMapview.onLowMemory();
    }
}