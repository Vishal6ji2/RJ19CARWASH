package com.example.rj19carwash.sessions;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeSession {

    public SharedPreferences themesession;
    public SharedPreferences.Editor editor;
    public Context context;


    public static final String IS_NIGHTMODE = "isNightMode";

    public ThemeSession(Context context){
        this.context = context;

        themesession = context.getSharedPreferences("themesession", Context.MODE_PRIVATE);
        editor = themesession.edit();

    }


    public void setTheme(boolean mode){
        editor.putBoolean(IS_NIGHTMODE, mode);

        editor.commit();
    }

    public boolean checkTheme(){
        if (themesession.getBoolean(IS_NIGHTMODE, false)){
            return true;
        }else {
            return false;
        }
    }
}
