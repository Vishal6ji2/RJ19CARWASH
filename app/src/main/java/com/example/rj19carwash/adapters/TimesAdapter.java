package com.example.rj19carwash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.TimeItemLayoutBinding;

import java.util.ArrayList;


public class TimesAdapter extends RecyclerView.Adapter<TimesAdapter.ViewHolder> {

    Context context;
    ArrayList<String> arrTimesList ;

    public TimesAdapter(Context context, ArrayList<String> arrTimesList) {
        this.context = context;
        this.arrTimesList = arrTimesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TimeItemLayoutBinding timeItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.time_item_layout,parent,false);

        return new ViewHolder(timeItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindTimes(arrTimesList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrTimesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TimeItemLayoutBinding timeItemLayoutBinding;

        public ViewHolder(@NonNull TimeItemLayoutBinding timeItemLayoutBinding) {
            super(timeItemLayoutBinding.getRoot());
            this.timeItemLayoutBinding = timeItemLayoutBinding;
        }

        public void bindTimes(String times){
            timeItemLayoutBinding.setTime(times);

            timeItemLayoutBinding.executePendingBindings();
        }
    }
}
