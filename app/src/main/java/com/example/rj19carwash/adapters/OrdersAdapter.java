package com.example.rj19carwash.adapters;

import static com.example.rj19carwash.utilities.TimeUtils.getDateTime;
import static com.example.rj19carwash.utilities.TimeUtils.getDayMonth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.OrdersItemLayoutBinding;
import com.example.rj19carwash.responses.OrdersResponse;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        OrdersItemLayoutBinding ordersItemLayoutBinding;

        public ViewHolder(@NonNull OrdersItemLayoutBinding ordersItemLayoutBinding) {
            super(ordersItemLayoutBinding.getRoot());
            this.ordersItemLayoutBinding = ordersItemLayoutBinding;
        }

        public void bindOrders(OrdersResponse.Datum ordersResponse){

            if (ordersResponse.getStatus().equals("0")) {
                ordersItemLayoutBinding.orderItemTime.setText(getDateTime(ordersResponse.getSlot()));
                ordersItemLayoutBinding.setOrders(ordersResponse);
                Picasso.get().load("https://www.rj19carwash.com/"+ordersResponse.getServiceId().getServiceImage()).placeholder(R.mipmap.ic_launcher_foreground).into(ordersItemLayoutBinding.orderItemImg);

            }

            ordersItemLayoutBinding.getRoot().setOnClickListener(view -> {

                Bundle bundle = new Bundle();
                bundle.putString("service_image", ordersResponse.getServiceId().getServiceImage());
                bundle.putString("service_name", ordersResponse.getServiceId().getName());
                bundle.putString("service_price", ordersResponse.getPrice());
                bundle.putString("service_time", ordersResponse.getSlot());
                bundle.putString("service_status", ordersResponse.getStatus());
                bundle.putInt("order_id", ordersResponse.getId());

                Navigation.findNavController(view).navigate(R.id.checkorderstatus, bundle);

            });

            ordersItemLayoutBinding.executePendingBindings();
        }
    }
}
