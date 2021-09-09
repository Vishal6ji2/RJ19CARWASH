package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.networks.CheckInternet.isConnected;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rj19carwash.R;
import com.example.rj19carwash.adapters.ServicesAdapter;
import com.example.rj19carwash.databinding.FragmentServicesBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.ServicesResponse;
import com.example.rj19carwash.sessions.UserSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesFragment extends Fragment {

    FragmentServicesBinding servicesBinding;

    ArrayList<ServicesResponse.Service> arrServicesList = new ArrayList<>();

    ServicesAdapter servicesAdapter;

    UserSession userSession;

    Bundle bundle;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        servicesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container, false);

        bundle = getArguments();

        if (isConnected(requireActivity())){
            if (bundle != null) {

                if (bundle.getString("subcat_name") != null){
                    servicesBinding.servicesTxtsubname.setText(bundle.getString("subcat_name"));
                }
                initViews();

            }
        }else {
            toast(requireActivity(), "Check Your Internet Connection");
        }
        return servicesBinding.getRoot();
    }

    private void initViews() {

        userSession = new UserSession(requireActivity());

        servicesBinding.servicesRecyclerview.setHasFixedSize(true);

        getServices();
    }

    private void getServices() {

        RetrofitClient.getInstance().getapi().getServices("Bearer "+userSession.getKeyToken().get(KEY_TOKEN))
                .enqueue(new Callback<ServicesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ServicesResponse> call, @NonNull Response<ServicesResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                arrServicesList = response.body().getServices();
                                servicesAdapter = new ServicesAdapter(requireActivity(), arrServicesList);
                                servicesBinding.servicesRecyclerview.setLayoutManager(new LinearLayoutManager(requireActivity()));
                                servicesBinding.servicesRecyclerview.setAdapter(servicesAdapter);
                            }else {
                                arrServicesList = null;
                                toast(requireActivity(), "Server error! try again later");
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ServicesResponse> call, @NonNull Throwable t) {
                        arrServicesList = null;
                        Log.d("serviceerror", t.getMessage());
                        toast(requireActivity(), "Something went wrong! try again later");

                    }
                });
    }
}