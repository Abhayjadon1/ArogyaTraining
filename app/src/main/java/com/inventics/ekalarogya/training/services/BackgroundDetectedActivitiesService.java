package com.inventics.ekalarogya.training.services;


import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.tasks.Task;
import com.inventics.ekalarogya.training.BuildConfig;;
import com.inventics.ekalarogya.training.utils.ExtraUtils;


/**
 * Created by sonu on 14:21, 08/10/18
 * Copyright (c) 2018 . All rights reserved.
 */
public class BackgroundDetectedActivitiesService extends Service {

    private static final long DETECTION_INTERVAL_IN_MILLISECONDS = 30 * 1000;

    private static final int CONFIDENCE = 70;

    private ActivityRecognitionClient mActivityRecognitionClient;
    private Intent mIntentService;
    private PendingIntent mPendingIntent;
    IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public BackgroundDetectedActivitiesService getServerInstance() {
            return BackgroundDetectedActivitiesService.this;
        }
    }

    public BackgroundDetectedActivitiesService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mActivityRecognitionClient = new ActivityRecognitionClient(this);
        mIntentService = new Intent(this, MyIntentService.class);
        mIntentService.putExtra(ExtraUtils.INTENT_SERVICE_KEY, MyIntentService.ACTIVITY_RECOGNITION);
        mPendingIntent = PendingIntent.getService(this, 1, mIntentService, PendingIntent.FLAG_UPDATE_CURRENT);

        requestActivityUpdatesButtonHandler();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    public void requestActivityUpdatesButtonHandler() {
        Task<Void> task = mActivityRecognitionClient.requestActivityUpdates(
                DETECTION_INTERVAL_IN_MILLISECONDS,
                mPendingIntent);

        task.addOnSuccessListener(result -> {
            if (BuildConfig.DEBUG) {
                Toast.makeText(getApplicationContext(),
                        "Successfully requested activity updates",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        task.addOnFailureListener(e -> {
            if (BuildConfig.DEBUG) {
                Toast.makeText(getApplicationContext(),
                        "Requesting activity updates failed to start",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void removeActivityUpdatesButtonHandler() {
        Task<Void> task = mActivityRecognitionClient.removeActivityUpdates(
                mPendingIntent);
        task.addOnSuccessListener(result -> {
            if (BuildConfig.DEBUG) {
                Toast.makeText(getApplicationContext(),
                        "Removed activity updates successfully!",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        task.addOnFailureListener(e -> {
            if (BuildConfig.DEBUG) {
                Toast.makeText(getApplicationContext(), "Failed to remove activity updates!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeActivityUpdatesButtonHandler();
    }
}
