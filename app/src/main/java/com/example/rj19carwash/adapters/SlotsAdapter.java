package com.example.rj19carwash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.SlotsItemLayoutBinding;
import com.example.rj19carwash.responses.SlotsResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SlotsAdapter extends RecyclerView.Adapter<SlotsAdapter.ViewHolder> {

    Context context;
    ArrayList<SlotsResponse.Data.Date> arrSlotList;

    public SlotsAdapter(Context context, ArrayList<SlotsResponse.Data.Date> arrSlotList) {
        this.context = context;
        this.arrSlotList = arrSlotList;
    }

    @NonNull
    @Override
    public SlotsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SlotsItemLayoutBinding slotsItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.slots_item_layout, parent, false);

        return new ViewHolder(slotsItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotsAdapter.ViewHolder holder, int position) {

        holder.bindSlots(arrSlotList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrSlotList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        SlotsItemLayoutBinding slotsItemLayoutBinding;

        public ViewHolder(@NonNull SlotsItemLayoutBinding slotsItemLayoutBinding) {
            super(slotsItemLayoutBinding.getRoot());
            this.slotsItemLayoutBinding = slotsItemLayoutBinding;
        }

        public void bindSlots(SlotsResponse.Data.Date slotList){
            slotsItemLayoutBinding.setSlots(slotList);

            slotsItemLayoutBinding.executePendingBindings();
        }
    }
}
