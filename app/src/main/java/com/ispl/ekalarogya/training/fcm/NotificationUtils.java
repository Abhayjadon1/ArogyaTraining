package com.ispl.ekalarogya.training.fcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.core.app.NotificationCompat;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Sonu on 01/06/17.
 */
public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();
    public static final int RIDE_BOOKED_NOTIFICATION_ID=1;

    public static final int FOREGROUND_LOCATION_NOTIFICATION_ID = 3;
    public static final int FOREGROUND_RECORD_NOTIFICATION_ID = 4;
    public static final String UPDATES_CHANNEL_ID="updates_channel";

    private Context mContext;

    public NotificationUtils() {
    }

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotificationMessage(String title, String message, long timeStamp, Intent intent, int notificationId, String channelId, boolean isAutoCancel) {
        showNotificationMessage(title, message, timeStamp, intent, null, notificationId,channelId,isAutoCancel);
    }

    public void showNotificationMessage(final String title, final String message, final long timeStamp, Intent intent, String imageUrl, int notificationId, String channelId, boolean isAutoCancel) {
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;
        int icon;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            icon = R.mipmap.ic_launcher;
        else
            icon = R.mipmap.ic_launcher;


        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext, channelId);

        final Uri alarmSound = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (!TextUtils.isEmpty(imageUrl)) {

            if (imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                Bitmap bitmap = getBitmapFromURL(imageUrl);

                if (bitmap != null) {
                    showBigNotification(bitmap, mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound, notificationId,isAutoCancel);
                } else {
                    showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound, notificationId,isAutoCancel);
                }
            }
        } else {
            showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound, notificationId,isAutoCancel);

        }
    }


    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message, long timeStamp, PendingIntent resultPendingIntent, Uri alarmSound, int notificationId, boolean isAutoCancel) {

        //Multiline notification style
       NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(message);

        if (FCMUtils.appendNotificationMessages) {
            // store the notification in shared pref first
            //YOSCV_Application.getInstance().getPrefManager(Constants.Login_SharedPreference).addNotification(message);

            // get the notifications from shared preferences
            //String oldNotification = YOSCV_Application.getInstance().getPrefManager(Constants.Login_SharedPreference).getNotifications();

          /*  List<String> messages = Arrays.asList(oldNotification.split("\\|"));

            for (int i = messages.size() - 1; i >= 0; i--) {
                inboxStyle.addLine(messages.get(i));
            }*/
        } else {
            //inboxStyle.addLine(message);
        }


        Notification notification;
        notification = mBuilder
                .setSmallIcon(icon)
                .setTicker(title)
                .setAutoCancel(isAutoCancel)
                .setContentTitle(title)
                .setContentText(message)

                 // Show controls on lock screen even when user hides sensitive content.
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)

                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setStyle(bigTextStyle)
                .setWhen(timeStamp)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager!=null)
        notificationManager.notify(notificationId, notification);
    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, int icon, String title, String message, long timeStamp, PendingIntent resultPendingIntent, Uri alarmSound, int notificationId, boolean isAutoCancel) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);
        Notification notification;
        notification = mBuilder
                .setTicker(title)
                .setAutoCancel(isAutoCancel)
                .setContentTitle(title)
                .setContentText(message)

                // Show controls on lock screen even when user hides sensitive content.
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)

                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setStyle(bigPictureStyle)
                .setWhen(timeStamp)
                .setSmallIcon(icon)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager!=null)
        notificationManager.notify(notificationId, notification);
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications() {
        NotificationManager notificationManager = (NotificationManager) ArogyaApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager!=null)
        notificationManager.cancelAll();
    }
    // Clears notification tray messages
    public static void clearSingleNotification(int notificationId) {
        NotificationManager notificationManager = (NotificationManager) ArogyaApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager!=null)
        notificationManager.cancel(notificationId);
    }
    public static Notification createForegroundNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, UPDATES_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(Notification.CATEGORY_SERVICE);
        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));
        return builder.build();
    }
}
