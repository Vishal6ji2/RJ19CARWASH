package com.example.rj19carwash.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.FragmentConfirmationOrderBinding;


public class ConfirmationOrderFragment extends Fragment {


    FragmentConfirmationOrderBinding confirmationOrderBinding;

    int result;
    String message;

    Bundle bundle;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        confirmationOrderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirmation_order, container, false);

        bundle = getArguments();

        if (bundle != null){
            result = bundle.getInt("result");
            message = bundle.getString("message");
        }

        if (result == 1){
            confirmationOrderBinding.confirmationFailurecard.setVisibility(View.GONE);
            confirmationOrderBinding.confirmationSuccesscard.setVisibility(View.VISIBLE);
        }else {
            confirmationOrderBinding.confirmationFailurecard.setVisibility(View.VISIBLE);
            confirmationOrderBinding.confirmationSuccesscard.setVisibility(View.GONE);

        }

        confirmationOrderBinding.confirmationBtnback.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.gobackhome));

        return confirmationOrderBinding.getRoot();
    }

}