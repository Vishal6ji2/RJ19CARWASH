package com.example.rj19carwash.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.FragmentConfirmOrderBinding;

public class ConfirmOrderFragment extends Fragment {

    FragmentConfirmOrderBinding confirmOrderBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        confirmOrderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_order, container, false);


        return confirmOrderBinding.getRoot();

    }
}