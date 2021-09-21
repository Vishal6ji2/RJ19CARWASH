package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.networks.CheckInternet.isConnected;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rj19carwash.R;
import com.example.rj19carwash.adapters.ServicesAdapter;
import com.example.rj19carwash.databinding.FragmentServicesBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.ServicesResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.utilities.CustomLoading;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesFragment extends Fragment {

    FragmentServicesBinding servicesBinding;

    ArrayList<ServicesResponse.Service> arrServicesList = new ArrayList<>();

    ServicesAdapter servicesAdapter;

    UserSession userSession;

    CustomLoading customLoading;

    Bundle bundle;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        servicesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container, false);

        bundle = getArguments();
        customLoading = new CustomLoading(requireContext());

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

        servicesBinding.servicesRefresh.setOnRefreshListener(() -> {
            servicesBinding.servicesRefresh.setRefreshing(false);
            initViews();
        });


        OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).popBackStack();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), backPressedCallback);


        return servicesBinding.getRoot();
    }

    private void initViews() {

        userSession = new UserSession(requireActivity());

        servicesBinding.servicesRecyclerview.setHasFixedSize(true);

        getServices();
    }

    private void getServices() {
        customLoading.startLoading(getLayoutInflater());

        arrServicesList.clear();

        RetrofitClient.getInstance().getapi().getServices("Bearer "+userSession.getKeyToken().get(KEY_TOKEN))
                .enqueue(new Callback<ServicesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ServicesResponse> call, @NonNull Response<ServicesResponse> response) {

                        customLoading.dismissLoading();
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                arrServicesList = response.body().getServices();
                                if (arrServicesList.size() != 0) {

                                    servicesBinding.servicesRecyclerview.setVisibility(View.VISIBLE);
                                    servicesBinding.servicesTxtempty.setVisibility(View.GONE);

                                    servicesAdapter = new ServicesAdapter(requireActivity(), arrServicesList);
                                    servicesBinding.servicesRecyclerview.setLayoutManager(new LinearLayoutManager(requireActivity()));
                                    servicesBinding.servicesRecyclerview.setAdapter(servicesAdapter);
                                }else {
                                    servicesBinding.servicesRecyclerview.setVisibility(View.GONE);
                                    servicesBinding.servicesTxtempty.setVisibility(View.VISIBLE);
                                }
                            }else {
                                arrServicesList = null;
                                toast(requireActivity(), "Server error! try again later");

                                servicesBinding.servicesRecyclerview.setVisibility(View.GONE);
                                servicesBinding.servicesTxtempty.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ServicesResponse> call, @NonNull Throwable t) {

                        customLoading.dismissLoading();

                        servicesBinding.servicesRecyclerview.setVisibility(View.GONE);
                        servicesBinding.servicesTxtempty.setVisibility(View.VISIBLE);

                        arrServicesList = null;
                        Log.d("serviceerror", t.getMessage());
                        toast(requireActivity(), "Something went wrong! try again later");

                    }
                });
    }
}