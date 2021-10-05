package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;
import static com.example.rj19carwash.utilities.TimeUtils.getDateTime;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.FragmentConfirmOrderBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.OrderStatusResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.utilities.CustomLoading;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderFragment extends Fragment {

    FragmentConfirmOrderBinding confirmOrderBinding;

    Bundle bundle;

    String service_image, service_name, service_price, service_time, service_status;

    int order_id;

    CustomLoading customLoading;

    UserSession userSession;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        confirmOrderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_order, container, false);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        bundle = getArguments();

        userSession = new UserSession(requireContext());
        customLoading = new CustomLoading(requireContext());

        if (bundle != null){
            service_image = bundle.getString("service_image");
            service_name = bundle.getString("service_name");
            service_price = bundle.getString("service_price");
            service_time = bundle.getString("service_time");
            service_status = bundle.getString("service_status");

            order_id = bundle.getInt("order_id");

        }


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Navigation.findNavController(requireView()).popBackStack();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);


        Picasso.get().load("https://www.rj19carwash.com/"+service_image).placeholder(R.mipmap.ic_launcher_foreground).into(confirmOrderBinding.confirmOrderimg);
        confirmOrderBinding.confirmTxtprice.setText(String.format("₹ %s", service_price));
        confirmOrderBinding.confirmTxttime.setText(getDateTime(service_time));
        confirmOrderBinding.confirmTxtservicename.setText(service_name);

        confirmOrderBinding.confirmBtncancel.setOnClickListener(view -> setOrderStatus("-1"));

        confirmOrderBinding.confirmBtncomplete.setOnClickListener(view -> setOrderStatus("1"));

        return confirmOrderBinding.getRoot();

    }

    private void setOrderStatus(String status) {

        customLoading.startLoading(getLayoutInflater());

        RetrofitClient.getInstance().getapi().setOrderStatus("Bearer "+userSession.getKeyToken().get(KEY_TOKEN), order_id, status)
                .enqueue(new Callback<OrderStatusResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<OrderStatusResponse> call, @NonNull Response<OrderStatusResponse> response) {

                        customLoading.dismissLoading();
                        if (response.isSuccessful() && response.body() != null){

                                if (response.body().getResponseCode() == 201){
                                   toast(requireContext(), response.body().getMessage());
                                }else {
                                    toast(requireContext(), response.body().getMessage());
                                }
                        }
                            Navigation.findNavController(requireView()).navigate(R.id.toOrders);
                    }

                    @Override
                    public void onFailure(@NonNull Call<OrderStatusResponse> call, @NonNull Throwable t) {

                        customLoading.dismissLoading();
                        Log.d("statuserror", t.getMessage());
                        toast(requireContext(), "Server error! try again later");
                    }
                });
    }
}