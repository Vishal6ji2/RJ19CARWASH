package com.example.rj19carwash.adapters;

import static com.example.rj19carwash.utilities.TimeUtils.getDayMonth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rj19carwash.R;
import com.example.rj19carwash.responses.SlotsResponse;

import java.util.ArrayList;


public class SlotsAdapter extends RecyclerView.Adapter<SlotsAdapter.ViewHolder> {

    Context context;
    ArrayList<SlotsResponse.Data.Slotlist.Date> arrSlotList;

    public interface SlotsItemClickListener{
       void onSlotClick(int position);
    }

    int selectedPosition = -1;

    SlotsItemClickListener slotsItemClickListener;

    public SlotsAdapter(Context context, ArrayList<SlotsResponse.Data.Slotlist.Date> arrSlotList, SlotsItemClickListener listener) {
        this.context = context;
        this.arrSlotList = arrSlotList;
        this.slotsItemClickListener = listener;
    }

    @NonNull
    @Override
    public SlotsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.slots_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlotsAdapter.ViewHolder holder, int position) {

        if (selectedPosition == position) {
            holder.cardView.setSelected(true); //using selector drawable
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.design_default_color_primary));
        } else {
            holder.cardView.setSelected(false);
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.pencilcolor));

        }

        holder.txtDate.setText(getDayMonth(arrSlotList.get(position).getName()));

        holder.cardView.setOnClickListener(view -> {

            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);
            slotsItemClickListener.onSlotClick(selectedPosition);

        });

    }

    @Override
    public int getItemCount() {
        return arrSlotList.size();
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

        TextView txtDate;

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.slots_item_cardtime);
            txtDate = itemView.findViewById(R.id.slots_item_txtdate);

        }
    }
}
