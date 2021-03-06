package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

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
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.rj19carwash.R;
import com.example.rj19carwash.adapters.SubCategoriesAdapter;
import com.example.rj19carwash.databinding.FragmentSubCategoriesBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.SubCategoriesResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.utilities.CustomLoading;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoriesFragment extends Fragment {

    UserSession userSession;

    FragmentSubCategoriesBinding subCategoriesBinding;

    CustomLoading customLoading;

    ArrayList<SubCategoriesResponse.Subcategory> arrSubCategoriesList = new ArrayList<>();
    SubCategoriesAdapter subCategoriesAdapter;

    int cat_id;

    String token;

    Bundle bundle;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        subCategoriesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sub_categories, container, false);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        customLoading = new CustomLoading(requireContext());


        OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).popBackStack();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), backPressedCallback);

        bundle = getArguments();
        if (bundle != null) {
            if (bundle.getInt("cat_id", 1) !=0){

                cat_id = bundle.getInt("cat_id", 1);
                initViews();
            }else {
//                it is showing this toast
                toast(requireActivity(), "Something went wrong");
            }
        }

        subCategoriesBinding.subcategoriesRefresh.setOnRefreshListener(() -> {
            subCategoriesBinding.subcategoriesRefresh.setRefreshing(false);
            initViews();
        });
/*

        OnBackPressedCallback callback = new OnBackPressedCallback(true */
/* enabled by default *//*
) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Navigation.findNavController(requireView()).navigate(R.id.backtocat);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);
*/


        return subCategoriesBinding.getRoot();

    }

    private void initViews() {
        userSession = new UserSession(requireActivity());

        subCategoriesBinding.subcategoriesRecyclerview.setHasFixedSize(true);

        token = userSession.getKeyToken().get(KEY_TOKEN);

        if (token != null && cat_id != 0) {
            getSubCategories(token, cat_id);
        }else {
            toast(requireActivity(), token+" "+cat_id);
        }
    }

    public void getSubCategories(String token, int cat_id) {
        customLoading.startLoading(getLayoutInflater());

        arrSubCategoriesList.clear();

        RetrofitClient.getInstance().getapi().getSubCategories("Bearer "+token, cat_id).enqueue(new Callback<SubCategoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubCategoriesResponse> call, @NonNull Response<SubCategoriesResponse> response) {

                customLoading.dismissLoading();
                if (response.isSuccessful() && response.body() != null){
                    arrSubCategoriesList = response.body().getSubcategories();
                    if (arrSubCategoriesList.size() != 0) {
                        subCategoriesBinding.subcategoriesRecyclerview.setVisibility(View.VISIBLE);
                        subCategoriesBinding.subcategoriesTxtempty.setVisibility(View.GONE);

                        subCategoriesAdapter = new SubCategoriesAdapter(requireActivity(), arrSubCategoriesList);
                        subCategoriesBinding.subcategoriesRecyclerview.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
                        subCategoriesBinding.subcategoriesRecyclerview.setAdapter(subCategoriesAdapter);
                    }else {
                        subCategoriesBinding.subcategoriesRecyclerview.setVisibility(View.GONE);
                        subCategoriesBinding.subcategoriesTxtempty.setVisibility(View.VISIBLE);
                    }
                }else {
                    arrSubCategoriesList = null;
                    toast(requireActivity(), "Server error! try again later");

                    subCategoriesBinding.subcategoriesRecyclerview.setVisibility(View.GONE);
                    subCategoriesBinding.subcategoriesTxtempty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SubCategoriesResponse> call, @NonNull Throwable t) {

                customLoading.dismissLoading();

                subCategoriesBinding.subcategoriesRecyclerview.setVisibility(View.GONE);
                subCategoriesBinding.subcategoriesTxtempty.setVisibility(View.VISIBLE);

                arrSubCategoriesList = null;
                Log.d("suberror", t.getMessage());
                toast(requireActivity(), "Something went wrong! try again later");
            }
        });
    }
}