package com.example.rj19carwash.adapters;

import static com.example.rj19carwash.utilities.TimeUtils.getDateTime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.TransactionItemLayoutBinding;
import com.example.rj19carwash.responses.TransactionResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {

    Context context;
    ArrayList<TransactionResponse.Datum> arrTransactionList;

    public TransactionsAdapter(Context context, ArrayList<TransactionResponse.Datum> arrTransactionList) {
        this.context = context;
        this.arrTransactionList = arrTransactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionsAdapter.ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.transaction_item_layout, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTransactions(arrTransactionList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrTransactionList.size();
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

        TransactionItemLayoutBinding transactionItemLayoutBinding;

        public ViewHolder(@NonNull TransactionItemLayoutBinding itemView) {
            super(itemView.getRoot());
            this.transactionItemLayoutBinding = itemView;
        }

        public void bindTransactions(TransactionResponse.Datum transactionResponse){

            if (transactionResponse.getStatus().equals("1")){
                transactionItemLayoutBinding.transactionItemStatus.setText("Completed");
                transactionItemLayoutBinding.transactionItemStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.quantum_googgreen));
                Picasso.get().load("https://www.rj19carwash.com/"+transactionResponse.getServiceId().getServiceImage()).placeholder(R.mipmap.ic_launcher_foreground).into(transactionItemLayoutBinding.transactionItemImg);
                if (transactionResponse.getSlot()!=null) {
                    transactionItemLayoutBinding.transactionItemTime.setText(getDateTime(transactionResponse.getSlot()));
                }
                transactionItemLayoutBinding.setTransaction(transactionResponse);
            }else if (transactionResponse.getStatus().equals("-1")){
                transactionItemLayoutBinding.transactionItemStatus.setText("Cancelled");
                if (transactionResponse.getSlot()!=null) {
                    transactionItemLayoutBinding.transactionItemTime.setText(getDateTime(transactionResponse.getSlot()));
                }
                Picasso.get().load("https://www.rj19carwash.com/"+transactionResponse.getServiceId().getServiceImage()).placeholder(R.mipmap.ic_launcher_foreground).into(transactionItemLayoutBinding.transactionItemImg);
                transactionItemLayoutBinding.transactionItemStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.quantum_googred));
                transactionItemLayoutBinding.setTransaction(transactionResponse);
            }
            transactionItemLayoutBinding.executePendingBindings();
        }
    }

}
