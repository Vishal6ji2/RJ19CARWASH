package com.example.rj19carwash.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.FragmentBookServiceBinding;
import com.example.rj19carwash.sessions.UserSession;
import com.squareup.picasso.Picasso;

public class BookServiceFragment extends Fragment {

    FragmentBookServiceBinding bookServiceBinding;

    UserSession userSession;

    Bundle bundle;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bookServiceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_service, container, false);

        initViews();

        bundle = getArguments();
        if (bundle != null){
            Picasso.get().load("https://www.rj19carwash.com/"+bundle.getString("image")).into(bookServiceBinding.bookserviceSerimg);
            bookServiceBinding.bookserviceTxtname.setText(bundle.getString("name"));
            bookServiceBinding.bookserviceTxtdesc.setText(bundle.getString("description"));
            bundle.getString("employees");

        }

        return bookServiceBinding.getRoot();
    }

    private void initViews() {

        userSession = new UserSession(requireActivity());

    }

    public void getSlots(){

    }
}