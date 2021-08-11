package com.example.rj19carwash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.CategoriesItemLayoutBinding;
import com.example.rj19carwash.responses.CategoriesResponse;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    Context context;
    ArrayList<CategoriesResponse> arrCategoriesList;

    public CategoriesAdapter(Context context, ArrayList<CategoriesResponse> arrCategoriesList) {
        this.context = context;
        this.arrCategoriesList = arrCategoriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoriesItemLayoutBinding categoriesItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.categories_item_layout,parent,false);
        return new ViewHolder(categoriesItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindCategories(arrCategoriesList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrCategoriesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CategoriesItemLayoutBinding categoriesItemLayoutBinding;

        public ViewHolder(@NonNull CategoriesItemLayoutBinding categoriesItemLayoutBinding) {
            super(categoriesItemLayoutBinding.getRoot());
            this.categoriesItemLayoutBinding = categoriesItemLayoutBinding;
        }

        public void bindCategories(CategoriesResponse categoriesResponse){
            categoriesItemLayoutBinding.setCategories(categoriesResponse);
            categoriesItemLayoutBinding.executePendingBindings();
        }
    }


}
