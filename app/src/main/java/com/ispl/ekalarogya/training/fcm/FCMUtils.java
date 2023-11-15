package com.ispl.ekalarogya.training.fcm;

import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.helper.PreferenceManger;

/**
 * Created by sonu on 19/12/17.
 */

public class FCMUtils {

    // flag to identify whether to show single line
    // or multi line test push notification tray
    public static boolean appendNotificationMessages = false;

    // type of Push
    public static final String NEW_MESSAGE_PUSH = "new_message_push";
    public static final String SEND_MESSAGE_PUSH = "send_message_push";
    public static final String MESSAGE_STATUS_PUSH = "message_status_push";


    // id to handle the notification in the notification try
    public static final int MESSAGE_NOTIFICATION_ID = 100;
    public static final int MESSAGE_NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final int ATTENDANCE_NOTIFICATION_ID = 102;

    public static final String NOTIFICATION_EXTRA = "notification_extra";


    public static String getFCMToken() {
        String fcm = ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.FCM_TOKEN);
        if (TextUtils.isEmpty(fcm)) {
            fcm = FirebaseInstanceId.getInstance().getInstanceId().getResult().getToken();
        }
        return fcm;

    }


}
