package com.example.rj19carwash.viewmodels;

import android.content.Context;
import android.util.Log;
import android.widget.MultiAutoCompleteTextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rj19carwash.repositories.LoginRepository;
import com.example.rj19carwash.responses.LoginResponse;
import com.example.rj19carwash.utilities.Resource;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {

    LoginRepository loginRepository;

    public MutableLiveData<String> getPhone() {
        return phone;
    }

    public MutableLiveData<String> getOtp() {
        return otp;
    }

    MutableLiveData<String> phone = new MutableLiveData<>();

    MutableLiveData<String> otp = new MutableLiveData<>();

    public LoginViewModel() {
        loginRepository = new LoginRepository();
    }

    public LiveData<Resource<LoginResponse>> getSendOtpDetails(){
        return loginRepository.onSendOtpClick(phone.getValue());
    }

    public LiveData<Resource<Boolean>> getVerifyOtpDetails(){
        return loginRepository.onVerifyClick(otp.getValue());
    }

}
