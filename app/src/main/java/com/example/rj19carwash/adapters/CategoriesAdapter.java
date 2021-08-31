package com.example.rj19carwash.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rj19carwash.R;
import com.example.rj19carwash.fragments.HomeFragmentDirections;
import com.example.rj19carwash.fragments.SubCategoriesFragment;
import com.example.rj19carwash.responses.CategoriesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    Context context;
    ArrayList<CategoriesResponse.Category> arrCategoriesList;
    FragmentManager fragmentManager;

    public CategoriesAdapter(Context context, FragmentManager fragmentManager, ArrayList<CategoriesResponse.Category> arrCategoriesList) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.arrCategoriesList = arrCategoriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.categories_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.catName.setText(arrCategoriesList.get(position).getCategoryName());
        Picasso.get().load("https://www.rj19carwash.com/"+arrCategoriesList.get(position).getCategoryImage()).into(holder.catImg);


        holder.itemView.setOnClickListener(view -> {
            // goto subcategories fragment

            Bundle bundle = new Bundle();
            bundle.putInt("cat_id", arrCategoriesList.get(position).getId());

            Navigation.findNavController(view).navigate(R.id.cat_to_subcat, bundle);

        });
    }

    @Override
    public int getItemCount() {
        return arrCategoriesList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView catImg;
        TextView catName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            catImg = itemView.findViewById(R.id.cate_item_icon);
            catName = itemView.findViewById(R.id.cate_item_name);
        }
    }
}
