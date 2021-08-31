package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.networks.CheckInternet.isConnected;
import static com.example.rj19carwash.sessions.UserSession.KEY_PHONE;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.FragmentChangePasswordBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.ChangePasswordResponse;
import com.example.rj19carwash.sessions.UserSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {

    FragmentChangePasswordBinding changePasswordBinding;

    UserSession userSession;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        changePasswordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false);

        if (isConnected(requireActivity())){

            initViews();
        }else {
            toast(requireActivity(), "Check your Internet Connection");
        }

        return changePasswordBinding.getRoot();
    }

    private void initViews() {

        userSession = new UserSession(requireActivity());

        if (!changePasswordBinding.changepwdNewpwd.getText().toString().equals("")){
            toast(requireActivity(), "Enter New Password");
        }else if (!changePasswordBinding.changepwdConfirmpwd.getText().toString().equals(changePasswordBinding.changepwdNewpwd.getText().toString())){
            toast(requireActivity(), "Password doesn't match!");
        }else {

            changePassword();
        }

    }

    private void changePassword() {

        RetrofitClient.getInstance().getapi().changePassword("Bearer "+userSession.getKeyToken().get(KEY_TOKEN), userSession.getCustomerData().get(KEY_PHONE), changePasswordBinding.changepwdNewpwd.getText().toString())
                .enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ChangePasswordResponse> call, @NonNull Response<ChangePasswordResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                if (response.body().getResponseCode() == 201){
                                    toast(requireActivity(), response.body().getMessage());
                                }
                            }else {
                                toast(requireActivity(), response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ChangePasswordResponse> call, @NonNull Throwable t) {

                        toast(requireActivity(), "server error! try again later");
                        Log.d("changepwderror",t.getLocalizedMessage());
                    }
                });
    }
}