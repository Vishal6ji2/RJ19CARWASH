package com.example.rj19carwash.viewmodels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rj19carwash.responses.LoginResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

public class RegisterViewModel extends ViewModel {

    FirebaseAuth firebaseAuth;

    String verificationId;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    MutableLiveData<LoginResponse.Data.SubmitCustomer> customerLiveData;
//    MutableLiveData<Boolean> logOutLiveData;


    public void onClick(View view) {


    }
}
