package com.example.rj19carwash.sessions;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class UserSession {

    public SharedPreferences userSession;
    public SharedPreferences.Editor editor;
    public Context context;


    public static final String IS_LOGIN = "isLoggedIn";

    public static final String KEY_CUSTOMER_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_CUSTOMER_STATUS = "status";
    public static final String KEY_TOKEN = "token";



    public UserSession(Context context) {
        this.context = context;

        userSession = context.getSharedPreferences("usersession",Context.MODE_PRIVATE);
        editor = userSession.edit();

    }
    /*{
        "response_code": 201,
            "message": "Login successfully",
            "errors": {},
        "data": {
        "id": 2,
                "name": null,
                "email": null,
                "password": "$2y$10$jDgs3jNw1dA5xRO5svymIOcvUhkHtnxp8BUTuxPjUHXDdJZZUu7Xa",
                "phone": "7239973921",
                "gender": null,
                "address": null,
                "profile": "/storage/app/public/",
                "customer_status": "0",
                "created_at": "2021-08-16T20:14:23.000000Z",
                "updated_at": "2021-08-21T09:13:25.000000Z",
                "token": "57|DHf1WUAOBGwQNV4NxCIkVkIuUYe5t4TyayZIMZbc"
    }
    }*/

    public void setUserSession(String name, String email, String phone, String gender
            , String address, String profile, String customerStatus){

        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_PROFILE, profile);
        editor.putString(KEY_CUSTOMER_STATUS, customerStatus);

        editor.commit();
    }

    public void setKeyToken(String phone, String token){

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_PHONE, phone);

        editor.commit();

    }

    public void setKeyCustomerId(int customerId){
        editor.putInt(KEY_CUSTOMER_ID, customerId);

        editor.commit();
    }

    public HashMap<String, String> getKeyToken(){
        HashMap<String, String> token = new HashMap<>() ;

        token.put(KEY_TOKEN, userSession.getString(KEY_TOKEN, null));

        return token;
    }

    public HashMap<String, Integer> getCustomerId(){
        HashMap<String, Integer> customerId = new HashMap<>();
        customerId.put(KEY_CUSTOMER_ID, userSession.getInt(KEY_CUSTOMER_ID,0));

        return customerId;
    }

    @NonNull
    public HashMap<String,String> getCustomerData(){

        HashMap<String,String> customerData = new HashMap<>();

        customerData.put(KEY_NAME,userSession.getString(KEY_NAME,""));
        customerData.put(KEY_EMAIL,userSession.getString(KEY_EMAIL,""));
        customerData.put(KEY_PHONE,userSession.getString(KEY_PHONE,""));
        customerData.put(KEY_GENDER,userSession.getString(KEY_GENDER,""));
        customerData.put(KEY_ADDRESS,userSession.getString(KEY_ADDRESS,""));
        customerData.put(KEY_PROFILE,userSession.getString(KEY_PROFILE,""));
        customerData.put(KEY_CUSTOMER_STATUS,userSession.getString(KEY_CUSTOMER_STATUS,""));


        return customerData;
    }

    public boolean checkLogin(){
        if (userSession.getBoolean(IS_LOGIN, false)){
            return true ;
        }else {
            return false;
        }
    }

    public void logoutCustomerSession(){
        editor.clear();
        editor.commit();
    }

}
