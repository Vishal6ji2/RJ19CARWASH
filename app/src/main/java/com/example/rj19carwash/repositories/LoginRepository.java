package com.example.rj19carwash.repositories;

import static com.example.rj19carwash.Views.toast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rj19carwash.activities.CategoriesActivity;
import com.example.rj19carwash.networks.MyApis;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.LoginResponse;
import com.example.rj19carwash.utilities.Resource;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    MyApis myApis;

    public LoginRepository(){
        myApis = RetrofitClient.getInstance().getapi();
    }

    FirebaseAuth firebaseAuth;

    String verificationId;

    protected LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    protected void setIsLoading(LiveData<Boolean> isLoading) {
        this.isLoading = isLoading;
    }

    protected LiveData<Boolean> isLoading;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;


    public LiveData<Resource<LoginResponse>> onSendOtpClick(String phone){

        MutableLiveData<Resource<LoginResponse>> data = new MutableLiveData<>();

        myApis.loginResponse(phone).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (response.body().getResponseCode() == 201){
                        LoginResponse.Data.SubmitCustomer submitCustomer = response.body().getData().getSubmitCustomer();

                        data.setValue(Resource.success(response.body()));
                        sendOtpToPhone(phone);

                    }else if (response.body().getResponseCode() == 409){
//                        isLoading.postValue(false);
//                        verifyPhone.postValue(false);

                        data.setValue(Resource.error("This number is not registered", null));

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {

//                verifyPhone.postValue(false);
//                isLoading.postValue(false);
                data.setValue(Resource.error(t.getMessage(), null));
                Log.d("LoginViewModel",t.getMessage());
            }
        });

        return data;
    }

    private void sendOtpToPhone(String phone) {

        firebaseAuth = FirebaseAuth.getInstance();

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                signinWithPhoneAuthCredentials(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {


                setIsLoading(isLoading);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationId = s;


            }
        };

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    public LiveData<Resource<Boolean>> onVerifyClick(String otp){
       return verifyOtp(verificationId, otp);
    }

    private LiveData<Resource<Boolean>> verifyOtp(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        return signinWithPhoneAuthCredentials(credential);
    }

    private LiveData<Resource<Boolean>> signinWithPhoneAuthCredentials(PhoneAuthCredential credential) {

        MutableLiveData<Resource<Boolean>> isVerified = new MutableLiveData<>();
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {

                    isVerified.setValue(Resource.success(true));
//                    verifyOtp.postValue(true);
//                    isLoading.postValue(false);
                    String phone = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getPhoneNumber();

                })
                .addOnFailureListener(e -> {
//                    verifyOtp.postValue(false);
//                    isLoading.postValue(false);
                    isVerified.setValue(Resource.error("otp failed", null));

                });

        return isVerified;
    }

}

