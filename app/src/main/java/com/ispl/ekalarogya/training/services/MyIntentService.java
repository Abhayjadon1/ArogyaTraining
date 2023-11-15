package com.ispl.ekalarogya.training.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.ResultReceiver;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.ispl.ekalarogya.training.BuildConfig;;
import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.OnTimerChangeListener;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.helper.MyTimerTask;
import com.ispl.ekalarogya.training.helper.PreferenceManger;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.service.DriverService;
import com.ispl.ekalarogya.training.utils.DeviceUtils;
import com.ispl.ekalarogya.training.utils.ExtraUtils;
import com.ispl.ekalarogya.training.utils.Logger;
import com.ispl.ekalarogya.training.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by Sonu on 17/08/17.
 */

public class MyIntentService extends IntentService {

    private static final String TAG = MyIntentService.class.getSimpleName();

    public static final String COUNTDOWN_TIMER = "countdown_timer";
    public static final String TIMER_VALUE = "timer_value";
    public static final String STOP_TIMER_TASK = "stop_timer_task";
    public static final String TRACK_DRIVER = "track_driver";
    public static final String RIDE_SOUND = "ride_sound";
    public static final String ACTIVITY_RECOGNITION = "activity_recognition";

    private Timer timer;

    protected ResultReceiver mReceiver;

    public MyIntentService(String name) {
        super(name);
    }


    public MyIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.hasExtra(ExtraUtils.INTENT_SERVICE_KEY)) {
            String key = intent.getStringExtra(ExtraUtils.INTENT_SERVICE_KEY);
            switch (key) {
                case COUNTDOWN_TIMER:
                    int timeCounter = intent.getIntExtra(TIMER_VALUE, 0);
                    startTimer(timeCounter);
                    break;

                case STOP_TIMER_TASK:
                    cancelTimer();
                    break;

                case RIDE_SOUND:
                    int isPlay = intent.getIntExtra(ExtraUtils.PLAY_SOUND, 0);
                    playAcceptRideSound(isPlay);
                    break;

                case TRACK_DRIVER:
                    double latitude = intent.getDoubleExtra(ExtraUtils.LATITUDE, 0);
                    double longitude = intent.getDoubleExtra(ExtraUtils.LONGITUDE, 0);
                    float speed = intent.getFloatExtra(ExtraUtils.SPEED, 0);
                    float accuracy = intent.getFloatExtra(ExtraUtils.ACCURACY, 0);
                    double batteryLevel = DeviceUtils.getBatteryLevel(this);
                    String motion = ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.MOTION);
                    if (!TextUtils.isEmpty(motion))
                        motion = "Y";
                    int signalStrength = ArogyaApplication.getPreferenceManager().getIntegerValue(PreferenceManger.SIGNAL_STRENGTH);
                    DriverService.sendLocationUpdate(this, latitude, longitude, speed, accuracy, batteryLevel, motion, signalStrength, new RetroAPICallback() {
                        @Override
                        public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
                            if (response.isSuccessful()) {
                                BaseResponse baseResponse = (BaseResponse) response.body();
                                if (baseResponse != null) {
                                    Logger.DebugLog(TAG, baseResponse.getStatusMessage());
                                    if (BuildConfig.DEBUG) {
                                        ToastUtils.shortToast("Location Updated.");
                                    }
                                } else {
                                    Logger.ErrorLog(TAG, "Failed to send location update");
                                }

                            } else {
                                Logger.ErrorLog(TAG, "Failed to send location update");

                            }
                        }

                        @Override
                        public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
                            Logger.ErrorLog(TAG, "Failed to send location update : " + t.getLocalizedMessage());
                        }

                        @Override
                        public void onNoNetwork(int requestCode) {

                        }
                    }, 0);
                    break;

                case ACTIVITY_RECOGNITION:
                    ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

                    // Get the list of the probable activities associated with the current state of the
                    // device. Each activity is associated with a confidence level, which is an int between
                    // 0 and 100.
                    ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();

                    for (DetectedActivity activity : detectedActivities) {
                        Logger.DebugLog(TAG, "Detected activity: " + activity.getType() + ", " + activity.getConfidence());
                        switch (activity.getType()) {
                            case DetectedActivity.STILL:
                                ArogyaApplication.getPreferenceManager().putString(PreferenceManger.MOTION, "N");
                                break;
                            default:
                                ArogyaApplication.getPreferenceManager().putString(PreferenceManger.MOTION, "Y");
                                break;
                        }
                    }
                    break;

            }
        }
    }

    private MediaPlayer mPlayer;
    private SoundPool soundPool;

    private void playAcceptRideSound(int isPlay) {
        try {

            if (isPlay == 1) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    AudioAttributes attributes = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build();
                    soundPool = new SoundPool.Builder()
                            .setAudioAttributes(attributes)
                            .build();
                } else {
                    soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
                }

                /** soundId for Later handling of sound pool **/
                int soundId = soundPool.load(this, R.raw.ride_notification_sound, 1); // in 2nd param u have to pass your desire ringtone

                soundPool.play(soundId, 1, 1, 0, 0, 1);

                mPlayer = MediaPlayer.create(this, R.raw.ride_notification_sound); // in 2nd param u have to pass your desire ringtone
                //mPlayer.prepare();
                mPlayer.setLooping(true);
                mPlayer.start();
            } else {
                if (mPlayer != null && mPlayer.isPlaying()) {
                    mPlayer.stop();
                    mPlayer.release();
                }
                if (soundPool != null) {
                    soundPool.release();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * start timer
     *
     * @param timeCounter number of seconds
     */
    public synchronized void startTimer(int timeCounter) {
        if (timeCounter < 1)
            return;
        if (timer == null)
            timer = new Timer();
        MyTimerTask skipTimerTask = new MyTimerTask(timeCounter, new OnTimerChangeListener() {
            @Override
            public void updateTimerProgress(int timeCounter) {
                sendTimerProgress(timeCounter);
            }

            @Override
            public void stopTimer() {
                cancelTimer();
            }
        });
        timer.scheduleAtFixedRate(skipTimerTask, 0, 1000);
    }

    private void cancelTimer() {
        if (timer != null)
            timer.cancel();
    }

    private void sendTimerProgress(int timeCounter) {
        Intent messageSent = new Intent(COUNTDOWN_TIMER);
        messageSent.putExtra(TIMER_VALUE, timeCounter);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(messageSent);
    }


    public static void sendLocationService(Context context, Location location) {

        Intent intent = new Intent(context, MyIntentService.class);
        intent.putExtra(ExtraUtils.LATITUDE, location.getLatitude());
        intent.putExtra(ExtraUtils.LONGITUDE, location.getLongitude());
        intent.putExtra(ExtraUtils.SPEED, location.getSpeed());
        intent.putExtra(ExtraUtils.ACCURACY, location.getAccuracy());
        intent.putExtra(ExtraUtils.INTENT_SERVICE_KEY, MyIntentService.TRACK_DRIVER);
        context.startService(intent);
          /*  if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O){
                context.startForegroundService(intent);
            }else{
                context.startService(intent);
            }*/
    }


}
