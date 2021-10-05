package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_PHONE;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.FragmentChangePasswordBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.ChangePasswordResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.utilities.CustomLoading;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {

    FragmentChangePasswordBinding changePasswordBinding;

    UserSession userSession;

    CustomLoading customLoading;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        changePasswordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        userSession = new UserSession(requireContext());

        customLoading = new CustomLoading(requireContext());


        changePasswordBinding.changepwdBtnsave.setOnClickListener(view -> {

            if (changePasswordBinding.changepwdNewpwd.getText().toString().equals("")){
                toast(requireActivity(), "Enter New Password");
            }else if (changePasswordBinding.changepwdNewpwd.getText().toString().length() <8){
                changePasswordBinding.changepwdNewpwd.setText("");
                changePasswordBinding.changepwdNewpwd.setHint("min 8 digits required");
            }else if (!(changePasswordBinding.changepwdConfirmpwd.getText().toString().equals(changePasswordBinding.changepwdNewpwd.getText().toString()))){
                toast(requireActivity(), "Password doesn't match!");
            }else {
                changePassword();
            }

        });

        OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                Navigation.findNavController(requireView()).popBackStack();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), backPressedCallback);

        return changePasswordBinding.getRoot();
    }


    private void changePassword() {

        customLoading.startLoading(getLayoutInflater());

    /*    RetrofitClient.getInstance().getapi().changePassword("Bearer "+userSession.getKeyToken().get(KEY_TOKEN), userSession.getCustomerData().get(KEY_PHONE), changePasswordBinding.changepwdNewpwd.getText().toString())
                .enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ChangePasswordResponse> call, @NonNull Response<ChangePasswordResponse> response) {
                       customLoading.dismissLoading();
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                if (response.body().getResponseCode() == 201){
                                    toast(requireActivity(), response.body().getMessage());
                                    Navigation.findNavController(requireView()).navigate(R.id.goto_profile);
                                }
                            }else {
                                toast(requireActivity(), response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ChangePasswordResponse> call, @NonNull Throwable t) {

                        customLoading.dismissLoading();
                        toast(requireActivity(), "server error! try again later");
                        Log.d("changepwderror",t.getLocalizedMessage());
                    }
                });
    */}

}