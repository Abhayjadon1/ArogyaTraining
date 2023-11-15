package com.ispl.ekalarogya.training.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by sonu on 26/11/17.
 */

public class IntentUtils {

    public static void callNumber(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    public static void openPlayStore(Context context, String packageName, boolean finish) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
        if (finish)
            ((AppCompatActivity) context).finish();
    }

    public static void shareText(Context context, String shareData) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareData);
        sendIntent.setType("text/plain");
        try {
            context.startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {
            ToastUtils.shortToast("No app found to share.");
        }
    }

    public static void openBrowser(Context context, String link) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(link));
        try {
            context.startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {
            ToastUtils.shortToast("No app found to open this link.");
        }
    }
}
