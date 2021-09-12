package com.example.rj19carwash.utilities;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.example.rj19carwash.R;

public class CustomLoading {

    Context context;

    AlertDialog alertDialog;

    public CustomLoading(Context context){
        this.context = context;
    }

    public void startLoading(LayoutInflater inflater){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setView(inflater.inflate(R.layout.custom_loading, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissLoading(){
        alertDialog.dismiss();
    }
}
