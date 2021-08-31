package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.rj19carwash.R;
import com.example.rj19carwash.adapters.CategoriesAdapter;
import com.example.rj19carwash.databinding.FragmentHomeBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.CategoriesResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.viewmodels.CategoriesViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    CategoriesViewModel categoriesViewModel;
    FragmentHomeBinding fragmentHomeBinding;

    ArrayList<CategoriesResponse.Category> arrCategoriesList = new ArrayList<>();
    CategoriesAdapter categoriesAdapter;

    UserSession userSession;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        initViews();

        return fragmentHomeBinding.getRoot();
    }

    public void initViews() {
        userSession = new UserSession(requireActivity());

        fragmentHomeBinding.categoriesRecyclerview.setHasFixedSize(true);
//        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);

        getCategories(userSession.getKeyToken().get(KEY_TOKEN));

    }

    public void getCategories(String token) {

        RetrofitClient.getInstance().getapi().getCategories("Bearer "+token).enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesResponse> call, @NonNull Response<CategoriesResponse> response) {

                if (response.isSuccessful() && response.body() != null){

                    arrCategoriesList = response.body().getCategories();

                    categoriesAdapter = new CategoriesAdapter(requireActivity(), requireActivity().getSupportFragmentManager(), arrCategoriesList);
                    fragmentHomeBinding.categoriesRecyclerview.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
                    fragmentHomeBinding.categoriesRecyclerview.setAdapter(categoriesAdapter);

                }else {
                    // this is showing
                    toast(requireActivity(), response.message());

                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoriesResponse> call, @NonNull Throwable t) {

                arrCategoriesList = null;
                Log.d("failCategories",t.getMessage());
                toast(requireActivity(), "Server error try again later");
            }
        });
    }
}