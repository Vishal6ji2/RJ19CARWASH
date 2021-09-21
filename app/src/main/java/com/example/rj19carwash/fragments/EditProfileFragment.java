package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_ADDRESS;
import static com.example.rj19carwash.sessions.UserSession.KEY_CUSTOMER_ID;
import static com.example.rj19carwash.sessions.UserSession.KEY_EMAIL;
import static com.example.rj19carwash.sessions.UserSession.KEY_NAME;
import static com.example.rj19carwash.sessions.UserSession.KEY_PHONE;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;
import static com.example.rj19carwash.utilities.ViewUtils.emailPattern;
import static com.example.rj19carwash.utilities.ViewUtils.phonePattern;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.FragmentEditProfileBinding;
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

public class EditProfileFragment extends Fragment implements OnMapReadyCallback {

//    Place place;

    GoogleMap mMap;

    FragmentEditProfileBinding editProfileBinding;

    CustomLoading customLoading;

    UserSession userSession;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        editProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile,container,false);

        userSession = new UserSession(requireContext());

        customLoading = new CustomLoading(requireContext());

        customLoading.startLoading(getLayoutInflater());
        editProfileBinding.editprofileEtPhone.setVisibility(View.GONE);
        setCustomerData();

        editProfileBinding.editprofileBtnSave.setOnClickListener(view -> showProfileDialog());

        editProfileBinding.editprofileMapview.onCreate(savedInstanceState);
        editProfileBinding.editprofileMapview.getMapAsync(this);

        MapsInitializer.initialize(requireContext());

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Navigation.findNavController(requireView()).popBackStack();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);


       /* ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
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


        if (!Places.isInitialized()){
            Places.initialize(requireContext(), "AIzaSyBVouc8BYsxVDP2u3575z2RtgAFQtsiVks");
        }
        Places.initialize(requireContext(), "AIzaSyBVouc8BYsxVDP2u3575z2RtgAFQtsiVks");
        editProfileBinding.editprofileEtAddress.setFocusable(false);
        editProfileBinding.editprofileEtAddress.setOnClickListener(view -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG, Place.Field.NAME);

            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(requireContext());

            resultLauncher.launch(intent);

        });
*/
        return editProfileBinding.getRoot();
    }


    private void showProfileDialog() {

        if (editProfileBinding.editprofileEtName.getText().toString().isEmpty()){
            toast(requireContext(), "Enter Name");

        }else if (!editProfileBinding.editprofileEtEmail.getText().toString().matches(emailPattern) && editProfileBinding.editprofileEtEmail.getText().toString().isEmpty()) {
            toast(requireContext(), "Invalid Email-id");

        }else if (!editProfileBinding.editprofileEtPhone.getText().toString().matches(phonePattern) && editProfileBinding.editprofileEtPhone.getText().toString().isEmpty()){
            toast(requireContext(), "Invalid Phone number");

        } else if (getLocationFromAddress(editProfileBinding.editprofileEtAddress.getText().toString()) == null) {
            toast(requireContext(), "enter your complete address");

        }else {
            updateProfile();
        }
  }

    private void updateProfile() {
        customLoading.startLoading(getLayoutInflater());

        RetrofitClient.getInstance().getapi().updateProfile("Bearer "+userSession.getKeyToken().get(KEY_TOKEN), userSession.getCustomerId().get(KEY_CUSTOMER_ID), editProfileBinding.editprofileEtName.getText().toString(), editProfileBinding.editprofileEtEmail.getText().toString(),editProfileBinding.editprofileEtAddress.getText().toString())
                .enqueue(new Callback<UpdateProfileResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<UpdateProfileResponse> call, @NonNull Response<UpdateProfileResponse> response) {

                        customLoading.dismissLoading();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getResponseCode() == 201) {
                                    UpdateProfileResponse.Data.Profile profile = response.body().getData().getProfile();

                                    userSession.setUserSession(profile.getName(), profile.getEmail(), profile.getPhone(), "", profile.getAddress(), profile.getProfile(), profile.getCustomerStatus());
                                    toast(requireContext(), response.body().getMessage());

                                    Navigation.findNavController(requireView()).navigate(R.id.saveprofile);
                                } else {
                                    toast(requireActivity(), response.body().getMessage());
                                }
                            } else {
                                toast(requireContext(), "Something went wrong! try again ");
                            }
                        } else {
                            toast(requireContext(), "Something went wrong! try again ");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UpdateProfileResponse> call, @NonNull Throwable t) {

                        customLoading.dismissLoading();
                        Log.d("updateerror", t.getLocalizedMessage());
                        toast(requireContext(), "server error! try again later");

                    }
                });
    }

    private void setCustomerData() {
        editProfileBinding.editprofileEtPhone.setVisibility(View.GONE);

        customLoading.dismissLoading();
        editProfileBinding.editprofileEtAddress.setText(userSession.getCustomerData().get(KEY_ADDRESS));
        editProfileBinding.editprofileEtEmail.setText(userSession.getCustomerData().get(KEY_EMAIL));
        editProfileBinding.editprofileEtName.setText(userSession.getCustomerData().get(KEY_NAME));
        editProfileBinding.editprofileEtPhone.setText(userSession.getCustomerData().get(KEY_PHONE));

        editProfileBinding.editprofileEtPhone.setVisibility(View.GONE);

       /* if (userSession.getCustomerData().get(KEY_PROFILE) != null && Objects.requireNonNull(userSession.getCustomerData().get(KEY_PROFILE)).equals("")) {
            Picasso.get().load("https://www.rj19carwash.com/" + userSession.getCustomerData().get(KEY_PROFILE)).placeholder(R.drawable.profileicon).into(editProfileBinding.editprofileImage);
        }else {
            editProfileBinding.editprofileImage.setImageResource(R.drawable.profileicon);
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