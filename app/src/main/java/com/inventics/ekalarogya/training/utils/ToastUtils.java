package com.inventics.ekalarogya.training.utils;

import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.StringRes;

import com.inventics.ekalarogya.training.app_base.ArogyaApplication;


public class ToastUtils {

    public static void shortToast(@StringRes int text) {
        shortToast(ArogyaApplication.getInstance().getString(text));
    }

    public static void shortToast(String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void longToast(@StringRes int text) {
        longToast(ArogyaApplication.getInstance().getString(text));
    }

    public static void longToast(String text) {
        show(text, Toast.LENGTH_LONG);
    }

    private static Toast makeToast(String text, @ToastLength int length) {
        return Toast.makeText(ArogyaApplication.getInstance(), text, length);
    }

    private static void show(String text, @ToastLength int length) {
        makeToast(text, length).show();
    }

    @IntDef({Toast.LENGTH_LONG, Toast.LENGTH_SHORT})
    private @interface ToastLength {

    }


}