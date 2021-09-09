package com.example.rj19carwash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.OrdersItemLayoutBinding;
import com.example.rj19carwash.responses.OrdersResponse;

import java.util.ArrayList;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    Context context;
    ArrayList<OrdersResponse.Datum> arrOrdersList;

    public OrdersAdapter(Context context, ArrayList<OrdersResponse.Datum> arrOrdersList) {
        this.context = context;
        this.arrOrdersList = arrOrdersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.orders_item_layout, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindOrders(arrOrdersList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrOrdersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        OrdersItemLayoutBinding ordersItemLayoutBinding;

        public ViewHolder(@NonNull OrdersItemLayoutBinding ordersItemLayoutBinding) {
            super(ordersItemLayoutBinding.getRoot());
            this.ordersItemLayoutBinding = ordersItemLayoutBinding;
        }

        public void bindOrders(OrdersResponse.Datum ordersResponse){
            ordersItemLayoutBinding.setOrders(ordersResponse);
            ordersItemLayoutBinding.executePendingBindings();
        }
    }
}
