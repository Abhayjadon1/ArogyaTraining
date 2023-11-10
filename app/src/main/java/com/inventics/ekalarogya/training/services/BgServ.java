package com.inventics.ekalarogya.training.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class BgServ extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("TAG BgServ", "onStartCommand: "+ System.currentTimeMillis());


        return START_STICKY;
        }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // stopping the process
        Log.d("TAG BgServ", "onStartCommand: ");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
