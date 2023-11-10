package com.inventics.ekalarogya.training.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.utils.Logger;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;


/**
 * Created by Sonu on 14/12/17.
 */

public class FirebaseMessageService extends FirebaseMessagingService {

    private static final String TAG = FirebaseMessageService.class.getSimpleName();
    public static final String RIDE_STATUS = "ride_status";
    public static final String DRIVER_TRACK = "driver_track";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Logger.DebugLog(TAG, " Refresh Token : " + token);
        ArogyaApplication.getPreferenceManager().putString(PreferenceManger.FCM_TOKEN, token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Logger.ErrorLog(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Logger.DebugLog(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Logger.DebugLog(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                //JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(remoteMessage.getData());
            } catch (Exception e) {
                Logger.ErrorLog(TAG, "Exception: " + e.getMessage());
            }
        }

    }


    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            //app is in foreground, broadcast the push message


        } else {
            // If app is in background, firebase itself handles the notification
        }
    }


    private void handleDataMessage(Map<String, String> dataPayload) {
        try {
            Iterator myVeryOwnIterator = dataPayload.keySet().iterator();
            JSONObject jsonObject = new JSONObject();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                String value = dataPayload.get(key);
                Logger.DebugLog(TAG, "Data : " + key + " : " + value + "\n");
                jsonObject.put(key, value);
            }
            String pushType = jsonObject.getString("push_type");
            switch (pushType) {

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}