package com.example.rj19carwash.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rj19carwash.repositories.CategoriesRepository;
import com.example.rj19carwash.responses.CategoriesResponse;

import java.util.ArrayList;

public class CategoriesViewModel extends ViewModel {

    CategoriesRepository categoriesRepository;

    public CategoriesViewModel(){
        categoriesRepository = new CategoriesRepository();
    }

    public LiveData<ArrayList<CategoriesResponse>> getCategories(String token){
        return categoriesRepository.getCategories(token);
    }

}
