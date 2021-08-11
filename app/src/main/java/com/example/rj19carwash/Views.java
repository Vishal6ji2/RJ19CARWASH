package com.example.rj19carwash;

import android.content.Context;
import android.widget.Toast;

public class Views {

    public static void toast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
