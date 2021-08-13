package com.example.rj19carwash.activities;

import static com.example.rj19carwash.Views.toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.rj19carwash.R;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.LoginResponse;
import com.example.rj19carwash.databinding.ActivityRegisterBinding;
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

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    String verificationId;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    ActivityRegisterBinding registerBinding;

    LoginResponse.Data.SubmitCustomer submitCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        
        registerBinding.tvSignin.setPaintFlags(registerBinding.tvSignin.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        registerBinding.btnRegister.setOnClickListener(view -> onSendOtpClick(Objects.requireNonNull(registerBinding.etPhone.getText()).toString()));

        registerBinding.tvSignin.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });

        registerBinding.btnOtpVerify.setOnClickListener(view -> onVerifyClick(Objects.requireNonNull(registerBinding.etOtpPinView.getText()).toString()));

    }

    public void onSendOtpClick(String phone){
        registerBinding.phoneLayout.setEnabled(false);
        registerBinding.regLoadinglayout.setVisibility(View.VISIBLE);

        RetrofitClient.getInstance().getapi().loginResponse(phone).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (response.body().getResponseCode() == 201){
                        submitCustomer = response.body().getData().getSubmitCustomer();

                        sendOtpToPhone(phone);

                    }else if (response.body().getResponseCode() == 409){

                        registerBinding.regLoadinglayout.setVisibility(View.GONE);
                        registerBinding.phoneLayout.setEnabled(true);
                        toast(RegisterActivity.this, "This Mobile no. is not registered yet");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {

                registerBinding.regLoadinglayout.setVisibility(View.GONE);
                registerBinding.phoneLayout.setEnabled(true);
                Log.d("LoginViewModel",t.getMessage());
                toast(RegisterActivity.this, t.getLocalizedMessage());
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
                registerBinding.regLoadinglayout.setVisibility(View.GONE);
                toast(RegisterActivity.this, e.getLocalizedMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationId = s;

                registerBinding.regLoadinglayout.setVisibility(View.GONE);
                registerBinding.phoneLayout.setVisibility(View.GONE);
                registerBinding.otpLayout.setVisibility(View.VISIBLE);
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

        registerBinding.otpLayout.setEnabled(false);
        registerBinding.regLoadinglayout.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    if (submitCustomer.getToken()!=null) {
                        startActivity(new Intent(this, CategoriesActivity.class).putExtra("token", submitCustomer.getToken()));
                        finish();
                    }
                    registerBinding.regLoadinglayout.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {

                    registerBinding.regLoadinglayout.setVisibility(View.GONE);
                    registerBinding.otpLayout.setEnabled(true);
                    toast(RegisterActivity.this, e.getLocalizedMessage());
                });

    }
}