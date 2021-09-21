package com.example.rj19carwash.networks;

import static com.example.rj19carwash.fragments.HomeFragment.BroadCastStringForAction;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyInternet extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("inside", "oncreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implement");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        handler.post(periodicUpdate);
        return START_STICKY;
    }

    public boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting())
        {
            return networkInfo != null && networkInfo.isConnected();
        }else
        {
            return false;
        }
    }

    Handler handler = new Handler();
    public Runnable periodicUpdate = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 1*1000 - SystemClock.elapsedRealtime()%1000);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(BroadCastStringForAction);
            broadcastIntent.putExtra("online_status", ""+isConnected(MyInternet.this));
            sendBroadcast(broadcastIntent);
        }
    };
}
