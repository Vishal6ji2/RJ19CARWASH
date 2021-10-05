package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_CUSTOMER_ID;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.content.Intent;
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
import com.example.rj19carwash.activities.ConfirmBookActivity;
import com.example.rj19carwash.databinding.FragmentBookServiceBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.OrderNowResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.utilities.CustomLoading;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookServiceFragment extends Fragment {

    FragmentBookServiceBinding bookServiceBinding;

    UserSession userSession;

    Bundle bundle;

    int service_id;
    int order_id;

    CustomLoading customLoading;

    String price;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bookServiceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_service, container, false);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        userSession = new UserSession(requireActivity());

        customLoading = new CustomLoading(requireContext());


        bundle = getArguments();
        if (bundle != null){
            service_id = bundle.getInt("id");

            Picasso.get().load("https://www.rj19carwash.com/"+bundle.getString("image")).placeholder(R.mipmap.ic_launcher_foreground).into(bookServiceBinding.bookserviceSerimg);

            bookServiceBinding.bookserviceTxtname.setText(bundle.getString("name"));
            bookServiceBinding.bookserviceTxtdesc.setText(bundle.getString("description"));

            price = bundle.getString("inrrupees");

        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Navigation.findNavController(requireView()).popBackStack();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);

        bookServiceBinding.bookserviceBtnbook.setOnClickListener(view -> makeOrderToServer());

        return bookServiceBinding.getRoot();
    }

    private void makeOrderToServer() {

        customLoading.startLoading(getLayoutInflater());

        RetrofitClient.getInstance().getapi().bookOrderNow("Bearer " + userSession.getKeyToken().get(KEY_TOKEN), service_id, price, Objects.requireNonNull(userSession.getCustomerId().get(KEY_CUSTOMER_ID)))
                .enqueue(new Callback<OrderNowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<OrderNowResponse> call, @NonNull Response<OrderNowResponse> response) {
                        customLoading.dismissLoading();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getResponseCode() == 201) {
                                    toast(requireContext(), "Redirecting to payment ");
                                    order_id = response.body().getData().getId();
                                    Log.d("customeridsecond", String.valueOf(response.body().getData().getCustomerId().getId()));

                                    Intent bundle = new Intent(requireActivity(), ConfirmBookActivity.class);
                                    bundle.putExtra("order_id", order_id);

                                    bundle.putExtra("price", price);
                                    startActivity(bundle);
                                } else if (response.body().getResponseCode() == 422) {
                                    toast(requireContext(), response.body().getMessage());
                                } else {
                                    toast(requireContext(), response.body().getMessage());
                                }
                            } else {
                                toast(requireContext(), "Something went wrong! try again later");
                            }
                        } else {
                            toast(requireContext(), "Something went wrong! try again later");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<OrderNowResponse> call, @NonNull Throwable t) {

                        customLoading.dismissLoading();
                        toast(requireContext(), "Server error! try again later");
                        Log.d("bookerror", t.getMessage());
                    }
                });
    }
}