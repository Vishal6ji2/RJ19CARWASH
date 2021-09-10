package com.example.rj19carwash.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.CategoriesItemLayoutBinding;
import com.example.rj19carwash.databinding.SubcategoriesItemLayoutBinding;
import com.example.rj19carwash.responses.SubCategoriesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder> {
    Context context;
    ArrayList<SubCategoriesResponse.Subcategory> arrCategoriesList;


    public SubCategoriesAdapter(Context context, ArrayList<SubCategoriesResponse.Subcategory> arrCategoriesList) {
        this.context = context;
        this.arrCategoriesList = arrCategoriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SubcategoriesItemLayoutBinding categoriesItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.subcategories_item_layout,parent,false);
        return new ViewHolder(categoriesItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindCategories(arrCategoriesList.get(position));

        holder.itemView.setOnClickListener(view -> {
            // goto subcategories fragment

            Bundle bundle = new Bundle();
            bundle.putInt("subcat_id", arrCategoriesList.get(position).getId());
            bundle.putString("subcat_name", arrCategoriesList.get(position).getCategoryName());

            Navigation.findNavController(view).navigate(R.id.subcat_to_services, bundle);

        });
    }

    @Override
    public int getItemCount() {
        return arrCategoriesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        SubcategoriesItemLayoutBinding categoriesItemLayoutBinding;

        public ViewHolder(@NonNull SubcategoriesItemLayoutBinding categoriesItemLayoutBinding) {
            super(categoriesItemLayoutBinding.getRoot());
            this.categoriesItemLayoutBinding = categoriesItemLayoutBinding;
        }

        public void bindCategories(SubCategoriesResponse.Subcategory subcategory){
            categoriesItemLayoutBinding.setSubcategories(subcategory);
            Picasso.get().load("https://www.rj19carwash.com/"+subcategory.getCategoryImage()).into(categoriesItemLayoutBinding.cateItemIcon);

            categoriesItemLayoutBinding.executePendingBindings();

        }
    }


}
