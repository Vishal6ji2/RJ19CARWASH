package com.example.rj19carwash.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.rj19carwash.repositories.CategoriesRepository;
import com.example.rj19carwash.responses.CategoriesResponse;

public class CategoriesViewModel extends ViewModel {

    CategoriesRepository categoriesRepository;

    public CategoriesViewModel(){
        categoriesRepository = new CategoriesRepository();
    }

    public LiveData<CategoriesResponse> getCategories(String token){
        return categoriesRepository.getCategories(token);
    }

}
