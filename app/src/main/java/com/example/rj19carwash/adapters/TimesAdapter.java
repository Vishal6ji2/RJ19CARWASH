package com.example.rj19carwash.adapters;

import static com.example.rj19carwash.utilities.TimeUtils.getTime;

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

import java.util.ArrayList;


public class TimesAdapter extends RecyclerView.Adapter<TimesAdapter.ViewHolder> {

    Context context;
    ArrayList<String> arrTimesList ;

    int selectedPosition = -1;

    public interface TimesItemClickListener{
       void onTimeClick(int position);
    }

    TimesItemClickListener timesItemClickListener;

    public TimesAdapter(Context context, ArrayList<String> arrTimesList, TimesItemClickListener timesItemClickListener) {
        this.context = context;
        this.arrTimesList = arrTimesList;
        this.timesItemClickListener = timesItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.time_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (selectedPosition == position) {
            holder.cardView.setSelected(true); //using selector drawable
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.design_default_color_primary));
        } else {
            holder.cardView.setSelected(false);
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.pencilcolor));
        }

        holder.txtTime.setText(getTime(arrTimesList.get(position)));


        holder.cardView.setOnClickListener(view -> {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);

            timesItemClickListener.onTimeClick(selectedPosition);
        });

    }

    @Override
    public int getItemCount() {
        return arrTimesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTime;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.times_item_cardtime);
            txtTime = itemView.findViewById(R.id.times_item_txttime);
        }

     }
}
