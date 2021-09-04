package com.example.rj19carwash.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.ServicesItemLayoutBinding;
import com.example.rj19carwash.responses.ServicesResponse;

import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
    Context context;
    ArrayList<ServicesResponse.Service> arrServicesList;

    public ServicesAdapter(Context context, ArrayList<ServicesResponse.Service> arrServicesList) {
        this.context = context;
        this.arrServicesList = arrServicesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ServicesItemLayoutBinding servicesItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.services_item_layout,parent,false);

        return new ViewHolder(servicesItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindServices(arrServicesList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrServicesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ServicesItemLayoutBinding servicesItemLayoutBinding;
        public ViewHolder(@NonNull ServicesItemLayoutBinding servicesItemLayoutBinding) {
            super(servicesItemLayoutBinding.getRoot());
            this.servicesItemLayoutBinding = servicesItemLayoutBinding;

        }

        public void bindServices(ServicesResponse.Service service){
            servicesItemLayoutBinding.setServices(service);
            servicesItemLayoutBinding.executePendingBindings();

            servicesItemLayoutBinding.getRoot().setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt("id", service.getId());
                bundle.putString("image", service.getServiceImage());
                bundle.putString("name", service.getName());
                bundle.putString("description", service.getLongDescription());
                bundle.putString("employees", service.getEmplyee());

                Navigation.findNavController(view).navigate(R.id.tobookservice, bundle);
            });
        }
    }
}
