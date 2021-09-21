package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_CUSTOMER_ID;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.rj19carwash.R;
import com.example.rj19carwash.adapters.TransactionsAdapter;
import com.example.rj19carwash.databinding.FragmentTransactionBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.OrdersResponse;
import com.example.rj19carwash.responses.TransactionResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.utilities.CustomLoading;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TransactionFragment extends Fragment {

    FragmentTransactionBinding transactionBinding;
    ArrayList<TransactionResponse.Datum> arrTransactionList = new ArrayList<>();

    TransactionsAdapter transactionsAdapter;
    UserSession userSession;

    CustomLoading customLoading;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        transactionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction, container, false);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);


        customLoading = new CustomLoading(requireContext());

        initViews();

        transactionBinding.transactionsRefresh.setOnRefreshListener(() -> {
            transactionBinding.transactionsRefresh.setRefreshing(false);
            initViews();
        });

        return transactionBinding.getRoot();
    }

    private void initViews() {

        userSession = new UserSession(requireContext());
        transactionBinding.transactionsRecyclerview.setHasFixedSize(true);

        getTransactions(userSession.getKeyToken().get(KEY_TOKEN), userSession.getCustomerId().get(KEY_CUSTOMER_ID));

    }

    private void getTransactions(String token, Integer customer_id) {

        customLoading.startLoading(getLayoutInflater());
        arrTransactionList.clear();

        RetrofitClient.getInstance().getapi().getTransactions("Bearer "+token, customer_id)
                .enqueue(new Callback<TransactionResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TransactionResponse> call, @NonNull Response<TransactionResponse> response) {

                        customLoading.dismissLoading();
                        if (response.isSuccessful() && response.body() != null){

                                if (response.body().getResponseCode() == 201){
                                    arrTransactionList = response.body().getData();

                                    if (arrTransactionList.size() != 0) {

                                        transactionBinding.transactionsTxtempty.setVisibility(View.GONE);
                                        transactionBinding.transactionsRecyclerview.setVisibility(View.VISIBLE);

                                        transactionsAdapter = new TransactionsAdapter(requireActivity(), arrTransactionList);
                                        transactionBinding.transactionsRecyclerview.setLayoutManager(new LinearLayoutManager(requireActivity()));
                                        transactionBinding.transactionsRecyclerview.setAdapter(transactionsAdapter);
                                    }else {

                                        transactionBinding.transactionsRecyclerview.setVisibility(View.GONE);
                                        transactionBinding.transactionsTxtempty.setVisibility(View.VISIBLE);
                                    }
                                }else {
                                    toast(requireActivity(), response.body().getMessage());

                                    transactionBinding.transactionsRecyclerview.setVisibility(View.GONE);
                                    transactionBinding.transactionsTxtempty.setVisibility(View.VISIBLE);

                                }
                            }else {
                                toast(requireActivity(), "Something went wrong! try again later");

                            transactionBinding.transactionsRecyclerview.setVisibility(View.GONE);
                            transactionBinding.transactionsTxtempty.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TransactionResponse> call, @NonNull Throwable t) {

                        customLoading.dismissLoading();

                        transactionBinding.transactionsRecyclerview.setVisibility(View.GONE);
                        transactionBinding.transactionsTxtempty.setVisibility(View.VISIBLE);

                        arrTransactionList = null;
                        Log.d("failtransactions",t.getMessage());
                        toast(requireActivity(), "Server error! try again later");
                    }
                });
    }
}