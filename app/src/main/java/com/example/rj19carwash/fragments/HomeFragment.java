package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_ADDRESS;
import static com.example.rj19carwash.sessions.UserSession.KEY_NAME;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.rj19carwash.R;
import com.example.rj19carwash.adapters.CategoriesAdapter;
import com.example.rj19carwash.databinding.FragmentHomeBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.CategoriesResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.utilities.CustomLoading;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    FragmentHomeBinding fragmentHomeBinding;

    CustomLoading customLoading;

    ArrayList<CategoriesResponse.Category> arrCategoriesList = new ArrayList<>();
    CategoriesAdapter categoriesAdapter;

    UserSession userSession;

    public static final String BroadCastStringForAction = "checkinternet";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        userSession = new UserSession(requireContext());
        customLoading = new CustomLoading(requireContext());
/*
        if (userSession.getCustomerData().get(KEY_NAME).isEmpty() && userSession.getCustomerData().get(KEY_ADDRESS).isEmpty()){
            toast(requireContext(), "Please complete your profile first");

            NavOptions navOptions = new NavOptions.Builder().build();
            Navigation.findNavController()
        }*/

        initViews();

        fragmentHomeBinding.categoriesRefresh.setOnRefreshListener(() -> {
          fragmentHomeBinding.categoriesRefresh.setRefreshing(false);
            initViews();
        });

        return fragmentHomeBinding.getRoot();
    }

    public void initViews() {

        fragmentHomeBinding.categoriesRecyclerview.setHasFixedSize(true);
//        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);

        getCategories(userSession.getKeyToken().get(KEY_TOKEN));

    }

    public void getCategories(String token) {

        customLoading.startLoading(getLayoutInflater());

        arrCategoriesList.clear();

        RetrofitClient.getInstance().getapi().getCategories("Bearer "+token).enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesResponse> call, @NonNull Response<CategoriesResponse> response) {

                customLoading.dismissLoading();
                if (response.isSuccessful() && response.body() != null){

                    arrCategoriesList = response.body().getCategories();

                    if (arrCategoriesList.size() != 0) {
                        fragmentHomeBinding.categoriesTxtempty.setVisibility(View.GONE);
                        fragmentHomeBinding.categoriesRecyclerview.setVisibility(View.VISIBLE);

                        categoriesAdapter = new CategoriesAdapter( requireActivity(), arrCategoriesList);
                        fragmentHomeBinding.categoriesRecyclerview.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
                        fragmentHomeBinding.categoriesRecyclerview.setAdapter(categoriesAdapter);
                    }else {
                        fragmentHomeBinding.categoriesRecyclerview.setVisibility(View.GONE);
                        fragmentHomeBinding.categoriesTxtempty.setVisibility(View.VISIBLE);
                    }
                }else {
                    toast(requireActivity(), "Something went wrong! try again");

                    fragmentHomeBinding.categoriesRecyclerview.setVisibility(View.GONE);
                    fragmentHomeBinding.categoriesTxtempty.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoriesResponse> call, @NonNull Throwable t) {

                customLoading.dismissLoading();

                fragmentHomeBinding.categoriesRecyclerview.setVisibility(View.GONE);
                fragmentHomeBinding.categoriesTxtempty.setVisibility(View.VISIBLE);

                arrCategoriesList = null;
                Log.d("failCategories",t.getMessage());
                toast(requireActivity(), "Server error try again later");
            }
        });
    }
}