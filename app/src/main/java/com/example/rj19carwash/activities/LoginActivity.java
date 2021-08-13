package com.example.rj19carwash.activities;

import static com.example.rj19carwash.Views.toast;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.ActivityLoginBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.LoginResponse;
import com.google.firebase.FirebaseApp;
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

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;

    FirebaseAuth firebaseAuth;

    String verificationId;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    LoginResponse.Data.SubmitCustomer submitCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        loginBinding.tvRegister.setPaintFlags(loginBinding.tvRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        loginBinding.btnSendOtp.setOnClickListener(view -> onSendOtpClick(Objects.requireNonNull(loginBinding.etPhone.getText()).toString()));

        loginBinding.btnOtpVerify.setOnClickListener(view -> onVerifyClick(Objects.requireNonNull(loginBinding.etOtpPinView.getText()).toString()));

    }

    public void onSendOtpClick(String phone){
        loginBinding.phoneLayout.setEnabled(false);
        loginBinding.loginLoadinglayout.setVisibility(View.VISIBLE);

        RetrofitClient.getInstance().getapi().loginResponse(phone).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (response.body().getResponseCode() == 201){
                        submitCustomer = response.body().getData().getSubmitCustomer();

                        sendOtpToPhone(phone);

                    }else if (response.body().getResponseCode() == 409){

                        loginBinding.loginLoadinglayout.setVisibility(View.GONE);
                        loginBinding.phoneLayout.setEnabled(true);
                        toast(LoginActivity.this, "This Mobile no. is not registered yet");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {

                loginBinding.loginLoadinglayout.setVisibility(View.GONE);
                loginBinding.phoneLayout.setEnabled(true);
                Log.d("LoginViewModel",t.getMessage());
                toast(LoginActivity.this, t.getLocalizedMessage());
            }
        });

    }

    private void sendOtpToPhone(String phone) {

        FirebaseApp.initializeApp(this);

        firebaseAuth = FirebaseAuth.getInstance();

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                loginBinding.loginLoadinglayout.setVisibility(View.GONE);
                toast(LoginActivity.this, e.getLocalizedMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationId = s;

                loginBinding.loginLoadinglayout.setVisibility(View.GONE);
                loginBinding.phoneLayout.setVisibility(View.GONE);
                loginBinding.otpLayout.setVisibility(View.VISIBLE);
            }
        };

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber("+91"+phone)
                .setActivity(this)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    public void onVerifyClick(String otp){
        verifyOtp(verificationId, otp);
    }

    private void verifyOtp(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredentials(credential);
    }

    private void signInWithPhoneAuthCredentials(PhoneAuthCredential credential) {

        loginBinding.otpLayout.setEnabled(false);
        loginBinding.loginLoadinglayout.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    if (submitCustomer.getToken()!=null) {
                        startActivity(new Intent(this, CategoriesActivity.class).putExtra("token", submitCustomer.getToken()));
                        finish();
                    }
                    loginBinding.loginLoadinglayout.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {

                    loginBinding.loginLoadinglayout.setVisibility(View.GONE);
                    loginBinding.otpLayout.setEnabled(true);
                    toast(LoginActivity.this, e.getLocalizedMessage());
                });

    }

}