package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_CUSTOMER_ID;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rj19carwash.R;
import com.example.rj19carwash.adapters.OrdersAdapter;
import com.example.rj19carwash.databinding.FragmentOrdersBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.OrdersResponse;
import com.example.rj19carwash.sessions.UserSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment {

    FragmentOrdersBinding ordersBinding;

    OrdersAdapter ordersAdapter;

    UserSession userSession;

    ArrayList<OrdersResponse.Datum> arrOrdersList = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ordersBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);

        initViews();

        return ordersBinding.getRoot();
    }

    private void initViews() {

        userSession = new UserSession(requireActivity());

        ordersBinding.ordersRecyclerview.setHasFixedSize(true);

        getOrders(userSession.getKeyToken().get(KEY_TOKEN), userSession.getCustomerId().get(KEY_CUSTOMER_ID));

    }

    private void getOrders(String token, Integer customer_id) {

        RetrofitClient.getInstance().getapi().getOrders("Bearer "+token, customer_id)
                .enqueue(new Callback<OrdersResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<OrdersResponse> call, @NonNull Response<OrdersResponse> response) {

                        if (response.isSuccessful()){
                            if (response.body() != null){
                                if (response.body().getResponseCode() == 201){
                                    arrOrdersList = response.body().getData();
                                    ordersAdapter = new OrdersAdapter(requireActivity(), arrOrdersList);
                                    ordersBinding.ordersRecyclerview.setLayoutManager(new LinearLayoutManager(requireActivity()));
                                    ordersBinding.ordersRecyclerview.setAdapter(ordersAdapter);

                                }else {
                                    // this is showing
                                    toast(requireActivity(), response.message());

                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<OrdersResponse> call, @NonNull Throwable t) {

                        arrOrdersList = null;
                        Log.d("failorders",t.getMessage());
                        toast(requireActivity(), "Server error try again later");
                    }
                });
    }
}